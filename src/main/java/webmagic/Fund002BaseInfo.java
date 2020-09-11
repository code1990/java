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
import util.DateUtil;
import util.JDBCUtil;
import util.UserAgentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-19 11:15
 */
public class Fund002BaseInfo {

    @Test
    public void getInfo123() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(sql);
        List<String> resultList = new ArrayList<>();
        for (int k = 0; k < codeList.size(); k++) {//codeList.size()
            String code = codeList.get(k);
            System.out.println(k + ">>>>>>" + code);
            String url = "http://fund.eastmoney.com/" + code + ".html";
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "UTF-8");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("infoOfFund").get(0).getElementsByTag("td");
            String money = elements.get(1).text().split("：")[1].split("亿元")[0];
            if(money.contains("--")){
                money="0.0";
            }
            String manager = elements.get(2).text().split("：")[1].replace("等","");
            String start = elements.get(3).text().split("：")[1];
            String company = elements.get(4).text().split("：")[1];

            String sql2 = "update t_fund_pool6 set fund_money=" + money + ",fund_manager='" + manager + "',fund_cdate=STR_TO_DATE('" + start + "', '%Y-%m-%d'),fund_company='" + company + "' where fund_code='" + code + "';";
            System.out.println(sql2);
            resultList.add(sql2);

        }
        JDBCUtil.insertList(resultList);

    }
}