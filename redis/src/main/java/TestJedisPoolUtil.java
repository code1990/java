import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author issuser
 * @date 2019-09-10 20:17
 */
public class TestJedisPoolUtil {
    private static volatile JedisPool jedisPool = null;
    private TestJedisPoolUtil(){}

    public static JedisPool getInstance(){
        if(jedisPool==null){
            synchronized (TestJedisPoolUtil.class){
                if (jedisPool==null){
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxActive(100);
                    config.setMaxIdle(32);
                    config.setMaxWait(100*1000);
                    config.setTestOnBorrow(true);
                    jedisPool = new JedisPool(config,"127.0.0.1",6379);
                }
            }
        }
        return jedisPool;
    }

    public static void close(JedisPool jedisPool, Jedis jedis){
        if(jedis!=null){
            jedisPool.returnBrokenResource(jedis);
        }
    }

    @Test
    public void testJedisPool(){
        JedisPool jedisPool = TestJedisPoolUtil.getInstance();
        Jedis jedis = null;
        jedis = jedisPool.getResource();
        jedis.set("k1","v1");
        TestJedisPoolUtil.close(jedisPool,jedis);
    }
}
