package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author issuser
 * @date 2019-09-10 20:55
 */
public class TestTxRedis {

    public boolean transMethod() throws Exception {
        Jedis jedis = new Jedis("127.0.0.1", 3000);
        int balance = 0;
        int debt = 0;
        int amtToSub = 10;
        jedis.watch("balance");
        Thread.sleep(8000);
        balance = new Integer(jedis.get("balance"));
        if (balance < amtToSub) {
            jedis.unwatch();
            return false;
        } else {
            Transaction transaction = jedis.multi();
            transaction.decrBy("balance", amtToSub);
            transaction.decrBy("debt", amtToSub);
            transaction.exec();
            balance = new Integer(jedis.get("balance"));
            debt = new Integer(jedis.get("debt"));
            System.out.println(balance);
            System.out.println(debt);
            return true;
        }

    }
    /**
     * 伪代码如下： 通俗点讲，watch命令就是标记一个键，如果标记了一个键，
     *
     * 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况通常可以在程序中 重新再尝试一次。
     * 首先标记了键balance，然后检查余额是否足够，不足就取消标记，并不做扣减； 足够的话，就启动事务进行更新操作，
     * 如果在此期间键balance被其它人修改， 那在提交事务（执行exec）时就会报错， 程序中通常可以捕获这类错误再重新执行一次，直到成功。
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws Exception
    {
        TestTxRedis test = new TestTxRedis();
        boolean retValue = test.transMethod();
        System.out.println("main retValue-------: " + retValue);
    }
}
