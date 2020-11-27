package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-21 10:43
 */
public class FundUtil {

    /**
     * http://fund.eastmoney.com/js/fundcode_search.js
     * <p>
     * QDII 无法及时分析 直接剔除
     * AC型基金 剔除C参考delFundAC
     */
    @Test
    public void getAllFund() throws Exception {
        String url = "http://fund.eastmoney.com/js/fundcode_search.js";
        Document document = HttpUtil.getHtml(url, "utf-8");
        String arrayStr = document.getElementsByTag("body").text().replace("var r = ", "").replace(";", "");
        JSONArray jsonArray = JSON.parseArray(arrayStr);
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String info = jsonArray.getString(i);
            if (info.contains("债") || info.contains("货币") || info.contains("年") || info.contains("月") || info.contains("理财型")) {
                continue;
            }
            if (info.contains("分级杠杆") || info.contains("固定收益") || info.contains("后端") || info.contains("场内") || info.contains("其他创新")) {
                continue;
            }
            if (info.contains("FOF") || info.contains("QDII-ETF") || info.contains("QDII-指数") || info.contains("油") || info.contains("QDII")) {
                continue;
            }
            JSONArray array = (JSONArray) jsonArray.get(i);
            System.out.println(array.toJSONString());
            String sql = "INSERT INTO t_fund_pool9 (fund_code,fund_name, fund_type) VALUES ('" + array.getString(0) + "', '" + array.getString(2) + "', '" + array.get(3) + "');";
            resultList.add(sql);
        }
        JDBCUtil.insertList(JDBCUtil.getConnection("stock"), resultList);
//        SQLiteUtil.executeSql(SQLiteUtil.getConnection("D:\\github\\java\\stock.db"),resultList);
    }

    @Test
    public void delFundAC() {
        //order by fund_name 把相同名称的ac基金集合在一起
        String sql = "select fund_code,fund_name from t_fund_pool9 order by fund_name;";
        ConnectionPool pool = new ConnectionPool("stock");
        List<String[]> list = JDBCUtil.getResultList(pool, sql);
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i);
            String code = array[0];
            String name = array[1];
            int length = name.length();
            String name2 = name.substring(0, length - 1);
            if (RegexUtil.isChar(name.substring(length - 1, length))) {
                //删除大的fund_code
                String sql2 = "select fund_code,fund_name from t_fund_pool9 where fund_name like '%" + name2 + "%' order by fund_code;";
                List<String[]> list2 = JDBCUtil.getResultList(pool, sql2);
                if (list2.size() > 1) {
                    String del = "";
                    for (int j = 0; j < list2.size(); j++) {
                        String[] array2 = list2.get(j);
                        System.out.println(Arrays.toString(array2));
                        if (array2[1].contains("A")) {
                            del = "B,C,D,E,I";
                            break;
                        }
                    }
                    {
                        String[] delArray = del.split(",");
                        for (int j = 0; j < delArray.length; j++) {
                            String sql3 = "delete from t_fund_pool9 where fund_name ='" + name2 + delArray[j] + "';";
                            JDBCUtil.update(pool, sql3);
                        }
                    }

                    if (del.equals("")) {
                        for (int j = 0; j < list2.size(); j++) {
                            String[] array2 = list2.get(j);
                            System.out.println(Arrays.toString(array2));
                            if (array2[1].contains("C")) {
                                del = "D,E,I";
                                break;
                            }
                        }
                        {
                            String[] delArray = del.split(",");
                            for (int j = 0; j < delArray.length; j++) {
                                String sql3 = "delete from t_fund_pool9 where fund_name ='" + name2 + delArray[j] + "';";
                                JDBCUtil.update(pool, sql3);
                            }
                        }
                    }
                    if (del.equals("")) {
                        for (int j = 0; j < list2.size(); j++) {
                            String[] array2 = list2.get(j);
                            System.out.println(Arrays.toString(array2));
                            if (array2[1].contains("E")) {
                                del = "I";
                                break;
                            }
                        }
                        String sql3 = "delete from t_fund_pool9 where fund_name ='" + name2 + "I';";
                        JDBCUtil.update(pool, sql3);
                    }

                }

            }

        }
    }

    @Test
    public void delSqlite() {
//        String sql ="select fund_code from t_fund_pool9";
        ConnectionPool pool = new ConnectionPool("stock");
//        List<String> list = JDBCUtil.getList(pool,sql);
//        for (int j = 0; j <list.size() ; j++) {
//            String code = list.get(j);
//            String url = "http://fundf10.eastmoney.com/jbgk_"+code+".html";
//            Document document = HttpUtil.getHtml(url);
//            Elements elements = document.getElementsByClass("txt_in").get(0).getElementsByClass("info w790").get(0).getElementsByTag("tr");
//            System.out.println(code+"\t"+elements.get(elements.size()-1).text().replaceAll(" ","\t"));
//        }

        List<String> list = TxtUtil.readTxt(new File("C:\\Users\\xiala\\Desktop\\1233 - 副本.txt"));
//        String sql ="select fund_code from t_fund_pool9 order by fund_code";
        List<String> list3 = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
            String info = list.get(i);
            String[] array = info.split("\t");
            String bd = "";
            String code = array[0];
            String mx = "";
            if (info.contains("保本模式")) {
                continue;
            }
            if (!info.contains("无跟踪")) {
                bd = array[array.length - 1];
            }


            if (!array[2].split("\\+")[0].contains("指数")) {
//                    System.out.println(info);
                info = info.split("业绩比较基准")[1].split("跟踪标的")[0];
                info = info.replace("\t", "");
                info = info.replace(" ", "");

                if (info.contains("定期") || info.contains("该基金暂未披露") || info.contains("定存")) {
                    System.out.println(array[0] + "\t" + "" + "\t" + bd);
                    mx = "";
                } else {
                    if (info.contains("+")) {
//                            System.out.println(info.split("\\+")[0]);
//                            System.out.println(array[0]+"\t"+info.split("\\+")[0]+"\t"+bd);
                        mx = info.split("\\+")[0];
                    } else {
//                            System.out.println(info);
//                            System.out.println(array[0]+"\t"+info+"\t"+bd);
                        mx = info;
                    }

                }
            } else {
//                    System.out.println(array.length+"\t"+array[2].split("\\+")[0].replace("×","*"));
                System.out.println(array[0] + "\t" + array[2].split("\\+")[0].replace("×", "*") + "\t" + bd);
                mx = array[2].split("\\+")[0].replace("×", "*");
            }
//                String zn="上证红利";
//            if(info.contains(zn)){
//                String zs ="000015";
////                if(info.contains("中证高端制造")){
////                    zs ="zs930820";
////                    zn="中证高端制造";
////                }else if(info.contains("中证1000")){
////                    zs ="zs000852";
////                    zn="中证1000";
////                }else if(info.contains("中证内地消费")){
////                    zs ="zs000942";
////                    zn="中证内地消费";
////                }else if(info.contains("中证新兴产业")){
////                    zs ="zs000964";
////                    zn="中证新兴产业";
////                }
//                String code=info.split("\t")[0];
            String sql2 = "update t_fund_mx set zs_mx='" + mx + "',zs_bd='" + bd + "' where fund_code ='" + code + "'";
//                System.out.println(sql2);
            list3.add(sql2);
//                sb.append(""+"\n");
//            }else{
//                if(info.contains("定期存款")){
//                    sb.append(""+"\n");
//                }else{
//                    sb.append(info+"\n");
//                }
//
//            }
        }
//        System.out.println(sb.toString());
        JDBCUtil.update(pool, list3);
//        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\1233.txt",sb.toString());
    }

    /**
     * 删除无法定量分析的基金 删除已经终止的基金
     */
    @Test
    public void checkInfo() {
        String sql = "select fund_code from t_fund_pool9";
        ConnectionPool pool = new ConnectionPool("stock");
        List<String> list = JDBCUtil.getList(pool, sql);
        List<String> list3 = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            String code = list.get(j);
            String url = "http://fund.eastmoney.com/" + code + ".html";
            Document document = HttpUtil.getHtml(url);
            Elements elements = document.getElementsByClass("ui-table-hover").get(0).getElementsByTag("tr");

            System.out.println(j + "\t" + url);
            boolean f = document.getElementsByClass("fundInfoItem").text().contains("基金已终止");
            if (elements.size() != 11 || f) {
                System.out.println(f);
//                System.out.println(elements.size());
//                System.out.println(elements.text());
                String sql2 = "delete from t_fund_pool9 where fund_code='" + code + "';";
                String sql3 = "delete from t_fund_mx where fund_code='" + code + "';";
                list3.add(sql2);
                list3.add(sql3);
            }
        }
        JDBCUtil.update(pool, list3);
    }

    /**
     * 在A股市场长期投资就是四大方向：消费、医药、金融、科技。
     * 这四大板块往往是一轮牛熊之后会创一次新高。
     * 另外周期性板块方向是券商、有色、军工、钢铁、煤炭、半导体、芯片等等。
     */
    @Test
    public void getInfo11() {
        String sql = "select fund_code from t_fund_pool9";
        ConnectionPool pool = new ConnectionPool("stock");
        List<String> list = JDBCUtil.getList(pool, sql);
        List<String> list3 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i);
            String url = "http://fund.eastmoney.com/" + code + ".html?spm=aladin";
            System.out.println(i + "\t" + url);
            Document document = HttpUtil.getHtml(url);
            Elements elements = document.getElementsByClass("dataOfFund").get(0).getElementsByClass("ui-font-middle");
            String v1 = convert(elements.get(2).text());
            if (v1.equals("--")) {
                v1 = "0.0";
            }
            String v3 = convert(elements.get(5).text());
            if (v3.equals("--")) {
                v3 = v1;
            }
            String v6 = convert(elements.get(7).text());
            if (v6.equals("--")) {
                v6 = v3;
            }
            String vy = convert(elements.get(3).text());
            if (vy.equals("--")) {
                vy = v6;
            }
            String vp = convert(document.getElementsByClass("estimated_netWrth").get(0).getElementsByTag("span").get(1).text());
            if (vp.equals("--")) {
                vp = "0.0";
            }
            Elements elements2 = document.getElementsByClass("dataItem02").get(0).getElementsByClass("dataNums").get(0).getElementsByTag("span");
            String vprice = convert(elements2.get(0).text());
            if (vprice.equals("--")) {
                vprice = "0.0";
            }
            String sql2 = "update t_fund_mx set v_1='" + v1 + "',v_3='" + v3 + "', v_6 = '" + v6 + "', v_y = '" + vy + "', v_p = '" + vp + "',v_price=" + vprice + " where fund_code ='" + code + "';";
            System.out.println(sql2);
            list3.add(sql2);
        }
        JDBCUtil.update(pool, list3);
    }

    public String convert(String value) {
        if (value.contains("%")) {
            value = value.replace("%", "");
        }
        return value;
    }

    @Test
    public void getBaseInfo() {
        String sql = "select fund_code from t_fund_pool9";
        ConnectionPool pool = new ConnectionPool("stock");
        List<String> list = JDBCUtil.getList(pool, sql);
        List<String> list3 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String code = list.get(i);
            String url = "http://fund.eastmoney.com/" + code + ".html?spm=aladin";
            System.out.println(i + "\t" + url);
            Document document = HttpUtil.getHtml(url);
            Elements elements = document.getElementsByClass("infoOfFund").get(0).getElementsByTag("td");
            String vm = convert(elements.get(1).text().split("：")[1]);
            if (vm.contains("亿元")) {
                vm = vm.split("亿元")[0];
            } else {
                vm = "0.0";
            }
            String vj = convert(elements.get(2).text().split("：")[1]);
            if (vj.contains("等")) {
                vj = vj.split("等")[0];
            }
            String vd = convert(elements.get(3).text().split("：")[1]);
            String sql2 = "update t_fund_mx set v_m='" + vm + "',v_j='" + vj + "', v_d = '" + vd + "' where fund_code ='" + code + "';";
            System.out.println(sql2);
            list3.add(sql2);
        }
        JDBCUtil.update(pool, list3);
    }

    @Test
    public void getInfo111() {
        String url = "http://quotes.money.163.com/trade/lsjysj_zhishu_399803.html";
        Document document = HttpUtil.getHtml(url);
        Elements elements = document.getElementsByClass("table_bg001 border_box limit_sale").get(0).getElementsByTag("tr");
        for (int i = 1; i < 20; i++) {
            Elements elements2 = elements.get(i).getElementsByTag("td");
            Kline line = new Kline();
            line.setOpen(new Double(elements2.get(1).text().replace(",", "")));
            line.setHigh(new Double(elements2.get(2).text().replace(",", "")));
            line.setLow(new Double(elements2.get(3).text().replace(",", "")));
            line.setClose(new Double(elements2.get(4).text().replace(",", "")));
            System.out.println(line.toString());
//            System.out.println(elements.get(i).text());
        }
    }

//    @Test
//    public void getPrice(){
//        String sql ="select fund_code from t_fund_pool9";
//        ConnectionPool pool =new ConnectionPool("stock");
//        List<String> list = JDBCUtil.getList(pool,sql);
//        List<String> list3 = new ArrayList<>();
//        for (int i = 0; i <list.size() ; i++) {
//            String code = list.get(i);
//            String url ="http://fund.eastmoney.com/"+code+".html?spm=aladin";
//            System.out.println(i+"\t"+url);
//            Document document = HttpUtil.getHtml(url);
//            Elements elements = document.getElementsByClass("dataItem02").get(0).getElementsByClass("dataNums").get(0).getElementsByTag("span");
//            String vprice = convert(elements.get(0).text());
//            if(vprice.equals("--")){
//                vprice="0.0";
//            }
//            String sql2 ="update t_fund_mx set v_price="+vprice+" where fund_code ='"+code+"';";
//            System.out.println(sql2);
//            list3.add(sql2);
//        }
//        JDBCUtil.update(pool,list3);
//    }


    @Test
    public void getInfo1111() {
        String url = "http://push2his.eastmoney.com/api/qt/stock/kline/get?cb=jQuery112408910454438758645_1606099316169&secid=1.000001&ut=fa5fd1943c7b386f172d6893dbfba10b&fields1=f1%2Cf2%2Cf3%2Cf4%2Cf5&fields2=f51%2Cf52%2Cf53%2Cf54%2Cf55%2Cf56%2Cf57%2Cf58&klt=101&fqt=0&beg=19900101&end=20220101&_=1606099316213";
        Document document = HttpUtil.getHtml(url);
        String text = document.getElementsByTag("pre").text();
        List<String> list = TxtUtil.readTxt(new File("C:\\Users\\xiala\\Desktop\\123.txt"));
        JSONObject jsonObject = JSON.parseObject(list.get(0));
        System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("klines");
        System.out.println(jsonArray.size());
        //http://quotes.money.163.com/trade/lsjysj_zhishu_000001.html
        //网易 日期 	开盘价 	最高价 	最低价 	收盘价 	涨跌额 	涨跌幅(%) 	成交量(股) 	成交金额(元)
        //东财 日期 	收盘价  开盘价 	最高价 	最低价 	涨跌额 	涨跌幅(%) 	成交量(股) 	成交金额(元)
        //网易是没有复权的数据
        //东财是复权后的数据
        ConnectionPool pool = new ConnectionPool("stock");
        List<String> list3 = new ArrayList<>();
        String zname = "上证指数";
        String zcode = "000001";
        for (int i = 0; i < jsonArray.size(); i++) {
            String[] array = jsonArray.getString(i).split("\\,");
            String vdate = array[0];
            String vopen = array[1];
            String vclose = array[2];
            String vhigh = array[3];
            String vlow = array[4];
            String vp = array[7];
            String sql = "INSERT INTO zs_mx (zs_name, zs_code, v_date, v_open, v_close, v_high, v_low, v_p) VALUES ('" + zname + "', '" + zcode + "', '" + vdate + "', '" + vopen + "', '" + vclose + "', '" + vhigh + "', '" + vlow + "', " + vp + ");";
            System.out.println(sql);
            list3.add(sql);
        }
        JDBCUtil.update(pool, list3);
    }

    @Test
    public void getFundDayInfo() {
        List<String> list = TxtUtil.readTxt(new File("date.txt"));
        List<String> urlList = new ArrayList<>();
        String code = "000001";
        String startDate = "";
        String endDate = "";
        for (int i = 0; i < list.size(); ) {
            if (i + 20 - 1 < list.size()) {
                startDate = list.get(i);
                endDate = list.get(i + 20 - 1);
                i += 20;
                System.out.println(startDate + "\t" + endDate);
            } else {
                startDate = list.get(i);
                endDate = list.get(list.size() - 1);
                i += 20;
            }
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code=" + code + "&page=1&per=65535&sdate=" + startDate + "&edate=" + endDate;
            System.out.println(url);
            urlList.add(url);
        }
        ConnectionPool pool = new ConnectionPool("stock");
        String sql2 = "select fund_code from t_fund_pool9 order by fund_code;";
        List<String> codeList = JDBCUtil.getList(pool,sql2);
        for (int k = 1367; k <codeList.size() ; k++) {
            code = codeList.get(k);

            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < urlList.size(); i++) {
                String url = urlList.get(i);
                url = url.replace("000001",code);
                System.out.println(code+"\t"+url);
                Document document = HttpUtil.getHtml(url);

                Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
                List<String> htmlList = new ArrayList<>();
                for (int kk = elements.size() - 1; kk >= 1; kk--) {
                    String str = elements.get(kk).text();
                    if (str.contains("暂无数据")) {
                        continue;
                    }
                    System.out.println(str);
                    htmlList.add(str);
                }
                for (int kkk = 0; kkk < htmlList.size(); kkk++) {
                    String str = htmlList.get(kkk);
                    String[] array = str.split(" ");
                    String percent = "0.0";
                    if(array.length==1){
                        continue;
                    }
                    if (array.length == 5 && array[2].contains("%")) {
                        percent = array[2].replace("%", "");
                    } else if (array.length == 6 && array[3].contains("%")) {
                        percent = array[3].replace("%", "");
                    }
                    sql2 = "insert into fund_mx(fund_code,fund_date,fund_value,fund_percent)values('" + code + "',STR_TO_DATE('" + array[0] + "', '%Y-%m-%d')," + array[1] + "," + percent + ");";
                    System.out.println(sql2);
                    resultList.add(sql2);
                }
            }
            for (int i = 0; i <resultList.size() ; i++) {
                System.out.println(resultList.get(i));
            }
//            break;
          JDBCUtil.insertList(pool,resultList);

        }
    }

    @Test
    public void updateIndexInfo(){
        //使用网易的不复权的历史数据
        //东财 复权2020-11-20 之前使用东财
        //每日更新使用 网易
        //http://quotes.money.163.com/trade/lsjysj_zhishu_000001.html
        String url ="http://quotes.money.163.com/trade/lsjysj_zhishu_000001.html";
        Document document = HttpUtil.getHtml(url);
        Elements elements = document.getElementsByClass("table_bg001 border_box limit_sale").get(0).getElementsByTag("tr");
        //日期 开盘价 最高价 最低价 收盘价 涨跌额 涨跌幅(%) 成交量(股) 成交金额(元)
        for (int i = 1; i <elements.size() ; i++) {
            System.out.println(elements.get(i).text());
        }
    }

    //Ma指标说明
    //一阴穿三线 可以加仓
    //5日>10日>20日 阴线穿越5日均线 危险信号
    //收盘价落在20日均线附近 10日>20日 落在20日下方视为加仓信号
    //5日均线 上穿越10日均线是为加仓信号 金叉
    //5日均线 下穿10日均线 视为危险信号 死叉
    //20日均线在10日均线上方 行情不加 下穿注意减仓
    //大阴线在下轨附近视为市场见底 可以加仓博弈 且20日均线在10日均线上方
    //葛兰碧八大法则：
    //    MA均线
    //（1）平均线从下降逐渐转为盘式上升，而价格从平均线下方突破平均线，为买进信号。
    //            （2）价格虽然跌破平均线，但又立刻回升到平均线上，此时平均线仍然持续上升，仍为买进信号。
    //            （3）价格趋势走在平均线上，价格下跌并未跌破平均线且立刻反转上升，也是买进信号。
    //            （4）价格突然暴跌，跌破平均线，且远离平均线，则有可能反弹上升，也为买进时机。
    //            （5）平均线从上升逐渐转为盘局或下跌，而价格向下跌破平均线，为卖出信号。
    //            （6）价格虽然向上突破平均线，但又立刻回跌至平均线下，此时平均线仍然持续下降，仍为卖出信号。
    //            （7）价格趋势走在平均线下，价格上升并未突破平均线且立刻反转下跌，也是卖出信号。
    //            （8）价格突然暴涨，突破平均线，且远离平均线，则有可能反弹回跌，也为卖出时机。
    //    对葛兰碧法则的记忆，只要掌握了支撑和压力的思想就不难记住。

    //①乖离率可分为正乖离率与负乖离率，若股价在平均线之上，则为正乖离；股价在平均线之下，则为负乖离；当股价与平均线相交时，则乖离率为零。正的乖离率愈大，表示短期获利愈大，则获利回吐可能性愈高；负的乖离率愈大，则空头回补的可能性也愈高。
//②股价与十日平均线乖离率达+8%以上为超买现象，是卖出时机；当其达-8%以下时为超卖现象，为买入时机。
//            ③股价与三十日平均线乖离率达+16%以上为超买现象，是卖出时机；当其达－16%以下为超卖现象，为买入时机。
//            ④个别股因受多空激战的影响，股价与各种平均线的乖离容易偏高，但发生次数并不多。
//            ⑤每股行情股价与平均线之间的乖离率达到最大百分比时，就会向零值靠近，甚至会低于零或高于零，这是正常现象。
//            ⑥多头市场的暴涨与空头市场的暴跌，会使乖离达到意想不到的百分比，但出现次数极少，时间也短，可视为一特例。
//            ⑦在大势上升市场如遇负乖离，可以持回跌价买进，因为进场危险性小。
//            ⑧在大势下跌的走势中如遇正乖离，可以持回升高价出售。
}
