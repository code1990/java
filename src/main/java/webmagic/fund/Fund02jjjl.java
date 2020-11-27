package webmagic.fund;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import util.TxtUtil;

import java.util.List;

/**
 * @author 911
 * @date 2020-08-05 09:38
 * http://fundf10.eastmoney.com/jjjl_005911.html
 */
public class Fund02jjjl  implements PageProcessor,Runnable {

    private Site site = Site.me().setRetryTimes(5).setSleepTime(1000).setTimeOut(10000);
    static String path = "C:\\Users\\xiala\\Desktop\\123\\jjjl.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\123\\jjjl123.txt";
    static List<String> list = TxtUtil.readTxt(path);
    StringBuilder all = new StringBuilder();

    @Override
    public void process(Page page) {
        all.append(TxtUtil.readTxtStr(target));
        Html html = page.getHtml();
        Document document = html.getDocument();
        Element element = document.getElementsByClass("box").first();

        StringBuilder sb = new StringBuilder();
        List<Element> elements = element.getElementsByTag("tr");
        for (int i = 1; i < elements.size(); i++) {
            String str = elements.get(i).text();
            System.out.println(str);
            sb.append(str+"\t");
        }
        sb.append(document.getElementsByClass("text").last().getElementsByTag("p").get(2).text()+"\t");
        all.append(sb.toString()+"\n");
        TxtUtil.writeTxt(target,all.toString());
        System.out.println(sb.toString());
//        System.out.println(document.toString());
//        System.out.println(document.getElementsByClass("text").last().getElementsByTag("p").get(2).text());
//        Object s2 = page.getHtml().xpath("/html/body/div[1]/div[8]/div[3]/div[2]/div[3]/div/div[2]/div/div[2]/div[1]/p[3]");
//        String info = document.getElementsByClass("text").first().getElementsByTag("p").get(2).text();
//        System.out.println(document.getElementsByClass("jl_intro").size());
//        System.out.println(s2.toString());//
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

        for (int i = 0; i < list.size(); i++) {//
            String url = list.get(i);//.split(",")[1];
            System.out.println((i+1)+"\t"+url);
//            url="http://fundf10.eastmoney.com/jjjl_005911.html";
            Spider.create(new Fund02jjjl()).addUrl(url).thread(5).run();
        }

    }

    @Override
    public void run() {

    }

//    @Test
//    public void getInfo(){
//        String path = "C:\\Users\\xiala\\Desktop\\123\\1233.txt";
//        List<String> list = TxtUtil.readTxt(path);
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i <list.size() ; i++) {
//            String str = list.get(i);
//            str = str.replace("http://fund.eastmoney.com/","http://fundf10.eastmoney.com/jjjl_");
//            sb.append(str+"\n");
//            System.out.println(str);
//        }
//        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\jjjl.txt",sb.toString());
//    }


}

