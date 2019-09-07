package java8;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author issuser
 * @date 2019-09-07 06:54
 * <p>
 * 流使程序员得以站在更高的抽象层次上对集合进行操作。
 * Stream 是用函数式编程方式在集合类上进行复杂操作的工具。
 */
public class ch3Stream {
    List<Artist> list = new ArrayList<>();

    @Before
    public void initList() {

        for (int i = 0; i < 10; i++) {
            Artist artist = new Artist();
            artist.setAge(new Random().nextInt(100) + "");
            artist.setName(new String[]{"a", "c", "b"}[new Random().nextInt(3)]);
            artist.setIsFrom(new String[]{"NewYork", "London", "Peking"}[new Random().nextInt(3)]);
            list.add(artist);
        }
    }

    /*外部迭代*/
    @Test
    public void testFor() {
        int count = 0;
        for (Artist artist : list) {
            if (artist.getIsFrom().equals("London")) {
                count++;
            }
        }
        System.out.println(count);
    }

    @Test
    public void testIterator() {
        int count = 0;
        Iterator<Artist> iterator = list.iterator();
        while (iterator.hasNext()) {
            Artist artist = iterator.next();
            if (artist.getIsFrom().equals("London")) {
                count++;
            }
        }
        System.out.println(count);
    }

    /*内部迭代 内部迭代将更多控制权交给了集合类。*/
    @Test
    public void testStream() {
        long count = list.stream()
                .filter(artist -> artist.getIsFrom().equals("Londo")).count();
        System.out.println(count);
    }

    /*
    像filter 这样只描述Stream，最终不产生新集合的方法叫作惰性求值方法；
而像count 这样最终会从Stream 产生值的方法叫作及早求值方法。
    * */
    @Test
    public void testFilter() {
        /*filter 只刻画出了Stream，但没有产生新的集合。*/
        list.stream().filter(artist -> {
            System.out.println(artist.getName());
            return artist.getIsFrom().equals("London");
        });
    }

    /*判断一个操作是惰性求值还是及早求值很简单：只需看它的返回值。如果返回值是Stream，
    那么是惰性求值；如果返回值是另一个值或为空，那么就是及早求值
    整个过程和建造者模式有共通之处。建造者模式使用一系列操作设置属性和配置，最后调
    用一个build 方法，这时，对象才被真正创建。
    */
    @Test
    public void testCount() {
        long count = list.stream().filter(artist -> {
            System.out.println(artist.getName());
            return artist.getIsFrom().equals("London");
        }).count();
        System.out.println(count);
    }

    @Test
    public void testStreamApi() {
        /*collect(toList()) 方法由Stream 里的值生成一个列表，是一个及早求值操作。*/
        List<String> collectList = Stream.of("a", "b", "c").collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("a", "b", "c"), collectList);
        /*Map如果有一个函数可以将一种类型的值转换成另外一种类型，map 操作就可以
使用该函数，将一个流中的值转换成一个新的流。*/
        /*传给map ➊ 的Lambda 表达式只接受一个String 类型的参数，返回一个新的String*/
        List<String> mapList = Stream.of("a", "b", "c")
                .map(str -> str.toUpperCase()).collect(Collectors.toList());
//        Assert.assertEquals(Arrays.asList("a","b","c"),mapList);

        /*遍历数据并检查其中的元素时，可尝试使用Stream 中提供的新方法filter*/
        List<String> filterList = Stream.of("a", "1abc", "abc1")
                .filter(value -> Character.isDigit(value.charAt(0))).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList("1abc"), filterList);
        /*flatMap 方法可用Stream 替换值， 然后将多个Stream 连接成一个Stream*/
        List<Integer> flatMapList = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
                .flatMap(value -> value.stream()).collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4), flatMapList);
        /*Stream 上常用的操作之一是求最大值和最小值。Stream API 中的max 和min 操作足以解决这一问题*/
        /*为了让Stream 对象按照曲目长度进行排序，需要传给它一个Comparator 对象。Java 8 提
供了一个新的静态方法comparing，使用它可以方便地实现一个比较*/
        Artist artist = list.stream().min(Comparator.comparing(value -> value.getAge())).get();
        System.out.println(artist.toString());
        /*3.3.7　reduce reduce 操作可以实现从一组值中生成一个值。*/
        /*将两个参数相加，acc 是累加器，保存着当前的累加结果。*/
        int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);
        System.out.println(count);
        BinaryOperator<Integer> accumulator = (acc, element) -> acc + element;
        int counts = accumulator.apply(
                accumulator.apply(
                        accumulator.apply(0, 1),
                        2),
                3);

    }

    @Test
    public void testComplexDemo(){
        Set<String> set = list.stream().filter(artist -> artist.getName().startsWith("a"))
                .map(artist -> artist.toString()).collect(Collectors.toSet());
        System.out.println(Arrays.toString(set.toArray()));
        /*分步求值的方式 样板代码太多 效率差及早产生新的集合 代码充斥垃圾变量保存中间结果*/
        /* 例如 int a=1;in b = a+1;int c=b+1;*/
        /*使用stream来清理掉样板代码很有帮助*/
        /*写出的函数没有副作用 没有副作用的函数不会改变程序或外界的状态 。给变量赋值也是一种副作用*/
        /*无论何时，将Lambda 表达式传给Stream 上的高阶函数，都应该尽量避免副作用。唯一的
例外是forEach 方法，它是一个终结方法。*/

    }
}

class Artist {
    private String name;
    private String age;
    private String isFrom;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIsFrom() {
        return isFrom;
    }

    public void setIsFrom(String isFrom) {
        this.isFrom = isFrom;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", isFrom='" + isFrom + '\'' +
                '}';
    }
}