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
}
