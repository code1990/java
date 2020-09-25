package webmagic;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.xml.serializer.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import util.MapUtil;
import util.MathUtil;
import util.TxtUtil;
import util.UserAgentUtil;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class JavaTest  implements PageProcessor {

    private Site site = Site.me().setRetryTimes(5).setSleepTime(1000).setTimeOut(10000);
    static String path = "D:\\github\\java\\src\\main\\resources\\course_java.txt";
    static List<String> list = TxtUtil.readTxt(path);
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        System.out.println(html.toString());
        Document document = html.getDocument();
//        Element element = document.getElementsByClass("t_fsz").first();
//        System.out.println(element.text());
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-])").all());
//        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

//        for (int i = 1; i < 2; i++) {
//            String url = list.get(i).split(",")[1];
//            System.out.println(url);
//
//        }
        Spider.create(new JavaTest()).addUrl("http://fundf10.eastmoney.com/jbgk_005911.html").thread(5).run();
    }

    @Test
    public void getInfo() throws Exception{
        String str ="C:\\Users\\xiala\\Desktop\\1233.txt";
        List<String> list = TxtUtil.readTxt(str);
        HashMap<String,Integer> map = new HashMap<>();
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i <list.size() ; i++) {
            String url = list.get(i).split("\t")[1];
            System.out.println(list.get(i));
            HttpGet httpGet = new HttpGet(url);
//            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
//            httpGet.setHeader("Content-Type", "text/html; charset=utf-8");
//            httpGet.setHeader("Content-Encoding", "gzip");
//            httpGet.setHeader("Connection", "keep-alive");
            HttpResponse response = httpClient.execute(httpGet);
            System.out.println(Arrays.toString(response.getAllHeaders()));
//            System.out.println(httpGet.getHeaders("Content-Encoding"));
//            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
//            System.out.println(response.getEntity().toString());
//            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
//            Document document = Jsoup.parse(contents);
//            System.out.println(document.toString());
//            String fans = document.getElementsByClass("others_fans").text();
//            System.out.println(document.getElementsByClass("others_info").text());
//            System.out.println(fans);
//            HttpResponse response=HttpUtils.doGet(url, headers);
            HttpEntity entity1=response.getEntity(); //压缩过的entity
            long len=entity1.getContentLength();//压缩过的长度
            System.out.println(entity1.getContentType());//类型
//            System.out.println(entity1.getContentEncoding().getValue());//压缩类型
            System.out.println("gzip:压缩前的内容长度"+len);
            GzipDecompressingEntity entity=new GzipDecompressingEntity(entity1);
            InputStream in=entity.getContent();
            // 开始读取内容
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1;) {
               out.append(new String(b, 0, n));
             }
            System.out.println(out.toString());
//            String str=Utils.getStringFromStream(in);
//            System.out.println("实际内容长度为："+str.getBytes().length);
//
            String acceptEncoding = "";

//            others_info
//            System.out.println();
//            String[] array = list.get(i).split(",");
//            map.put("",1);
//            String s= list.get(i);
//            if(map.get(s)==null){
//                map.put(s,1);
//            }else{
//                map.put(s,map.get(s)+1);
//            }
        }
//        MapUtil.sortDesc(map);
    }
//    public static String getStringFromResponse(HttpResponse response) {
//        if (response == null) {
//            return null;
//        }
//        String responseText = "";
//        InputStream in = getInputStreamFromResponse(response);
//        Header[] headers = response.getHeaders("Content-Encoding");
//        for(Header h : headers){
//            if(h.getValue().indexOf("gzip") > -1){
//                //For GZip response
//                try{
//                    GZIPInputStream gzin = new GZIPInputStream(is);
//                    InputStreamReader isr = new InputStreamReader(gzin,"utf-8");
//                    responseText = Utils.getStringFromInputStreamReader(isr);
//                }catch (IOException exception){
//                    exception.printStackTrace();
//                }
//                break;
//            }
//        }
//        responseText = Utils.getStringFromStream(in);
//        return responseText;
//    }
    public static String compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new String(Base64.encodeBase64(out.toByteArray()));
    }

    @Test
    public void getInfo111(){
        String path ="C:\\Users\\xiala\\Desktop\\123";
        File[] files = new File(path).listFiles();
        HashSet<String> set = new HashSet<>();
        HashMap<String,String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <files.length; i++) {
            File file = files[i];
            List<String> list = TxtUtil.readTxt(file);
            for (int j = 0; j <list.size() ; j++) {
                String str = list.get(j);
                if (!str.contains("\t")){
                    continue;
                }
                String url = str.split("\t")[0];
                if(!set.contains(url)){
                    System.out.println(set.add(url));
                    sb.append(url+"\n");
                }
            }
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\000.txt",sb.toString());
    }

    @Test
    public void getLength(){
        String path = "D:\\workspaceE1\\client-new\\com.csair.soc.disp.monitor.ui\\src\\com\\csair\\soc\\disp\\monitor\\newui\\dialog";
        File[] file = new File(path).listFiles();
        for (int i = 0; i <file.length ; i++) {
            if(file[i].isDirectory()){
                File[] file2 = file[i].listFiles();
                for (int j = 0; j <file2.length ; j++) {
                    String name = file2[j].getName();
//                    if(!name.contains("Dialog")){
//                        continue;
//                    }
                    System.out.println(name);
                }
            }else{
                System.out.println(file[i].getName());
            }


        }
    }

    @Test
    public void get00(){
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\112.txt");
        for (int i = 0; i <list.size() ; i++) {
//            if(list.get(i).contains("2021考研")){
//                continue;
//            }
            System.out.println("http://dict.cn/"+list.get(i));
        }
    }
}
