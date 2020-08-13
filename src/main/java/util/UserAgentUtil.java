package util;

import org.junit.Test;

import java.util.Random;

/**
 * @author 911
 * @date 2020-08-08 16:31
 */
public class UserAgentUtil {
    private static final String WIN7_Chrome="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1";
    private static final String WIN7_Firefox="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0) Gecko/20100101 Firefox/6.0";
    private static final String WIN7_Safari="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
    private static final String WIN7_Opera="Opera/9.80 (Windows NT 6.1; U; zh-cn) Presto/2.9.168 Version/11.50";

    public static String getRandomAgent(){
        Random ra =new Random();
        String[] array = new String[]{WIN7_Chrome,WIN7_Firefox,WIN7_Safari,WIN7_Opera};
        return array[ra.nextInt(3)+1];
    }

}
