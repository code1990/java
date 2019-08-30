import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;

/**
 * @author issuser
 * @date 2019-08-30 21:48
 *
 * Lock接口添加了比监听器更为弹性的锁操作
 *
 * 重入锁 描述了一个可重入的互斥锁
 * 条件 Condition Lock实现同步功能 Condition取代wait notification方法
 * 读写锁 读多写少的场景 ReadWriteLock
 * 重入读写锁 ReentrantLock
 * StampedLock 具有三种控制读写访问模式的锁
 *
 *
 */
public class LockTest {

    @Test
    public void testReentrantLock(){
        Executor executor = Executors.newFixedThreadPool(2);
        final ReentrantLock lock = new ReentrantLock();
        lock.lock();
    }

    @Test
    public void testCondition() throws InterruptedException {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        condition.signal();
        condition.wait();
    }

    @Test
    public void testReadWriteLock(){
        ReadWriteLock lock = new ReentrantReadWriteLock(true);
        final Lock rlock = lock.readLock();
        final Lock wlock = lock.writeLock();
    }
}
