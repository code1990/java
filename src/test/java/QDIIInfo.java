import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import util.RegexUtil;
import util.TxtUtil;
import util.UserAgentUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 911
 * @date 2020-10-28 15:00
 */
public class QDIIInfo {

    @Test
    public void getQDIIPool() throws Exception {
        StringBuilder sb = new StringBuilder();
        HttpClient httpClient = HttpClients.createDefault();
        String url = "http://fund.eastmoney.com/js/fundcode_search.js";
        System.out.println(url);
        List<String> htmlList = new ArrayList<>();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
        HttpResponse response = httpClient.execute(httpGet);
        String contents = EntityUtils.toString(response.getEntity(), "utf8");
        Document document = Jsoup.parse(contents);
        String arrayStr = document.getElementsByTag("body").text().replace("var r = ", "").replace(";", "");
        JSONArray jsonArray = JSON.parseArray(arrayStr);
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String info = jsonArray.get(i).toString();
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
            String preName = "";
            if (i > 0) {
                JSONArray preArray = (JSONArray) jsonArray.get(i - 1);
                preName = RegexUtil.getChina(preArray.get(2).toString());
            }
            String currentName = array.get(2).toString();
            String simpleName = RegexUtil.getChina(currentName);
            if (!preName.equals("") && simpleName.equals(preName)) {
                continue;
            }
            System.out.println(array.get(0) + "\t" + currentName + "\t" + array.get(3));
            sb.append(array.get(0) + "\t" + currentName + "\t" + array.get(3) + "\n");
            String sql = "INSERT INTO t_fund_pool8 (fund_code,fund_name, fund_type) VALUES ('" + array.get(0) + "', '" + currentName + "', '" + array.get(3) + "');";
            System.out.println(sql);
            resultList.add(sql);
        }
        TxtUtil.writeTxt(new File("QDII.txt"), sb.toString());
    }

    @Test
    public void getInfo111() {
        List<String> list = TxtUtil.readTxt(new File("pool.txt"));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String info = list.get(i);
            if (info.contains("QDII")||info.contains("油")) {
                continue;
            }
            sb.append(info + "\n");
        }
        TxtUtil.writeTxt(new File("pool.txt"), sb.toString());
    }
}
