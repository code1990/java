package helloworld;

import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author issuser
 * @date 2019-08-24 14:09
 */
public class PatternMatcherTest {

    /*Pattern定义要使用的正则表达式对象*/
    /*Mattern使用正则标记与指定内容匹配
     * \\表示转义
     * [abc] 匹配a,b,c任意一个
     * [^abc]不是a,b,c任意一个
     *[a-z] 所有的小写字母
     * [a-zA-Z]所有的字母
     * \d \s \w 分别表示任意的数字 任意的空白符 任意的字母数字
     * \D \S \W 表示对上面取反
     * ^开头 $结尾
     * ? 0次或1次 +表示1次多次 * 所有的次
     * {n} n次 {n,} n次以上 {n,m}n到m次
     * */
    @Test
    public void testRegex() {
        /*匹配任意的大写 小写取反即为大写*/
        String regex = "[^a-z]";
        System.out.println("Hello".replaceAll(regex, ""));
        /*按照数字拆分*/
        String regex2 = "\\d+";
        System.out.println(Arrays.toString("Hello1java2world3Ok".split(regex2)));
        String regex3 = "\\d+(\\.\\d+)?";
        System.out.println("10.11".matches(regex3));
        String regex4 = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        System.out.println("192.168.0.1".matches(regex4));
        String regex5 = "\\d{4}-\\d{2}-\\d{2}";
        System.out.println("2019-01-01".matches(regex5));
        String regex6 = "\\w+@\\w+\\.ww+";
        System.out.println("123@qq.com".matches(regex6));
    }

    @Test
    public void testPattern() throws Exception {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("100");
        System.out.println(matcher.matches());
    }

    @Test
    public void testLocal() {
        /*国际化*/
        Locale locale = Locale.getDefault();
        System.out.println(locale.toLanguageTag());
        Locale usa = Locale.US;
        System.out.println(usa);
        /*classpath就是指src/main/java，src/main/resources，src/main/webapp，src/main/properties*/

    }

    @Test
    public void getResources() throws Exception {
        /*读取系统资源文件*/
        String path = PatternMatcherTest.class.getResource("config.properties").getPath();
        System.out.println(path);
//        ResourceBundle rb = ResourceBundle.getBundle(path);
//        System.out.println(rb.getString("hello"));
        InputStream is = PatternMatcherTest.class.getResourceAsStream("config.properties");
        Properties p = new Properties();
        p.load(is);
        System.out.println(p.get("hello"));
    }

    @Test
    public void testRuntime() {
        /*运行时操作对象 单例对象*/
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max" + runtime.maxMemory());
        System.out.println("Total" + runtime.totalMemory());
        System.out.println("Free" + runtime.freeMemory());
        /*释放内存*/
        /*等价于System.gc*/
        runtime.gc();
        System.out.println("Max" + runtime.maxMemory());
        System.out.println("Total" + runtime.totalMemory());
        System.out.println("Free" + runtime.freeMemory());
    }
}
