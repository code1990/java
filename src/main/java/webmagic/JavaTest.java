package webmagic;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import util.TxtUtil;

import java.util.List;

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
}
