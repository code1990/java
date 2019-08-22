import org.junit.Test;

import java.util.*;

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
    public void testCollection(){
        Collection<String> collection = new ArrayList<String>();
        for(String str:new String[]{"Hello","World"}){
            collection.add(str);
        }
        collection.remove(1);
        if(!collection.isEmpty()){
            System.out.println(collection.size());
        }
        Iterator<String> iterator = collection.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    /*
    * 2.List的实现类如下 主要方法 get/set
    * ArrayList可变数组 基于索引适合随机访问 插入删除较慢
    * LinkedList链表 适合快速插入和删除 随机访问较慢
    * */
    @Test
    public void testList(){
        /*最为常用*/
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        int random = (int)(Math.random()*list.size());
        list.remove(random);
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
            if("a".equals(list.get(i))){
                list.set(i,"aa");
            }
        }
    }

    /*
    * 3.Set接口实现类 不可以重复主要用来过滤 first() last()
    * HashSet 根据hash表来实现 随机存放
    * TreeSet 实现了Set接口和SortedSet接口 遍历时候递增排序
    * */
    @Test
    public void testSet(){
        TreeSet<String> treeSet = new TreeSet<String>();
        for (int i = 0; i <5 ; i++) {
            treeSet.add(i+"");
        }
        Iterator<String> iterator = treeSet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    /*
    * 3.Map 键值对
    * HashMap 添加删除的效率更好 基于hash表实现 随机存放 允许key,vlaue为null
    * TreeMap 实现了Map和SortedMap接口 保证顺序
    * */
    @Test
    public void testMap(){
        Map<Integer,String> map= new HashMap<Integer,String>();
        map.put(1,"1");
        map.put(2,"2");
        map.put(3,"3");
        /*遍历key*/
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> keyIt = keySet.iterator();
        while (keyIt.hasNext()){
            System.out.println(keyIt.next());
        }
        /*遍历value*/
        for(String value : map.values()){
            System.out.println(value);
        }
        /*同时遍历key value*/
        for(Map.Entry<Integer, String> entry : map.entrySet()){
            Integer mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey+":"+mapValue);
        }
    }

    @Test
    public void testInfo(){
        int[] array = new int[200];
        for (int i = 0; i <150 ; i++) {
            int random = new Random().nextInt(100);
            array[i]=random;
        }
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        Set<Integer> set = new HashSet<Integer>();
        for (int x:array) {
            set.add(x);
        }
        System.out.println(Arrays.toString(set.toArray()));
    }

}
