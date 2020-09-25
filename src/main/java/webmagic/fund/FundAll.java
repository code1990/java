package webmagic.fund;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import util.HttpClientUtil;
import util.MapUtil;
import util.TxtUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author 911
 * @date 2020-08-06 10:42
 */
public class FundAll {
    String url = "http://fund.eastmoney.com/js/fundcode_search.js";

    @Test
    public void getInfo() {
        String str = HttpClientUtil.get(url);
        str = str.replace("var r = ", "");
        str = str.replace(";", "");
        JSONArray jsonArray = JSON.parseArray(str);
//        JSONArray array = jsonArray.getJSONArray(0);
        HashSet set = new HashSet();
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123\\11.txt");
        for (int i = 0; i <list.size() ; i++) {
            System.out.println("'"+list.get(i).split("\t")[1]+"',");
        }
//        for (int i = 0; i <list.size() ; i++) {
//        for (int k = 0; k < jsonArray.size(); k++) {
//            JSONArray array = jsonArray.getJSONArray(k);
//            StringBuilder sb = new StringBuilder("insert into t_fund_all(fund_code,fund_name,fund_type)values(");
////            for (int j = 0; j < array.size(); j++) {
////                if (j == 1 || j == 4) {
////                    continue;
////                }
////                if (j == 3) {
////                    sb.append("'" + array.get(j) + "');");
////                } else {
////                    sb.append("'" + array.get(j) + "',");
////                }
////
//////                    System.out.println(array.get(j));
////            }
//
////            System.out.println(sb.toString());
////                String code = array.get(0).toString();
////                if(list.get(i).equals(code)){
////                    System.out.println(array);
////                    break;
////                }
////                System.out.println();
////            if ( info.contains("股票型") ||info.contains("QDII")||info.contains("混合型")||info.contains("股票指数")){//固定
////                if(info.contains("QDII-ETF")||info.contains("债")||info.contains("年")||info.contains("债")){//
////                    continue;
////                }
////                System.out.println(info);
////            }
////            }
//        }

//        Iterator iterator = set.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//        for (int i = 0; i <set.size() ; i++) {
//            System.out.println(set.);
//        }
//        System.out.println(jsonArray.toJSONString());
//        System.out.println(str);
    }

    @Test
    public void getInfo11() {
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123\\fund123.txt");
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            if (array.length == 2 && array[1].length() == 6) {
                System.out.println("http://fund.eastmoney.com/" + array[1] + ".html");
            }
//            else{
//                System.out.println(list.get(i));
//            }
//            System.out.println(list.get(i).split("\t").length);
        }
    }

    @Test
    public void getInfo1() {
        String url = "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=000083&page=1&per=65535&sdate=2019-03-01&edate=2019-04-1";
        String str = HttpClientUtil.get(url);
        System.out.println(str);
    }


    @Test
    public void getInfo2() {
        HashSet set = new HashSet();
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123\\11.txt");
        HashMap<Integer,String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            map.put(new Integer(array[0]),array[1]);
        }
        for (int i = 0; i <6391 ; i++) {
            System.out.println((i+1)+"\t"+map.get(i+1));
        }
//        MapUtil.
        //        Iterator iterator = set.iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }
//        for (int i = 0; i <set.size() ; i++) {
//            System.out.println(set.);
//        }
//        System.out.println(jsonArray.toJSONString());
//        System.out.println(str);
    }

    @Test
    public void getInfo11111(){
//        StringBuilder sb = new StringBuilder("sz\t");
//        for (int i = 1900001; i <= 1900116; i++) {
//            System.out.println(i);
//            sb.append(i+".txt"+"\t");
//        }
//        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\d_191.sh",sb.toString());
        for (int i = 2; i <=191 ; i++) {
            System.out.println("sh d_"+i+".sh");
        }
    }
}
