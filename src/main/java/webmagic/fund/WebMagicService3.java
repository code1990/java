package webmagic.fund;

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

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-07 21:44
 */
public class WebMagicService3 {
    static String path = "C:\\Users\\xiala\\Desktop\\123\\pool2url.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\123\\pool2url100.txt";
    static List<String> list = TxtUtil.readTxt(path);
    static StringBuilder all = new StringBuilder();

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list.size(); i++) {//list.size()
            String url = list.get(i);//.split(",")[1];
            System.out.println((i + 1) + "\t" + url);
//            url="http://quotes.money.163.com/fund/jzzs_000001.html?start=2020-01-01&end=2020-03-31";
            String code = url.split(".com/")[1].split(".html")[0];
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            Document document = Jsoup.parse(contents);
            Elements elements = document.getElementsByClass("infoOfFund").get(0).getElementsByTag("td");
//            String parentInfo = TxtUtil.readTxtStr("C:\\Users\\xiala\\Desktop\\123\\code\\" + code + ".txt");
            StringBuilder sb = new StringBuilder();
//            for (int j = elements.size() -1; j >= 1; j--) {//elements.size() -1
            for (int j = 1; j < elements.size()-1; j++) {
                String str = elements.get(j).text();
                if (str.contains("基金规模") || str.contains("基金经理")|| str.contains("成 立 日")|| str.contains("管 理 人")) {
                    System.out.println(str);
                    str = str.split("：")[1];
                    if(str.contains("（2020-06-30）")){
                        str = str.replace("（2020-06-30）","");
                    }
                    sb.append( str + "\t");
                }
            }
            all.append(code + "\t" +sb.toString()+"\n");
            TxtUtil.writeTxt(target, all.toString());
//            System.out.println(sb.toString());
        }

    }

    @Test
    public void getIngo123() {
        String path = "C:\\Users\\xiala\\Desktop\\123\\pool2url100.txt";
        List<String> list = TxtUtil.readTxt(path);
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
//            str = str.replace("亿元","");
//            str = str.replace("--","0");
            String[] array = str.split("\t");
            System.out.println("update t_fund_pool2 set fund_money="+array[1]+",fund_manager='"+array[2]+"',fund_cdate=STR_TO_DATE('"+array[3]+"', '%Y-%m-%d'),fund_company='"+array[4]+"' where fund_code='"+array[0]+"';");
//            if(array.length!=5){
//                System.out.println(array[0]+"\t"+array[1]+"\t"+array[3]+"\t"+array[4]+"\t"+array[5]);
//            }else {
//                System.out.println(str);
//            }
//            System.out.println(array.length);
//            System.out.println();
//            System.out.println("http://fund.eastmoney.com/"+list.get(i)+".html");
//            try {
//                new File("C:\\Users\\xiala\\Desktop\\123\\code\\" + list.get(i) + ".txt").createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Test
    public void getInfo(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\code";
        File[] fileArray = new File(path).listFiles();
        for (int i = 0; i <fileArray.length ; i++) {
//            System.out.println(fileArray[i].getName());
            List<String> list = TxtUtil.readTxt(fileArray[i]);
            for (int j = 0; j <list.size() ; j++) {
                String str = list.get(j);
//                str = str.replaceAll(" ","\t");
//                if (str.split("\t").length!=7){
//                    System.out.println(str);
//                    continue;
//                }
                String[] array = getArray(str);//str.split("\t");

                if(array.length==6){
                    if(array[3].contains("%")){
                        System.out.println(str);
                    }else{
//                        System.out.println(str);
                    }

                }

            }
        }
    }

    public static String[] getArray(String str) {
        str = str.replaceAll(" ","\t");
        return str.split("\t");
    }
}
