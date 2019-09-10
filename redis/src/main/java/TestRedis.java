import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author issuser
 * @date 2019-09-10 19:25
 */
public class TestRedis {

    @Test
    public void testConnectRedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println(jedis.ping());//PONG 表示连接正常
    }

}
