package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-01 17:22
 */
public class StockUtil {
    /**
     * 1. 3日均价=3日收盘价之和/3
     * 2. 6日均价=6日收盘价之和/6
     * 3. 12日均价=12日收盘价之和/12
     * 4. 24日均价=24日收盘价之和/24
     * 5. BBI=（3日均价+6日均价+12日均价+24日均价）/4
     */
    public static double getBbi(double[] x) {
        double result = 0.0;
        double avg3 = MathUtil.getAvg(getArrayByIndex(x, 3));
        double avg6 = MathUtil.getAvg(getArrayByIndex(x, 6));
        double avg12 = MathUtil.getAvg(getArrayByIndex(x, 12));
        double avg20 = MathUtil.getAvg(getArrayByIndex(x, 20));
        double[] array = new double[]{avg3, avg6, avg12, avg20};
        result = MathUtil.getAvg(array);
        return result;
    }

    public static double[] getArrayByIndex(double[] x, int index) {
        double[] result = new double[index];
        for (int j = 0; j < index; j++) {
            result[j] = x[x.length - j - 1];
        }
        return result;
    }

    public static Pair getGzInfo(String code) throws Exception {
        Pair<Double, Double> tuple = Pair.of(0.0, 0.0);
        String url = "http://fundgz.1234567.com.cn/js/" + code + ".js?rt=1603852762223" + System.currentTimeMillis();
        Document document = HttpUtil.getHtml(url);
        String content = document.getElementsByTag("body").text();
        if ("jsonpgz();".equals(content) || content.trim().length() == 0) {
            return tuple;
        }
        content = content.replace("jsonpgz(", "").replace(");", "");
        System.out.println(url);
        JSONObject jsonObject = JSON.parseObject(content);
        tuple = Pair.of(jsonObject.getDouble("gsz"), jsonObject.getDouble("gszzl"));
        return tuple;
    }
    //N日移动平均线=N日收市价之和/N

    /**
     * MA，表示简单算术移动平均线，这是我们最常见和使用的移动平均线。
     * <p>
     * 用法：
     * <p>
     * MA(X,N)，X的N日简单移动平均，算法为(X1+X2+X3+...+Xn)/N。
     * <p>
     * 从以上简单算术移动平均线的算法我们可以看到，数值X1/X2/X3/X4/X5均分配了同等权重，都是1/N。
     *
     * @param x
     * @return
     */
    public static Double getMa(double[] x) {
        return MathUtil.getAvg(x);
    }

    /**
     * MA(x,n)-移动平均，是最简单的n日内的平均值
     * <p>
     * SMA(x,n,m)-简单移动平均，m为当日的权重，是个0~1之间的值
     * <p>
     * EMA(x,n)-指数移动平均，这个函数以相关周期为权重进行计算
     * <p>
     * DMA(x,m)-动态移动平均，这个函数以动态设定的权重m进行计算
     * <p>
     * TMA(x,p,q)-递归移动平均，这个函数可以完全控制当前周期的权重和上一次值的权重
     * <p>
     * WMA(x,m)-加权移动平均，这个函数对于近日的权重会比其它函数敏感
     * <p>
     * SMA，表示移动平均线，对近期数值可以赋予更高权重，这也是SMA和MA最明显的区别。
     * <p>
     * 用法：
     * <p>
     * SMA(X,N,M)，X的N日移动平均，M为权重，如Y=(X*M+Y'*(N-M))/N。
     * <p>
     * 根据公式原理，我们可以写成：
     * <p>
     * SMA=a*p1+(1-a)*SMA'=a*(p1-SMA')+SMA'=a*(p1+(1-a)*p2+(1-a)² *p3+...){其中p1指今天价格，p2指昨天价格，SMA'指昨天的移动平均值，依此类推}
     * <p>
     * a为平滑指数，展开得到1/a=1+(1-a)+(1-a)²+(1-a)³+...
     * <p>
     * 所以，SMA=p1+(1-a)*p2+(1-a)² *p3+(1-a)³ *p4+.../1+(1-a)+(1-a)²+(1-a)³+...
     * <p>
     * 随着时间的回推，数值的权重呈现指数级缩小，由此SMA可以达到给予近期数值更高的权重，更贴合市场价格的表现。
     *
     * @param x
     * @return
     */
    public static Double getSma(double[] x) {
        return MathUtil.getAvg(x);
    }

    /**
     * EMA，表示指数移动平均，又名EXPMA。
     * <p>
     * 用法：
     * <p>
     * EMA(X,N)：X的N日指数移动平均，算法为Y=(X*2+Y'*(N-1))/(N+1)。
     * <p>
     * 数学本质上SMA和EMA同属于指数移动平均，只不过EMA属于SMA的一个特例，赋予了今日更高的权重，相同数值天数条件下比SMA均线表现更加敏感。
     * <p>
     * EMA(X,N)就相当于SMA(X,N+1,2)。
     *
     * @param x
     * @return
     */
    public static Double getEma(double[] x) {
        return MathUtil.getAvg(x);
    }


//    @Test
//    public void getInfo111(){
////        double[] array = new double[]{1.0,2.0,3.0,4.0};
////        System.out.println(Arrays.toString(getArrayByIndex(array,1)));
////        System.out.println(Arrays.toString(getArrayByIndex(array,2)));
////        System.out.println(Arrays.toString(getArrayByIndex(array,3)));
////        System.out.println(Arrays.toString(getArrayByIndex(array,4)));
//        double[] x = new double[20];
//        for (int i = 0; i <20 ; i++) {
//            x[i]=(double)i/10;
//            System.out.println(x[i]);
//        }
//        System.out.println(getBbi(x));
//    }

    public MutableTriple getIndexMa(List<Kline> list) {
        List<Double> doubles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            doubles.add(list.get(i).getClose());
        }
        MutableTriple<Double, Double, Double> tuple3 = MutableTriple.of(0.0, 0.0, 0.0);
        double avg20 = MathUtil.getAvg(doubles);
        double avg10 = MathUtil.getAvg(doubles, 10);
        double avg5 = MathUtil.getAvg(doubles, 5);
        tuple3.left = avg5;
        tuple3.middle = avg10;
        tuple3.right = avg20;
        return tuple3;
    }
}
