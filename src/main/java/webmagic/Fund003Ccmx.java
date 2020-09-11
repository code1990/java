package webmagic;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.JDBCUtil;
import util.MapUtil;
import util.MathUtil;
import util.TxtUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 911
 * @date 2020-08-19 12:19
 */
public class Fund003Ccmx {

    @Test
    public void getInfo1231() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(sql);
        List<String> resultList = new ArrayList<>();
        for (int k = 0; k < codeList.size(); k++) {//
            String code = codeList.get(k);
            String url = "http://fund.eastmoney.com/" + code + ".html";
            System.out.println(k + ">>>>>>>" + code);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            Document document = Jsoup.parse(contents);
            Elements elements = document.getElementsByClass("ui-table-hover").get(0).getElementsByTag("tr");
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                Element elementTd = elements.get(j).getElementsByTag("td").get(0);
                if (str.contains("暂无数据") || !elementTd.toString().contains("href")) {
                    continue;
                }
                String href =elementTd.getElementsByTag("a").get(0).attr("href");
                String comInfo = href.replace("http://quote.eastmoney.com/","");
                comInfo = comInfo.replace(".html","");
                comInfo = comInfo.replace("sh","");
                comInfo = comInfo.replace("sz","");
                if(comInfo.contains("hk")){
                    comInfo = comInfo.replace("hk/","");
                    comInfo = comInfo+".HK";
                }else if(comInfo.contains("us")){
                    comInfo = comInfo.replace("us/","");
                }
                sb.append("'"+comInfo + "',");
            }
            if (sb.toString().length()==0){
                continue;
            }
            String sql2 ="select hy_name,gn_name from t_com_hy_gn_mx where com_code in ("+sb.toString().substring(0,sb.length()-1)+");";
            List<String[]> tmpList = JDBCUtil.getResultList(sql2);
            Map<String,Integer> map1 = new HashMap<>();
            Map<String,Integer> map2 = new HashMap<>();
            for (int i = 0; i < tmpList.size(); i++) {
                String key = tmpList.get(i)[0];
                if(map1.get(key)==null){
                    map1.put(key,1);
                }else{
                    map1.put(key,1+map1.get(key));
                }
            }
            for (int i = 0; i < tmpList.size(); i++) {
                String[] array2 = tmpList.get(i)[1].split(",");
                for (int j = 0; j <array2.length ; j++) {
                    String key = array2[j];
                    if(map2.get(key)==null){
                        map2.put(key,1);
                    }else{
                        map2.put(key,1+map2.get(key));
                    }
                }
            }
            String sql3 ="update t_fund_pool6 set hy_name='" + MapUtil.getBig(map1) + "',gn_name='" + MapUtil.getBig(map2) + "'  where fund_code='" + code + "';";
            System.out.println(sql3);
            resultList.add(sql3);
        }
        JDBCUtil.insertList(resultList);
    }
}