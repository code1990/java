package util;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author 911
 * @date 2020-08-11 15:27
 */
public class MathUtil {

    //方差s^2=[(x1-x)^2 +...(xn-x)^2]/n 或者s^2=[(x1-x)^2 +...(xn-x)^2]/(n-1)
    public static double getVariance(double[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        return dVar/m;
    }

    //标准差σ=sqrt(s^2)
    public static double getStandardeviation(double[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        double dAve=sum/m;//求平均值
        double dVar=0;
        for(int i=0;i<m;i++){//求方差
            dVar+=(x[i]-dAve)*(x[i]-dAve);
        }
        //reture Math.sqrt(dVar/(m-1));
        return Math.sqrt(dVar/m);
    }

    public static double  getAvg(double[] x) {
        int m=x.length;
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x[i];
        }
        return sum/m;//求平均值
    }

    public static double  getAvg(List<Double> x) {
        int m=x.size();
        double sum=0;
        for(int i=0;i<m;i++){//求和
            sum+=x.get(i);
        }
        return sum/m;//求平均值
    }

    public static double  getAvg(List<Double> x,int index) {
        int m=index;
        double sum=0;
        for(int i=0;i<index;i++){//求和
            sum+=x.get(i);
        }
        return sum/index;//求平均值
    }

    public static String getRound(double x,int index){
        StringBuilder sb = new StringBuilder("#.");
        for (int i = 0; i < index; i++) {
            sb.append("0");
        }
        String result =new java.text.DecimalFormat(sb.toString()).format(x);
        if(result.startsWith(".")){
            return "0"+result;
        }else if(result.startsWith("-.")){
            return result.replace("-.","-0.");
        }
        return result;
    }
    public static double getRound2(double x,int index){
        StringBuilder sb = new StringBuilder("#.");
        for (int i = 0; i < index; i++) {
            sb.append("0");
        }
        String result =new java.text.DecimalFormat(sb.toString()).format(x);
        if(result.startsWith(".")){
            return new Double("0"+result);
        }else if(result.startsWith("-.")){
            return new Double(result.replace("-.","-0."));
        }
        return new Double(result);
    }

    public static String getRound4(double x){
        String result =new java.text.DecimalFormat("#.0000").format(x);
        if(result.startsWith(".")){
            return "0"+result;
        }else if(result.startsWith("-.")){
            return result.replace("-.","-0.");
        }
        return result;
    }

    public static String getSum(List<String> list){
        double result =0.0;
        for (int i = 0; i < list.size(); i++) {
            result +=new Double(list.get(i));
        }
        return getRound4(result);
    }

    public static void sortByWord(List<String> list){
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        Object[] array1 = list.toArray();
        Arrays.sort(array1, comparator);
        for (int i = 0; i <array1.length ; i++) {
            System.out.println(array1[i].toString());
        }
    }

    public static String getRandom(int length){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }
}
