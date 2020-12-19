import org.junit.Test;
import util.TxtUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnkiTest {

    @Test
    public void getInfo111() {

        List<String> list = TxtUtil.readTxt(new File("D:\\github\\java\\anki123.txt"));

        String title = "04.SQL索引优化__";
        for (int i = 0; i < list.size(); i++) {
            String num = i + "";
            if (i < 9) {
                num = "0" + (i + 1);
            }
//            System.out.println(title + num);
            System.out.println(list.get(i)+"\t"+list.get(i));
        }

    }

    @Test
    public void getInfo1111() {
        String path = "D:\\BaiduNetdiskDownload\\大型电商--谷粒商城\\3.高可用集群篇（架构师提升篇）\\视频";
        File[] files = new File(path).listFiles();
        Map<Integer, String> map = new HashMap<>();
        String fileSplit ="：";
        for (int i = 0; i < files.length; i++) {
            String value ="";
            String name =files[i].getName();
            if (name.contains(fileSplit)){
                String info = name.split(fileSplit)[0];
                value = name.split(fileSplit)[1];
                info = info.replace("第","").replace("节","");
                int num = convertNumber(info);
                String prefix =""+num;
                if (num<=9){
                    prefix = "0"+num;
                }
                if (value.contains("-")){
                    value = value.split("-")[0];
                }
                map.put(num,prefix+"."+value);
            }else{
                String key = name.split(".")[0];
                if (key.length()==1){
                    name = "0"+name;
                }
//                if (name.contains("-")){
//                    name = name.split("-")[0];
//                }
                map.put(new Integer(key),name);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            if (map.get(i)==null){
                continue;
            }
            sb.append(map.get(i+1)+"\t"+map.get(i+1)+"\n");
        }
        TxtUtil.writeTxt(new File("anki123.txt"),sb.toString());
    }

    public Integer convertNumber(String cn) {
        int result =0;
        Map<String, Integer> map = new HashMap<>();
        map.put("零", 0);
        map.put("一", 1);
        map.put("二", 2);
        map.put("两", 2);
        map.put("三", 3);
        map.put("四", 4);
        map.put("五", 5);
        map.put("六", 6);
        map.put("七", 7);
        map.put("八", 8);
        map.put("九", 9);
        map.put("十", 10);
        if (cn.length()==1){
            result = map.get(cn);
        }else if (cn.length()==2){
            String[] array = cn.split("");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <array.length ; i++) {
                if (array[i].equals("十")){
                    sb.append("1");
                }else{
                    sb.append(map.get(array[i]));
                }
            }
            result = new Integer(sb.toString());
        }
        return result;
    }
}
