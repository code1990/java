package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 911
 * @date 2020-12-22 08:36
 */
public class FundInUtil {

    private String sqlPool = "select fund_code from t_fund_pool_100;";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * http://fund.eastmoney.com/js/fundcode_search.js
     * <p>
     * QDII 无法及时分析 直接剔除
     * 专注于分析场内基金
     */
    @Test
    public void getPoolInner() throws Exception {
        String url = "http://fund.eastmoney.com/js/fundcode_search.js";
        Document document = HttpUtil.getHtml(url, "utf-8");
        String arrayStr = document.getElementsByTag("body").text().replace("var r = ", "").replace(";", "");
        JSONArray jsonArray = JSON.parseArray(arrayStr);
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String info = jsonArray.getString(i);
            if(info.contains("场内")&& !info.contains("货币型")&&!info.contains("债")){
                JSONArray array = (JSONArray) jsonArray.get(i);
                System.out.println(array.toJSONString());
                String sql = "INSERT INTO t_fund_pool_in (fund_code,fund_name, fund_type) VALUES ('" + array.getString(0) + "', '" + array.getString(2) + "', '" + array.get(3) + "');";
                resultList.add(sql);
            }
        }
        JDBCUtil.insertList(JDBCUtil.getConnection("stock"), resultList);
//        SQLiteUtil.executeSql(SQLiteUtil.getConnection("D:\\github\\java\\stock.db"),resultList);
    }


    @Test
    public void getFundBase(){
        ConnectionPool pool = new ConnectionPool("stock",10);
        List<String> list = JDBCUtil.getList(pool, sqlPool);
        List<String> list3 = new ArrayList<>();
        String currentDate = simpleDateFormat.format(new Date());
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i);
            String url = "http://fund.eastmoney.com/" + code + ".html?spm=aladin";
            System.out.println(i + "\t" + url);
            Document document = HttpUtil.getHtml(url);
            String sql2 ="";
            Elements parent = document.getElementsByClass("infoOfFund");
            //新基金
            if(document.getElementsByClass("xfinfo").size()==1){
                continue;
            }else{
                Elements elements = parent.get(0).getElementsByTag("td");
                String vm = elements.get(1).text().split("：")[1];
                if (vm.contains("亿元")) {
                    vm = vm.split("亿元")[0];
                } else {
                    vm = "0.0";
                }
                String vj = elements.get(2).text().split("：")[1];
                if (vj.contains("等")) {
                    vj = vj.split("等")[0];
                }
                String vd = elements.get(3).text().split("：")[1];
                if(vd.equals("--")){
                    vd =currentDate;
                }
                if (vm.equals("--")){
                    vm="0.0";
                }
//                sql2 = "insert into t_fund_mx_100 (fund_code,v_m,v_j,v_d)values('"+code+"','"+vm+"','"+vj+"','"+vd+"');";
                sql2 ="update t_fund_mx_100 set fund_money="+vm+",fund_manager='"+vj+"',fund_date=STR_TO_DATE('" + vd + "', '%Y-%m-%d') where fund_code ='"+code+"';";
                System.out.println(sql2);
            }
            list3.add(sql2);
        }
//        JDBCUtil.insertList(JDBCUtil.getConnection("stock"), list3);
    }


    @Test
    public void getInfo11() {
//        String sql = "select fund_code from t_fund_pool9";
        ConnectionPool pool = new ConnectionPool("stock",10);
        List<String> list = JDBCUtil.getList(pool, sqlPool);
        List<String> list3 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i);
            String url = "http://fund.eastmoney.com/" + code + ".html";
//            System.out.println(i + "\t" + url);
            Document document = HttpUtil.getHtml(url);
            String text = document.getElementsByClass("fundInfoItem").text();
            boolean f = text.contains("终止");
            //没有十大持仓信息 或者基金终止 直接删除
            if ( f) {
                System.out.println(f);
                String sql2 = "delete from t_fund_pool_100 where fund_code='" + code + "';";
                String sql3 = "delete from t_fund_mx_100 where fund_code='" + code + "';";
                System.out.println(sql2);
                System.out.println(sql3);
                list3.add(sql2);
                list3.add(sql3);
                continue;
            }
            if (text.contains("认购结束")||document.getElementsByClass("infoOfFund").size()==0){
                continue;
            }
            if(document.getElementsByClass("xfinfo").size()==1){
                continue;
            }

            Elements elements = document.getElementsByClass("dataOfFund").get(0).getElementsByClass("ui-font-middle");
//            for (int j = 0; j <elements.size() ; j++) {
//                System.out.println(elements.get(j));
//            }
            String v1 = StringUtil.convert(elements.get(2).text());
            if (v1.equals("--")) {
                v1 = "0.0";
            }
            String v3 = StringUtil.convert(elements.get(5).text());
            if (v3.equals("--")) {
                v3 = v1;
            }
            String v6 = StringUtil.convert(elements.get(7).text());
            if (v6.equals("--")) {
                v6 = v3;
            }
            String vy = StringUtil.convert(elements.get(3).text());
            if (vy.equals("--")) {
                vy = v6;
            }
            String vy3 = StringUtil.convert(elements.get(6).text());
            if (vy3.equals("--")) {
                vy3 = vy;
            }
            String vp = StringUtil.convert(document.getElementsByClass("estimated_netWrth").get(0).getElementsByTag("span").get(1).text());
            if (vp.equals("--")) {
                vp = "0.0";
            }
            Elements elements2 = document.getElementsByClass("dataItem02").get(0).getElementsByClass("dataNums").get(0).getElementsByTag("span");
            String vprice = StringUtil.convert(elements2.get(0).text());
            if (vprice.equals("--")) {
                vprice = "0.0";
            }
            String sql2 = "update t_fund_mx_100 set v_1=" + v1 + ",v_3=" + v3 + ", v_6 = " + v6 + ", v_y_1 = " + vy + ",v_y_3="+vy3+", v_percent = " + vp + ",v_price=" + vprice + " where fund_code ='" + code + "';";
            System.out.println(sql2);
//            break;
            list3.add(sql2);
        }
        JDBCUtil.update(pool, list3);
    }

}
