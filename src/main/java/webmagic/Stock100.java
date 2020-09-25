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
import java.util.*;

/**
 * @author 911
 * @date 2020-08-27 09:54
 */
public class Stock100 {

    @Test
    public void getInfo() throws Exception {
        String path = "C:\\Users\\xiala\\Desktop\\1234.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            str = str.replaceAll(",", "");
            String[] array = str.split("\t");
            if (array[5].contains("M")) {
                str = array[5].replace("M", "");
            }
            if (array[5].contains("B")) {
                str = MathUtil.getRound4(new Double(array[5].replace("B", "")) / 10);
            }
            if (array[5].contains("K")) {
                str = MathUtil.getRound4(new Double(array[5].replace("K", "")) / 100);
            }
            if (array[5].contains("-")) {
                str = "0";
            }
            String dateStr = DateUtil.convertDate(array[0]);
            String percent = array[6].replace("%", "");
//            System.out.println(list.get(i));
            String sql = "insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('" + dateStr + "', '%Y-%m-%d')," + array[1] + "," + array[2] + "," + array[3] + "," + array[4] + "," + str + "," + percent + ",'" + array[7] + "');";
            list2.add(sql);
            System.out.println(sql);
//            System.out.println(array[0]);
//            String str = list.get(i).replace("年")
//            System.out.println(list.get(i));
//            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09");
//            System.out.println(now);
        }
//        JDBCUtil.insertList(list2);
    }

    @Test
    public void updateS100() throws Exception {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("https://cn.investing.com/indices/shanghai-composite", "sh_001");
        map.put("https://cn.investing.com/indices/szse-component", "sz_001");
        map.put("https://cn.investing.com/indices/hang-sen-40", "hk_001");
        map.put("https://cn.investing.com/indices/nq-100", "us_001");
        List<String> resultList = new ArrayList<>();
        for (Map.Entry<String, String> clEntry : map.entrySet()) {
            String url = clEntry.getKey();
            String type = clEntry.getValue();
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("top bold inlineblock").get(0).getElementsByTag("span");
            String dateStr = DateUtil.getLastFriday();
            if ("".equals(DateUtil.getLastFriday())) {
                dateStr = DateUtil.getYesterday();
            }
            String close = elements.get(0).text().replace(",", "");
            String percent = elements.get(3).text().replace("%", "").replace("+", "");
            elements = document.getElementById("quotes_summary_secondary_data").getElementsByTag("span");
            String[] m = elements.get(1).text().split(",");
            String money = "0";
            if (m.length != 1) {
                money = m[0] + "." + m[1];
            }
            String open = elements.get(4).text().replace(",", "");
            String str = "";
            if (elements.size() == 9) {
                str = elements.get(6).text();
            } else if (elements.size() == 8) {
                percent = document.getElementsByClass("arial_20 greenFont  pid-179-pcp parentheses").text().replace("%", "").replace("+", "");
                if (null == percent || "".equals(percent)) {
                    percent = document.getElementsByClass("arial_20 redFont  pid-179-pcp parentheses").text().replace("%", "").replace("+", "");
                }
//                System.out.println(percent);
                open = elements.get(3).text().replace(",", "");
                money = "0";
                str = elements.get(5).text();
            }
            String[] m1 = str.split(" - ");
//            System.out.println(elements.size());
//            System.out.println(elements.get(6).text());
            String low = m1[0].replace(",", "");
            String high = m1[1].replace(",", "");
            String sql = "insert into t_s_001(s_date,s_close,s_open,s_high,s_low,s_m,s_percent,s_type)values(STR_TO_DATE('" + dateStr + "', '%Y-%m-%d')," + close + "," + open + "," + high + "," + low + "," + money + "," + percent + ",'" + type + "');";
            System.out.println(sql);
            resultList.add(sql);
        }
        JDBCUtil.insertList(resultList);
        getBull3();
        getBull2();
    }

    //
//    @Test
    public void getBull3() {
        int step = 20;
        List<String> typeList = JDBCUtil.getList("select distinct s_type from t_s_001  order by s_type;");
        for (int k = 0; k < typeList.size(); k++) {
            String type = typeList.get(k);
            //查询所有的未更新的时间
            String sql2 = "select distinct s_date from t_s_001 where s_type='" + type + "' and fund_bull_3 =0 and s_date >str_to_date('2020-02-06', '%Y-%m-%d') order by s_date desc; ";
//        String sql2 = "select distinct s_date from t_s_001 where fund_code ='"+code+"' and fund_bull_3 =0 and s_date >str_to_date('2020-02-06', '%Y-%m-%d') order by s_date desc; ";
            List<String> dateStrList = JDBCUtil.getList(sql2);
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < dateStrList.size(); i++) {
                String sDate = dateStrList.get(i);
                String sql = "select s_close from t_s_001 where s_date <str_to_date('" + sDate + "', '%Y-%m-%d') and s_type='" + type + "' order by s_date desc limit " + step + ";";
//                System.out.println(sql);
                List<String> resultList2 = JDBCUtil.getList(sql);
                if (resultList2.size() != step) {
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
                sb100.append(" where s_type='" + type + "' and s_date=str_to_date('" + sDate + "', '%Y-%m-%d');");
                resultList.add(sb100.toString());
                System.out.println(sb100.toString());
            }
            JDBCUtil.update(resultList);
        }


    }

//    @Test
    public void getBull2() {
        int step = 20;
        int size = 2;
        String type="us_001";
        String sql2 = "select distinct s_date from t_s_001 where s_type='" + type + "' and fund_bull_4 =0 and s_date >str_to_date('2020-02-06', '%Y-%m-%d') order by s_date desc; ";
        List<String> dateStrList = JDBCUtil.getList(sql2);
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < dateStrList.size(); i++) {
            String sDate = dateStrList.get(i);
            String sql = "select s_close from t_s_001 where s_date <str_to_date('" + sDate + "', '%Y-%m-%d') and s_type='" + type + "' order by s_date desc limit " + step + ";";
            List<String> resultList2 = JDBCUtil.getList(sql);
            if (resultList2.size() != step) {
                break;
            }
            double[] x = new double[step];
            for (int j = 0; j < resultList2.size(); j++) {
                x[j] = new Double(resultList2.get(j));
            }
            double avg = MathUtil.getAvg(x);
            double std = MathUtil.getStandardeviation(x);
            double bull4 = avg + size * std;
            double bull5 = avg - size * std;
            //布林线带宽=（上轨-下轨）/中线。
//            double bull6 = 2 * size * std / avg;
            StringBuilder sb100 = new StringBuilder();
            sb100.append("update t_s_001 set fund_bull_4=" + MathUtil.getRound4(bull4));
            sb100.append(",fund_bull_5=" + MathUtil.getRound4(bull5));
            sb100.append(" where s_type ='us_001' and s_date=str_to_date('" + sDate + "', '%Y-%m-%d');");
            System.out.println(sb100.toString());
            resultList.add(sb100.toString());
        }
        JDBCUtil.update(resultList);
    }
}
