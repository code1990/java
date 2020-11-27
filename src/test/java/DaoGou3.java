import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.HttpUtil;
import util.JDBCUtil;
import util.MathUtil;
import util.TxtUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 911
 * @date 2020-10-28 14:38
 */
public class DaoGou3 {
    /**
     * sh000001获取历史净值
     * http://push2his.eastmoney.com/api/qt/stock/kline/get?cb=jQuery112407164273128723818_1603866456776&secid=1.000001&ut=fa5fd1943c7b386f172d6893dbfba10b&fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5&fields2=f51%2Cf52%2Cf53%2Cf54%2Cf55%2Cf56%2Cf57%2Cf58&klt=101&fqt=0&beg=19900101&end=20220101&_=1603866456777
     */

    /**
     * 获取分钟线
     * http://push2his.eastmoney.com/api/qt/stock/trends2/get?cb=jQuery112407659126861780046_1603866942711&secid=1.000001&ut=fa5fd1943c7b386f172d6893dbfba10b&fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5%2Cf6%2Cf7%2Cf8%2Cf9%2Cf10%2Cf11&fields2=f51%2Cf53%2Cf56%2Cf58&iscr=0&ndays=1&_=1603866942863
     */

    /**
     * 基金净值
     * http://api.fund.eastmoney.com/f10/lsjz?callback=jQuery18305662557800736644_1603875504293&fundCode=002190&pageIndex=1&pageSize=20&startDate=&endDate=&_=1603875504303
     */


    @Test
    public void getInfo111() {
        String path = "D:\\gitee\\work\\123";
        File[] array = new File(path).listFiles();

        Set<String> set = new HashSet<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            List<String> list = TxtUtil.readGBKTxt(array[i].getAbsolutePath());
            for (String item : list) {
                if (set.contains(item)) {
                    System.out.println(11);
                } else {
                    System.out.println(item);
                    sb.append(item + "\n");
                }
            }
        }
        TxtUtil.writeTxt(new File("112.txt"), sb.toString());
//        LinkedHashSet<String> hashSet = new LinkedHashSet<String>(list);
//        List<String> list2 = new ArrayList<>(hashSet);
//        for (int i = 0; i <list2.size() ; i++) {
//            System.out.println(list2.get(i));
//        }
    }

    public static void main(String[] args) {
//        System.out.println("\033[30;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[31;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[32;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[33;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[34;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[35;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[36;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[37;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[40;31;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[41;32;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[42;33;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[43;34;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[44;35;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[45;36;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[46;37;4m" + "我滴个颜什" + "\033[0m");
//        System.out.println("\033[47;4m" + "我滴个颜什" + "\033[0m");
        String sql = "select fund_percent from t_fund_day where fund_code ='320007' order by fund_date ";
        List<String> list = JDBCUtil.getList(sql);
        List<Double> list2 = new ArrayList<Double>();
        List<Integer> list3 = new ArrayList<Integer>();
        double count = 0.0;
        Integer count2 = 0;
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            double value2 = new Double(list.get(i));
            if(count>=0.0){
                if(value2 >=0.0){
                    count +=value2;
                    count2+=1;
                }else{
                    list2.add(count);
                    count = 0.0;
                    count +=value2;
                    list3.add(count2);
                    count2=0;
                    count2+=1;
                }
            }else{
                if(value2 >=0.0){
                    list2.add(count);
                    count = 0.0;
                    count +=value2;
                    list3.add(count2);
                    count2=0;
                    count2+=1;
                }else{
                    count +=value2;
                    count2+=1;
                }
            }
            if(i== list.size()-1){
                list2.add(count);
                list3.add(count2);
            }
        }
        System.out.println("===============");
        double sum =0.0;
        for (int i = 0; i <list2.size() ; i++) {
            Double value = MathUtil.getRound2(list2.get(i),2);
            if(value <-1.0){
                System.out.println(value+"\t"+list3.get(i));
            }

//            sum+=value;
//            System.out.println(MathUtil.getRound2(sum,2));
        }
    }

    @Test
    public void getInfo000(){
        List<String> list = TxtUtil.readTxt(new File("pool3.txt"));
        for (int i = 0; i <list.size() ; i++) {
            String code = list.get(i).split("\t")[0];
            String url = "http://fundf10.eastmoney.com/jbgk_"+code+".html";
            Document document = HttpUtil.getHtml(url);
            Elements elements = document.getElementsByClass("txt_in").get(0).getElementsByClass("info w790").get(0).getElementsByTag("tr");
            System.out.println(code+"\t"+elements.get(elements.size()-1).text().replaceAll(" ","\t"));
        }
    }
}
