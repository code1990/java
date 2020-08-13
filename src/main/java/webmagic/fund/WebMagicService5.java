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
import util.MapSortUtil;
import util.TxtUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 911
 * @date 2020-08-07 21:44
 */
public class WebMagicService5 {
    static String path = "C:\\Users\\xiala\\Desktop\\123\\pool3.txt";
    static String target = "C:\\Users\\xiala\\Desktop\\123\\pool3url1002.txt";
    static List<String> list = TxtUtil.readTxt(path);
    static StringBuilder all = new StringBuilder();

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list.size(); i++) {//list.size()
            String url = "http://fund.eastmoney.com/"+list.get(i)+".html";//.split(",")[1];
            System.out.println((i + 1) + "\t" + url);
            String code = list.get(i);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            Document document = Jsoup.parse(contents);
            Elements elements = document.getElementsByClass("ui-table-hover").get(0).getElementsByTag("tr");
//            String parentInfo = TxtUtil.readTxtStr("C:\\Users\\xiala\\Desktop\\123\\code\\" + code + ".txt");
            StringBuilder sb = new StringBuilder();
//            for (int j = elements.size() -1; j >= 1; j--) {//elements.size() -1
            for (int j = 1; j < elements.size(); j++) {
                String str = elements.get(j).text();
                Element elementTd = elements.get(j).getElementsByTag("td").get(0);
                if(str.contains("暂无数据")||!elementTd.toString().contains("href")){
                    continue;
                }
                sb.append( elementTd.text() + "\t"+elementTd.getElementsByTag("a").get(0).attr("href")+ "\t");
            }
            all.append(code + "\t" +sb.toString()+"\n");
            TxtUtil.writeTxt(target, all.toString());
            System.out.println(code + "\t" +sb.toString()+"\n");
        }

    }

    @Test
    public void getInfo(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\pool3url100.txt";
        String path2 ="C:\\Users\\xiala\\Desktop\\123\\hy.txt";
        String path3 ="C:\\Users\\xiala\\Desktop\\123\\11111.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = TxtUtil.readTxt(path2);
        List<String> list3 = TxtUtil.readTxt(path3);
        HashMap<String,String> map = new HashMap<>();
        for (int i = 0; i < list2.size(); i++) {
            String[] array = list2.get(i).split("\t");
            map.put(array[1],array[0]);
        }
        StringBuilder sb2 = new StringBuilder("");
        for (int j = 0; j < list3.size(); j++) {
            sb2.append(list3.get(j)+",");
        }
        HashMap<String,Integer> map6= new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            StringBuilder sb = new StringBuilder(array[0]+"\t");
            HashMap<String,Integer> map2 = new HashMap<>();
            for (int j = 1; j <array.length ; j++) {
                if(map.get(array[j])!=null){
                    sb.append(map.get(array[j])+"\t");
                    if(map2.get(map.get(array[j]))==null){
                        map2.put(map.get(array[j]),1);
                    }else{
                        map2.put(map.get(array[j]),1+map2.get(map.get(array[j])));
                    }
                }else{
                    sb.append(array[j]+"\t");
                    map2.put(array[j],1);
                }
//                System.out.println(map.get(array[j]));
            }
            System.out.println(array[0]+"\t"+ MapSortUtil.sortByValueDesc(map2).toString());
            if(sb2.toString().contains(array[0])){
//                System.out.println(array[0]+"\t"+ MapSortUtil.sortByValueDesc(map2).toString());
                if(map2!=null){
                    for (Map.Entry<String, Integer> m : map2.entrySet()) {
                        String mapKey = m.getKey();
                        Integer mapValue = m.getValue();
//                        System.out.println(mapKey + ":" + mapValue);
                        if(map6.get(mapKey)==null){
                            map6.put(mapKey,mapValue);
                        }else{
                            map6.put(mapKey,map6.get(mapKey)+mapValue);
                        }
                    }
                }
            }

//            System.out.println(sb.toString());
//            map.put(array[1],array[0]);
        }
        System.out.println(MapSortUtil.sortByValueDesc(map6).toString());
    }


    @Test
    public void getInfo123(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\269.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            String url =array[1];
            url = "http://data.eastmoney.com/bkzj/"+url.split("/90.")[1]+".html";
            System.out.println(url);
            sb.append("insert into t_fund_gn(gn_name,gn_url)values('"+array[0]+"','"+url+"');\n");
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\insert_t_fund_gn.sql",sb.toString());
    }

    @Test
    public void getInfo124(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\hytz123.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            String code = array[2].split("\\.")[3];
            System.out.println(Arrays.toString(array[2].split("\\.")));
            sb.append("insert into t_fund_hy_mx(com_code,com_name,hy_name)values('"+code+"','"+array[1].replace(" ","")+"','"+array[3]+"');\n");
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\insert_t_fund_gn.sql",sb.toString());
    }

    @Test
    public void getInfo125(){
        String path ="C:\\Users\\xiala\\Desktop\\123\\269.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            System.out.println(array[1]);
            sb.append("insert into t_fund_hy( hy_name, hy_url)values('US"+array[0]+"','"+array[1]+"');\n");
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\insert_t_fund_hy.sql",sb.toString());
    }

    @Test
    public void getInfo1203(){
//        String path ="C:\\Users\\xiala\\Desktop\\123\\tmp.txt";
        List<String> list = TxtUtil.readTxt(target);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            if(array.length!=1) {
//                boolean flag = false;
                StringBuilder sb1 = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                String[] arrayInfo = array[1].split(".html,");
//                System.out.println(array[1]);
                for (int j = 0; j <arrayInfo.length ; j++) {
                    String[] tmp = arrayInfo[j].split(",");
                    String code = tmp[1].replace("http://quote.eastmoney.com/","");
                    code = code.replace(".html","");
                    code = code.replace("sh","");
                    code = code.replace("sz","");
                    if(code.contains("hk")){
                        code = code.replace("hk/","");
                        code = code+".HK";

                    }else if(code.contains("us")){
                        code = code.replace("us/","");
                    }
                    sb1.append(""+tmp[0].replace(" ","")+",");
                    sb2.append(""+code.replace(" ","")+",");
                }
//                for (int j = 1; j <array.length ;) {
////                    System.out.println(array[j]);
//                    String code = array[j+1].replace("http://quote.eastmoney.com/","");
//                    code = code.replace(".html","");
//                    code = code.replace("sh","");
//                    code = code.replace("sz","");
//                    if(code.contains("hk")){
//                        code = code.replace("hk/","");
//                        code = code+".HK";
//
//                    }else if(code.contains("us")){
//                        code = code.replace("us/","");
//                    }
//                    sb1.append("'"+array[j]+"',");
//                    sb2.append("'"+code+"',");
////                    System.out.println(code);
//                    j+=2;
//                }
                String fundCode = array[0];
                String ccName = sb1.toString().substring(0,sb1.length()-1);
                String ccCode = sb2.toString().substring(0,sb2.length()-1);
                String str = "insert into t_fund_cc_mx(fund_code,cc_name,cc_code)values('"+fundCode+"','"+ccName+"','"+ccCode+"');";
                System.out.println(str);
            }


//                sb.append("'"+array[0]+"',\n");
//            }else{
//                System.out.println(list.get(i));
//                if(){
//
//                }
////                System.out.println(array[0]);
//            }
//            System.out.println(list);

        }
//        sb.append(");");
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\123\\insert_t_fund_hy.sql",sb.toString());
    }
}
