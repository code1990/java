package util;

import java.util.*;

public class MapUtil {
    public static void  printMap(Map map){
        Iterator entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
        }
    }
    public static void  printMap(Map map1,Map map2){
        Iterator entries = map1.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
//            System.out.println(entry.getKey() + "\t" + entry.getValue()+"\t"+map2.get(entry.getKey()));
            System.out.println("'"+entry.getKey()+"',");
//            System.out.println("insert into t_com_hy_gn(com_code,com_name,hy_name,gn_name)values('"+entry.getKey() + "','" + entry.getValue()+"','"+map2.get(entry.getKey()));
        }
    }
    //降序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDesc(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<K, V> entry : result.entrySet()){
//            String mapKey = entry.getKey();
//            String mapValue = entry.getValue();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        return result;
    }

    //升序排序
    public static <K, V extends Comparable<? super V>> Map<K, V> sortAsc(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
