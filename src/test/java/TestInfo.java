import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;
import util.JDBCUtil;
import util.MapUtil;
import util.TxtUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TestInfo {

    @Test
    public void getInfo() {
        for (File file : new File("E:\\新建文件夹\\第四部分实例篇\\第14章\\").listFiles()) {
            System.out.println();
            String name = file.getName();
            System.out.println(name);
            List<String> list = TxtUtil.readGBKTxt(file.getAbsolutePath());
            StringBuilder sb = new StringBuilder();
            for (int i = 3; i < list.size(); i++) {
                String str = list.get(i);
                if (str.startsWith("package")) {
                    str = "package chapter14;";
                }
                sb.append(str + "\n");
                System.out.println(str);
            }
            System.out.println(sb.toString());
            TxtUtil.writeGBKTxt("D:\\github\\javaweb\\rcp_work\\code\\src\\chapter14\\" + name, sb.toString());
//            break;
//            System.out.println(Arrays.toString(list.toArray()));
        }
    }


    @Test
    public void getInfo111() {
        String path = "C:\\Users\\xiala\\Desktop\\";
        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<String, Integer> map2 = new HashMap<String, Integer>();
//        map.put("",0);
        for (File file : new File(path).listFiles()) {
            if(!file.getName().contains(".txt")){
               continue;
            }
            List<String> list = TxtUtil.readTxt(file.getAbsolutePath());
            String name = file.getName();
            for (int i = 1; i < list.size(); i++) {
//                System.out.println(list.get(i));
                String[] str = list.get(i).split(",");
                String info = str[1].trim();
//                System.out.println(info);
                List<Word> result = WordSegmenter.seg(info);
//
                for (int j = 0; j < result.size(); j++) {
                    String key = result.get(j).getText();
                    System.out.println(key);
                    int value = 1;
                    if (map2.get(key) == null) {
                        map2.put(key, value);
                    } else {
                        map2.put(key, map2.get(key) + value);
                    }
                }
                System.out.println(JSON.toJSONString(result));
//                System.out.println(file.getName());
                int key = 0;
                if (name.contains("1m")) {
                    key = 1;
                } else if (name.contains("3m")) {
                    key = 3;
                } else if (name.contains("1y")) {
                    key = 6;
                }
                if (map.get(info) == null) {
                    map.put(info, key);
                } else {
                    map.put(info, map.get(info) + key);
                }

            }
        }
//        for(Map.Entry<String,Integer> m:map.entrySet()){
//            System.out.println(m.getKey()+"\t\t"+m.getValue());
//        }
        Map<String, Integer> sortMap = MapUtil.sortDesc(map);
        for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
        Map<String, Integer> sortMap2 = MapUtil.sortDesc(map2);
        for (Map.Entry<String, Integer> entry : sortMap2.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }


    @Test
    public void getInfo1() {
        String path = "C:\\Users\\xiala\\Desktop\\1.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> legendData = new ArrayList<>();
        List<Series> seriesList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
//            System.out.println(list.get(i));
            String[] array = list.get(i).replaceAll("%", "").split(",");
            legendData.add(array[0]);
//            System.out.println();
//            System.out.println(array[2]);
//            System.out.println(array[3]);
//            System.out.println(array[4]);
//            System.out.println(array[5]);
//            System.out.println(array[6]);
            Series series = new Series();
            series.setName(array[0]);
            List<String> dataList = new ArrayList<>();
            dataList.add(new Double(array[2]) * 100 + "");
            dataList.add(new Double(array[3]) * 100 + "");
            dataList.add(new Double(array[4]) * 100 + "");
            dataList.add(new Double(array[5]) * 100 + "");
            dataList.add(new Double(array[6]) * 100 + "");
            series.setData(dataList);
            seriesList.add(series);
//            System.out.println(series);
        }
        List<String> legend = new ArrayList<>();
        legend.add("近1周");
        legend.add("近1月");
        legend.add("近3月");
        legend.add("近6月");
        legend.add("今年来");
//        System.out.println(Arrays.toString(legend.toArray()));
//        System.out.println(Arrays.toString(legendData.toArray()));
//        System.out.println(Arrays.toString(seriesList.toArray()));
        String head = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>ECharts</title>\n" +
                "    <!-- 引入 echarts.js -->\n" +
                "    <!-- 这里是加载刚下好的echarts.min.js，注意路径 -->\n" +
                "    <script crossorigin=\"anonymous\" integrity=\"sha384-et+fqdf7kslHL5Ip8rXSJPUPODLa7eMfpFTBaCfnlMzrcAz/wxI5Xm/mNTZwd+7H\" src=\"https://lib.baomitu.com/echarts/4.7.0/echarts.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->\n" +
                "    <div id=\"main\" style=\"width: 1000px;height:600px;\"></div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        // 基于准备好的dom，初始化echarts实例\n" +
                "\n" +
                "var myChart = echarts.init(document.getElementById('main'));\n" +
                "\n" +
                "// 指定图表的配置项和数据\n";
        String option = "var option = {\n" +
                "        title: {\n" +
                "            text: '折线图堆叠'\n" +
                "        },\n" +
                "        tooltip: {\n" +
                "            trigger: 'axis'\n" +
                "        },\n" +
                "        legend: {\n" +
                "            data:" + getJsonStr(legendData) + "\n" +
                "        },\n" +
                "        grid: {\n" +
                "            left: '3%',\n" +
                "            right: '4%',\n" +
                "            bottom: '3%',\n" +
                "            containLabel: true\n" +
                "        },\n" +
                "        toolbox: {\n" +
                "            feature: {\n" +
                "                saveAsImage: {}\n" +
                "            }\n" +
                "        },\n" +
                "        xAxis: {\n" +
                "            type: 'category',\n" +
                "            boundaryGap: false,\n" +
                "            data: " + getJsonStr(legend) + "\n" +
                "        },\n" +
                "        yAxis: {\n" +
                "            type: 'value'\n" +
                "        },\n" +
                "        series: \n" + Arrays.toString(seriesList.toArray()) +
                "    };\n";
        String end = "// 使用刚指定的配置项和数据显示图表。\n" +
                "myChart.setOption(option);\n" +
                "\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\" + System.currentTimeMillis() + ".html", head + option + end);
        System.out.println(option);
    }

    public String getJsonStr(List<?> list) {
        String result = Arrays.toString(list.toArray()).replaceAll(",", "','");
        result = result.replace("[", "['");
        result = result.replace("]", "']");
        return result;
    }

    @Test
    public void createJson() {
        JSONObject root = new JSONObject();
        JSONObject title = new JSONObject();
        title.put("text", "折线图堆叠");
        root.put("title", title);
        JSONObject tooltip = new JSONObject();
        tooltip.put("trigger", "axis");
        root.put("tooltip", tooltip);
        JSONArray legend = new JSONArray();
        legend.add("近1周");
        legend.add("近1月");
        legend.add("近3月");
        legend.add("近6月");
        legend.add("今年来");
        root.put("legend", legend);
        System.out.println(root.toJSONString());
    }

    class Series {
        private String name;
        private String type = "line";
        private String stack = "总量";
        private List<String> data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return " \n{\nname:'" + name + "',\n type:'" + type + "',\n stack:'" + stack + "',\n data:" + Arrays.toString(data.toArray())
                    + "\n}\n";
        }
    }

    @Test
    public void getInfo11() {
        String path = "C:\\Users\\xiala\\Desktop\\123.txt";
        List<String> list = TxtUtil.readTxt(path);
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        Map<String,Double> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            String[] array = list.get(i).replaceAll("%", "").split(",");
            List<String> tmpList = new ArrayList<>();
            for (int j = 0; j < array.length; j++) {
                if (j == 0 || j == 3 || j == 4 || j == 5) {
                    if(i==0){
                        tmpList.add("'"+array[j]+"'");
                    }else{
                        if(j==0){
                            tmpList.add("'"+array[j]+"'");
                        }else{
                            tmpList.add(new Double(array[j])*1000+"");
                        }
                    }


                }
            }
            list2.add(Arrays.toString(tmpList.toArray()));
            list3.add("{type: 'line', smooth: true, seriesLayoutBy: 'row'}");
            if(i!=0){
                map.put(array[0],new Double(array[6])-new Double(array[4]));
            }

        }
        System.out.println(Arrays.toString(list2.toArray()));
        System.out.println(Arrays.toString(list3.toArray()));
        String head = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>ECharts</title>\n" +
                "    <!-- 引入 echarts.js -->\n" +
                "    <!-- 这里是加载刚下好的echarts.min.js，注意路径 -->\n" +
                "    <script crossorigin=\"anonymous\" integrity=\"sha384-et+fqdf7kslHL5Ip8rXSJPUPODLa7eMfpFTBaCfnlMzrcAz/wxI5Xm/mNTZwd+7H\" src=\"https://lib.baomitu.com/echarts/4.7.0/echarts.min.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->\n" +
                "    <div id=\"main\" style=\"width: 1000px;height:600px;\"></div>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        // 基于准备好的dom，初始化echarts实例\n" +
                "\n" +
                "var myChart = echarts.init(document.getElementById('main'));\n" +
                "\n" +
                "// 指定图表的配置项和数据\n";
        String option = "  var  option = {\n" +
                "        legend: {},\n" +
                "        tooltip: {\n" +
                "            trigger: 'axis',\n" +
                "            showContent: false\n" +
                "        },\n" +
                "        dataset: {\n" +
                "            source: "+Arrays.toString(list2.toArray())+
                "        },\n" +
                "        xAxis: {type: 'category'},\n" +
                "        yAxis: {gridIndex: 0},\n" +
                "        grid: {top: '10%'},\n" +
                "        series: "+Arrays.toString(list3.toArray())+
                "    };\n";
        String end = "// 使用刚指定的配置项和数据显示图表。\n" +
                "myChart.setOption(option);\n" +
                "\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\" + System.currentTimeMillis() + ".html", head + option + end);
//        System.out.println(option);

        Map<String, Double> sortMap2 = MapUtil.sortDesc(map);
        for (Map.Entry<String, Double> entry : sortMap2.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

    @Test
    public void getInfo123(){
        for (int i = 1; i <32 ; i++) {
            String str = ""+i;
            if(i<10){
                str = "0"+i;
            }
            System.out.println("### 2020-07-"+str+" 日报");
        }
    }

    @Test
    public void getInfo666(){
        try {
            new File("123.sql").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> list = TxtUtil.readTxt("123.sql");
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }

    }

}
