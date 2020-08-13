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
 * @date 2020-08-07 12:39
 */
public class FundCore2 implements PageProcessor,Runnable {

    private Site site = Site.me().setRetryTimes(5).setSleepTime(1000).setTimeOut(10000);
    static String path = "C:\\Users\\xiala\\Desktop\\123\\123312.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\123\\1000.txt";
    static List<String> list = TxtUtil.readTxt(path);
    StringBuilder all = new StringBuilder();

    @Override
    public void process(Page page) {
        all.append(TxtUtil.readTxtStr(target));
        Html html = page.getHtml();
        Document document = html.getDocument();
        String code = page.getUrl().toString().split("jzzs_")[1].split(".html")[0];
        System.out.println(code);
        code = code.split("\\(")[1].split("\\)")[0];
        Element element = document.getElementById("fn_fund_value_trend");

        StringBuilder sb = new StringBuilder();
        List<Element> elements = element.getElementsByTag("tr");
        for (int i = 1; i < elements.size(); i++) {
            String str = elements.get(i).text();
            System.out.println(str);
            sb.append(code+"\t"+str+"\n");
        }
        all.append(sb.toString()+"\n");
        TxtUtil.writeTxt(target,all.toString());
        System.out.println(sb.toString());

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {

        for (int i = 0; i < list.size(); i++) {//list.size()
            String url = list.get(i);//.split(",")[1];
            System.out.println((i+1)+"\t"+url);
//            url="http://quotes.money.163.com/fund/jzzs_000001.html?start=2020-01-01&end=2020-03-31";
            Spider.create(new FundCore2()).addUrl(url).thread(5).run();
        }

    }

    @Override
    public void run() {

    }

//    @Test
//    public void getInfo(){
//        String path = "C:\\Users\\xiala\\Desktop\\123\\pool2.txt";
//        List<String> list = TxtUtil.readTxt(path);
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i <list.size() ; i++) {
//            String str = list.get(i);
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-01-01&edate=2020-01-31\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-02-01&edate=2020-02-28\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-03-01&edate=2020-03-31\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-04-01&edate=2020-04-30\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-05-01&edate=2020-05-31\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-06-01&edate=2020-06-30\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-07-01&edate=2020-07-31\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-08-01&edate=2020-08-31\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-09-01&edate=2020-09-30\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-10-01&edate=2020-10-31\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-11-01&edate=2020-11-30\n");
//            sb.append("http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+str+"&page=1&per=65535&sdate=2020-12-01&edate=2020-12-31\n");
////            str = "http://fundf10.eastmoney.com/ccmx_"+str+".html";//str.replace("http://fund.eastmoney.com/","http://fundf10.eastmoney.com/jjjl_");
////            sb.append(str+"\n");
//            System.out.println(str);
//        }
//        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\1233122.txt",sb.toString());
//    }
}
