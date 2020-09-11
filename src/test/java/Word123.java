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
import util.MathUtil;
import util.TxtUtil;
import util.UserAgentUtil;

import java.text.Collator;
import java.util.*;

/**
 * @author 911
 * @date 2020-09-09 09:21
 */
public class Word123 {

    //https://www.koolearn.com/dict/fenlei_2_0_38.html
    @Test
    public void getInfo123() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 15; i > 0; i--) {
//            String url= "https://www.koolearn.com/dict/tag_330_"+i+".html";
//            String url= "https://www.koolearn.com/dict/tag_383_"+i+".html";
//            String url= "https://www.koolearn.com/dict/tag_384_"+i+".html";
            String url = "https://www.koolearn.com/dict/tag_375_" + i + ".html";
//            System.out.println(url);
            List<String> htmlList = new ArrayList<>();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("word-box").get(0).getElementsByTag("a");
            for (int j = elements.size() - 1; j >= 1; j--) {
                System.out.println(elements.get(j).text());
            }
        }
    }
    @Test
    public void getInfo1236() throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 1; i <= 100; i++) {
//            String url= "https://www.koolearn.com/dict/tag_330_"+i+".html";
//            String url= "https://www.koolearn.com/dict/tag_383_"+i+".html";
//            String url= "https://www.koolearn.com/dict/tag_384_"+i+".html";
            String url = "https://www.koolearn.com/dict/fenlei_4_0_" + i + ".html";
//            System.out.println(url);
            List<String> htmlList = new ArrayList<>();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("word-wrap").get(0).getElementsByClass("word-title");
            for (int j = 0; j <elements.size(); j++) {
                System.out.println(elements.get(j).text().replace("更多","")+"\t"+"https://www.koolearn.com"+elements.get(j).getElementsByTag("a").get(0).attr("href"));
            }
        }
    }


    @Test
    public void getInfo1233() {
        // Collator 类是用来执行区分语言环境的 String 比较的，这里选择使用CHINA
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        String[] arrStrings = {"a", "b", "d", "c", "j"};
        // 使根据指定比较器产生的顺序对指定对象数组进行排序。
        Arrays.sort(arrStrings, comparator);
//        for (int i = 0; i < arrStrings.length; i++) {
//
//            System.out.println(arrStrings[i]);
//        }

        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\125.txt");
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            if(str.contains(" ")){
                list2.add(str);
            }else {
                list1.add(str);
            }
        }
        Object[] array1 = list1.toArray();
        Object[] array2 = list2.toArray();
        Arrays.sort(array1, comparator);
        Arrays.sort(array2, comparator);
        for (int i = 0; i <array1.length ; i++) {
            System.out.println(array1[i].toString());
        }
        for (int i = 0; i <array2.length ; i++) {
            System.out.println(array2[i].toString());
        }
    }

    @Test
    public void getInfo0000(){
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\124.txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
//            System.out.println(str);
            if(str.contains("...")){
                str = str.replace("..."," ");
            }
            if(str.contains(" ")){
                str = str.replaceAll(" ","+");
            }
            if(str.contains("'")){
                sb.append("https://www.hujiang.com/ciku/"+str.replaceAll("\\+","_").replace("'","%27")+"\n");
            }else{
                System.out.println("https://dict.cn/search?q="+str);
            }

        }
        System.out.println(sb.toString());
    }

    @Test
    public void getInfo01000() throws Exception {
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123456.txt");
        StringBuilder sb = new StringBuilder();
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 167; i <=3264; i++) {//167 3264
            String url = list.get(i);
            List<String> htmlList = new ArrayList<>();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);
            String str = document.getElementsByClass("keyword").get(0).text();
            String str2 = document.getElementsByClass("basic clearfix").get(0).getElementsByTag("li").get(0).text();

            System.out.println(str+"\t"+str2);
//            Elements elements = document.getElementsByClass("keyword").get(0).getElementsByClass("word-title");
//            for (int j = 0; j <elements.size(); j++) {
//                System.out.println(elements.get(j).text().replace("更多","")+"\t"+"https://www.koolearn.com"+elements.get(j).getElementsByTag("a").get(0).attr("href"));
//            }
//            break;
        }
    }

    @Test
    public void getInfo12333() {
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\124.txt");
        List<String> list2 = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\125.txt");
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            String str2 = list2.get(i);
            if(str2.startsWith(str)){
                if(str2.contains("；")){
                    String[] array = str2.split("；");
                    if(array.length>4){
                        System.out.println(array[0]+"；"+array[1]+"；"+array[2]);
                    }else{
                        System.out.println(str2);
                    }
                }else{
                    System.out.println(str2);
                }

            }else {
                System.out.println(str);
                break;
            }
        }
//        Object[] array1 = list1.toArray();
//        Object[] array2 = list2.toArray();
//        Arrays.sort(array1, comparator);
//        Arrays.sort(array2, comparator);
//        for (int i = 0; i <array1.length ; i++) {
//            System.out.println(array1[i].toString());
//        }
//        for (int i = 0; i <array2.length ; i++) {
//            System.out.println(array2[i].toString());
//        }
    }

    @Test
    public void getInfo1111() throws Exception{
        HttpClient httpClient = HttpClients.createDefault();
//            String url = "https://kaoyan.koolearn.com/20170418/977762.html";
//            List<String> htmlList = new ArrayList<>();
//            HttpGet httpGet = new HttpGet(url);
//            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
//            HttpResponse response = httpClient.execute(httpGet);
//            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
//            Document document = Jsoup.parse(contents);
//            Elements elements = document.getElementsByClass("xqy_core_text").get(0).getElementsByTag("td");
//            for (int j = 0; j <elements.size(); j++) {
////                System.out.println(elements.get(j).text());
////                System.out.println(elements.get(j).getElementsByTag("a").size());
//                if(elements.get(j).getElementsByTag("a").size()>0){
//                    System.out.println(elements.get(j).text()+"\t"+elements.get(j).getElementsByTag("a").get(0).attr("href"));
//                }
//
//            }
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\1111.txt");
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i).split("\t")[1];
            for (int j = 1; j <5 ; j++) {
                if(j==1){
                    list2.add(str);
                }else {
                    list2.add(str.replace(".html","_"+j+".html"));
                }
            }
        }
        for (int i = 0; i <list2.size() ; i++) {
            String url = list2.get(i);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            Document document = Jsoup.parse(contents);
            System.out.println(document.baseUri());
            if(document.toString().contains("xqy_core_text")){
                Elements elements = document.getElementsByClass("xqy_core_text").get(0).getElementsByTag("p");
                for (int j = 0; j <elements.size()-2 ; j++) {
                    System.out.println(elements.get(j).text().trim());
                }
            }

        }
    }

    @Test
    public void getInfo123333(){
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\11111.txt");
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            String[] array = list.get(i).split("\t");
            for (int j = 0; j <array.length ; j++) {
                if(array[j]==null || "".equals(array[j])){
                    continue;
                }
                list2.add(array[j]);
            }
        }
        MathUtil.sortByWord(list2);
    }

    @Test
    public void getOften() throws Exception{
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\11111.txt");
        HttpClient httpClient = HttpClients.createDefault();
//        List<String> list2 = new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            if(str.contains("/")){
                str= str.split("/")[0];
            }
            if(str.contains("(")){
                str= str.split("\\(")[0];
            }
//            String url = "https://dict.cn/search?q="+str;
            String url = "https://fanyi.baidu.com/?aldtype=16047#en/zh/"+str;

            System.out.println(url);
//            HttpGet httpGet = new HttpGet(url);
//            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
//            HttpResponse response = httpClient.execute(httpGet);
//            String contents = EntityUtils.toString(response.getEntity(), "gbk");
//            Document document = Jsoup.parse(contents);
//            System.out.println(document.toString());
//            Elements elements = document.getElementsByClass("highcharts-data-labels").get(0).getElementsByTag("span");
//            System.out.println(elements.text());
//            break;
        }

//        for (int i = 167; i <=3264; i++) {//167 3264
//            String url = list.get(i);
//            List<String> htmlList = new ArrayList<>();
//
//            String str = document.getElementsByClass("keyword").get(0).text();
//            String str2 = document.getElementsByClass("basic clearfix").get(0).getElementsByTag("li").get(0).text();
//
//            System.out.println(str+"\t"+str2);
    }

    @Test
    public void getInfo111111(){
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\111222.txt");
        for (int i = 101+1; i <list.size() ; i++) {
            String str = list.get(i);
            String[] array = str.split("\t");
            if(array[0].equals(array[1])){

                if(array.length==3){
                    System.out.println(array[1]+"\t"+array[2]);
                }else if(array.length==2){
                    System.out.println(str);
                    System.out.println(i);
                    break;
                }if(array.length==4){
                    System.out.println(array[1]+"\t"+array[2]+";"+array[3]);
                }
            }else{
                System.out.println(str);
                System.out.println(i);
                break;
            }
        }
    }

    @Test
    public void getInfo11111(){
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\111222.txt");
        Map<String,String> map = new LinkedHashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            String[] array = str.split("\t");
            if(array.length==1||array[0].equals("") || array[0]==null){
                continue;
            }
            map.put(array[0],array[1]);
        }
        List<String> list2 = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\111223.txt");
        for (int i = 0; i <list2.size() ; i++) {
            String str = list2.get(i);
            String[] array = str.split("\t");
            String key ="";
            if(map.get(array[0])==null){
                key="";
            }else{
                key=map.get(array[0]);
            }
            if(array.length==2){
                System.out.println(array[0]+"\t"+array[1]+"\t"+key);
            }else if(array.length==3){
                System.out.println(array[0]+"\t"+array[1]+";"+array[2]+"\t"+key);
            }else{
//                System.out.println(array.length);
                System.out.println(str);
//                System.out.println(i);
                break;
            }
        }
    }
}
