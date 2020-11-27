import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.HttpUtil;
import util.StringUtil;
import util.TxtUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-11 13:46
 */
public class Test123 {

    @Test
    public void getInfo11() {
        List<String> list = TxtUtil.readTxt("tdx.txt");
//        for (int i = 1; i <= 242; i++) {
//            list.add("http://www.gupang.com/tongdaxin/list_1_" + i + ".html");
//        }
        StringBuilder sb = new StringBuilder();
        for (int i = 666; i < list.size(); i++) {
//            StringBuilder sb = new StringBuilder();
            String url = list.get(i).split("\t")[1];
            System.out.println(i + "\t" + url);
            String title = list.get(i).split("\t")[0];
            title = StringUtil.replaceStr(title);
//            System.out.println(title);
            Document document = HttpUtil.getHtml(url);
//            System.out.println(document.toString());
            String position = "";
            if (document.getElementsByClass("nav").get(0).getElementsByTag("a").size() == 3) {
                position = document.getElementsByClass("nav").get(0).getElementsByTag("a").get(2).text();
            } else {
                position = "000";
            }
            position = StringUtil.replaceStr(position);
//            System.out.println(position);
            String content = document.getElementsByClass("content").get(0).text();
            content = content.replaceAll("; ", ";\n");
            if (content.length() > 0) {
                TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000\\" + position + "\\" + title + ".txt", content);
            } else {
                TxtUtil.appendFile("D:\\BaiduNetdiskDownload\\000EBK\\tmp.txt", sb.toString());
            }
//            System.out.println(content);

//            Elements elements = document.getElementsByClass("list_2").get(0).getElementsByTag("a");
//            for (int j = 0; j < elements.size(); j++) {
//                Element e = elements.get(j);
//                sb.append(e.text() + "\t" + e.attr("href") + "\n");
//            }
//            break;
        }
//        TxtUtil.writeTxt(new File("tdx.txt"), sb.toString());
    }

    @Test
    public void getInfo111() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 577; i++) {
            list.add("https://www.55188.com/forum.php?mod=forumdisplay&fid=48&typeid=58&filter=typeid&typeid=58&page=" + i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String url = list.get(i);
            Document document = HttpUtil.getHtml(url);
            Elements elements = document.getElementById("threadlisttableid").getElementsByTag("article");
            for (int j = 0; j <elements.size() ; j++) {
                System.out.println(elements.get(j).text());
            }
//            System.out.println(document.toString());
            break;
//            StringBuilder sb = new StringBuilder();
//            String url = list.get(i).split("\t")[1];
//            System.out.println(i+"\t"+url);
//            String title = list.get(i).split("\t")[0];
//            title = StringUtil.replaceStr(title);
////            System.out.println(title);

//            String position ="";
//            if(document.getElementsByClass("nav").get(0).getElementsByTag("a").size()==3){
//                position = document.getElementsByClass("nav").get(0).getElementsByTag("a").get(2).text();
//            }else{
//                position ="000";
//            }
//            position = StringUtil.replaceStr(position);
////            System.out.println(position);
//            String content = document.getElementsByClass("content").get(0).text();
//            content = content.replaceAll("; ",";\n");
//            if(content.length()>0){
//                TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000\\"+position+"\\"+title+".txt",content);
//            }else{
//                TxtUtil.appendFile("D:\\BaiduNetdiskDownload\\000EBK\\tmp.txt",sb.toString());
//            }
//            System.out.println(content);

//            Elements elements = document.getElementsByClass("list_2").get(0).getElementsByTag("a");
//            for (int j = 0; j < elements.size(); j++) {
//                Element e = elements.get(j);
//                sb.append(e.text() + "\t" + e.attr("href") + "\n");
//            }
//            break;
        }
//        TxtUtil.writeTxt(new File("tdx.txt"), sb.toString());
    }

}
