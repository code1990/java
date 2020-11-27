package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 911
 * @date 2020-08-08 18:40
 */
public class RegexUtil {
    static Pattern p = Pattern.compile("[\u4e00-\u9fa5]");

    public static boolean isContainChinese(String str) {
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    public void isEnglish(String str) {
        //【全为英文】返回true  否则false
        boolean result1 = str.matches("[a-zA-Z]+");
        //【全为数字】返回true
        Boolean result6 = str.matches("[0-9]+");
        //【除英文和数字外无其他字符(只有英文数字的字符串)】返回true 否则false
        boolean result2 = str.matches("[a-zA-Z0-9]+");
        //【含有英文】true
        String regex1 = ".*[a-zA-z].*";
        boolean result3 = str.matches(regex1);
        //【含有数字】true
        String regex2 = ".*[0-9].*";
        boolean result4 = str.matches(regex2);
        //判断是否为纯中文，不是返回false
        String regex3 = "[\\u4e00-\\u9fa5]+";
        boolean result5 = str.matches(regex3);
        System.out.println(result1 + "--" + result2 + "--" + result3
                + "--" + result4 + "--" + result5 + "--" + result6);
    }

    public static void main(String[] args) {
        String regEx1 = "[\\u4e00-\\u9fa5]";
        String regEx2 = "[a-z||A-Z]";
        String regEx3 = "[0-9]";
        String str = "1 2fdAsz我是hhhZ大傻逼";
        String s1 = matchResult(Pattern.compile(regEx1), str);
        String s2 = matchResult(Pattern.compile(regEx2), str);
        String s3 = matchResult(Pattern.compile(regEx3), str);
        System.out.println(s1 + "\n" + s2 + "\n" + s3);
    }

    public static String matchResult(Pattern p, String str) {
        StringBuilder sb = new StringBuilder();
        Matcher m = p.matcher(str);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                sb.append(m.group());
            }
        }
        return sb.toString();
    }

    public static String getChina(String str) {
        String regEx1 = "[\\u4e00-\\u9fa5]";
        return matchResult(Pattern.compile(regEx1), str);
    }
    public static boolean isChar(String str) {
        if(str==null) {return false;}
        return str.matches("[a-zA-Z]+");
    }

}
