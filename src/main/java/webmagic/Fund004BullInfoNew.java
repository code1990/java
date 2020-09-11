//package webmagic;
//
//import org.junit.Test;
//import util.JDBCUtil;
//import util.MathUtil;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author 911
// * @date 2020-08-19 15:27
// * bull指标计算 使用移动窗口方式进行
// */
//public class Fund004BullInfoNew {
//
//    @Test
//    public void getInfo0123() {
//        String sql = "select fund_code from t_fund_pool6 order by fund_code;";
//        int moveStep = 1;
//        int winSize = 20;
//        List<String> codeList = JDBCUtil.getList(sql);
//        for (int k = 337; k < codeList.size(); k++) {
//            String code = codeList.get(k);
//            System.out.println(k + ">>>>>>>" + code);
//            String sql1 = "select fund_value,fund_date from t_fund_day where fund_code ='" + code + "' order by fund_date; ";
//            List<String[]> valueList = JDBCUtil.getResultList(sql1);
//            int total = valueList.size();
//            List<String> resultList = new ArrayList<>();
//            long start = System.currentTimeMillis();
//            for (int i = 0; i <total ; i = i + moveStep) {
//                if (winSize+i>=total){
//                    break;
//                }
//                double[] x = new double[winSize];
//                for (int j = 0; j <winSize ; j++) {
//                    x[j]=new Double(valueList.get(j+i)[0]);
//                }
//                String uDate = valueList.get(winSize+i)[1];
//                double bull2 = MathUtil.getAvg(x);
//                double bull1 = MathUtil.getAvg(x) + 3 * MathUtil.getStandardeviation(x);
//                double bull3 = MathUtil.getAvg(x) - 3 * MathUtil.getStandardeviation(x);
//                StringBuilder sb100 = new StringBuilder();
//                sb100.append("update t_fund_day set fund_bull_1=" + MathUtil.getRound4(bull1));
//                sb100.append(",fund_bull_2=" + MathUtil.getRound4(bull2));
//                sb100.append(",fund_bull_3=" + MathUtil.getRound4(bull3));
//                sb100.append(" where fund_code ='"+code+"' and fund_date=str_to_date('"+uDate+"', '%Y-%m-%d');");
//                resultList.add(sb100.toString());
//            }
//            JDBCUtil.update(resultList);
//            long end = System.currentTimeMillis();
//            double timeDouble= Double.parseDouble(Long.toString(end-start));
//            System.out.println("耗时>>>" + timeDouble/(double)1000 + "秒");
//        }
//    }
//
//
//}
