package word;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.TxtUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 911
 * @date 2020-08-07 21:44
 */
public class Work51 {
    static String path = "C:\\Users\\xiala\\Desktop\\work_request.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\100000.txt";
    static List<String> list = TxtUtil.readTxt(path);

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list.size(); i++) {//list.size()
            String url = list.get(i);//.split(",")[1];
            if(!url.startsWith("https://jobs.51job.com/guangzhou-")){
                continue;
            }
//            System.out.println((i + 1) + "\t" + url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);
            Elements elements = document.getElementsByClass("bmsg job_msg inbox").get(0).getElementsByTag("p");
            int start = 0;
            for (int j = 1; j < elements.size() - 1; j++) {
                String text = elements.get(j).text();
                if (text.contains("：")) {
                    start = j;
                }
                if (j > start && start != 0) {
                    TxtUtil.appendFile(target, text);
                }
                System.out.println(text);

            }
        }

    }

    @Test
    public void getInfo(){
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\work.txt");
        Set<String> set = new HashSet<>();
        for (int i = 0; i <list.size() ; i++) {

            String str = list.get(i);
            if(str.startsWith("职能类别")||str.startsWith("关键字")){
                continue;
            }
//            if(str.length()<100){
//                continue;
//            }
//            if (!set.add(str)) {
//                continue;
//            }
//            System.out.println(list.get(i).replaceAll("。","\n").replaceAll("；","\n"));
            String regex1 = ".*[a-zA-z].*";
            boolean result3 = str.matches(regex1);
            if(result3){
                System.out.println(str);
            }
        }
    }




}
