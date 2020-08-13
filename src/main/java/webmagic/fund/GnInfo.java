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
    public void getInfo(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\0810.txt";
        List<String> list = TxtUtil.readTxt(path);
        HashMap<String,String> map1 = new HashMap<>();
        HashMap<String,String> map2 = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            String comCode = array[1].replace(" ","");
            String comName = array[2].replace(" ","");
            String gn = array[3].replace(" ","");
            sb.append("insert into t_fund_gn_mx(com_code,com_name,gn_name)values('"+comCode+"','"+comName+"','"+gn+"');\n");

            if(map1.get(comCode)==null){
                map1.put(comCode,gn);
            }else{
                map1.put(comCode,map1.get(comCode)+","+gn);
            }
            map2.put(comCode,comName);
//            System.out.println();
        }
        Map<String,String> readMap = TxtUtil.readMap("C:\\Users\\xiala\\Desktop\\123\\1233.txt");
        Iterator entries = map1.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            System.out.println("alter table t_com_hy_gn_mx set gn_name='"+entry.getValue()+"' where com_code='"+entry.getKey()+"';");
//            System.out.println(entry.getKey() +"\t"+map2.get(entry.getKey())+"\t"+readMap.get(entry.getKey())+ "\t" + entry.getValue());
//            System.out.println("'"+entry.getKey()+"',");
//            System.out.println("insert into t_com_hy_gn(com_code,com_name,hy_name,gn_name)values('"+entry.getKey() + "','" + entry.getValue()+"','"+map2.get(entry.getKey()));
        }
//        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\insert_t_fund_hy.sql",sb.toString());
//        MapUtil.printMap(map1);
//        MapUtil.printMap(map2,map1);
    }

    @Test
    public void getInfo009() throws Exception{
        String path ="C:\\Users\\xiala\\Desktop\\123\\001jj.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = new ArrayList<>();//TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\123\\002jj.txt");
        HashMap<String,String> map = new HashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String str = list.get(i);
            if(str.contains("开放") && str.contains("债")){
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
        String startDate ="2020-07-10";
        String endDate ="2020-08-11";
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i <list2.size() ; i++) {//list.size()
            String url =  "http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code="+list2.get(i)+"&page=1&per=65535&sdate="+startDate+"&edate="+endDate;
            System.out.println((i+1)+"\t"+url);
            String code = url.split("&code=")[1].split("&")[0];
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(),"gbk");
            Document document = Jsoup.parse(contents);

            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            StringBuilder sb = new StringBuilder();
            for (int j = elements.size() -1; j >= 1; j--) {//elements.size() -1
//            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                System.out.println(str);
                sb.append(code + "\t" + str + "\n");
            }
            all.append(sb.toString());
            TxtUtil.writeTxt(target,all.toString());
            System.out.println(sb.toString());
        }

        List<String> list3 = TxtUtil.readTxt(target);
        Map<String,String> map3 = new HashMap<>();

        for (int i = 0; i <list3.size() ; i++) {
            String[] array = list3.get(i).split("\t");
            String key = array[0];
            String value = array[1];
            if(map3.get(array[0])==null){
                map3.put(key,value+"\t");
            }else{

                map3.put(key,new StringBuilder(map3.get(key)).append(value+"\t").toString());
            }

        }
        StringBuilder sb2 = new StringBuilder();
        for (Map.Entry<String,String> entry : map3.entrySet()) {
            StringBuilder sb = new StringBuilder();
            String[] array =entry.getValue().split("\t");
            for (int i = 0; i <array.length ; i++) {
//                System.out.println(array[i].split(" ")[3].replace("%",""));
                String str = array[i].split(" ")[3];
                str = str.replace("%","");
                if(RegexUtil.isContainChinese(str)){
//                    System.out.println(array[i]);
                    break;
                }
//                str = str.replace("封闭期","0.00");
//                str = str.replace("申购","0.00");
//                str = str.replace("开放","");
//                str = str.replace("限制大额","");
//                str = str.replace("暂停","");
                sb.append(str+"\t");
            }
            if(array.length==20&&sb.toString().length()>0){
                System.out.println();
                sb2.append("#"+entry.getKey() + "\t" + sb.toString()+"\n");
            }
//            System.out.println(array.length);

        }
        TxtUtil.writeTxt(target,sb2.toString());
    }

    @Test
    public void getInfo000() throws Exception{
        String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                ".aspx?type=lsjz&code=007874&page=1&per=65535&sdate=2020-01-01&edate=2020-02-01";
        String code="007874";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
        HttpResponse response = httpClient.execute(httpGet);
        String contents = EntityUtils.toString(response.getEntity(),"gbk");
        Document document = Jsoup.parse(contents);

        Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        for (int j = elements.size() -1; j >= 1; j--) {//elements.size() -1
//            for (int j = 1; j < elements.size(); j++) {
            String str = elements.get(j).text();
            if (str.contains("暂无数据")) {
                continue;
            }
//            System.out.println(str);
            sb.append(code + "\t" + str + "\n");
            list.add(str);
        }
        for (int i = 0; i <list.size() ; i++) {
//            DecimalFormat df = new DecimalFormat("#.00");
            String[] array = list.get(i).split(" ");
            System.out.println(array[0]+"\t"+array[1]);
        }
    }

    @Test
    public void getInfoBULL(){
        String path ="C:\\Users\\admin\\Desktop\\BULL.txt";
        List<String> list = TxtUtil.readTxt(path);
        HashMap<String,String> map = new HashMap<>();

//        for (int i = 0; i <list.size() ; i++) {
//            String array = list.get(i).split("\t")[1];
//        }
        Collections.reverse(list);
        for (int i = 1; i <=20 ; i++) {
            double[] x = new double[i];
            for (int j = 0; j <list.size() ; j++) {
                String info = list.get(j).split("\t")[1];
                if(j<x.length){
                    x[j]=new Double(info);
                }
                if(x.length==j){
                    break;
                }
            }
//            System.out.println(Arrays.toString(x));
//            System.out.println(MathUtil.getAvg(x));
//            System.out.println(MathUtil.getStandardeviation(x));

            for (int j = 0; j <100 ; j++) {
                double avg =MathUtil.getAvg(x)+j*MathUtil.getStandardeviation(x);
                if(avg>1.4168&& avg<1.4404){
                    System.out.println(x.length+">>>>"+avg+">>>"+j);
                }
            }

        }
    }
}
