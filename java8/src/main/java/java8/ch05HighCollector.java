package java8;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * @author issuser
 * @date 2019-09-07 18:25
 * <p>
 * 5.1　方法引用标准语法为Classname::methodName,只是提供了和Lambda 表达式等价的一种结构
 * Lambda 表达式经常调用参数
 * 凡是使用Lambda 表达式的地方，就可以使用方法引用
 * 这段代码不仅比原来的代码短，而且更易阅读
 * 另一个要注意的地方是方法引用自动支持多个参数
 * 方法引用只不过是基于这样的事实，提供了一种简短的语法
 * <p>
 * 直观上看，流是有序的，因为流中的元素都是按顺序处理的。这种顺序称为出现顺序。出
 * 现顺序的定义依赖于数据源和对流的操作
 * <p>
 * 流的目的不仅是在集合类之间做转换，而且同时提供了一组处理数据的通用操作。有些集
 * 合本身是无序的，但这些操作有时会产生顺序
 * <p>
 * 一些中间操作会产生顺序，比如对值做映射时，映射后的值是有序的，这种顺序就会保留下来
 * 因为HashSet 是无序的，使用了映射操作后，得到的集合仍然是无序的
 * <p>
 * 比如使用并行流时，forEach 方法不能保证元素是按顺序处理的
 * <p>
 * 这就是收集器，一种通用的、从流生成复杂值的结构。只要将它传给collect 方法，所有
 * 的流就都可以使用它了。
 * <p>
 * 方􀅖 法引用是一种引用方法的轻量级语法，形如：ClassName::methodName。
 * 􀅖 收集器可用来计算流的最终值，是 reduce 方法的模拟。
 * 􀅖 Java 8 提供了收集多种容器类型的方式，同时允许用户自定义收集器。
 */
public class ch05HighCollector {
    List<Artist> list = new ArrayList<>();

    @Before
    public void init() {
        for (int i = 0; i < 10; i++) {
            Artist artist = new Artist();
            artist.setAge(new Random().nextInt(100) + "");
            artist.setName(new String[]{"a", "c", "b"}[new Random().nextInt(3)]);
            artist.setIsFrom(new String[]{"NewYork", "London", "Peking"}[new Random().nextInt(3)]);
            list.add(artist);
        }
    }

    @Test
    public void testMethodQuote() {
        /*artist->artist.getName() 简单写法 Artist::getName*/
        /*String[]::new;*/
        List<Integer> number = asList(1, 2, 3, 4);
        List<Integer> sameOrder = number.stream().collect(toList());
        Assert.assertEquals(number, sameOrder);
        Set<Integer> numbers = new HashSet<>(asList(1, 2, 3, 4));
        List<Integer> noOrder = numbers.stream().collect(toList());
        Assert.assertEquals(numbers, noOrder);
    }

    @Test
    public void testCollection() {
        TreeSet<Artist> set = list.stream().collect(toCollection(TreeSet::new));
        String result = list.stream().map(Artist::getAge).collect(Collectors.joining(",", "[", "]"));

    }


}













