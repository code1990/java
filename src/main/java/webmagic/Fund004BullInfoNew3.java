package webmagic;

import org.junit.Test;
import util.JDBCUtil;
import util.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-19 15:27
 * bull指标计算 使用移动窗口方式进行
 * 使用4倍标准差除以中轨 计算带宽
 */
public class Fund004BullInfoNew3 {

    @Test
    public void getInfo0123() {
        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
        int moveStep = 1;
        int winSize = 20;
        int size =2;
        List<String> codeList = JDBCUtil.getList(sql);
        for (int k = 0; k <codeList.size() ; k++) {
            String code = codeList.get(k);
            System.out.println(k + ">>>>>>>" + code);
            String sql1 = "select fund_value,fund_date from t_fund_day where fund_code ='" + code + "' order by fund_date; ";
            List<String[]> valueList = JDBCUtil.getResultList(sql1);
            int total = valueList.size();
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i <total ; i = i + moveStep) {
                if (winSize+i>=total){
                    break;
                }
                double[] x = new double[winSize];
                for (int j = 0; j <winSize ; j++) {
                    x[j]=new Double(valueList.get(j+i)[0]);
                }
                String uDate = valueList.get(winSize+i)[1];
                double bull2 = MathUtil.getAvg(x);
                double bull6 = 2*size * MathUtil.getStandardeviation(x)/bull2;
//                double bull4 = MathUtil.getAvg(x) + size * MathUtil.getStandardeviation(x);
//                double bull5 = MathUtil.getAvg(x) - size * MathUtil.getStandardeviation(x);
                StringBuilder sb100 = new StringBuilder();
                sb100.append("update t_fund_day set fund_bull_6=" + MathUtil.getRound4(bull6));
//                sb100.append(",fund_bull_4=" + MathUtil.getRound4(bull2));
//                sb100.append(",fund_bull_5=" + MathUtil.getRound4(bull5));
                sb100.append(" where fund_code ='"+code+"' and fund_date=str_to_date('"+uDate+"', '%Y-%m-%d');");
                System.out.println(sb100.toString());
                resultList.add(sb100.toString());
            }
            JDBCUtil.update(resultList);
            break;
        }
    }


}
