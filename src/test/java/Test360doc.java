//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import org.jsoup.nodes.Document;
//import org.junit.Test;
//import util.HttpUtil;
//import util.TxtUtil;
//
//import java.io.File;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 911
// * @date 2020-11-18 12:12
// */
//public class Test360doc {
//
//    @Test
//    public void getInfo11() throws Exception{
//        int num = 175;
//        List<String> list = new ArrayList<>();
//        for (int i = 1; i <=175 ; i++) {
//            String url ="https://api.360doc.com/Ajax/ArticleHandler.ashx?op=getothersuserarticle&callback=jQuery1800025933498661043553_1605665033130&curnum="+i+"&pagenum=10&cid=-1&isoriginal=0&userid=1792975&sortarttype=1&sign=303E3261E235DB66BD31E7A9C2A5F71D85A017C6&_=1605665059118";
//            list.add(url);
//        }
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i <list.size() ; i++) {
//            String url =list.get(i);
//            Document document = HttpUtil.getHtml(url);
//            String info = document.getElementsByTag("body").text();
//            System.out.println(url);
//            int start = info.indexOf("(");
//            int end = info.length()-1;
//            info = info.substring(start+1,end);
//            JSONObject jsonObject = JSONObject.parseObject(info);
//            JSONArray jsonArray= jsonObject.getJSONArray("listitem");
//            for (int j = 0; j <jsonArray.size() ; j++) {
//                JSONObject obj = jsonArray.getJSONObject(j);
//                String title = URLEncoder.encode(obj.getString("articletitle"), "UTF-8");
//                String artUrl = obj.getString("arturl");
//                System.out.println(title+"\t"+artUrl);
//                sb.append(title+"\t"+artUrl+"\n");
//            }
//            TxtUtil.writeTxt(new File("360doc.txt"),sb.toString());
//        }
//
//    }
//}
