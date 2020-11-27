import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
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

/**
 * @author 911
 * @date 2020-10-28 08:43
 * 1.Triple抽象类，实现这个抽象类的类能够存储三个对象
 * 2.ImmutableTriple不可变组件对象
 * 3.MutableTriple可改变值的三个元素组件对象
 */
public class DaoGou2better {
    /**
     * 2.30-2.40之间获取当前的估值情况 根据估值 判断买点
     **/
    public Pair getGzInfo(String code) throws Exception {
        Pair<Double, Double> tuple = Pair.of(0.0, 0.0);
        String url = "http://fundgz.1234567.com.cn/js/" + code + ".js?rt=1603852762223" + System.currentTimeMillis();
        Document document = HttpUtil.getHtml(url);
        String content = document.getElementsByTag("body").text();
        if ("jsonpgz();".equals(content)||content.trim().length()==0) {
            return tuple;
        }
        content = content.replace("jsonpgz(", "").replace(");", "");
        System.out.println(url);
        JSONObject jsonObject = JSON.parseObject(content);
        tuple = Pair.of(jsonObject.getDouble("gsz"), jsonObject.getDouble("gszzl"));
        return tuple;
    }

    public Pair getYearValue(String code) throws Exception {
        Pair<Double, Double> tuple = Pair.of(0.0, 0.0);
        String url = "http://fund.eastmoney.com/" + code + ".html";
        Document document = HttpUtil.getHtml(url);
        if (document.getElementsByClass("dataItem01").size() == 0) {
            return tuple;
        }
        String value = document.getElementsByClass("dataItem01").get(0).getElementsByTag("dd").get(2).getElementsByClass("ui-font-middle ui-color-red ui-num").text();
        if (value.trim().equals("")) {
            return tuple;
        }
        Double result1 = new Double(value.replace("%", ""));
        if (result1 < 10.0) {
            return tuple;
        }
        String text = document.getElementsByClass("estimated_netWrth").text();
        double result2 = 0.0;
        if (text.contains("净值估算平均偏差：") && text.contains("%")) {
            result2 = new Double(text.replace("净值估算平均偏差：", "").replace("%", ""));
        }
        tuple = Pair.of(result1, result2);
        return tuple;
    }

    /**
     * @throws Exception 连续跌3天 或者跌幅在3%以上给与加仓
     */
    @Test
    public void getInfo() throws Exception {
        //第一件事情是更新排名信息
        List<String> list = TxtUtil.readTxt(new File("pool3.txt"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String logStr = TxtUtil.readTxtStr(new File("000.log"));
        if (!logStr.contains(sdf.format(new Date()))) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                String[] array = list.get(i).split("\t");
                String code = list.get(i).split("\t")[0];
                System.out.println(i+"\t"+code);
                Pair tuple = getYearValue(code);
                sb.append(array[0] + "\t" + array[1] + "\t" + array[2] + "\t" + tuple.getLeft() + "\t" + tuple.getRight() + "\n");
            }
            TxtUtil.writeTxt(new File("pool3.txt"), sb.toString());
            TxtUtil.writeTxt(new File("000.log"),sdf.format(new Date()));
            list = TxtUtil.readTxt(new File("pool3.txt"));
        }
        List<String> codeList = new ArrayList<>();
        Map<String, ImmutableTriple<String, Double, Double>> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            codeList.add(array[0]);
            ImmutableTriple<String, Double, Double> tuple3 = ImmutableTriple.of(array[1], new Double(array[3]), new Double(array[4]));
            map.put(array[0], tuple3);
        }
        String startDate = DateUtil.getMoth(-2);
        String endDate = DateUtil.getCurrentStr();
        List<String> urlList = new ArrayList<>();
        HttpClient httpClient = HttpClients.createDefault();

        for (int i = 0; i < codeList.size(); i++) {
            String code = codeList.get(i);
            String url = "http://fund.eastmoney.com/f10/F10DataApi" +
                    ".aspx?type=lsjz&code=" + code + "&page=1&per=20&sdate=" + startDate + "&edate=" + endDate;
            urlList.add(url);
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();
        for (int i = 0; i < urlList.size(); i++) {
            String url = urlList.get(i);
            String code = codeList.get(i);
            Document document = HttpUtil.getHtml(url);
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
            double currentPercent = 0.0;
            double onePercent = 0.0;
            double twoPercent = 0.0;
            for (int k = 0; k < x.length; k++) {
                String str = htmlList.get(k);
                if(str.contains("暂停申购")){
                    continue;
                }
                String[] array = str.split(" ");
//                System.out.println(array.length);
                x[k] = new Double(array[1]);
                if (array.length == 6) {
                    if (k == 19) {
                        currentPercent = new Double(array[3].replace("%", ""));
                    }
                    if (k == 18) {
                        onePercent = new Double(array[3].replace("%", ""));
                    }
                    if (k == 17) {
                        twoPercent = new Double(array[3].replace("%", ""));
                    }
                } else if (array.length == 5) {
                    if (k == 19) {
                        currentPercent = new Double(array[2].replace("%", ""));
                    }
                    if (k == 18) {
                        onePercent = new Double(array[2].replace("%", ""));
                    }
                    if (k == 17) {
                        twoPercent = new Double(array[2].replace("%", ""));
                    }
                }

            }
            double currentValue = x[19];
//            double oneValue = x[18];
            double bull2 = MathUtil.getAvg(x);
            double stat = MathUtil.getStandardeviation(x);
            double bull1 = bull2 + 1.7 * stat;
            double bull3 = bull2 - 1.7 * stat;
            String status = "";
            Pair<Double, Double> tuple = getGzInfo(code);
            //平均估值偏差
            ImmutableTriple<String, Double, Double> tuple3 = map.get(code);
            double gzRepair = tuple3.right;
            //接近真实的估值百分比
            double gzPercent = tuple.getRight() - gzRepair;
            double gzValue = tuple.getLeft();
            if (gzPercent == 0.0 && gzValue == 0.0) {
                continue;
            }
            //昨天的净值大于中轨
            if (currentValue > bull2) {
                //今日估值接近上轨
                if (gzValue >= bull1) {
                    //今日估值越过上轨卖出
                    if (gzValue >= (bull2 + 2 * stat)) {
                        status = "净值中轨上\t估值突破上轨\t卖出";
                        //今日估值在上轨附近
                    } else {
                        status = "净值中轨上\t估值接近上轨\t卖出";
                    }
                }
//                else {
//                    if (gzPercent <= -2.0) {
//                        if (gzValue <= bull2) {
//                            status = "净值中轨上\t估值跌破中轨\t买入";
//                        } else {
//                            status = "净值中轨上\t估值中轨上\t超卖可买";
//                        }
//                    }
//                }
            } else {
                //今日的估值接近下轨
                if (gzValue <= bull3) {
                    //估值跌破下轨 买入信号
                    if (gzValue <= (bull2 - 2 * stat)) {
                        status = "净值中轨下\t估值突破下轨\t买入";
                        //估值接近下轨
                    } else {
                        status = "净值中轨下\t估值接近下轨\t买入";
                    }
                }
//                else {
//                    if (gzPercent <= -2.0) {
//                        status = "净值中轨下\t估值下轨上\t超卖可买";
//                    }
//                }
            }
            //买入的条件必须是下轨未底线 和超卖行情为底线
            boolean isBuy = false;
            //连续三日下跌 并且下跌幅度大于-3 中间出现小幅度涨 后面再次跌的 合计作为加仓信号
//            if ( (gzPercent < 0) && (twoPercent+onePercent + currentPercent + gzPercent) < -4) {
//                isBuy = true;
//                if (gzValue < bull2) {
//                    status = "4日下跌-3估值中轨下\t买入\t"+(twoPercent+onePercent + currentPercent + gzPercent);
//                }
//                if (gzValue <= (bull2 + 1 * stat)) {
//                    status = "4日下跌-3估值中轨上\t买入\t"+(twoPercent+onePercent + currentPercent + gzPercent);
//                }
//                //连续2日下跌 并且下跌幅度大于-4
//            } else if (currentPercent < 0 && gzPercent < 0 && (currentPercent + gzPercent) < -4) {
//                isBuy = true;
//                if (gzValue < bull2) {
//                    status = "2日下跌-4估值中轨下\t买入\t"+(currentPercent + gzPercent);
//                }
//                if (gzValue <= (bull2 + 1 * stat)) {
//                    status = "2日下跌-4估值中轨上\t买入\t"+(currentPercent + gzPercent);
//                }
//            }


            //单日超跌
            if(gzPercent<=-3){
                if (gzValue < bull2) {
                    status = "1日下跌-3估值中轨下\t买入\t"+(currentPercent + gzPercent);
                }
                if (gzValue <= (bull2 + 1 * stat)) {
                    status = "1日下跌-3估值中轨上\t买入\t"+(currentPercent + gzPercent);
                }
                //下跌2天
            }else  if ((currentPercent < 0.5 && gzPercent < 0.5)) {
                //超卖
                if(((currentPercent + gzPercent) <= -4)){
                    if (gzValue < bull2) {
                        status = "2日下跌-4估值中轨下\t买入\t"+(currentPercent + gzPercent);
                    }
                    if (gzValue <= (bull2 + 1 * stat)) {
                        status = "2日下跌-4估值中轨上\t买入\t"+(currentPercent + gzPercent);
                    }
                    //4日超卖
                }else if((twoPercent+onePercent + currentPercent + gzPercent) <= -4){
                    if (gzValue < bull2) {
                        status = "4日下跌-4估值中轨下\t买入\t"+(twoPercent+onePercent + currentPercent + gzPercent);
                    }
                    if (gzValue <= (bull2 + 1 * stat)) {
                        status = "4日下跌-4估值中轨上\t买入\t"+(twoPercent+onePercent + currentPercent + gzPercent);
                    }
                }
                isBuy = true;

            }

//            if (isBuy) {
//                if (gzValue < bull2) {
//                    status = "估值中轨下\t买入";
//                }
//                if (gzValue <= (bull2 + 1 * stat)) {
//                    status = "估值中轨上\t买入";
//                }
//            }
            //使用BBI多空指标 捕捉市场短期的超卖机会
            //今日估值大于中轨，小于bbi,并且估值跌幅超过-1.5 是为超卖行为 超卖可以适当介入
//            if (gzValue > bull2) {
//                double bbiValue = StockUtil.getBbi(x);
//                if (gzValue <= bbiValue && gzPercent < -1.5) {
//                    status = "中轨上方的超级买点\t"+gzPercent;
//                }
//            }

            if (!"".equals(status)) {
                if (status.contains("买")) {
//                    System.out.println(currentValue);
                    System.out.println(i + "\t" + code + "\t" + map.get(code) + "\t" + status);
//                    sb.append(code + "\t" + tuple3.middle + "\t" + currentValue + "\t" + gzValue + "\t" + gzPercent + "\t" + tuple3.left + "\t" + "\t" + status + "\n");
                    sb2.append("33#" + code + "\n");
                    sb5.append(list.get(i)+"\t"+status+"\n");
                } else {
                    System.err.println(i + "\t" + code + "\t" + map.get(code) + "\t" + status);
                    sb3.append(code + "\t" + currentValue + "\t" + gzValue + "\t" + gzPercent + "\t" + map.get(code) + "\t" + status + "\n");
                    sb4.append("33#" + code + "\n");
                }
            }
        }
//        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_buy.txt", sb.toString());
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_buy.EBK", sb2.toString());
//        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_sell.txt", sb3.toString());
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\000000_sell.EBK", sb4.toString());
//        getInfo11111();
        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\compare.txt",sb5.toString());

    }

//    @Test
//    public void getInfo11111() throws Exception {
//        List<String> valueList = TxtUtil.readTxt(new File("D:\\BaiduNetdiskDownload\\000EBK\\000000_buy.EBK"));
//        StringBuilder sb3 = new StringBuilder();
//        Map<String,Double> map = new HashMap<>();
//        List<String> list = TxtUtil.readTxt(new File("pool3.txt"));
//        for (int i = 0; i < valueList.size(); i++) {
//            String code = valueList.get(i).split("#")[1];
//            for (int j = 0; j <list.size() ; j++) {
//                if(list.get(j).startsWith(code)){
//                    sb3.append(list.get(j) + "\n");
//                    break;
//                }
//            }
//        }
//        TxtUtil.writeTxt("D:\\BaiduNetdiskDownload\\000EBK\\compare.txt",sb3.toString());
//    }

//    @Test
//    public void getInfo1122() throws Exception {
//        String str = "";
//        List<String> list = TxtUtil.readTxt(new File("pool.txt"));
//        for (int i = 0; i < list.size(); i++) {
//            String code = list.get(i).split("\t")[0];
//            if (getYearValue(code) >= 20.0) {
//                System.out.println(list.get(i) + "\t" + getYearValue(code));
//            }
//        }
//    }

    @Test
    public void getPool2() throws Exception {
        String[] array = new String[]{"z", "1y", "3y", "6y", "jn", "1y"};
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String type = array[i];
            list.addAll(getInfoByType(type));
//            System.out.println(list.size());
        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<String>(list);
        List<String> list2 = new ArrayList<>(hashSet);
        for (int i = 0; i < list2.size(); i++) {
            sb.append(list2.get(i) + "\n");
        }
        TxtUtil.writeTxt(new File("pool2.txt"), sb.toString());
    }

    public List<String> getInfoByType(String type) throws Exception {
        List<String> resultList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String url = "http://fund.eastmoney.com/data/FundGuideapi.aspx?dt=0&rs=" + type + ",100&sd=&ed=&sc=" + type + "&st=desc&pi=" + i + "&pn=20&zf=diy&sh=list&rnd=0." + MathUtil.getRandom(16);
            list.add(url);
        }
        HttpClient httpClient = HttpClients.createDefault();
        for (int i = 0; i < list.size(); i++) {
            String url = list.get(i);
            System.out.println(url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("User-Agent", UserAgentUtil.getRandomAgent());
            HttpResponse response = httpClient.execute(httpGet);
            String contents = EntityUtils.toString(response.getEntity(), "utf8");
            Document document = Jsoup.parse(contents);
            String arrayStr = document.getElementsByTag("body").text().replace("var rankData =", "").replace(";", "");
            JSONObject jsonObject = JSON.parseObject(arrayStr);
            JSONArray jsonArray = jsonObject.getJSONArray("datas");
            for (int k = 0; k < jsonArray.size(); k++) {
                String info = jsonArray.get(k).toString();
                if (info.contains("债") || info.contains("货币") || info.contains("年") || info.contains("月") || info.contains("理财型")) {
                    continue;
                }
                if (info.contains("分级杠杆") || info.contains("固定收益") || info.contains("后端") || info.contains("场内") || info.contains("其他创新")) {
                    continue;
                }
                if (info.contains("FOF") || info.contains("QDII-ETF") || info.contains("QDII-指数") || info.contains("油") || info.contains("QDII")) {
                    continue;
                }
                resultList.add(info.replaceAll(",", "\t"));
            }
        }
        return resultList;
    }

}
