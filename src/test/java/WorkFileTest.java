import org.apdplat.word.segmentation.Word;
import org.junit.Test;
import util.TxtUtil;
import word.WordTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorkFileTest {
    private final String admin = System.getProperty("user.name");
    private final String path = "F:\\pdf\\dir\\1.txt";
    List<String> list = TxtUtil.readTxt(path);

    @Test
    public void getNumberInfo() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 1; i < list.size(); i++) {
            String name = list.get(i);
            if (name.contains("微信分享")) {
                continue;
            }
            if (name.contains("关键字")) {
                continue;
            }
            if (name.contains("职能类别")) {
                continue;
            }
//            String  str="SUN公司被Oracle收购，是否意味着java被逼上了死路？";
            String s = "\\d+.\\d+|\\w+";
            Pattern pattern = Pattern.compile(s);
            Matcher ma = pattern.matcher(name);

            while (ma.find()) {
                String tmp = ma.group().trim().toLowerCase();
                if (tmp.contains("k")) {
                    continue;
                }
                if (!"".equals(RegexTest.getNumber(tmp))) {
                    continue;
                }
//                System.out.println(ma.group());
                if (map.get(tmp) == null) {
                    map.put(tmp, 1);
                } else {
                    map.put(tmp, map.get(tmp) + 1);
                }
            }
        }
        Map<String, Integer> sortMap = MapTest.sortByValueDescending(map);
        for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
            if (entry.getValue() < 10) {
                continue;
            }
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

    @Test
    public void getkeyInfo() {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 1; i < list.size(); i++) {
            String name = list.get(i);
            if (name.contains("微信分享")) {
                continue;
            }
            if (name.contains("关键字")) {
                continue;
            }
            if (name.contains("职能类别")) {
                continue;
            }
            if(name.contains("上班地址")){
                continue;
            }
            List<Word> list = WordTest.getWord(name);
            for(Word word:list){
                String tmp = word.getText();
                if (tmp.contains("k")) {
                    continue;
                }
                if (!"".equals(RegexTest.getNumber(tmp))) {
                    continue;
                }
                if(RegexTest.checkChar(tmp)){
                    continue;
                }
                if (map.get(tmp) == null) {
                    map.put(tmp, 1);
                } else {
                    map.put(tmp, map.get(tmp) + 1);
                }
            }
        }
        Map<String, Integer> sortMap = MapTest.sortByValueDescending(map);
        for (Map.Entry<String, Integer> entry : sortMap.entrySet()) {
            if (entry.getValue() < 10) {
                continue;
            }
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }
    }

    public String getChar(String name) {
//        String name = "java微服务";
        String regEx2 = "[^A-Za-z]";
        Pattern p = Pattern.compile(regEx2);
        Matcher m = p.matcher(name);
        return m.replaceAll("").trim();
    }

}
