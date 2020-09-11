package webmagic;

import org.junit.Test;
import util.JDBCUtil;
import util.MathUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-19 15:27
 * bull指标计算 使用移动窗口方式进行
 */
public class Fund004BullInfoNewUpdate {



    @Test
    public void getInfo123() {
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        int step = 20;
        List<String> codeList = JDBCUtil.getList(sql);
        for (int k = 0; k < codeList.size(); k++) {
            List<String> resultList = new ArrayList<>();
            String code = codeList.get(k);
            String sql1 = "select max(fund_date) as fund_date from t_fund_day where fund_code ='"+code+"';";
            String startStr = JDBCUtil.getList(sql1).get(0);
            System.out.println(k + ">>>>>>>" + code);
            String sql2 = "select distinct fund_date from t_fund_day where fund_code ='" + code + "' order by fund_date; ";
            List<String> dateStrList = JDBCUtil.getList(sql2);
            Collections.reverse(dateStrList);
            String sDate = dateStrList.get(step);
            String eDate = dateStrList.get(1);
            String uDate = startStr;


            sql = "select fund_value from t_fund_day where fund_date >=str_to_date('" + sDate + "', '%Y-%m-%d') and fund_date<=str_to_date('" + eDate + "', '%Y-%m-%d')  and fund_code='" + code + "'; ";
            System.out.println(sql);
            List<String> resultList2 = JDBCUtil.getList(sql);
            if (resultList2.size() != step) {
                break;
            }
            double[] x = new double[20];
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
            sb100.append(" where fund_code ='" + code + "' and fund_date=str_to_date('" + uDate + "', '%Y-%m-%d');");
            System.out.println(sb100.toString());
            resultList.add(sb100.toString());

//            break;
        JDBCUtil.update(resultList);
        }


    }


}
