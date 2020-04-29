package concurrent;

import org.junit.Test;

import java.util.Random;
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
        rlock.lock();
        System.out.println("123");
        rlock.unlock();
    }

    /*使用lock接口实现卖票*/
    private int num = 30;
    private Lock lock = new ReentrantLock();

    public  void sale(){
        lock.lock();
        if(num>30){
            System.out.println(Thread.currentThread().getName()+"sale"+num--);
        }
    }
    @Test
    public void testSale(){
        LockTest test = new LockTest();
        new Thread(()->{
            for (int i = 0; i <40 ; i++) {
                test.sale();
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i <40 ; i++) {
                test.sale();
            }
        },"BB").start();
    }

    @Test
    public void test(){
        /*线程间通信 synchronized使用wait notify
        * Lock使用await+signal*/
        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        /*先持有锁 然后等待 最后唤醒*/
        lock.lock();
        int random = new Random().nextInt(2);
        while (random!=2){
            try {
                notFull.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            random++;
        }
        notFull.signalAll();
    }
}
