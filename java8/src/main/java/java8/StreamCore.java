package java8;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * @author issuser
 * @date 2019-09-07 16:32
 * Stream所有的api
 *
 * 2.stream的特性
 * 我们首先列出stream的如下三点特性，在之后我们会对照着详细说明
 * 1.stream不存储数据
 * 2.stream不改变源数据
 * 3.stream的延迟执行特性
 *
 *.创建stream
 * 1）通过数组创建
 * 2）通过集合创建流
 * 3）创建空的流
 * 4）创建无限流
 * 5）创建规律的无限流
 *
 * 常用API
 * 2. filter(T -> boolean)保留 boolean 为 true 的元素
 * 3. distinct()去除重复元素
 * 4. sorted() / sorted((T, T) -> int)如果流中的元素的类实现了 Comparable 接口
 * 5. limit(long n)返回前 n 个元素
 * 6. skip(long n)去除前 n 个元素
 * 7. map(T -> R)将流中的每一个元素 T 映射为 R（类似类型转换）
 * 8. flatMap(T -> Stream将流中的每一个元素 T 映射为一个流，再把每一个流连接成为一个流
 * 9. anyMatch(T -> boolean)流中是否有一个元素匹配给定的 T -> boolean 条件
 * 10. allMatch(T -> boolean)流中是否所有元素都匹配给定的 T -> boolean 条件
 * 11. noneMatch(T -> boolean)流中是否没有元素匹配给定的 T -> boolean 条件
 * 12. findAny() 和 findFirst()findAny()：找到其中一个元素 findFirst()：找到第一个元素
 * 13. reduce((T, T) -> T) 和 reduce(T, (T, T) -> T)用于组合流中的元素，如求和，求积，求最大值等
 * 13. count()返回流中元素个数，结果为 long 类型
 * 14. collect()收集方法，我们很常用的是 collect(toList())，当然还有 collect(toSet()) 等，
 * 15. forEach()返回结果为 void，
 * 16. unordered()还有这个比较不起眼的方法，返回一个等效的无序流
 *
 *
 * Java 8 引入了一个新的容器类 Optional，可以代表一个值存在或不存在
 * Optional 类比较常用的几个方法有：
 * isPresent() ：值存在时返回 true，反之 flase
 * get() ：返回当前值，若值不存在会抛出异常
 * orElse(T) ：值存在时返回该值，否则返回 T 的值
 * Optional 类还有三个特化版本 OptionalInt，OptionalLong，OptionalDouble，
 *
 *
 * 五. collect 收集数据
 * coollect 方法作为终端操作，接受的是一个 Collector 接口参数，能对数据进行一些收集归总操作
 * 1. 收集
 * toList
 * toSet
 * toCollection
 * List newlist = list.stream.collect(toList());
 * 2. 汇总（1）counting用于计算总和：
 * （2）summingInt ，summingLong ，summingDoublesumming，没错，也是计算总和，不过这里需要一个函数参数
 * （3）averagingInt，averagingLong，averagingDouble看名字就知道，求平均数
 * （4）summarizingInt，summarizingLong，summarizingDouble
 * 这三个方法比较特殊，比如 summarizingInt 会返回 IntSummaryStatistics 类型
 * IntSummaryStatistics 包含了计算出来的平均值，总数，总和，最值，可以通过下面这些方法获得相应的数据
 * 3. 取最值maxBy，minBy 两个方法，需要一个 Comparator 接口作为参数
 * 4. joining 连接字符串其底层实现用的是 StringBuilder
 * 5. groupingBy 分组groupingBy 用于将数据分组，最终返回一个 Map 类型多级分组groupingBy 可以接受一个第二参数实现多级分组：
 * 6. partitioningBy 分区分区与分组的区别在于，分区是按照 true 和 false 来分的
 */
public class StreamCore {

    List<Person> list = new ArrayList<>();
    @Before
    public void init(){
        list.add(new Person("jack", 20));
        list.add(new Person("mike", 25));
        list.add(new Person("tom", 30));
    }

    @Test
    public void test01CreateStream(){
        //1.通过Arrays.stream
        int[] arr = new int[]{1,2,34,5};
        IntStream intStream = Arrays.stream(arr);
        //2.通过Stream.of
        Stream<Integer> stream1 = Stream.of(1,2,34,5,65);
        //注意生成的是int[]的流
        Stream<int[]> stream2 = Stream.of(arr,arr);
        stream2.forEach(System.out::println);
        List<String> strs = Arrays.asList("11212","dfd","2323","dfhgf");
        //创建普通流
        Stream<String> stream  = strs.stream();
        //创建并行流
        Stream<String> parallelStream = strs.parallelStream();
        //创建一个空的stream
        Stream<Integer> emptystream  = Stream.empty();
        //创建无限流，通过limit提取指定大小
        Stream.generate(()->new Person("name",10)).limit(20).forEach(System.out::println);
        /*产生规律的数据*/
        Stream.iterate(0,x->x+1).limit(10).forEach(System.out::println);
    }

    @Test
    public void testStreamApi(){
        list = list.stream()
                .filter(person -> person.getAge() == 20)
                .collect(toList());
        list = list.stream()
                .sorted((p1, p2) -> p1.getAge() - p2.getAge())
                .collect(toList());
        list = list.stream()
                .sorted(Comparator.comparingInt(Person::getAge))
                .collect(toList());
        list = list.stream()
                .limit(2)
                .collect(toList());
        list = list.stream()
                .skip(2)
                .collect(toList());
        List<String> newlist = list.stream().map(Person::getName).collect(toList());
        List<String> tmpList = new ArrayList<>();
        tmpList.add("aaa bbb ccc");
        tmpList = tmpList.stream().map(s -> s.split(" ")).flatMap(Arrays::stream).collect(toList());
        boolean bol = list.stream().anyMatch(person -> person.getAge() == 20);
        int sum1 = list.stream().map(Person::getAge).reduce(0, (a, b) -> a + b);
        int sum2 = list.stream().map(Person::getAge).reduce(0, Integer::sum);
        list.stream().forEach(System.out::println);
    }

    @Test
    public void testOptional(){
        /*Optional，它是一个容器类，能代表一个值存在或不存在，这个后面会讲到*/
        /*即不接受任何起始值，但因为没有初始值，需要考虑结果可能不存在的情况，因此返回的是 Optional 类型*/
        Optional<Integer> sum = list.stream().map(Person::getAge).reduce(Integer::sum);

    }


    @Test
    public void testNumberStream(){
        /*流转换为数值流*/
        IntStream intStream = list.stream().mapToInt(Person::getAge);
        LongStream longStream = list.stream().mapToLong(Person::getAge);
        DoubleStream doubleStream = list.stream().mapToDouble(Person::getAge);
        /*数值流转换为流*/
        Stream<Integer> stream = intStream.boxed();
        int sum = intStream.sum();
        System.out.println(IntStream.rangeClosed(1, 100));
        System.out.println(IntStream.range(1, 100));
    }

    @Test
    public void testCollect(){

        List newlist = list.stream().collect(toList());
        long l1 = list.stream().count();
        long l2 = list.stream().collect(counting());
        int sum1 = list.stream().collect(summingInt(Person::getAge));
        int sum2 = list.stream().mapToInt(Person::getAge).sum();
        Double average1 = list.stream().collect(averagingInt(Person::getAge));
        OptionalDouble average2 = list.stream().mapToInt(Person::getAge).average();
        IntSummaryStatistics l = list.stream().collect(summarizingInt(Person::getAge));
        Optional<Person> optional = list.stream().collect(maxBy(comparing(Person::getAge)));
        Optional<Person> optional2 = list.stream().max(comparing(Person::getAge));
        String s = list.stream().map(Person::getName).collect(joining());
        Map<Integer, List<Person>> map = list.stream().collect(groupingBy(Person::getAge));
        Map<Integer, Integer> map2 = list.stream().collect(groupingBy(Person::getAge, summingInt(Person::getAge)));
        /*根据年龄是否小于等于20来分区*/
        Map<Boolean, List<Person>> map3 = list.stream()
                .collect(partitioningBy(p -> p.getAge() <= 20));
    }

    public static class Person{
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
