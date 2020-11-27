import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.HttpUtil;
import util.Kline;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-22 15:11
 */
public class KlineTest {
    private List<Kline> list = new ArrayList<>();
    private Kline gzValue = new Kline(4321.42, 4360.74, 4312.98, 4328.12);

//    @Before
    public void init() {
        String url = "http://quotes.money.163.com/trade/lsjysj_zhishu_399803.html";
        Document document = HttpUtil.getHtml(url);
        Elements elements = document.getElementsByClass("table_bg001 border_box limit_sale").get(0).getElementsByTag("tr");

        for (int i = 1; i < 20; i++) {
            Elements elements2 = elements.get(i).getElementsByTag("td");
            Kline line = new Kline();
            line.setOpen(new Double(elements2.get(1).text().replace(",", "")));
            line.setHigh(new Double(elements2.get(2).text().replace(",", "")));
            line.setLow(new Double(elements2.get(3).text().replace(",", "")));
            line.setClose(new Double(elements2.get(4).text().replace(",", "")));
            System.out.println(line.toString());
            list.add(line);
        }
    }

    @Test
    public void checkInfo() {
//        init();
//        System.out.println(list.get(0).toString());
        System.out.println(gzValue.isRed());
        boolean isBuy = false;
        //假设今天的实时估值为阳线
        Kline yest1 = list.get(0);
        Kline yest2 = list.get(1);
        if(gzValue.isRed()){
            //早晨之星
            if(yest1.isGreen() && yest2.isGreen()){
                if(yest1.getHigh()>gzValue.getOpen() && yest1.getHigh()>yest2.getClose()){
                    if(yest1.getUpLine()>yest1.getBody()&& yest1.getDownLine()>yest1.getBody()&& yest1.getBody()<10){
                        isBuy=true;
                        System.out.println("早晨十字星，买入");
                    }
                }
            }
            if(yest2.isGreen()){
                if(yest1.getHigh()>gzValue.getOpen() && yest1.getHigh()>yest2.getClose()){
                    if(yest1.getUpLine()<=yest1.getBody()&& yest1.getDownLine()<=yest1.getBody()){
                        isBuy=true;
                        System.out.println("早晨之星，买入");
                    }
                }
            }

            if(yest1.isGreen()&& gzValue.getBody()/yest1.getBody()<=1.3 || gzValue.getBody()/yest1.getBody()>=0.7&&gzValue.getBody()>30){
                if(gzValue.getUpLine()/yest1.getDownLine()<=1.3 || gzValue.getUpLine()/yest1.getDownLine()>=0.7){
                    isBuy=true;
                    System.out.println("好友反攻，买入");
                }else if(yest1.getClose()>=gzValue.getHalf()){
                    isBuy=true;
                    System.out.println("曙光出现，买入");
                }else if(yest1.getOpen()>=gzValue.getHalf()){
                    isBuy=true;
                    System.out.println("数日东升，买入");
                }
            }
        }
        if(yest1.getDownLine()/yest1.getBody()<0.5||gzValue.getDownLine()/gzValue.getBody()<0.5){
            if(yest1.getUpLine()/yest1.getBody()>=2||gzValue.getUpLine()/gzValue.getBody()>=2){
                isBuy=true;
                System.out.println("倒锤头线，买入");
            }
        }
        if(yest1.getUpLine()/yest1.getBody()<0.5||gzValue.getUpLine()/gzValue.getBody()<0.5){
            if(yest1.getDownLine()/yest1.getBody()>=3||gzValue.getDownLine()/gzValue.getBody()>=3){
                isBuy=true;
                System.out.println("锤头线，买入");
            }
        }
        if(yest1.isGreen()&& yest1.getLow()/gzValue.getLow()>=0.7 ||yest1.getLow()/gzValue.getLow()<=1.3){
            isBuy=true;
            System.out.println("平底，买入");
        }
        if(yest1.isRed() && yest2.isGreen() && gzValue.getHigh()<yest2.getLow()&&yest1.getHigh()<yest2.getLow()){
            isBuy=true;
            System.out.println("低位并排阳线，买入");
        }
    }

//    public boolean isRedLine(){
//        return this.close>=this.open;
//    }
}
