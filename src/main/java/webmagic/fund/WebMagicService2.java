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
import util.MapUtil;
import util.TxtUtil;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-07 21:44
 */
public class WebMagicService2 {
    static String path = "C:\\Users\\xiala\\Desktop\\123\\1233122.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\123\\100000.txt";
    static List<String> list = TxtUtil.readTxt(path);
//    static StringBuilder all = new StringBuilder();

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list.size(); i++) {//list.size()
            String url = list.get(i);//.split(",")[1];
            System.out.println((i + 1) + "\t" + url);
//            url="http://quotes.money.163.com/fund/jzzs_000001.html?start=2020-01-01&end=2020-03-31";
            String code = url.split("code=")[1].split("&page=")[0];
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);
            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            String parentInfo = TxtUtil.readTxtStr("C:\\Users\\xiala\\Desktop\\123\\code\\" + code + ".txt");
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
//            all.append(sb.toString());
            TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\code\\" + code + ".txt", parentInfo+sb.toString());
            System.out.println(sb.toString());
        }

    }

    @Test
    public void getIngo123() {
        String path = "C:\\Users\\xiala\\Desktop\\123\\pool2.txt";
        List<String> list = TxtUtil.readTxt(path);
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
            try {
                new File("C:\\Users\\xiala\\Desktop\\123\\code\\" + list.get(i) + ".txt").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getInfo(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\code";
        File[] fileArray = new File(path).listFiles();
        HashMap<String,Integer> hashMap = new HashMap<>();
        for (int i = 0; i <fileArray.length ; i++) {
//            System.out.println(fileArray[i].getName());
            List<String> list = TxtUtil.readTxt(fileArray[i]);
            boolean flag = false;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <list.size() ; j++) {
                String str = list.get(j);
                String[] array = getArray(str);//str.split("\t");

//                str = str.replaceAll(" ","\t");
                if (array.length<7){
                    flag = true;
                    if(j-1>=0){
                        System.out.println(str);
                        String[] array2 = getArray(list.get(j-1));//str.split("\t");
                        if(!array[3].contains("%")){
                            System.out.println(array2[3]+"\t"+array[3]);
                            if(str.contains("封闭期 封闭期")){
                                if(str.split(" ").length==5){
                                    sb.append(str.replace("封闭期 封闭期","0.00 封闭期 封闭期"+"\n"));
                                }else{
                                    sb.append(str.replace("封闭期 封闭期",str.split(" ")[1]+" 0.00 封闭期 封闭期"+"\n"));
                                }

                            }else{
                                double less = (new Double(array2[3])-new Double(array[3]))/new Double(array[3])*100;
                                DecimalFormat df = new DecimalFormat("#.00");
                                String str111 = df.format(less);
                                if(str111.startsWith(".")){
                                    sb.append("0"+str111+"\n");
                                }else if(str111.startsWith("-.")){
                                    sb.append(str111.replace("-.","-0."+"\n"));
                                }else{
//                            sb.append(array2[3]+"\t"+array[3]);
                                    sb.append(str111+"\n");
                                }
                            }

                        }else{

                            sb.append(str.replace(array[2],array[2]+" "+array[2]+"\n"));
                        }
//                     

//                        sb.append(less);
                    }else{
//                        sb.append(j+">>>>"+str);
                        if(str.contains("封闭期 封闭期")){
                            if(str.split(" ").length==5){
                                sb.append(str.replace("封闭期 封闭期","0.00 封闭期 封闭期"+"\n"));
                            }else{
                                sb.append(str.replace("封闭期 封闭期",str.split(" ")[1]+" 0.00 封闭期 封闭期"+"\n"));
                            }

                        }else if(str.contains("开放申购 封闭期")){
                            sb.append(str.replace("开放申购 封闭期","0.00 开放申购 封闭期"+"\n"));
                        }
                    }

                }else {
                    sb.append(str+"\n");
                }


            }
            if (flag){
                System.out.println(sb.toString());
                TxtUtil.writeTxt(fileArray[i],sb.toString());
            }
        }
        MapUtil.sortDesc(hashMap);
    }

    public static String[] getArray(String str) {
        str = str.replaceAll(" ","\t");
        str = str.replaceAll("%","");
        return str.split("\t");
    }

    @Test
    public void getInfo111(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\code";
        File[] fileArray = new File(path).listFiles();
//        HashSet<String> hashMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <fileArray.length ; i++) {
            List<String> list = TxtUtil.readTxt(fileArray[i]);
            boolean flag = false;
            for (int j = 0; j <list.size() ; j++) {
                String str = list.get(j);
                String[] array = getArray(str);
                sb.append("insert into t_fund_day(fund_code,fund_date,fund_value,fund_value_all,fund_percent)values('"+array[0]+"',STR_TO_DATE('"+array[1]+"', '%Y-%m-%d'),"+array[2]+","+array[3]+","+array[4]+");\n");
//                if (array.length>7){
////                    flag= true;
////                    break;
//                    System.out.println(fileArray[i].getName()+"\t"+str);
//                }

//                System.out.println(array.length);
            }
//            if (flag){
//                fileArray[i].delete();
//            }
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\1000001.sql",sb.toString());
    }
}
