import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import util.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Daogou {

//    static {
//        System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver85.exe");
//    }

    /**
     * http://fund.eastmoney.com/js/fundcode_search.js
     *
     * QDII 无法及时分析 直接剔除
     */
//    @Test
    public void getAllFund() throws Exception {
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
            if (info.contains("FOF") || info.contains("QDII-ETF") || info.contains("QDII-指数")||info.contains("油")|| info.contains("QDII")) {
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
//        TxtUtil.writeTxt(new File("pool.txt"), sb.toString());
        JDBCUtil.insertList(resultList);
//        Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
//        for (int j = elements.size() - 1; j >= 1; j--) {
//            String str = elements.get(j).text();
//            if (str.contains("暂无数据")) {
//                continue;
//            }
//            System.out.println(str);
//            htmlList.add(str);
//        }
//        int start = 0;
//        if (htmlList.size() >= 2) {
//            start = 1;
//        }
//        List<String> resultList = new ArrayList<>();
//        for (int i = start; i < htmlList.size(); i++) {
//            String str = htmlList.get(i);
//            String[] array = str.split(" ");
//            String percent = "0.0";
//            if (array.length == 5 && array[2].contains("%")) {
//                percent = array[2].replace("%", "");
//            } else if (array.length == 6 && array[3].contains("%")) {
//                percent = array[3].replace("%", "");
//            }
//            sql = "insert into t_fund_day(fund_code,fund_date,fund_value,fund_percent)values('" + code + "',STR_TO_DATE('" + array[0] + "', '%Y-%m-%d')," + array[1] + "," + percent + ");";
//            System.out.println(sql);
//            resultList.add(sql);
//        }

    }

    /**
     * http://fund.eastmoney.com/f10/F10DataApi.aspx?type=lsjz&code=110022&sdate=2018-02-22&edate=2018-03-02&per=20
     * 参数说明如下：
     * <p>
     * type 类型，历史净值用lsjz表示
     * code 基金代码，六位数字
     * sdate 开始日期，格式是yyyy-mm-dd
     * edate 结束日期，格式是yyyy-mm-dd
     * per 一页显示多少条记录
     */

    @Test
    public void getInfo() throws Exception {
        List<String> list = TxtUtil.readTxt(new File("pool.txt"));
        List<String> codeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).contains("油")){
                continue;
            }
            codeList.add(list.get(i).split("\t")[0]);
        }

//        String dateInfo = DateUtil.get20Date();
        String startDate = DateUtil.getMoth(-2);
        String endDate = DateUtil.getCurrentStr();
        List<String> urlList = new ArrayList<>();
        HttpClient httpClient = HttpClients.createDefault();

        for (int i = 0; i < codeList.size(); i++) {
            String code = codeList.get(i);
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code=" + code + "&page=1&per=65535&sdate=" + startDate + "&edate=" + endDate;
            urlList.add(url);
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);

            String code = codeList.get(i);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "gbk");
            Document document = Jsoup.parse(contents);
            List<String> htmlList = new ArrayList<>();
            Elements elements = document.getElementsByClass("w782").get(0).getElementsByTag("tr");
            for (int j = elements.size() - 1; j >= 1; j--) {
                String str = elements.get(j).text();
                if (str.contains("暂无数据")) {
                    continue;
                }
                htmlList.add(str);
            }
            if (htmlList.size() < 20) {
                continue;
            }
            double[] x = new double[20];
            for (int k = 0; k < x.length; k++) {
                String str = htmlList.get(k);
                String[] array = str.split(" ");
                x[k] = new Double(array[1]);
            }
            double currentValue = x[19];
            double bull2 = MathUtil.getAvg(x);
            double bull1 = bull2 + 1.7 * MathUtil.getStandardeviation(x);
            double bull3 = bull2 - 1.7 * MathUtil.getStandardeviation(x);
            boolean flag = currentValue >= bull1 || currentValue <= bull3;

            if (flag) {
                System.out.println((i + 1) + "\t" + url);

                url = "http://fund.eastmoney.com/"+code+".html";
                httpGet = new HttpGet(url);
                httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
                response = httpClient.execute(httpGet);
                contents = EntityUtils.toString(response.getEntity(), "utf-8");
                document = Jsoup.parse(contents);
                if (document.getElementsByClass("ui-font-middle ui-color-red ui-num").size() == 0) {
                    continue;
                }
                String value = document.getElementsByClass("ui-font-middle ui-color-red ui-num").get(0).text();
                if (new Double(value.replace("%","")) < 30.0) {
                    continue;
                }
                String name = document.getElementsByClass("fundDetail-tit").text();
                String percent = "";
                if (currentValue <= bull3){
                    percent = MathUtil.getRound4((currentValue / bull3 - 1) * 100) + "%";
                    sb.append("33#" + code + "\n");
                    System.out.println(DateUtil.getYesterday() + "\t" + code + "\t" + "\t买点");
                }else{
                    sb2.append("33#" + code + "\n");
                    System.out.println(DateUtil.getYesterday() + "\t" + code + "\t" + "\t卖点");
                }
                sb3.append(code+"\t"+percent+"\t"+value + "\t" + name + "\t" + "\n");
            }
        }
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\" + DateUtil.getCurrentStr() + "_buy.EBK", sb.toString());
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\" + DateUtil.getCurrentStr() + "_sell.EBK", sb2.toString());
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\compare.txt", sb3.toString());
    }

    @Test
    public void getInfo123() {
        String path = "D:\\Program Files (x86)\\ArcGIS\\DeveloperKit10.2\\java\\samples\\arcobjects";
        for (File file : new File(path).listFiles()) {
            for (File file2 : file.listFiles()) {
                System.out.println(file.getName() + "===>" + file2.getName());
            }
        }
    }

    @Test
    public void getInfo1() {
        String path = "C:\\Users\\xiala\\Desktop\\new 2";
        List<String> list = TxtUtil.readTxt(path);
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            StringBuilder sb = new StringBuilder();
            if (str.split(" ").length != 2) {
                for (int j = 0; j < str.split(" ").length - 1; j++) {
                    sb.append(str.split(" ")[j]);
                }
            } else {
                sb.append(str);
            }

            if (sb.toString().contains("（教学视频")) {
                System.out.println(sb.toString().split("（教学视频")[0]);
            } else {

                if (sb.toString().split("\\.").length == 2) {
                    System.out.println(sb.toString());
                }
            }

        }
    }

    @Test
    public void getInfo11111() {
        String path = "C:\\Users\\xiala\\Desktop\\fund123\\fund_pool.txt";
        List<String> list1 = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\fund123\\MS_ALL.txt");
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i).split("\t")[1];
            String value = list.get(i).split("\t")[2];
            map.put(key, value);
        }
        for (int i = 1; i < list1.size(); i++) {
            String str = list1.get(i).split(",")[1];
            if (map.get(str) != null) {
                System.out.println(list1.get(i));
                sb.append(list1.get(i) + "\n");
            }
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\fund123\\ms_ccmx_bak.txt", sb.toString());
    }

    @Test
    public void getInfo1111() {
        String path = "C:\\Users\\xiala\\Desktop\\fund123\\fund_pool.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
//        Map<String,String> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String key = list.get(i).split("\t")[1];
            String value = list.get(i).split("\t")[2];
            String str = "http://fundf10.eastmoney.com/ccmx_007886.html".replace("007886", key) + "\n";
            System.out.println(str);
            sb.append(str);
//            map.put(key,value);
        }

        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\fund123\\es_ccmx.txt", sb.toString());
    }


    @Test
    public void getInfo111() throws Exception {
        String str = "ActiveMQ、RabbitMQ、Kafka、RocketMQ、ZeroMQ";
        String path = "D:\\github\\javahome\\消息中间件\\";
        String[] array = str.split("、");
        new File(path).mkdirs();
        for (int i = 0; i < array.length; i++) {
            new File(path + array[i]).mkdirs();
            new File(path + array[i] + "\\test.md").createNewFile();
        }
    }


    public static void main(String[] args) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
//        System.out.println("今天是"+weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1]);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder sb = new StringBuilder();
        int count = 0;
        String startDate = "";
        String endDate = "";
        for (int i = 1; i <= 50; i++) {
            Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            date = calendar.getTime();
            int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            if (0 == index || 6 == index) {
                continue;
            }
            count++;
            if (count == 1) {
                endDate = sdf.format(date);
            }
            if (count == 20) {
                startDate = sdf.format(date);
                break;
            }
//            sb.append(sdf.format(date) + "\n");
        }
        System.out.println(startDate + "\t" + endDate);
//        TxtUtil.writeTxt(new File("date.txt"),sb.toString());
    }

    @Test
    public void getInfo1203() throws Exception {
        String path = "D:\\BaiduNetdiskDownload\\000EBK\\2020-10-26.txt";
        List<String> urlList = TxtUtil.readTxt(path);
        HttpClient httpClient = HttpClients.createDefault();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
//            System.out.println((i + 1) + "\t" + url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf-8");
            Document document = Jsoup.parse(contents);
            if (document.getElementsByClass("ui-font-middle ui-color-red ui-num").size() == 0) {
                continue;
            }
            String value = document.getElementsByClass("ui-font-middle ui-color-red ui-num").get(0).text();
            String name = document.getElementsByClass("fundDetail-tit").text();
            System.out.println((i + 1) + "\t" + value + "\t" + name + "\t" + url);
        }
//        ChromeOptions options = new ChromeOptions();
//        // 设置禁止加载项
//        Map<String, Object> prefs = new HashMap<String, Object>();
//        // 禁止加载js
//        prefs.put("profile.default_content_settings.javascript", 2); // 2就是代表禁止加载的意思
//        // 禁止加载css
//        prefs.put("profile.default_content_settings.images", 2); // 2就是代表禁止加载的意思
//        options.setExperimentalOption("prefs", prefs);
//        WebDriver driver = new ChromeDriver(options);
//        for (int i = 90; i < list.size(); i++) {
//            String url = list.get(i);
//            driver.get(url);
//            if (i == 0) {
//                Thread.sleep(5000);
//            }
//            if(driver.getPageSource().contains("gz_gsz")){
//
//            System.out.println(i+"\t"+driver.findElement(By.id("gz_gsz")).getText() + "\t" + driver.findElement(By.xpath("//*[@id=\"body\"]/div[12]/div/div/div[3]/div[1]/div[1]/dl[1]/dd[3]/span[2]")).getText());
//            }
//        }
    }

}
