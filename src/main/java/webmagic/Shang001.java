package webmagic;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-27 11:26
 */
public class Shang001 {

    @Test
    public void getInfo() throws Exception {
        String path = "C:\\Users\\xiala\\Desktop\\1234.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            str = str.replaceAll(",", "");
            String[] array = str.split("\t");
            str = MathUtil.getRound4(new Double(array[6])/1000000);
            String dateStr = DateUtil.convertDate2(array[0]);
            String percent = array[5];
            if(percent.contains("None")){
                percent="0";
            }
            System.out.println(list.get(i));
            //日期	收盘价	最高价	最低价	开盘价	涨跌幅	成交量
            String sql = "insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('" + dateStr + "', '%Y-%m-%d')," + array[1] + "," + array[4] + "," + array[2] + "," + array[3] + "," + str + "," + percent + ",'sz_001');";
            list2.add(sql);
            System.out.println(sql);
//            System.out.println(array[0]);
//            String str = list.get(i).replace("年")
//            System.out.println(list.get(i));
//            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09");
//            System.out.println(now);
        }
        JDBCUtil.insertList(list2);
    }

    @Test
    public void updateS100() throws Exception {
        String url = "https://cn.investing.com/indices/shanghai-composite";
        HttpClient httpClient = HttpClients.createDefault();
        List<String> resultList = new ArrayList<>();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
        HttpResponse response = httpClient.execute(httpGet);
        String contents = EntityUtils.toString(response.getEntity(), "gbk");
        Document document = Jsoup.parse(contents);

        Elements elements = document.getElementsByClass("top bold inlineblock").get(0).getElementsByTag("span");
        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String close = elements.get(0).text().replace(",", "");
        String percent = elements.get(3).text().replace("%", "").replace("+", "");
        elements = document.getElementsByClass("bottomText").get(0).getElementsByTag("span");
        String[] m = elements.get(1).text().split(",");
        String money = m[0] + "." + m[1];
        String open = elements.get(4).text().replace(",", "");
        String[] m1 = elements.get(6).text().split(" - ");
        String low = m1[0].replace(",", "");
        String high = m1[1].replace(",", "");
        String sql = "insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent)values(STR_TO_DATE('" + dateStr + "', '%Y-%m-%d')," + close + "," + open + "," + high + "," + low + "," + money + "," + percent + ");";
        System.out.println(sql);
        resultList.add(sql);
        JDBCUtil.insertList(resultList);
    }

    @Test
    public void getBullInfo(){
        int step=20;
        //查询所有的未更新的时间
        String sql2 = "select distinct s_date from t_s_001 where s_type='sz_001'  and fund_bull_3 =0 order by s_date desc; ";
//        String sql2 = "select distinct s_date from t_s_001 where fund_code ='"+code+"' and fund_bull_3 =0 and s_date >str_to_date('2020-02-06', '%Y-%m-%d') order by s_date desc; ";
        List<String> dateStrList = JDBCUtil.getList(sql2);
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < dateStrList.size(); i++) {
            String sDate=dateStrList.get(i);
            String sql = "select s_close from t_s_001 where s_type='sz_001' and s_date <str_to_date('"+sDate+"', '%Y-%m-%d')  order by s_date desc limit "+step+";";
            System.out.println(sql);
            List<String> resultList2 = JDBCUtil.getList(sql);
            if(resultList2.size()!=step){
                break;
            }
            double[] x = new double[step];
            for (int j = 0; j < resultList2.size(); j++) {
                x[j] = new Double(resultList2.get(j));

            }
            double bull2 = MathUtil.getAvg(x);
            double bull1 = MathUtil.getAvg(x) + 3 * MathUtil.getStandardeviation(x);
            double bull3 = MathUtil.getAvg(x) - 3 * MathUtil.getStandardeviation(x);
            StringBuilder sb100 = new StringBuilder();
            sb100.append("update t_s_001 set fund_bull_1=" + MathUtil.getRound4(bull1));
            sb100.append(",fund_bull_2=" + MathUtil.getRound4(bull2));
            sb100.append(",fund_bull_3=" + MathUtil.getRound4(bull3));
            sb100.append(" where s_type='sz_001' and s_date=str_to_date('"+sDate+"', '%Y-%m-%d');");
            resultList.add(sb100.toString());
            System.out.println(sb100.toString());
        }

        JDBCUtil.update(resultList);
    }
}
