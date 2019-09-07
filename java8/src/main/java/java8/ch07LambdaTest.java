package java8;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author issuser
 * @date 2019-09-07 23:34
 */
public class ch07LambdaTest {

    public static List<String> allToUpperCase(List<String> words) {
        return words.stream()
                .map(string -> string.toUpperCase())
                .collect(Collectors.<String>toList());
    }

    /*测试大写转换*/
    @Test
    public void multipleWordsToUppercase() {
        List<String> input = asList("a", "b", "hello");
        List<String> result = allToUpperCase(input);
        Assert.assertEquals(asList("A", "B", "HELLO"), result);
    }

    public static List<String> elementFirstToUpperCaseLambdas(List<String> words) {
        return words.stream()
                .map(value -> {
                    char firstChar = Character.toUpperCase(value.charAt(0));
                    return firstChar + value.substring(1);
                })
                .collect(Collectors.<String>toList());
    }

    @Test
    public void twoLetterStringConvertedToUppercaseLambdas() {
        List<String> input = Arrays.asList("ab");
        List<String> result = elementFirstToUpperCaseLambdas(input);
        Assert.assertEquals(asList("Ab"), result);
    }

    public static String firstToUppercase(String value) {
        char firstChar = Character.toUpperCase(value.charAt(0));
        return firstChar + value.substring(1);
    }
    /*请用方法引用。任何Lambda 表达式都能被改写为普通方法，然后使用方法引用直接引用。*/
    @Test
    public void twoLetterStringConvertedToUppercase() {
        String input = "ab";
        String result = firstToUppercase(input);
        Assert.assertEquals("Ab", result);
    }
}
