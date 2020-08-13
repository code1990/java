package webmagic.fund;

import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 911
 * @date 2020-08-09 10:55
 */
public class MapTest {

    @Test
    public void getInfo() {
        /**
         * 最常见也是大多数情况下用的最多的，一般在键值对都需要使用
         */
        Map<String, String> map = new HashMap<String, String>();
        map.put("熊大", "棕色");
        map.put("熊二", "黄色");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
        }

        //key
        for (String key : map.keySet()) {
            System.out.println(key);
        }
        //value
        for (String value : map.values()) {
            System.out.println(value);
        }

        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, String> entry = entries.next();
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+":"+value);
        }



        for(String key : map.keySet()){
            String value = map.get(key);
            System.out.println(key+":"+value);
        }



    }
}
