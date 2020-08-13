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
import us.codecraft.webmagic.Spider;
import util.TxtUtil;
import util.UserAgentUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 911
 * @date 2020-08-07 21:44
 */
public class WebMagicService {
    static String path = "C:\\Users\\xiala\\Desktop\\123\\123312.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\123\\1000.txt";
    static List<String> list = TxtUtil.readTxt(path);
    static StringBuilder all = new StringBuilder();
    public static void main(String[] args)  throws Exception{
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list.size(); i++) {//list.size()
            String url = list.get(i);//.split(",")[1];
            System.out.println((i+1)+"\t"+url);
//            url="http://quotes.money.163.com/fund/jzzs_000001.html?start=2020-01-01&end=2020-03-31";
            String code = url.split("jzzs_")[1].split(".html")[0];
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(),"gbk");
            Document document = Jsoup.parse(contents);
            Elements elements = document.select("div#fn_fund_value_trend tr");

            StringBuilder sb = new StringBuilder();
            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                System.out.println(str);
                sb.append(code+"\t"+str+"\n");
            }
            all.append(sb.toString()+"\n");
            TxtUtil.writeTxt(target,all.toString());
            System.out.println(sb.toString());
        }

    }

    @Test
    public void getInfo() throws Exception{
        String path ="C:\\Users\\xiala\\Desktop\\123\\pool2.txt";
        List<String> list = TxtUtil.readTxt(path);
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i <list.size() ; i++) {//list.size()
            String url =  "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=000083&page=1&per=65535&sdate=2020-07-08&edate=2020-08-08".replace("000083",list.get(i));
            System.out.println((i+1)+"\t"+url);
            String code = url.split("&code=")[1].split("&")[0];
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(),"gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
//            String parentInfo = TxtUtil.readTxtStr("C:\\Users\\xiala\\Desktop\\123\\code\\" + code + ".txt");
            StringBuilder sb = new StringBuilder();
            for (int j = elements.size() -1; j >= 1; j--) {//elements.size() -1
//            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                System.out.println(str);
                sb.append(code + "\t" + str + "\n");
            }
            all.append(sb.toString());
            TxtUtil.writeTxt(target,all.toString());
            System.out.println(sb.toString());
        }
    }

    @Test
    public void getInfo123(){
        List<String> list = TxtUtil.readTxt(target);
        Map<String,String> map = new HashMap<>();

        for (int i = 0; i <list.size() ; i++) {
            String[] array = list.get(i).split("\t");
            String key = array[0];
            String value = array[1];
            if(map.get(array[0])==null){
                map.put(key,value+"\t");
            }else{

                map.put(key,new StringBuilder(map.get(key)).append(value+"\t").toString());
            }

        }
        for (Map.Entry<String,String> entry : map.entrySet()) {
            StringBuilder sb = new StringBuilder();
            String[] array =entry.getValue().split("\t");
            for (int i = 0; i <array.length ; i++) {
//                System.out.println(array[i].split(" ")[3].replace("%",""));
                String str = array[i].split(" ")[3];
                str = str.replace("%","");
                str = str.replace("封闭期","0.00");
                str = str.replace("申购","0.00");
                str = str.replace("开放","");
                str = str.replace("限制大额","");
                str = str.replace("暂停","");
                sb.append(str+"\t");
            }
            if(array.length==20){
                System.out.println("#"+entry.getKey() + "\t" + sb.toString());
            }
//            System.out.println(array.length);

        }
    }
}
