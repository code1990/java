package helloworld;

import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author issuser
 * @date 2019-08-19 23:04
 */
public class CollectionTest {

    /*
    List接口和Set接口是Collection的子接口
    List接口的实现类有ArrayList和LinkedList
    Set接口的实现类有HashSet和TreeSet
    Map键值对的主要实现有HashMap TreeMap
    * */

    /*1.Collection接口主要包括如下方法
     *  add添加 remove删除 iterator遍历 size查看个数 isEmpty是否为空
     * */

    @Test
    public void testCollection() {
        Collection<String> collection = new ArrayList<String>();
        for (String str : new String[]{"Hello", "World"}) {
            collection.add(str);
        }
        collection.remove(1);
        if (!collection.isEmpty()) {
            System.out.println(collection.size());
        }
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /*
     * 2.List的实现类如下 主要方法 get/set
     * ArrayList可变数组 基于索引适合随机访问 插入删除较慢
     * LinkedList链表 适合快速插入和删除 随机访问较慢
     * */
    @Test
    public void testList() {
        /*最为常用*/
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        int random = (int) (Math.random() * list.size());
        list.remove(random);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            if ("a".equals(list.get(i))) {
                list.set(i, "aa");
            }
        }

    }

    /*
     * 3.Set接口实现类 不可以重复主要用来过滤 first() last()
     * HashSet 根据hash表来实现 随机存放
     * TreeSet 实现了Set接口和SortedSet接口 遍历时候递增排序
     * */
    @Test
    public void testSet() {
        TreeSet<String> treeSet = new TreeSet<String>();
        for (int i = 0; i < 5; i++) {
            treeSet.add(i + "");
        }
        Iterator<String> iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /*
     * 3.Map 键值对
     * HashMap 添加删除的效率更好 基于hash表实现 随机存放 允许key,vlaue为null
     * TreeMap 实现了Map和SortedMap接口 保证顺序
     * HashTable不允许key,value为null
     * */
    @Test
    public void testMap() {
        Map<Integer, String> table = new Hashtable<Integer, String>();
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        /*遍历key*/
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> keyIt = keySet.iterator();
        while (keyIt.hasNext()) {
            System.out.println(keyIt.next());
        }
        /*遍历value*/
        for (String value : map.values()) {
            System.out.println(value);
        }
        /*同时遍历key value*/
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
        }
    }

    @Test
    public void testVector() {
        /*vector向量类*/
        /*利用子类为父类实例化 结构功能不变*/
        /*ArrayList非线程安全 采取异步处理 Vector线程安全 同步处理*/
        List<String> list = new Vector<String>();
        list.add("Hello");
        System.out.println(list.size());
        System.out.println(Arrays.toString(list.toArray()));
    }

    @Test
    public void testListIterator() {
        /*iterator只能从前往后迭代 listiterator可以从后往前迭代*/
        List<String> list = new ArrayList<String>();
        list.add("Hello");
        list.add("Java");
        list.add("World");
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
    }

    @Test
    public void testEnumeration() {
        /*Vector利用枚举类实现数据的输出*/
        Vector<String> vector = new Vector<String>();
        vector.add("Hello");
        vector.add("Java");
        vector.add("World");
        Enumeration<String> enumeration = vector.elements();
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement());
        }
    }

    @Test
    public void testStack() {
        Stack<String> stack = new Stack<String>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.pop();
        stack.pop();
        stack.pop();
    }

    @Test
    public void testProperties() {
        /*properties是HashTable的子类 主要用来做配置文件*/
        Properties properties = new Properties();
        properties.setProperty("WUH", "武汉");
        properties.setProperty("CAN", "广州");
        System.out.println(properties.getProperty("CGO", "河南"));
    }

    @Test
    public void testCollections() {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "String", "Integer");
        Collections.reverse(list);
        System.out.println(Arrays.toString(list.toArray()));

    }

    @Test
    public void testStream(){
        /*利用流简化集合的操作*/
        List<String> list = new ArrayList<>();
        list.add("String");
        list.add("Byte");
        list.add("Short");
        list.add("Integer");
        list.add("Integer");
        Stream<String> stream = list.stream();
        System.out.println(stream.count());
        List<String> newList = stream.distinct().collect(Collectors.toList());
        /*指定过滤*/
        List<String> filterList = stream.distinct().filter((x)->x.contains("Byte")).collect(Collectors.toList());
       /*实现数据的分页操作*/
        List<String> pagerList = stream.distinct().map((x)->x.toLowerCase()).filter((x)->x.contains("t")).skip(1).limit(1).collect(Collectors.toList());
        /*数据的匹配查询*/
        if(stream.anyMatch((x)->x.contains("String"))){
            System.out.println("存在String");
        }
        /*利用断言型接口引用 实现多条件的过滤*/
        Predicate<String> p1=(x)->x.contains("er");
        Predicate<String> p2=(x)->x.contains("te");
        /*2者都满足*/
        if(stream.anyMatch(p1.or(p2))){
            System.out.println("存在String");
        }
        /*利用foreach输出 java8新特性*/
        newList.forEach(System.out::println);
        filterList.forEach(System.out::println);
        pagerList.forEach(System.out::println);
    }

    @Test
    public void testMapReduce(){
        List<StreamEntity> list = new ArrayList<>();
        list.add(new StreamEntity(12.1,99));
        list.add(new StreamEntity(11.1,999));
        list.add(new StreamEntity(121.11,9911));
        list.stream().map((x)->x.getAmount()*x.getPrice())
                .forEach(System.out::println);
        /*统计总价*/
        double allPrice =list.stream().map((x)->x.getAmount()*x.getPrice())
                .reduce((sum,m)->sum+m).get();
        System.out.println(allPrice);

        /*DoubleStream IntStream LongStream */
        DoubleSummaryStatistics dss = list.stream()
                .mapToDouble((sc)->sc.getPrice()*sc.getAmount())
                .summaryStatistics();
        System.out.println(dss.getCount());
        System.out.println(dss.getMax());
        System.out.println(dss.getAverage());
        System.out.println(dss.getMin());
        System.out.println(dss.getSum());
    }

    @Test
    public void testInfo() {
        int[] array = new int[200];
        for (int i = 0; i < 150; i++) {
            int random = new Random().nextInt(100);
            array[i] = random;
        }
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        Set<Integer> set = new HashSet<Integer>();
        for (int x : array) {
            set.add(x);
        }
        System.out.println(Arrays.toString(set.toArray()));
    }

}
