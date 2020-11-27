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
import util.JDBCUtil;
import util.TxtUtil;
import util.UserAgentUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-19 09:33
 * 获取基金净值
 *
 * 获取基金的旧数据
 * 基金池增加
 * 交易时间优化
 */
public class Fund001Value {
    @Test
    public void getInfo0002() throws Exception {
        List<String> dateList = getTradeDate();

        HttpClient httpClient = HttpClients.createDefault();
        String sql = "select fund_code from t_fund_pool8 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(sql);
        for (int k = 0; k < codeList.size(); k++) {
            List<String> urlList = new ArrayList<>();
            String code = codeList.get(k);
            System.out.println(k + ">>>>>>" + code);
            for (int i = 1; i < dateList.size(); i++) {
                String startDate = dateList.get(i-1);
                String endDate = dateList.get(i);
                String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                        ".aspx?type=lsjz&code=" + code + "&page=1&per=20&sdate=" + startDate + "&edate=" + endDate;
                System.out.println(url);
                urlList.add(url);
            }
            List<String> htmlList = new ArrayList<>();
            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
                HttpResponse response = httpClient.execute(httpGet);
                String contents = EntityUtils.toString(response.getEntity(), "gbk");
                Document document = Jsoup.parse(contents);

                Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
                int index =1;
                if(i==0){
                    index=0;
                }
                for (int j = elements.size() - 1-index; j >= 1; j--) {
                    String str = elements.get(j).text();
                    if (str.contains("暂无数据")) {
                        continue;
                    }
                    htmlList.add(str);
                }
            }
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < htmlList.size(); i++) {
                String str = htmlList.get(i);
                String[] array = str.split(" ");
                String percent = "0.0";
                if (array.length == 5 && array[2].contains("%")) {
                    percent = array[2].replace("%", "");
                } else if (array.length == 6 && array[3].contains("%")) {
                    percent = array[3].replace("%", "");
                }
                sql = "insert into t_fund_day8(fund_code,fund_date,fund_value,fund_percent)values('" + code + "',STR_TO_DATE('" + array[0] + "', '%Y-%m-%d')," + array[1] + "," + percent + ");";
                System.out.println(sql);
                resultList.add(sql);

            }
            JDBCUtil.insertList(resultList);
        }

    }

    public static void main(String[] args) {
//        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
//        Calendar calendar = Calendar.getInstance();
////        System.out.println("今天是"+weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1]);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 1; i < 365 * 2; i++) {
//            Date date = new Date();
////        Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.DAY_OF_MONTH, -i);
//            date = calendar.getTime();
//            int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//
//            if (0 == index || 6 == index) {
//                continue;
//            }
//            if(sdf.format(date).contains("2018")){
//                break;
//            }
//            System.out.println(sdf.format(date) + "\t" + (weekDays[index]));
//            sb.append(sdf.format(date)+"\n");
//        }
//        TxtUtil.writeTxt(new File("date.txt"),sb.toString());
        List<String> list = TxtUtil.readTxt(new File("date.txt"));
        Collections.reverse(list);
        List<String> infoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
            if(i==0 || (i+1)%20==0||list.size()-1==i){
//                System.out.println(list.get(i));
                infoList.add(list.get(i));
            }
        }
        for (int i = 1; i <infoList.size() ; i++) {
            System.out.println(infoList.get(i-1)+"\t"+infoList.get(i));
        }
    }

    public static List<String> getTradeDate(){
        List<String> list = TxtUtil.readTxt(new File("date.txt"));
        Collections.reverse(list);
        List<String> infoList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(i==0 || (i+1)%20==0||list.size()-1==i){
                infoList.add(list.get(i));
            }
        }

        return infoList;
    }


//    @Test
    public void getInfo111(){
        List<String> list = getTradeDate();
//        Collections.reverse(list);
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }
    }
}
