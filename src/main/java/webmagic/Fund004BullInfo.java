package webmagic;

import org.junit.Test;
import util.JDBCUtil;
import util.MathUtil;
import util.TxtUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//*
// * @author 911
// * @date 2020-08-19 15:27
// * buLL指标计算 不灵活


public class Fund004BullInfo {
    @Test
    public void getInfo123() throws Exception{
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        int step=20;
        List<String> codeList = JDBCUtil.getList(sql);

        for (int k = 0; k < codeList.size(); k++) {
            String code =codeList.get(k);
            System.out.println(k + ">>>>>>>" + code);
            //查询所有的未更新的时间
            String sql2 = "select distinct fund_date from t_fund_day where fund_code ='"+code+"' and fund_bull_3 =0 and fund_date >str_to_date('2020-02-06', '%Y-%m-%d') order by fund_date desc; ";
            List<String> dateStrList = JDBCUtil.getList(sql2);
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < dateStrList.size(); i++) {
                String sDate=dateStrList.get(i);
                sql = "select fund_value from t_fund_day where fund_date <str_to_date('"+sDate+"', '%Y-%m-%d') and fund_code ='"+code+"' order by fund_date desc limit "+step+";";
                System.out.println(sql);
                List<String> resultList2 = JDBCUtil.getList(sql);
                if(resultList2.size()!=step){
                    break;
                }
                double[] x = new double[step];
                for (int j = 0; j < resultList2.size(); j++) {
                    x[j] = new Double(resultList2.get(j));

                }
                double bull2 = MathUtil.getAvg(x);
                double bull1 = MathUtil.getAvg(x) + 3 * MathUtil.getStandardeviation(x);
                double bull3 = MathUtil.getAvg(x) - 3 * MathUtil.getStandardeviation(x);
                StringBuilder sb100 = new StringBuilder();
                sb100.append("update t_fund_day set fund_bull_1=" + MathUtil.getRound4(bull1));
                sb100.append(",fund_bull_2=" + MathUtil.getRound4(bull2));
                sb100.append(",fund_bull_3=" + MathUtil.getRound4(bull3));
                sb100.append(" where fund_code ='"+code+"' and fund_date=str_to_date('"+sDate+"', '%Y-%m-%d');");
                resultList.add(sb100.toString());
                System.out.println(sb100.toString());
            }

            JDBCUtil.update(resultList);
        }

    }


}
