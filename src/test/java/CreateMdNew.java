import org.junit.Test;
import util.TxtUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: spider
 * @Date: 2020-04-18 12:18
 * @Author: code1990
 * @Description: 第1章信息化知识 1
 * 1.1信息与信息化 1
 * 1.1.1信息 1
 * 1.1.2信息系统 5
 * 1.1.3信息化 7
 * 1.1.4国家信息化体系要素 8
 * 1.1.5信息技术发展及趋势 13
 * 1.2国家信息化战略和规划 20
 * 1.2.1国家信息化战略目标 20
 * 1.2.2信息化的指导思想和基本原则 21
 * 1.2.3我国信息化发展的主要任务和发展重点 22
 */
public class CreateMdNew {
    private String admin = System.getProperty("user.name");
    Map<String, String> map = new HashMap<>();

    @Test
    public void filterInfo() {
        String path = "C:\\Users\\" + admin + "\\Desktop\\124.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
//            if(name.contains(" 章")){
//                name = name.replace(" 章","");
//            }
//            if (name.contains("节") || name.contains("章")) {
//                name = name.replaceAll("\\s", "");
//            }
            if (name.contains("小结")) {
                continue;
            }
//            if (name.contains("教学") || name.contains("例题") || name.contains("部分")) {
//                continue;
//            }
//            if (name.contains("练习") || name.contains("答案")) {
//                continue;
//            }
            if (name.contains("/")) {
                name = name.replaceAll("/", "");
            }
            if (name.contains("*")) {
                name = name.replaceAll("\\*", "");
            }
            if (name.contains(":")) {
                name = name.replaceAll(":", "");
            }
            if (name.contains(" ")) {
                String[] tmpArray = name.split(" ");
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < tmpArray.length - 1; j++) {
                    stringBuilder.append(tmpArray[j]);
                }
                name = stringBuilder.toString();
//            String name ="";
//                if (tmpArray.length == 2) {
//                    name = tmpArray[0].trim();
//                } else {
//                    name = tmpArray[0].trim() + tmpArray[1].trim();
//                }
            }

            sb.append(name + "\n");
        }
        TxtUtil.writeTxt(path, sb.toString());
    }

    @Test
    public void getInfo3() {
        String path = "C:\\Users\\" + admin + "\\Desktop\\124.txt";
        List<String> list = TxtUtil.readTxt(path);
        String chapter = "";
        String tmp = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            try {
                if (name.contains("章") && !name.contains("章程")) {
                    if (sb.length() != 0) {
                        TxtUtil.writeTxt(tmp, "## " + chapter + "\n" + sb.toString());
                        sb.setLength(0);
                    }
//                    chapter = name.replace("第", "").split("章")[0];
//                    if (new Integer(chapter) < 10) {
//                        chapter = "0" + chapter;
//                    }
                    tmp = "C:\\Users\\" + admin + "\\Desktop\\dir\\" + filter(name) + ".md";
                    System.out.println(tmp);
                    new File(tmp).createNewFile();
                    chapter = name;
                } else {
                    if (name.contains(".")) {
                        if (name.split("\\.").length == 2) {
                            if (i + 1 < list.size()) {
                                if (list.get(i + 1).split("\\.").length == 2) {
                                    sb.append("#### " + name + "\n\n\n\n");
                                } else {
                                    sb.append("### " + name + "\n");
                                }
                            } else {
                                sb.append("### " + name + "\n");
                            }

                        } else {
                            sb.append("#### " + name + "\n\n\n\n");
                        }
                    } else {
                        sb.append("#### " + name + "\n\n\n\n");
                    }

                }
                if (i == list.size() - 1) {
                    TxtUtil.writeTxt(tmp, sb.toString());
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public String filter(String name) {
        String[] array = name.replace("第", "").split("章")[0].split("");
        map.put("一", "1");
        map.put("二", "2");
        map.put("三", "3");
        map.put("四", "4");
        map.put("五", "5");
        map.put("六", "6");
        map.put("七", "7");
        map.put("八", "8");
        map.put("九", "9");
        map.put("十", "0");
//        System.out.println(name+"\t"+array.length);
        if (array.length == 1) {
            if (Character.isDigit(array[0].charAt(0))) {
                return name.replace(array[0], "0" + array[0]);
            }
            if (name.contains("十")) {
                name = name.replace(array[0], "1" + map.get(array[0]));
            } else {
                name = name.replace(array[0], "0" + map.get(array[0]));
            }
        } else if (array.length == 2) {
            if (Character.isDigit(array[0].charAt(0))) {
                return name;
            }
            if ("十".equals(array[0])) {
                name = name.replace(array[0], "1");
                name = name.replace(array[1], map.get(array[1]));

            } else {
                name = name.replace(array[0], map.get(array[0]));
                name = name.replace("十", "0");
            }
        } else if (array.length == 3) {
            if (name.contains("十")) {
                name = name.replace("十", "");
            }
            name = name.replace(array[0], map.get(array[0]));
            name = name.replace(array[2], map.get(array[2]));
        }
        return name;
    }


    @Test
    public void dealFile() {
        String path = "C:\\Users\\admin\\Desktop\\124.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
//            String name = list.get(i).replaceAll("．",".").replaceAll(" ","");
            String name = list.get(i);
            String[] array = name.split("　");
            System.out.println(array[0] + array[1]);
            sb.append(array[0] + array[1] + "\n");
        }
        TxtUtil.writeTxt(path, sb.toString());
    }

    @Test
    public void createBook() {
        String path = "C:\\Users\\admin\\Desktop\\125.txt";
        List<String> list = TxtUtil.readTxt(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            new File("D:\\github\\book\\java\\" + name).mkdir();
        }
    }

    @Test
    public void createJavaCode() {
        String path = "C:\\Users\\" + admin + "\\Desktop\\124.txt";
        List<String> list = TxtUtil.readTxt(path);
        String chapter = "";
//        String tmp = "";
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);

            if (name.contains("第") && name.contains("章")) {
                if (sb.length() != 0) {
                    sb.append("\t}\n");
                    sb.append("}\n");
//                    System.out.println(">>>>>" + sb.toString());
                    TxtUtil.writeTxt("C:\\Users\\" + admin + "\\Desktop\\dir\\Chapter" + chapter + "Test.java", sb.toString());
                    sb.setLength(0);
                }
                name = getNumber(name);
                if (new Integer(name) <= 9) {
                    name = "0" + name;
                }
                chapter = name;
                sb.append("import org.junit.Test;\n" +
                        "\n" +
                        "public class Chapter" + name + "Test {\n");
//                System.out.println(name);
            } else {
                if (name.contains(".")) {

                    if (name.split("\\.").length == 2) {
                        if (sb.toString().contains("void")) {
                            sb.append("\t\n\t}\n\n");
                        }
                        String tmp = name;
                        name = getNumber(name);
                        sb.append("    @Test\n" +
                                "    public void test" + name + "() {\n");
                        sb.append("\t\t// " + tmp + "\n");
                    } else {
//                        System.out.println(">>>>"+name);
                        sb.append("\t\t// " + name + "\n");
                    }
                }
                if (i == list.size() - 1) {
                    sb.append("\t}\n");
                    sb.append("}\n");
//                    System.out.println(">>>>>" + sb.toString());
                    TxtUtil.writeTxt("C:\\Users\\" + admin + "\\Desktop\\dir\\Chapter" + chapter + "Test.java", sb.toString());
                    sb.setLength(0);
                }
            }
//            System.out.println(name);
//            String a = "love12next34csde54434java";
//            String regEx = "[^0-9]";
//            Pattern p = Pattern.compile(regEx);
//            Matcher m = p.matcher(name);
//            System.out.println(m.replaceAll("").trim());
//            System.out.println(m.replaceFirst("").trim());

        }
        System.out.println(sb.toString());
    }

    public String getNumber(String name) {
        String regEx = "[^0-9]";
        String regEx2 = "^[A-Za-z]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.replaceAll("").trim();
    }

    @Test
    public void getChar(){
        String name ="java微服务";
        String regEx2 = "[^A-Za-z]";
        Pattern p = Pattern.compile(regEx2);
        Matcher m = p.matcher(name);
        System.out.println(m.replaceAll("").trim());
    }
}
