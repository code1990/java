package webmagic.fund;

import org.junit.Test;
import util.JDBCUtil;
import util.MathUtil;
import util.TxtUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-19 08:38
 */
public class Fund101 {
    @Test
    public void getInfo1234(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\4187.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder("create table as select * from t_fund_all where fund_code in(");
        for (int i = 0; i <list.size() ; i++) {
//            System.out.println(list.get(i));
            sb.append("'"+list.get(i)+"',");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void getInfo(){
        String sql ="select count(*) as count,gn_name from t_fund_gn2 group by gn_name order by gn_name";
        List<String[]> list = JDBCUtil.getResultList(sql);
//        HashMap<String,String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        List<String> list1= new
                ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            String[] array =list.get(i);
            String key =array[0];
            String value = array[1];
            System.out.println("insert into t_fund_gn2_info(count,gn_name)values("+key+",'"+value+"');");
//            if(key.contains("A")){
//            System.out.println(key+"\t"+value);
//                sb.append("'"+value+"',");
//            }
//            if(key.contains("C")){
//                key = array[0].replace("C","");
//            }
//            if (map.get(key)==null){
//                map.put(key,value);
//            }else{
//                System.out.println(array[0]+"\t"+value+"\t"+map.get(key));
//                if(array[0].contains("A")){
//                    value=value;
//                }
//                sb.append("'"+value+"',");
//                sb.append("'"+map.get(key)+"',");
//            }
        }
        System.out.println(sb.toString());
    }

    @Test
    public void get123Info(){
        String sql ="select id,count,gn_name from t_fund_gn2_info order by id;";
        List<String[]> list = JDBCUtil.getResultList(sql);
        List<String> dateList = JDBCUtil.getList("select  fund_date from t_fund_day where fund_code = '000001' order by fund_date;");
        for (int i = 57; i <list.size() ; i++) {
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
    public void getInfo000() throws Exception{
        for (int j = 1; j <=64 ; j++) {
            System.out.println("ALTER TABLE t_fund_gn2_mx ADD COLUMN `fund_"+j+"_percent` double  DEFAULT NULL;");
        }
//        String time = "2020-12-31";
//        DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = fm.parse(time);
////Date date2 = new Date(System.currentTimeMillis());
//        String str = String.format("%tj",date);//得到time日期是在这年的第几天
//        System.out.println(str);
    }
}
