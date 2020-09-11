package webmagic.fund;

import org.junit.Test;
import util.JDBCUtil;

import java.util.*;

/**
 * @author 911
 * @date 2020-08-26 16:28
 */
public class FundGnMx {

    @Test
    public void getInfo(){
        String sql ="select * from t_fund_gn2_mx where fund_date = date_sub(curdate(),interval 1 day); ";
        List<String[]> list = JDBCUtil.getResultList(sql);
        String sql2 = "select id,gn_name from t_fund_gn2_info order by id;";
        List<String[]> list2 = JDBCUtil.getResultList(sql2);
        List<Integer> list3 = new ArrayList<>();
        for (int i = 3; i <list.get(0).length ; i++) {
            if(new Double(list.get(0)[i])>0){
                list3.add(i-2);
            }
        }
        StringBuilder sb = new StringBuilder();
        Map<Integer,String> map = new HashMap<>();
        for (int i = 0; i <list2.size() ; i++) {
            map.put(new Integer(list2.get(i)[0]),list2.get(i)[1]);
        }
        for (int k = 0; k <list3.size() ; k++) {
            System.out.println(map.get(list3.get(k)));
            sb.append("'"+map.get(list3.get(k))+"',");
        }
        String sql5 = "select count(*) as count,fund_code from t_fund_gn2 where gn_name in ("+sb.toString().substring(0,sb.length()-1)+") group by fund_code order by count asc;";
        List<String[]> list5 = JDBCUtil.getResultList(sql5);
        for (int i = 0; i <list5.size() ; i++) {
            System.out.println(Arrays.toString(list5.get(i)));
        }
        System.out.println(sql5);
//        for (int i = 0; i <list2.size() ; i++) {
//            sb.append(li)
//        }
//        System.out.println(Arrays.toString(list.toArray()));
//        for (int i = 0; i <list.size() ; i++) {
//            System.out.println(list.get(i)[1]);
//        }
    }
}
