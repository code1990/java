package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 911
 * @date 2020-11-11 14:02
 */
public class StringUtil {
    public static String replaceStr(String orgStr){
        if (null!=orgStr&&!"".equals(orgStr.trim())) {
            String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(orgStr);
            return m.replaceAll("");
        }
        return null;
    }

    public static String convert(String value) {
        if (value.contains("%")) {
            value = value.replace("%", "");
        }
        return value;
    }
}
