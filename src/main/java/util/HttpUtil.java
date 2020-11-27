package util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author 911
 * @date 2020-11-01 12:32
 */
public class HttpUtil {

    private static HttpClient httpClient = HttpClients.createDefault();

    public static Document getHtml(String url) {
        Document document = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            document = Jsoup.parse(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document getHtml(String url,String charset) {
        Document document = null;
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), charset);
            document = Jsoup.parse(contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

}
