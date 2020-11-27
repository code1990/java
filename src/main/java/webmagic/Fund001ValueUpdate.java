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
import util.UserAgentUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//*
// * @author 911
// * @date 2020-08-19 09:33
// * 更新基金净值
// 当前方法适合做插入计算


public class Fund001ValueUpdate {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentStr = sdf.format(new Date());
    String sql1 = "select max(fund_date) as fund_date from t_fund_day;";
    String startDate = JDBCUtil.getList(sql1).get(0);

    @Test
    public void getInfo0002() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(sql);

        for (int k = 0; k < codeList.size(); k++) {
            String code = codeList.get(k);
            System.out.println(k + ">>>>>>" + code);

            String endDate = currentStr;
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code=" + code + "&page=1&per=65535&sdate=" + startDate + "&edate=" + endDate;
            System.out.println(url);
            List<String> htmlList = new ArrayList<>();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            for (int j = elements.size() - 1; j >= 1; j--) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                System.out.println(str);
                htmlList.add(str);
            }
            int start =0;
            if(htmlList.size()>=2){
                start=1;
            }
            List<String> resultList = new ArrayList<>();
            for (int i = start; i < htmlList.size(); i++) {
                String str = htmlList.get(i);
                String[] array = str.split(" ");
                String percent = "0.0";
                if (array.length == 5 && array[2].contains("%")) {
                    percent = array[2].replace("%", "");
                } else if (array.length == 6 && array[3].contains("%")) {
                    percent = array[3].replace("%", "");
                }
                sql = "insert into t_fund_day(fund_code,fund_date,fund_value,fund_percent)values('" + code + "',STR_TO_DATE('" + array[0] + "', '%Y-%m-%d')," + array[1] + "," + percent + ");";
                System.out.println(sql);
                resultList.add(sql);
            }
//            System.out.println(Arrays.toString(resultList.toArray()));
//            break;
            JDBCUtil.insertList(resultList);
        }
    }
}
