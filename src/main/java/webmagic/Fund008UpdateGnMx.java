package webmagic;

import org.junit.Test;
import util.DateUtil;
import util.JDBCUtil;
import util.MathUtil;

import java.util.*;

/**
 * @author 911
 * @date 2020-08-25 12:39
 * //https://mirrors.cloud.tencent.com/mysql/downloads/MySQL-5.7/
 */
public class Fund008UpdateGnMx {

    @Test
    public void updateGnInfo(){
        updateGnMx();
        updateGnSum();
    }

    @Test
    public void updateGnMx(){
        String sql ="select id,count,gn_name from t_fund_gn2_info order by id;";
        List<String[]> list = JDBCUtil.getResultList(sql);
        List<String> dateList = JDBCUtil.getList("select  fund_date from t_fund_day where fund_code = '000001' and fund_date not in (select fund_date from t_fund_gn2_mx) order by fund_date desc;");
        for (int i = 0; i <list.size() ; i++) {
            String[] array = list.get(i);
            String id =array[0];
            String gn= array[2];
            Double count1 = new Double(array[1]);
            List<String> resutList = new ArrayList<>();
            System.out.println(i+">>>"+gn);
            for (int j = 0; j <dateList.size() ; j++) {
                String date = dateList.get(j);
                String sql2 ="select sum(fund_percent) as count from t_fund_day where  fund_date = STR_TO_DATE('"+date+"', '%Y-%m-%d') and fund_code in (select fund_code from t_fund_gn2 where gn_name = '"+gn+"');";
                String per ="";
                if(null==JDBCUtil.getList(sql2)||JDBCUtil.getList(sql2).size()==0){
                    per="0.0";
                }else{
                    Double count2 = new Double(JDBCUtil.getList(sql2).get(0));
                    per = MathUtil.getRound4(count2/count1);
                }
                String sql5="";
                if(i==0){
                    sql5 = "insert into t_fund_gn2_mx(gn_name,fund_date,fund_"+id+"_percent) values('"+gn+"',str_to_date('"+date+"', '%Y-%m-%d'), "+per+");";
                }else{
                    sql5 = "update t_fund_gn2_mx set gn_name='"+gn+"',fund_"+id+"_percent="+per+" where fund_date = str_to_date('"+date+"', '%Y-%m-%d');";
                }
                System.out.println(sql5);
                resutList.add(sql5);
            }
            JDBCUtil.insertList(resutList);
        }
    }

    @Test
    public void updateGnSum(){
        String sql ="select id from t_fund_gn2_info order by id;";
        List<String[]> list = JDBCUtil.getResultList(sql);
        String sql100="select fund_date from t_fund_gn2_mx where fund_date not in (select fund_date from t_fund_gn2_sum) order by fund_date";
        List<String[]> list100 = JDBCUtil.getResultList(sql100);
        for (int i = 0; i <list.size() ; i++) {
            String[] array = list.get(i);
            String id =array[0];
            List<String> resultList = new ArrayList<>();
            for (int j = 0; j <list100.size() ; j++) {
                String sql11000 = "select fund_"+(i+1)+"_percent,fund_date from t_fund_gn2_mx where fund_date = STR_TO_DATE('"+list100.get(j)[0]+"', '%Y-%m-%d')";
                List<String[]> list2 = JDBCUtil.getResultList(sql11000);
                System.out.println(sql11000);
                for (int k = 0; k < list2.size(); k++) {
                    double per = new Double(list2.get(k)[0]);
                    String date = list2.get(k)[1];
                    String sql5="";
                    if(i==0){
                        sql5 = "insert into t_fund_gn2_sum(fund_date,fund_"+id+"_percent) values(str_to_date('"+date+"', '%Y-%m-%d'), "+per+");";
                    }else{
                        sql5 = "update t_fund_gn2_sum set fund_"+id+"_percent="+per+" where fund_date = str_to_date('"+date+"', '%Y-%m-%d');";
                    }
                    System.out.println(sql5);
                    resultList.add(sql5);
                }
            }
//            String sql100="select fund_"+id+"_percent,fund_date from t_fund_gn2_mx where fund_date not in (select fund_date from t_fund_gn2_sum);";
            System.out.println(sql100);

            JDBCUtil.insertList(resultList);
//            break;
        }
    }

    @Test
    public void getInfo(){
        List<String[]> list = JDBCUtil.getResultList("select id,gn_name from t_fund_gn2_info");
        Map<Integer,String> map = new LinkedHashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String[] array = list.get(i);
            map.put(new Integer(array[0]),array[1]);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <64 ; i++) {
            if(i==63){
                sb.append("fund_"+(i+1)+"_percent");
            }else{
                sb.append("fund_"+(i+1)+"_percent,");
            }

        }
        String sql2 ="select "+sb.toString()+" from t_fund_gn2_sum where fund_date = date_sub(curdate(),interval 1 day) ";
        String[] array = JDBCUtil.getResultList(sql2).get(0);
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i <array.length ; i++) {
            if(new Double(array[i])>0){
                sb2.append("'"+map.get(i+1)+"',");
            }
        }
        String queryStr = sb2.toString().substring(0,sb2.length()-1);
        String sql3 = "select count(*) as count,fund_code from t_fund_gn2 where gn_name in ("+queryStr+") group by fund_code order by count asc";
        List<String[]> list3 = JDBCUtil.getResultList(sql3);
        for (int i = 0; i <list3.size() ; i++) {
            String[] array3 = list3.get(i);
            String code = array3[1];
            String dateStr = DateUtil.getYesterday();
            String[] info = getAddCount(dateStr,code);
            System.out.println(array3[0]+"\t"+array3[1]+"\t"+info[0]+"\t"+info[1]);
        }
    }

    public String[] getAddCount(String sDate,String code){
        int count =0;
        double value =0;
        String[] array = new String[2];
        String sql = "select fund_percent from t_fund_day where fund_date <=str_to_date('"+sDate+"', '%Y-%m-%d') and fund_code ='"+code+"' order by fund_date desc limit 7;";
//        System.out.println(sql);
        List<String> resultList2 = JDBCUtil.getList(sql);
        for (int i = 0; i <resultList2.size() ; i++) {
            Double v = new Double(resultList2.get(i));
            if (v<0){
                continue;
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
