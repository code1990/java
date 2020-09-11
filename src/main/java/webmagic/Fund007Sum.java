package webmagic;

import org.junit.Test;
import util.JDBCUtil;
import util.MapUtil;
import util.TxtUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 911
 * @date 2020-08-19 16:30
 */
public class Fund007Sum {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentStr = sdf.format(new Date());

    String str ="";

    @Test
    public void getInfo(){
        String sql ="select fund_code from t_fund_pool6 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(sql);
        for (int k = 0; k <codeList.size() ; k++) {
            List<String> urlList = new ArrayList<>();
            String code = codeList.get(k);
            System.out.println(k + ">>>>>>" + code);
            String sql1 ="select max(fund_date) as fund_date from t_fund_day where fund_code ='"+code+"';";
            String startStr = JDBCUtil.getList(sql1).get(0);
            String sql3 ="select fund_value,fund_percent from t_fund_day where fund_code ='"+code+"' and fund_date=str_to_date('"+startStr+"', '%Y-%m-%d');";
            List<String[]> tmpList = JDBCUtil.getResultList(sql3);
            System.out.println(sql3);
            String value = tmpList.get(0)[0];
            String percent = tmpList.get(0)[1];
            System.out.println(value+"\t"+percent);

        }
    }


    @Test
    public void getInfo111(){
        String path ="C:\\Users\\xiala\\Desktop\\1233.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String key = list.get(i).split("\t")[0];
            String value = list.get(i).split("\t")[1];
            list2.add("insert into t_fund_gn2(gn_name,fund_code)values('"+key+"','"+value+"');");
        }
        JDBCUtil.insertList(list2);
//        MapUtil.sortDesc(map);
    }

    @Test
    public void getInfo666(){
        String sql ="select distinct gn_name from t_fund_gn2 order by gn_name;";
        List<String> list = JDBCUtil.getList(sql);
        System.out.println(Arrays.toString(list.toArray()));
    }

}
