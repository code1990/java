package webmagic;

import org.junit.Test;
import util.DateUtil;
import util.JDBCUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 911
 * @date 2020-08-31 09:31
 */
public class Fund010 {

    @Test
    public void getInfo123() {
        String sql0 = "select distinct s_type from t_s_001 order by s_type";
        List<String> typeList = JDBCUtil.getList(sql0);
        JDBCUtil.update("delete from t_s_mx ;");
        for (int k = 0; k < typeList.size(); k++) {
            String type = typeList.get(k);
            System.out.println(type);
            String sql = "select s_date,s_percent from t_s_001 where s_type='" + type + "' and s_date >=str_to_date('2018-01-01', '%Y-%m-%d')  order by s_date desc;";
            List<String[]> list = JDBCUtil.getResultList(sql);
            for (int i = 0; i < list.size(); i++) {
                String dateStr = list.get(i)[0];

                String percent = list.get(i)[1];
                int w = DateUtil.getWeekOfYear(dateStr);
                int d = DateUtil.getWeek(dateStr);
                int y = DateUtil.getYear(dateStr);
                int flag = 1;
                if (new Double(percent) < 0) {
                    flag = -1;
                }
                String sql3 = "select distinct week_id from t_s_mx where week_id=" + w + " and year_id="+y+ " and s_type= '" + type + "';";
                System.out.println(sql3);
                List<String> t = JDBCUtil.getList(sql3);
                String sql4 = "";
                if (t == null || t.size() == 0) {
                    sql4 = "insert into t_s_mx(year_id,week_id,value_" + d + ",s_type,s_date)values("+y+"," + w + "," + flag + ",'" + type + "',str_to_date('"+dateStr+"', '%Y-%m-%d'));";
                    System.out.println(">>>" + sql4);
                    JDBCUtil.update(sql4);
                } else {
                    sql4 = "update t_s_mx set value_" + d + "=" + flag + " where week_id=" + w + " and year_id="+y+" and s_type= '" + type + "';";
                    System.out.println(">>1>" + sql4);

                    JDBCUtil.update(sql4);
                }
            }
        }

    }


    @Test
    public void getInfo1231(){
        String sql0 = "select distinct s_type from t_s_001 order by s_type";
        List<String> typeList = JDBCUtil.getList(sql0);
        for (int k = 0; k < typeList.size(); k++) {
            String type = typeList.get(k);
            System.out.println(type);
            String sql = "select s_date,s_percent from t_s_001 where s_type='" + type + "' and s_date >=str_to_date('2018-01-01', '%Y-%m-%d')  order by s_date desc;";
            List<String[]> list = JDBCUtil.getResultList(sql);
            int count1 =0;
            int count2 =0;
            boolean flag = true;
            for (int i = 0; i < list.size(); i++) {
                String dateStr = list.get(i)[0];
                String percent = list.get(i)[1];
                System.out.println(percent);
                if (i==0  ) {
                    if (new Double(percent) < 0){
                        flag= false;
                    }
                    if(flag){
                        count1+=1;
                    }else{
                        count2+=-1;
                    }
                }else{
                    if (new Double(percent) < 0){
                        if(flag){
                            System.out.println(">>>>>"+count1);
                            count1=0;
                        }else{
                            count2+=-1;
                        }
                    }else{
                        if(flag){
                            count1+=1;
                        }else{
                            System.out.println(">>>>>"+count2);
                            count2=0;
                        }
                    }
                }
            }
        }
    }

    @Test
    public void getInfo0123(){
        StringBuilder sb = new StringBuilder();
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i <64 ; i++) {
            if(i==63){
                sb.append("fund_"+(i+1)+"_percent");
            }else{
                sb.append("fund_"+(i+1)+"_percent,");
            }
            map.put("fund_"+(i+1)+"_percent",0);
        }
        String sql ="select "+sb.toString()+" from t_fund_gn2_mx order by fund_date;";


        String sql2 ="select "+sb.toString()+" from t_fund_gn2_sum where fund_date = date_sub(curdate(),interval 1 day) ";
        String[] array = JDBCUtil.getResultList(sql2).get(0);
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i <array.length ; i++) {
            if(new Double(array[i])>0){
                sb2.append("'"+map.get(i+1)+"',");
            }

        }
    }


}
