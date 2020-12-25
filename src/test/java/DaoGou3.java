import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.*;

import java.io.File;
import java.util.*;

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

    /**
     * 深圳大盘所有指数 23页
     * http://46.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112400006077467768177414_1606816183152&pn=7&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:5&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152&_=1606816183168
     */

    /**
     *上证大盘所有指数 11页
     * http://63.push2.eastmoney.com/api/qt/clist/get?cb=jQuery112407832804967399252_1606816495537&pn=1&pz=20&po=1&np=1&ut=bd1d9ddb04089700cf9c27f6f7426281&fltt=2&invt=2&fid=f3&fs=m:0+t:5&fields=f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f12,f13,f14,f15,f16,f17,f18,f20,f21,f23,f24,f25,f26,f22,f33,f11,f62,f128,f136,f115,f152&_=1606816495538
     */

    /**
     * http://www.csindex.com.cn/zh-CN/indices/index-detail/399983
     * 指数具体的成分详情
     */

    /**
     * 反向查找基金的属性 对基金定位
     */

    /**
     *
     * 指数的最低点抄底 买入对应的基金
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

    @Test
    public void getInfo1111(){
        ConnectionPool pool = new ConnectionPool("stock");
        String sql ="select ts_code from index_basic where market in ('CSI','SZSE');";
        List<String> list = JDBCUtil.getList(pool,sql);
        Map<String,Integer> map =new HashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String code = list.get(i).split("\\.")[0];
            if(map.get(code)==null){
                map.put(code,1);
            }else {
                map.put(code,map.get(code)+1);
                System.out.println(code);
            }
        }
//        MapUtil.printMap(map);
    }
}
