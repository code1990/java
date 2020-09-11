package webmagic;

import org.junit.Test;
import util.JDBCUtil;
import util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-19 16:30
 */
public class Fund005JunXian {
    @Test
    public void getInfo111(){
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(sql);
        for (int k = 0; k < codeList.size(); k++) {
            String code = "161725";//codeList.get(k);
            String sql2 = "select  fund_date from t_fund_day where fund_code ='"+code+"'   and fund_date >str_to_date('2020-02-06', '%Y-%m-%d') order by fund_date desc; ";
            List<String> dateStrList = JDBCUtil.getList(sql2);
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < dateStrList.size(); i++) {
                String dateStr = dateStrList.get(i);
                double value20 = getAvgByStep(dateStr,20,code);
                double value30 = getAvgByStep(dateStr,30,code);
                double value60 = getAvgByStep(dateStr,60,code);
                double value120 = getAvgByStep(dateStr,120,code);
                String sum7 = getSumByStep(dateStr,7,code);
                String sum30 = getSumByStep(dateStr,30,code);
                String sum90 = getSumByStep(dateStr,90,code);
                String sum180 = getSumByStep(dateStr,180,code);
                String sum365 = getSumByStep(dateStr,365,code);
                String[] info = getAddCount(dateStr,code);
                StringBuilder sb = new StringBuilder();
                sb.append("update t_fund_day set fund_20=" + MathUtil.getRound4(value20));
                sb.append(",fund_30=" + MathUtil.getRound4(value30));
                sb.append(",fund_60=" + MathUtil.getRound4(value60));
                sb.append(",fund_120=" + MathUtil.getRound4(value120));
                sb.append(",fund_sum_7=" + sum7);
                sb.append(",fund_sum_30=" + sum30);
                sb.append(",fund_sum_90=" + sum90);
                sb.append(",fund_sum_180=" + sum180);
                sb.append(",fund_sum_365=" + sum365);
                sb.append(",fund_count=" + info[0]);
                sb.append(",fund_count_value=" + info[1]);
                sb.append(" where fund_code ='"+code+"' and fund_date=str_to_date('"+dateStr+"', '%Y-%m-%d');");
                resultList.add(sb.toString());
                System.out.println(sb.toString());
//                break;
            }
            JDBCUtil.insertList(resultList);
            break;
        }

    }



    public Double getAvgByStep(String sDate,int step,String code){
        double result =0.0;
        String sql = "select fund_value from t_fund_day where fund_date <str_to_date('"+sDate+"', '%Y-%m-%d') and fund_code ='"+code+"' order by fund_date desc limit "+step+";";
        System.out.println(sql);
        List<String> resultList2 = JDBCUtil.getList(sql);
        if(resultList2.size()!=step){
            return result;
        }
        double[] x = new double[step];
        for (int j = 0; j < resultList2.size(); j++) {
            x[j] = new Double(resultList2.get(j));
        }
        result = MathUtil.getAvg(x);
        return result;
    }

    public String getSumByStep(String sDate,int step,String code){
        String result ="";
        String sql = "select fund_percent from t_fund_day where fund_date <str_to_date('"+sDate+"', '%Y-%m-%d') and fund_code ='"+code+"' order by fund_date desc limit "+step+";";
        System.out.println(sql);
        List<String> resultList2 = JDBCUtil.getList(sql);
        result = MathUtil.getSum(resultList2);
        return result;
    }

    public String[] getAddCount(String sDate,String code){
        int count =0;
        double value =0;
        String[] array = new String[2];
        String sql = "select fund_percent from t_fund_day where fund_date <=str_to_date('"+sDate+"', '%Y-%m-%d') and fund_code ='"+code+"' order by fund_date desc;";
        System.out.println(sql);
        List<String> resultList2 = JDBCUtil.getList(sql);
        for (int i = 0; i <resultList2.size() ; i++) {
            Double v = new Double(resultList2.get(i));
            if (v<0){
                break;
            }else{
                count +=1;
                value+=v;
            }
        }
        array[0]=count+"";
        array[1]=MathUtil.getRound4(value)+"";
        return array;
    }

}
