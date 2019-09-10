import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author issuser
 * @date 2019-09-10 19:36
 */
public class TestRedisDataTypeCURD {
    private Jedis jedis;
    @Before
    public void connectRedis(){
        jedis = new Jedis("127.0.0.1",6379);
    }
    @Test
    public void testRedisString(){
        /*set*/
        jedis.set("username","green");
        /*get*/
        System.out.println(jedis.get("username"));
        /*append*/
        jedis.append("username"," hello");
        /*get*/
        System.out.println(jedis.get("username"));
        /*del*/
        jedis.del("username");
        System.out.println(jedis.get("username"));
        /*设置多个值*/
        jedis.mset("username","jim","age","18","sex","male");
        System.out.println(jedis.get("username")+"\t"+jedis.get("age")+"\t"+jedis.get("sex"));
    }
    @Test
    public void testRedisList(){
        jedis.del("context");
        System.out.println(jedis.lrange("context",0,-1));
        System.out.println(jedis.lpush("context","hello"));
        System.out.println(jedis.lpush("context","my name is jim"));
        //lrange 获取数据 接收3个参数  1、key  2、从什么开始 3、到那里结束 -1 表示最后一个
        System.out.println(jedis.lrange("context",0,-1));
    }
    @Test
    public void testRedisSet(){
        jedis.sadd("user","jim");
        jedis.sadd("user","tom");
        System.out.println(jedis.smembers("user"));
        System.out.println(jedis.sismember("user","lily"));
        System.out.println(jedis.scard("user"));
        System.out.println(jedis.srem("user","lily"));
        System.out.println(jedis.smembers("user"));
    }
    @Test
    public void testRedisZSet(){
        jedis.zadd("user",12,"jim");
        jedis.zadd("user",22,"tom");
        jedis.zadd("user",32,"lily");

        System.out.println(jedis.zrange("user",0,-1));
        System.out.println(jedis.zrem("user","jim"));
        System.out.println(jedis.zrange("user",0,-1));
        System.out.println(jedis.zrank("user","tom"));
    }
    @Test
    public void testRedisHash(){
        Map<String,String> map = new HashMap<>();
        map.put("username","jim");
        map.put("age","18");
        map.put("sex","male");
        jedis.hmset("user",map);
        List<String> result = jedis.hmget("user","username","age","sex");
        System.out.println(Arrays.toString(result.toArray()));

        /*del*/
        jedis.del("user","age");
        System.out.println(jedis.mget("user","age"));
        System.out.println(jedis.hlen("user"));
        System.out.println(jedis.exists("user"));
        System.out.println(jedis.hkeys("user"));
        System.out.println(jedis.hvals("user"));

    }

}
