import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
    public static String getNumber(String name) {
        String regEx = "[^0-9]";
        String regEx2 = "^[A-Za-z]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.replaceAll("").trim();
    }

    public static boolean checkChar(String str){
        String regEx =  ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    @Test
    public void remove(){
        String regEx =  ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("12*");
        System.out.println(m.matches());
        System.out.println(m.replaceAll(regEx).trim());
    }
}
