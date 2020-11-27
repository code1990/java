import org.apache.commons.lang3.ArrayUtils;
import util.StockUtil;

/**
 * @author 911
 * @date 2020-11-10 13:43
 */
public class Daogou5 {

    /**
     * 给定当天的估值信息 涨幅计算今天是买点还是卖点
     * @param args
     */
    public static void main(String[] args) {
        double n = 0.01;
        double n1 = 12;
        double q = 12;
        double m1 = 12;
        double shorts = 12;
        double longs = 12;
        double m = 12;
        double mid = 12;
        //给定昨日的20个交易日的收盘价
        double[] x = new double[20];
        //给定今日的估算收盘价
        double gzValue = 0.0;
        ////输出AA05:收盘价的5日简单移动平均
        double[] array5 =  ArrayUtils.addAll(StockUtil.getArrayByIndex(x,4), gzValue);
        double aa05 = StockUtil.getMa(array5);
        double aa05Ref = StockUtil.getMa(StockUtil.getArrayByIndex(x,5));
        ////五日乖离率赋值:(收盘价-AA05)/AA05*100
        double bias5 = (gzValue-aa05)/aa05*100;
        //BB05赋值:(AA05/1日前的AA05-1)*100的反正切*180/3.1416
        double bb05=Math.atan((aa05/aa05Ref-1)*100)*180/3.1416;
        //速度5赋值:(AA05-1日前的AA05)/1日前的AA05的3日指数移动平均*100的3日[1日权重]移动平均
        //速度5:=SMA(EMA((AA05-REF(AA05,1))/REF(AA05,1),3)*100,3,1);
//        double speed5 =
    }
}
