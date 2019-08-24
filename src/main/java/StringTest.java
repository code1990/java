import org.junit.Test;

/**
 * @author issuser
 * @date 2019-08-16 16:46
 * 字符串的常用方法
 */
public class StringTest {

    public static void main(String[] args) {
        /*1.字符串的 声明与创建*/
        String str = "HelloWorld";
        /*2.字符串的与其他数据的连接*/
        String string = "Today" + " is my birthday, I am " + 19 + " years old.Tomorrow I am " + (19 + 1) + "years old, I have " + 2.3 + "dollar";
        /*3.获取字符串的长度*/
        System.out.println("Hello".length());
        /*4.字符串的查找 如果查找到返回指定的下标 否则返回-1*/
        if ("Hello".indexOf("H") != -1) {
            System.out.println("索引值不等于-1表示包含");
        } else {
            System.out.println("索引值等于-1表示不包含");
        }
        if ("Hello".contains("H")) {
            System.out.println("包含");
        }
        /*5.判断字符串最后一次出现的位置*/
        if ("Hello".lastIndexOf("l") != -1) {
            System.out.println("最后一次出现的位置:" + "Hello".lastIndexOf("l"));
        }
        /*6.获取指定下标的字符 下标从0开始*/
        char c = "Hello".charAt(1);
        System.out.println(c);
        /*7.获取字符串的子串 下标从0开始 结束位置不包含*/
        String subStr = "HelloWorld".substring(0, 5);
        System.out.println(subStr);
        /*8.字符串去空格*/
        System.out.println("  HelloWorld  ".trim());
        /*9.字符串的 替换和全部替换*/
        System.out.println("HelloWorld".replace("l", "i"));
        System.out.println("HelloWorld".replaceAll("l", "i"));
        /*10.判断字符串的为xx开头和和以xx结尾*/
        System.out.println("Hello".startsWith("h"));
        System.out.println("Hello".endsWith("x"));
        /*11.判断字符串是否相等 忽略大小写判断相等*/
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = new String(str1);
        System.out.println(str1.equals(str2));
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str3));
        System.out.println(str1 == str3);
        System.out.println(str1.equals(str4));
        System.out.println(str1 == str4);
        System.out.println("Hello".equalsIgnoreCase("HEllo"));
        /*12.按照字典表中每一个字符的Unicode码比较2个字符串的大小 大于返回正数 小于返回负数 等于返回0*/
        System.out.println("a".compareTo("b"));
        System.out.println("b".compareTo("a"));
        System.out.println("a".compareTo("a"));
        /*13.字符串的大小写转换*/
        System.out.println("Hello".toLowerCase());
        System.out.println("Hello".toUpperCase());
        /*14.字符串的拼接 使用+拼接字符串内存开销太大 */
        /*StringBuffer StringBuilder 都是动态字符串 使用上无任何区别 前者是线程安全的 后者性能更好*/
        StringBuilder sb = new StringBuilder();
        for (char cc : "HelloWorld".toCharArray()) {
            /*字符串拼接*/
            sb.append(cc);
        }
        /*字符串在指定位置插入指定字符串*/
        sb.insert(4, "Java");
        System.out.println(sb.toString());
        /*字符串在指定位置删除指定起始下标位置的字符*/
        sb.delete(0, 1);
        System.out.println(sb.toString());

        /*字符串格式化*/
        String name = "Green";
        int age = 10;
        double score = 60.112333;
        String info = String.format("name:%s,age:%d,score:%5.2f", name, age, score);
        System.out.println(info);
    }


}
