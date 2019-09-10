import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author issuser
 * @date 2019-09-10 20:47
 */
public class TestMasterSlave {

    @Test
    public void testMasterSlave(){
        Jedis jedisMaster = new Jedis("127.0.0.1",6379);
        Jedis jedisSlave = new Jedis("127.0.0.1",6380);
        jedisSlave.slaveof("127.0.0.1",6379);
        jedisMaster.set("k3","v3");
        String result = jedisSlave.get("k3");
        System.out.println(result);
    }

}
