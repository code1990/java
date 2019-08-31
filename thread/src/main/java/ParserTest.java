import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author issuser
 * @date 2019-08-30 21:07
 * 高级控制通用同步方法的类
 * countDownLatch 门栓 多个线程在门口等待 直到们打开才运行
 * cyclicbarrier循环屏障 让一组线程彼此等待 直到到达公共的屏障点  才会继续执行
 * exchanger交换器 线程之间彼此交换对象的同步点
 * semaphore信号量 维护一组许可证 约束访问限制资源的线程数 主要用于多个线程共享资源的互斥使用,另一个用于并发线程数量的控制
 * parser同步器 当最后一个线程抵达后 线程得以继续执行
 */
public class ParserTest {

    /*@Test
    public void testCountDownLatch() throws Exception{
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(3);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread()+"run");
                try {
                    start.await();
                    System.out.println(Thread.currentThread()+"run");
                    Thread.sleep(1000);
                    *//*countDown开始运行*//*
                    end.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(r);
            System.out.println("main thread run");
            Thread.sleep(1000);
            start.countDown();
            end.await();
            executorService.shutdownNow();
        }
    }*/

    @Test
    public void testCountDownLatch2() {
        /*6个人一次离开教室才关门 调用countDown方法计算器减一 当计算器变为0 调用await方法 唤醒其他线程继续执行*/
        CountDownLatch latch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "离开教室");
                latch.countDown();
            }, i + "").start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "");
    }

    @Test
    public void testCyclicBarrier() {
        CyclicBarrier barrier = new CyclicBarrier(7, () -> {
            System.out.println("收集7个龙珠可以召唤神了");
        });
        for (int i = 1; i <= 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "龙珠收集完");
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "" + i).start();
        }
    }

    @Test
    public void testExechanger() throws Exception {
        final Exchanger<String> exechanger = new Exchanger<>();
        String str = exechanger.exchange("x");
    }

    @Test
    public void testSemaphore() {
        /*设置公平策略为true 可以保证先进先出*/
        /*acquire 线程调用改该方法 获取信号量 信号量减一 */
        /*release释放信号量 信号量+1 线程唤醒*/
        /*模拟3个停车位停放6个车子*/
        Semaphore semaphore = new Semaphore(3);/*3个车位*/
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到车位了");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName() + "离开");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, i + "").start();
        }
    }

    @Test
    public void testPhaser() {
        final Phaser phaser = new Phaser(1);
        phaser.register();
    }

}
