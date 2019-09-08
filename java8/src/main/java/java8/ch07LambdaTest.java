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
 *
 * 重构、测试驱动开发（TDD）和持续集成（CI）越来越流行
 *
 * 这个代码异味是使用继承，其目的只是为了覆盖一个方法。ThreadLocal 就是一个很好的
 * 例子
 *
 * 不要重复你劳动（Don’t Repeat Yourself，DRY）是一个众所周知的模式
 * 如果有一个整体上大概相似的模式，只是行为上有所不同，就可以试着加入一个Lambda 表达式。
 *
 * 第一种是将Lambda 表达式放入一个方法测试，这种方式要测那
 * 个方法，而不是Lambda 表达式本身。例7-8 是一个将一组字符串转换成大写的方法。
 *
 * 测试替身也常被称为模拟，事实上测试存根和模拟都属于测试替身。区别是模
 * 拟可以验证代码的行为
 *
 * 多数的测试替身都很复杂，使用Mockito 这样的框架有助于更容易地产生测试替身
 *
 * 流有一个方法让你能查看每个值，同时能继续操作流。这就是peek 方法
 *
 * 记录日志这是peek 方法的用途之一。为了像调试循环那样一步一步跟踪，可在peek 方法
 * 中加入断点，这样就能逐个调试流中的元素了。
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
