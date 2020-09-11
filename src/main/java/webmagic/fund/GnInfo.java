package webmagic.fund;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.*;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author 911
 * @date 2020-08-10 11:09
 */
public class GnInfo {
    static String target = "C:\\Users\\xiala\\Desktop\\123\\1200.txt";
    //    static List<String> list = TxtUtil.readTxt(path);
    static StringBuilder all = new StringBuilder();
    HttpClient httpClient = HttpClients.createDefault();

    @Test
    public void getInfo() {
        String path = "C:\\Users\\xiala\\Desktop\\123\\0810.txt";
        List<String> list = TxtUtil.readTxt(path);
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            String comCode = array[1].replace(" ", "");
            String comName = array[2].replace(" ", "");
            String gn = array[3].replace(" ", "");
            sb.append("insert into t_fund_gn_mx(com_code,com_name,gn_name)values('" + comCode + "','" + comName + "','" + gn + "');\n");

            if (map1.get(comCode) == null) {
                map1.put(comCode, gn);
            } else {
                map1.put(comCode, map1.get(comCode) + "," + gn);
            }
            map2.put(comCode, comName);
//            System.out.println();
        }
        Map<String, String> readMap = TxtUtil.readMap("C:\\Users\\xiala\\Desktop\\123\\1233.txt");
        Iterator entries = map1.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            System.out.println("alter table t_com_hy_gn_mx set gn_name='" + entry.getValue() + "' where com_code='" + entry.getKey() + "';");
//            System.out.println(entry.getKey() +"\t"+map2.get(entry.getKey())+"\t"+readMap.get(entry.getKey())+ "\t" + entry.getValue());
//            System.out.println("'"+entry.getKey()+"',");
//            System.out.println("insert into t_com_hy_gn(com_code,com_name,hy_name,gn_name)values('"+entry.getKey() + "','" + entry.getValue()+"','"+map2.get(entry.getKey()));
        }
//        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\insert_t_fund_hy.sql",sb.toString());
//        MapUtil.printMap(map1);
//        MapUtil.printMap(map2,map1);
    }

    @Test
    public void getInfo009() throws Exception {
        String path = "C:\\Users\\xiala\\Desktop\\123\\001jj.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = new ArrayList<>();//TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123\\002jj.txt");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str.contains("开放") && str.contains("债")) {
                System.out.println(str);
                list2.add(str.split("\t")[0]);
            }
//            String code = list.get(i).split("\t")[0];
//            String price = list.get(i).split("\t")[1];
//            map.put(code,price);

        }
//        for (int i = 0; i < list2.size(); i++) {
//            String code = list2.get(i);
//            System.out.println(code+"\t"+map.get(code));
//        }
        String startDate = "2020-07-10";
        String endDate = "2020-08-11";
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list2.size(); i++) {//list.size()
            String url = "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=" + list2.get(i) + "&page=1&per=65535&sdate=" + startDate + "&edate=" + endDate;
            System.out.println((i + 1) + "\t" + url);
            String code = url.split("&code=")[1].split("&")[0];
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            StringBuilder sb = new StringBuilder();
            for (int j = elements.size() - 1; j >= 1; j--) {//elements.size() -1
//            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                System.out.println(str);
                sb.append(code + "\t" + str + "\n");
            }
            all.append(sb.toString());
            TxtUtil.writeTxt(target, all.toString());
            System.out.println(sb.toString());
        }

        List<String> list3 = TxtUtil.readTxt(target);
        Map<String, String> map3 = new HashMap<>();

        for (int i = 0; i < list3.size(); i++) {
            String[] array = list3.get(i).split("\t");
            String key = array[0];
            String value = array[1];
            if (map3.get(array[0]) == null) {
                map3.put(key, value + "\t");
            } else {

                map3.put(key, new StringBuilder(map3.get(key)).append(value + "\t").toString());
            }

        }
        StringBuilder sb2 = new StringBuilder();
        for (Map.Entry<String, String> entry : map3.entrySet()) {
            StringBuilder sb = new StringBuilder();
            String[] array = entry.getValue().split("\t");
            for (int i = 0; i < array.length; i++) {
//                System.out.println(array[i].split(" ")[3].replace("%",""));
                String str = array[i].split(" ")[3];
                str = str.replace("%", "");
                if (RegexUtil.isContainChinese(str)) {
//                    System.out.println(array[i]);
                    break;
                }
//                str = str.replace("封闭期","0.00");
//                str = str.replace("申购","0.00");
//                str = str.replace("开放","");
//                str = str.replace("限制大额","");
//                str = str.replace("暂停","");
                sb.append(str + "\t");
            }
            if (array.length == 20 && sb.toString().length() > 0) {
                System.out.println();
                sb2.append("#" + entry.getKey() + "\t" + sb.toString() + "\n");
            }
//            System.out.println(array.length);

        }
        TxtUtil.writeTxt(target, sb2.toString());
    }

    @Test
    public void getInfo000() throws Exception {
        String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                ".aspx?type=lsjz&code=007874&page=1&per=65535&sdate=2020-01-01&edate=2020-02-01";
        String code = "007874";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
        HttpResponse response = httpClient.execute(httpGet);
        String contents = EntityUtils.toString(response.getEntity(), "gbk");
        Document document = Jsoup.parse(contents);

        Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        for (int j = elements.size() - 1; j >= 1; j--) {//elements.size() -1
//            for (int j = 1; j < elements.size(); j++) {
            String str = elements.get(j).text();
            if (str.contains("暂无数据")) {
                continue;
            }
//            System.out.println(str);
            sb.append(code + "\t" + str + "\n");
            list.add(str);
        }
        for (int i = 0; i < list.size(); i++) {
//            DecimalFormat df = new DecimalFormat("#.00");
            String[] array = list.get(i).split(" ");
            System.out.println(array[0] + "\t" + array[1]);
        }
    }

    @Test
    public void getInfo0002() throws Exception {
        List<String> dateList = DateUtil.getDate(2020);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dateList.size(); i++) {
            String startDate = dateList.get(i).split("\t")[0];
            String endDate = dateList.get(i).split("\t")[1];
            String code = "007874";
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code=" + code + "&page=1&per=65535&sdate=" + startDate + "&edate=" + endDate;
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            for (int j = elements.size() - 1; j >= 1; j--) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                sb.append(code + "\t" + str + "\n");
            }
            System.out.println(sb.toString());
            TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\007874.txt", sb.toString());
        }

//        for (int i = 0; i <list.size() ; i++) {
////            DecimalFormat df = new DecimalFormat("#.00");
//            String[] array = list.get(i).split(" ");
//            System.out.println(array[0]+"\t"+array[1]);
//        }
    }

    @Test
    public void getInfo123() {
        List<String> list = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123\\007874.txt");
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).split("\t")[1].replace("%", "");
            String[] array = str.split(" ");
            String sql = "insert into t_fund_007874(fund_code,fund_date,fund_value,fund_percent)values('007874',STR_TO_DATE('" + array[0] + "', '%Y-%m-%d')," + array[1] + "," + array[3] + ");";
            System.out.println(sql);
            resultList.add(sql);

        }
        JDBCUtil.insertList(resultList);
    }

    /**
     * 布林线通常可由上轨（压力线）、中轨（行情平衡线）和下轨（支撑线）三条轨道线组成，属于通道式指标或路径式指标
     * 中界线：nn日内收盘价的算术平均
     * 阻力线：中界线++标准差因子××标准差
     * 支撑线：中界线−−标准差因子××标准差
     * 标准差因子=2
     */
    @Test
    public void getInfoBULL() {
//        String path ="C:\\Users\\admin\\Desktop\\BULL.txt";
        String path = "C:\\Users\\xiala\\Desktop\\123\\BULL.txt";
        List<String> list = TxtUtil.readTxt(path);
        Collections.reverse(list);
        for (int i = 1; i <= 20; i++) {
            double[] x = new double[i];
            for (int j = 0; j < list.size(); j++) {
                String info = list.get(j).split("\t")[1];
                if (j < x.length) {
                    x[j] = new Double(info);
                }
                if (x.length == j) {
                    break;
                }
            }
//            System.out.println(Arrays.toString(x));
//            System.out.println(MathUtil.getAvg(x));
//            System.out.println(MathUtil.getStandardeviation(x));

            for (int j = 1; j <= 3; j++) {
                double avg = MathUtil.getAvg(x) + j * MathUtil.getStandardeviation(x);
                if (avg > 1.4168 && avg < 1.4404) {
                    System.out.println(x.length + ">>>>" + avg + ">>>" + j);
                }
            }

        }
    }

    @Test
    public void getCCI() {

        String path = "C:\\Users\\xiala\\Desktop\\123\\BULL.txt";
        List<String> list = TxtUtil.readTxt(path);
        Collections.reverse(list);
        for (int i = 1; i <= 20; i++) {
            double[] x = new double[i];
            for (int j = 0; j < list.size(); j++) {
                String info = list.get(j).split("\t")[1];
                if (j < x.length) {
                    x[j] = new Double(info);
                }
                if (x.length == j) {
                    break;
                }
            }
//            System.out.println(Arrays.toString(x));
//            System.out.println(MathUtil.getAvg(x));
//            System.out.println(MathUtil.getStandardeviation(x));

//            for (int j = 0; j <100 ; j++) {
            double price = 1.4168;
//                double avg =MathUtil.getAvg(x)+j*MathUtil.getStandardeviation(x);
            //MA=近N日收盘价的累计之和÷N
            double ma = MathUtil.getAvg(x);
            //MD=近N日（MA－收盘价）的累计之和÷N
            double md = getMd(x, ma);
            //TP=（最高价+最低价+收盘价）÷3
            double tp = price;
            //CCI（N日）=（TP－MA）÷MD÷0.015
            double cci = (tp - ma) / md / 0.015;
            if (cci > price) {
                System.out.println(x.length + ">>" + cci);
            }
//                if(avg>1.4168&& avg<1.4404){
//                    System.out.println(x.length+">>>>"+avg+">>>"+j);
//                }
//            }

        }
    }

    public static double getMd(double[] x, double ma) {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            sum += ma - x[i];
        }
        return sum / x.length;
    }

    @Test
    public void getBUllInfo() {
//        String path ="C:\\Users\\xiala\\Desktop\\123\\pool5.txt";
//        List<String> list = TxtUtil.readTxt(path);
//        for (int i = 0; i <list.size() ; i++) {
//
//            break;
//        }
//        String sql ="select fund_value from t_fund_day where fund_date >=str_to_date('2020-01-17', '%Y-%m-%d') and fund_date<=str_to_date('2020-02-25', '%Y-%m-%d')  and fund_code='007874'; ";
//        sql = sql.replace("007874",list.get(i));
        String sql = "select distinct fund_date from t_fund_007874 order by fund_date; ";
        System.out.println(sql);
        List<String> resultList = JDBCUtil.getList(sql);
//        for (int j = 0; j <resultList.size() ; j++) {
//            System.out.println(resultList.get(j));
//        }
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            if (i + 20 < resultList.size()) {
                System.out.println(resultList.get(i) + "\t" + resultList.get(i + 19));
                dateList.add(resultList.get(i) + "\t" + resultList.get(i + 19) + "\t" + resultList.get(i + 20));
            }
        }
        List<String> list100 = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            String sDate = dateList.get(i).split("\t")[0];
            String eDate = dateList.get(i).split("\t")[1];
            String uDate = dateList.get(i).split("\t")[2];
            sql = "select fund_value from t_fund_007874 where fund_date >=str_to_date('" + sDate + "', '%Y-%m-%d') and fund_date<=str_to_date('" + eDate + "', '%Y-%m-%d')  and fund_code='007874'; ";
            System.out.println(sql);
            List<String> resultList2 = JDBCUtil.getList(sql);
//            System.out.println(resultList2.size());
            double[] x = new double[20];
            for (int j = 0; j < resultList2.size(); j++) {
                x[j] = new Double(resultList2.get(j));

            }
            System.out.println(Arrays.toString(x));
            double bull2 = MathUtil.getAvg(x);
            double bull1 = MathUtil.getAvg(x) + 3 * MathUtil.getStandardeviation(x);
            double bull3 = MathUtil.getAvg(x) - 3 * MathUtil.getStandardeviation(x);
            System.out.println(uDate + "\t" + MathUtil.getRound4(bull1) + "\t" + MathUtil.getRound4(bull2) + "\t" + MathUtil.getRound4(bull3));
            StringBuilder sb100 = new StringBuilder();
            sb100.append("update t_fund_007874 set fund_bull_1=" + MathUtil.getRound4(bull1));
            sb100.append(",fund_bull_2=" + MathUtil.getRound4(bull2));
            sb100.append(",fund_bull_3=" + MathUtil.getRound4(bull3));
            sb100.append(" where fund_date=str_to_date('"+uDate+"', '%Y-%m-%d');");
            System.out.println(sb100.toString());
            list100.add(sb100.toString());
//            +"' where fund_date=str_to_date('" ++ uDate + "', '%Y-%m-%d');";
        }
        JDBCUtil.insertList(list100);

//        Collections.reverse(list);

    }

    //20日均线是某支股票在市场上往前20天的平均收盘价格，其意义在于它反映了这支股票20天的平均成本。

    @Test
    public void getJunXian20() {
        int[] arrayInfo = new int[]{10};
//        int[] arrayInfo = new int[]{20,30,60,120};
        String sql = "select distinct fund_date from t_fund_007874 order by fund_date; ";
        System.out.println(sql);
        List<String> resultList = JDBCUtil.getList(sql);

        List<String> list100 = new ArrayList<>();
        for (int k = 0; k <arrayInfo.length ; k++) {
            int index = arrayInfo[k];
            System.out.println(index);

            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                StringBuilder sb = new StringBuilder();
                if (i + index < resultList.size()) {
                    sb.append(resultList.get(i) + "\t" + resultList.get(i + index-1) + "\t" + resultList.get(i + index));
                    dateList.add(sb.toString());
                }
            }


            for (int i = 0; i < dateList.size(); i++) {
                String sDate = dateList.get(i).split("\t")[0];
                String eDate = dateList.get(i).split("\t")[1];
                String uDate = dateList.get(i).split("\t")[2];
                sql = "select fund_value from t_fund_007874 where fund_date >=str_to_date('" + sDate + "', '%Y-%m-%d') and fund_date<=str_to_date('" + eDate + "', '%Y-%m-%d')  and fund_code='007874'; ";
                System.out.println(sql);
                List<String> resultList2 = JDBCUtil.getList(sql);
                double[] x = new double[index];
                for (int j = 0; j < resultList2.size(); j++) {
                    x[j] = new Double(resultList2.get(j));
                }
                System.out.println(Arrays.toString(x));
                double bull2 = MathUtil.getAvg(x);
                StringBuilder sb100 = new StringBuilder();
                sb100.append(" update t_fund_007874 set fund_"+index+"=" + MathUtil.getRound4(bull2));
                sb100.append(" where fund_date=str_to_date('"+uDate+"', '%Y-%m-%d');");
//                System.out.println(sb100.toString());
                list100.add(sb100.toString());
            }
//            System.out.println(sb.toString());
        }
        JDBCUtil.insertList(list100);
//        for (int j = 0; j <list100.size() ; j++) {
//            System.out.println(list100.get(j));
//        }


    }


    /**
     * 以EMA1的参数为12日EMA2的参数为26日，DIF的参数为9日为例来看看MACD的计算过程
     1、计算移动平均值（EMA）
     12日EMA的算式为
     EMA（12）=前一日EMA（12）×11/13+今日收盘价×2/13
     26日EMA的算式为
     EMA（26）=前一日EMA（26）×25/27+今日收盘价×2/27
     2、计算离差值（DIF）
     DIF=今日EMA（12）－今日EMA（26）
     3、计算DIF的9日EMA
     根据离差值计算其9日的EMA，即离差平均值，是所求的MACD值。为了不与指标原名相混淆，此值又名
     DEA或DEM。
     今日DEA（MACD）=前一日DEA×8/10+今日DIF×2/10。
     计算出的DIF和DEA的数值均为正值或负值。
     用（DIF-DEA）×2即为MACD柱状图。
     */

    @Test
    public void getEma(){
        int[] arrayInfo = new int[]{10,20};//
        List<String> list100 = new ArrayList<>();
        for (int i = 0; i <arrayInfo.length ; i++) {
            int index =arrayInfo[i];
            String sql = "select distinct fund_date from t_fund_007874 where fund_"+index+" !=0.0 order by fund_date; ";
            System.out.println(sql);
            List<String> resultList = JDBCUtil.getList(sql);
            for (int j = 1; j <resultList.size() ; j++) {
                String lDate = resultList.get(j-1);
                sql =" select fund_"+index+" from t_fund_007874 where fund_date=str_to_date('"+lDate+"', '%Y-%m-%d');";
                System.out.println(sql);
                double lValue = new Double(JDBCUtil.getList(sql).get(0));
                String cDate = resultList.get(j);
                sql =" select fund_value from t_fund_007874 where fund_date=str_to_date('"+lDate+"', '%Y-%m-%d');";
                double cValue = new Double(JDBCUtil.getList(sql).get(0));
                double ema=lValue/(index+1)*index+cValue/(index+1)*1;
                System.out.println(ema);
                StringBuilder sb100 = new StringBuilder();
                sb100.append(" update t_fund_007874 set fund_ema_"+index+"=" + MathUtil.getRound4(ema));
                sb100.append(" where fund_date=str_to_date('"+cDate+"', '%Y-%m-%d');");
                list100.add(sb100.toString());
            }
        }
        JDBCUtil.insertList(list100);
        for (int j = 0; j <list100.size() ; j++) {
            System.out.println(list100.get(j));
        }

    }

    @Test
    public void getDIf(){
        int index =20;
        String sql = "select distinct fund_date from t_fund_007874 where fund_"+index+" !=0.0 order by fund_date; ";
        System.out.println(sql);
        List<String> resultList = JDBCUtil.getList(sql);
        List<String> list100 = new ArrayList<>();
        for (int k = 0; k <resultList.size() ; k++) {
            System.out.println(resultList.get(k));
            String cDate = resultList.get(k);
            double dif= 0.0;
            sql = "select fund_ema_10,fund_ema_20 from t_fund_007874 where fund_date=str_to_date('"+cDate+"', '%Y-%m-%d');";
            List<String[]> tmpList = JDBCUtil.getResultList(sql);
            dif=new Double(tmpList.get(0)[0])-new Double(tmpList.get(0)[1]);
            StringBuilder sb100 = new StringBuilder();
            sb100.append(" update t_fund_007874 set fund_dif=" + MathUtil.getRound4(dif));
            sb100.append(" where fund_date=str_to_date('"+cDate+"', '%Y-%m-%d');");
            list100.add(sb100.toString());
        }
        JDBCUtil.insertList(list100);
        for (int j = 0; j <list100.size() ; j++) {
            System.out.println(list100.get(j));
        }
    }

    @Test
    public void getDea(){
        int[] arrayInfo = new int[]{10};
//        int[] arrayInfo = new int[]{20,30,60,120};
        String sql = "select distinct fund_date from t_fund_007874 where fund_ema_20 != 0.0 order by fund_date; ";
        System.out.println(sql);
        List<String> resultList = JDBCUtil.getList(sql);

        List<String> list100 = new ArrayList<>();
        for (int k = 0; k <arrayInfo.length ; k++) {
            int index = arrayInfo[k];
            System.out.println(index);
            List<String> dateList = new ArrayList<>();
            for (int i = 0; i < resultList.size(); i++) {
                StringBuilder sb = new StringBuilder();
                if (i + index < resultList.size()) {
                    sb.append(resultList.get(i) + "\t" + resultList.get(i + index-1) + "\t" + resultList.get(i + index));
                    dateList.add(sb.toString());
                }
            }
            for (int i = 0; i < dateList.size(); i++) {
                String sDate = dateList.get(i).split("\t")[0];
                String eDate = dateList.get(i).split("\t")[1];
                String uDate = dateList.get(i).split("\t")[2];
                sql = "select fund_dif from t_fund_007874 where fund_date >=str_to_date('" + sDate + "', '%Y-%m-%d') and fund_date<=str_to_date('" + eDate + "', '%Y-%m-%d')  and fund_code='007874'; ";
                System.out.println(sql);
                List<String> resultList2 = JDBCUtil.getList(sql);
                System.out.println(resultList2.size());
                double[] x = new double[index];
                for (int j = 0; j < resultList2.size(); j++) {
                    x[j] = new Double(resultList2.get(j));
                }
                System.out.println(Arrays.toString(x));
                double bull2 = MathUtil.getAvg(x);
                StringBuilder sb100 = new StringBuilder();
                sb100.append(" update t_fund_007874 set fund_dea=" + MathUtil.getRound4(bull2));
                sb100.append(" where fund_date=str_to_date('"+uDate+"', '%Y-%m-%d');");
//                System.out.println(sb100.toString());
                list100.add(sb100.toString());
            }
        }
        JDBCUtil.insertList(list100);
        for (int j = 0; j <list100.size() ; j++) {
            System.out.println(list100.get(j));
        }

    }

    @Test
    public void getBar(){
        String sql = "select distinct fund_date from t_fund_007874 where fund_dea != 0.0 order by fund_date; ";
        System.out.println(sql);
        List<String> resultList = JDBCUtil.getList(sql);

        List<String> list100 = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            String cDate = resultList.get(i);
            sql = "select fund_dif,fund_dea from t_fund_007874 where fund_date =str_to_date('" + cDate + "', '%Y-%m-%d') and fund_code='007874'; ";
            double bar =0.0;
            List<String[]> tmpList = JDBCUtil.getResultList(sql);
            bar=new Double(tmpList.get(0)[0])-new Double(tmpList.get(0)[1]);
            StringBuilder sb100 = new StringBuilder();
            sb100.append(" update t_fund_007874 set fund_bar=" + MathUtil.getRound4(bar));
            sb100.append(" where fund_date=str_to_date('"+cDate+"', '%Y-%m-%d');");
            list100.add(sb100.toString());

        }
        JDBCUtil.insertList(list100);
        for (int j = 0; j <list100.size() ; j++) {
            System.out.println(list100.get(j));
        }

    }


    @Test
    public void get10Info(){
        String sql ="select fund_5_percent from t_fund_gn2_mx";
        List<String> list = JDBCUtil.getList(sql);
        double sum =0.0;
        for (int i = 0; i <list.size() ; i++) {
            sum+=new Double(list.get(i));
            System.out.println(MathUtil.getRound4(sum));
        }
    }

    @Test
    public void get1236Info(){
        String sql ="select id from t_fund_gn2_info order by id;";
        List<String[]> list = JDBCUtil.getResultList(sql);
        for (int i = 0; i <list.size() ; i++) {
            String[] array = list.get(i);
            String id =array[0];
            List<String> resutList = new ArrayList<>();
            String sql100="select fund_"+id+"_percent,fund_date from t_fund_gn2_mx";
            System.out.println(sql100);
            List<String[]> list2 = JDBCUtil.getResultList(sql100);
            double sum =0.0;
            for (int j = 0; j < list2.size(); j++) {
                double value = new Double(list2.get(j)[0]);
                String date = list2.get(j)[1];
                sum+=value;
                System.out.println(date+"\t"+value+"\t"+MathUtil.getRound4(sum));
                String sql5 = "update t_fund_gn2_sum set fund_"+id+"_percent="+sum+" where fund_date = str_to_date('"+date+"', '%Y-%m-%d');";
                resutList.add(sql5);
            }
            JDBCUtil.insertList(resutList);
        }
    }

    @Test
    public void getqInfo(){
        String sql ="select id,gn_name from t_fund_gn2_info order by id;";
        List<String[]> list = JDBCUtil.getResultList(sql);
        for (int i = 0; i <64 ; i++) {
            System.out.println("<ChartSummaryColumn name=\"fund_"+(i+1)+"_percent\" function=\"com.fr.data.util.function.NoneFunction\" customName=\""+list.get(i)[1]+"\"/>");
        }
    }
}
