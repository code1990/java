import com.alibaba.fastjson.JSON;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.junit.Test;
import util.MapUtil;
import util.TxtUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Daogou {

    @Test
    public void getInfo() {
        String path = "C:\\Users\\xiala\\Desktop\\";
        Map<String, Double> map = new HashMap<String, Double>();
        for (File file : new File(path).listFiles()) {
            if (!file.getName().contains(".txt")) {
                continue;
            }
            List<String> list = TxtUtil.readTxt(file.getAbsolutePath());
            for (int i = 1; i < list.size(); i++) {
                String str = list.get(i).replace("%", "");
                String[] array = str.split(",");
                String key = array[0];
                String value = array[1];
                if (str.startsWith(",")) {
                    key = array[1];
                    value = array[2];
                }
                if (map.get(key) == null) {
                    map.put(key, new Double(value));
                } else {
                    map.put(key, map.get(key) + new Double(value));
                }
            }

        }
        Map<String, Double> sortMap = MapUtil.sortDesc(map);
        for (Map.Entry<String, Double> entry : sortMap.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
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
            }else{
                sb.append(str);
            }

            if (sb.toString().contains("（教学视频")) {
                System.out.println(sb.toString().split("（教学视频")[0]);
            } else {

                if(sb.toString().split("\\.").length==2){
                    System.out.println(sb.toString());
                }
            }

        }
    }

    @Test
    public void getInfo11111(){
        String path ="C:\\Users\\xiala\\Desktop\\fund123\\fund_pool.txt";
        List<String> list1 = TxtUtil.readTxt("C:\\Users\\xiala\\Desktop\\fund123\\MS_ALL.txt");
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        Map<String,String> map = new HashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String key = list.get(i).split("\t")[1];
            String value = list.get(i).split("\t")[2];
            map.put(key,value);
        }
        for (int i = 1; i <list1.size() ; i++) {
            String str = list1.get(i).split(",")[1];
            if(map.get(str)!=null){
                System.out.println(list1.get(i));
                sb.append(list1.get(i)+"\n");
            }
        }
        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\fund123\\ms_ccmx_bak.txt",sb.toString());
    }

    @Test
    public void getInfo1111(){
        String path ="C:\\Users\\xiala\\Desktop\\fund123\\fund_pool.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
//        Map<String,String> map = new HashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            String key = list.get(i).split("\t")[1];
            String value = list.get(i).split("\t")[2];
            String str = "http://fundf10.eastmoney.com/ccmx_007886.html".replace("007886",key)+"\n";
            System.out.println(str);
            sb.append(str);
//            map.put(key,value);
        }

        TxtUtil.writeTxt("C:\\Users\\xiala\\Desktop\\fund123\\es_ccmx.txt",sb.toString());
    }
}
