import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.UserAgentUtil;

import java.net.URLEncoder;
import java.util.*;

/**
 * @author 911
 * @date 2020-10-30 21:10
 */
public class DaliPanSearch {

    @Test
    public void getInfo111() throws Exception{
        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();
        String keyInfo ="缠论";
        int pageNum =53;
        String key = URLEncoder.encode(keyInfo,"utf-8");
        for (int i = 1; i <=pageNum ; i++) {
            String url ="https://www.dalipan.com/search?keyword="+key+"&page="+i;
            HttpClient httpClient = HttpClients.createDefault();
//        String url = "http://fund.eastmoney.com/js/fundcode_search.js";

            System.out.println(url);
            List<String> htmlList = new ArrayList<>();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf8");
            Document document = Jsoup.parse(contents);
            Elements list2 = document.getElementsByClass("resource-item-wrap valid");

            for (int j = 0; j <list2.size() ; j++) {
                String info = list2.get(j).getElementsByTag("a").text();
//                System.out.println(info);
                set.add(info);
            }
        }
        System.out.println("====================");
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


    }
}
