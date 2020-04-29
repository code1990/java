package util;

import org.junit.Test;
import util.TxtUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author wei
 * @description
 * @date 2020/4/24
 */
public class BeanValidationTest {

//    @NotNull
    private String name;

    @Test
    public void yaml2properties() {
        String path = "C:\\Users\\Administrator\\Desktop\\application.yaml";
        List<String> list = TxtUtil.readTxt(path);
        Map<Integer, String> map = new HashMap<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            if (!name.contains("  ") && !name.contains("#")) {
                list2.add(i);
            }
        }
        list2.add(list.size());
        Collections.reverse(list);
        StringBuilder sb = new StringBuilder();
        int sonLength = 0;
        List<String> list3 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            if (name.contains("#")) {
                list3.add(name.trim());
                continue;
            }
            String[] array = name.split("  ");
            sonLength = array.length;
            String all = "";
            for (int j = sonLength; j >= 1; j--) {
                for (int k = i; k < list.size(); k++) {
                    String tmp = list.get(k);
                    if (tmp.contains("#")) {
                        continue;
                    }
                    if (j == tmp.split("  ").length) {
                        tmp = tmp.replace(": ", "=");
                        if (tmp.endsWith(":")) {
                            tmp = tmp.replace(":", ".");
                        }
                        all = tmp.trim() + all;
                        break;
                    }
                }
            }
            if (!all.endsWith(".") && !all.trim().equals("")) {
                list3.add(all);
            }
        }
        Collections.reverse(list3);
        for (int i = 0; i < list3.size(); i++) {
            System.out.println(list3.get(i));
        }
    }

    @Test
    public void yaml2propertiesnew() {
        String path = "C:\\Users\\Administrator\\Desktop\\application.yaml";
        List<String> list = TxtUtil.readTxt(path);
        Collections.reverse(list);
        int sonLength = 0;
        List<String> list3 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            if (name.contains("#")) {
                list3.add(name.trim());
                continue;
            }
            String[] array = name.split("  ");
            sonLength = array.length;
            String all = "";
//            for (int j = sonLength; j >= 1; j--) {
//                for (int k = i; k < list.size(); k++) {
//                    String tmp = list.get(k);
//                    if (tmp.contains("#")) {
//                        continue;
//                    }
//                    if (j == tmp.split("  ").length) {
//                        tmp = tmp.replace(": ", "=");
//                        if (tmp.endsWith(":")) {
//                            tmp = tmp.replace(":", ".");
//                        }
//                        all = tmp.trim() + all;
//                        break;
//                    }
//                }
//            }
            if (!name.contains(": ")) {
                continue;
            }
            while (sonLength >= 1) {
                for (int k = i; k < list.size(); k++) {
                    String tmp = list.get(k);
                    if (tmp.contains("#")) {
                        continue;
                    }
                    if (sonLength == tmp.split("  ").length) {
                        tmp = tmp.replace(": ", "=");
                        if (tmp.endsWith(":")) {
                            tmp = tmp.replace(":", ".");
                        }
                        all = tmp.trim() + all;
                        break;
                    }
                }
                sonLength--;
            }
            if (!all.endsWith(".") && !all.trim().equals("")) {
                list3.add(all);
            }
        }
        Collections.reverse(list3);
        for (int i = 0; i < list3.size(); i++) {
            System.out.println(list3.get(i));
        }


    }

    @Test
    public void properties2yaml() {
        String path = "C:\\Users\\Administrator\\Desktop\\application.properties";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        int start = new Integer(TxtUtil.readTxt("C:\\Users\\Administrator\\Desktop\\ok.txt").get(0));
        for (int i = start; i < list.size(); i++) {
            String name = list.get(i);
            if (name.startsWith("#")) {
                sb.append(name + "\n");
            } else {
                String[] array = name.split("\\.");
                boolean flag = sb.toString().equals("") || sb.toString().contains(array[0] + ":");
                System.out.println(flag);
//            if(sb.toString().equals("") || !sb.toString().contains(array[0] + ":")){
                if (!sb.toString().contains(array[0] + ":")) {
                    sb.append(array[0] + ":\n");
                }

//            if (!sb.toString().contains(array[0] + ":")) {
//                sb.append(array[0] + ":\n");
//            }
                for (int j = 1; j < array.length; j++) {
                    String son = array[j];
//                    if(!sb.toString().contains(son + ":")){
//                        sb.append(array[0] + ":\n");
//                    }else{
                    son = son.replace("=", ": ");
                    sb.append("  " + son);
//                    }
//                System.out.println(son);
//                if(!sb.toString().contains(son)){
//                    sb.append(son+":\n");
//                }
//                else{
//
//                }
                }
            }
        }
        System.out.println(sb.toString());
    }


    @Test
    public void getInfo() {
        String kw = "微服务";
        String path = "C:\\Users\\Administrator\\Desktop\\" + kw;
//        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (File f : new File(path).listFiles()) {
            List<String> tmp = TxtUtil.readTxt(f.getAbsolutePath());
            for (int i = 0; i < tmp.size(); i++) {
                String name = tmp.get(i).trim();
//                System.out.println(name);
                if (set.add(name)) {
                    System.out.println(name);
                    sb.append(name + "\n");
                }
            }
            TxtUtil.writeTxt(path + "11.txt", sb.toString());
//            list.addAll(tmp);
        }
    }

    @Test
    public void getInfo66() throws IOException {
        List<String> list = new ArrayList<>();
        list.add("mybatis");
        list.add("zookeeper");
        list.add("dubbo");
        list.add("elasticsearch");
        list.add("memcached");
        list.add("redis");
        list.add("并发");
        list.add("java");
        list.add("spring");
        list.add("linux");
        list.add("springboot");
        list.add("springcloud");
        list.add("rabbitmq");
        list.add("kafka");
        for (int i = 0; i < list.size(); i++) {
            String kw = list.get(i);
            new File("C:\\Users\\Administrator\\Desktop\\work\\" + kw + ".txt").createNewFile();
        }
//        String kw = "mybatis";
//        String kw = "zookeeper";
//        String kw = "dubbo";
//        String kw = "elasticsearch";
//        String kw = "memcached";
//        String kw = "redis";
//        String kw = "并发";
//        String kw = "java";
//        String kw = "spring";
//        String kw = "linux";
//        String kw = "springboot";
//        String kw = "springcloud";
//        String kw = "rabbitmq";
//        String kw = "kafka";
//        String kw = "redis";
//        String kw = "redis";
//        String kw = "redis";
        for (int i = 0; i < list.size(); i++) {
            String kw = list.get(i);
            String str2 = java.net.URLEncoder.encode(kw, "utf-8");
            String url = "https://www.jianshu.com/search?q=" + str2 + "%E9%9D%A2%E8%AF%95%E9%A2%98&page=1&type=note";
            System.out.println(url);
        }
//        String url = "https://www.baidu.com/s?ie=utf-8&wd="+str2+"%E9%9D%A2%E8%AF%95%E9%A2%98%20%E5%8D%9A%E5%AE%A2%E5%9B%AD";

//        List<String> list = new ArrayList<>();
//        list.add("a");
//        list.add("a");
//        list.add("c");
//        Set<String> set = new HashSet<>();
//        for (int i = 0; i < list.size(); i++) {
//            String name = list.get(i);
//            if(set.add(name)){
//                System.out.println(name);
//            }
//        }


    }

    @Test
    public void getInfo1() {
        String path = "C:\\Users\\Administrator\\Desktop\\springcloud.txt";
        Set<String> set = new HashSet<>();
        for (String str : TxtUtil.readTxt(path)) {
            if(str.contains("http")){
                continue;
            }
            if (set.add(str)) {
                System.out.println(str);
            }
        }
    }
}

