package webmagic.fund;

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
import util.MapSortUtil;
import util.TxtUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 911
 * @date 2020-08-07 21:44
 */
public class WebMagicService6 {
    static String path = "C:\\Users\\admin\\Desktop\\1233.txt";
    static String target = "C:\\Users\\admin\\Desktop\\fund123.txt";
    static List<String> list = TxtUtil.readTxt(path);
    static StringBuilder all = new StringBuilder();

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 3342; i <1 ; i++) {//list.size()
            String code =list.get(i);//"000083"
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code="+code+"&page=1&per=65535&sdate=2020-07-18&edate=2020-08-18";
            System.out.println((i + 1) + "\t" + url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            Document document = Jsoup.parse(contents);
            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            StringBuilder sb = new StringBuilder();
//            for (int j = elements.size() -1; j >= 1; j--) {//elements.size() -1
            int count1 =0;
            int count2 =0;
            double v1 =0.0;
            double v2 =0.0;
            boolean flag = false;
            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                if (str.contains("封闭期")||str.split(" ").length<6){
                    continue;
                }
                System.out.println();
                double v = new Double(str.split(" ")[3].replace("%",""));
                if(j==1 && v>0){
                    flag=true;
                }
                if(flag){
                    if(v>0){
                        v1+=v;
                        count1+=1;
                    }else{
                        flag=false;
                    }
                }
                if(v>0){
                    count2+=1;
                }
                v2+=v;
            }
            sb.append(code+"\t"+v1+"\t"+count1+"\t"+v2+"\t"+count2+"\n");
            System.out.println(i+">>>"+sb.toString());
            all.append(sb.toString());
//            all.append(code + "\t" +sb.toString()+"\n");
            TxtUtil.writeTxt(target, all.toString());
//            System.out.println(code + "\t" +sb.toString()+"\n");
        }

    }





}
