import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author issuser
 * @date 2019-08-30 21:07
 *  高级控制通用同步方法的类
 *  countDownLatch 门栓 多个线程在门口等待 直到们打开才运行
 *  cyclicbarrier同步屏障 允许线程彼此等待 直到到达公共的屏障点
 *  exchanger交换器 线程之间彼此交换对象的同步点
 *  semaphore信号量 维护一组许可证 约束访问限制资源的线程数
 *  parser同步器 当最后一个线程抵达后 线程得以继续执行
 */
public class ParserTest {

    @Test
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
                    /*countDown开始运行*/
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
    }

    @Test
    public void testCyclicBarrier(){
        final CyclicBarrier barrier = new CyclicBarrier(1, new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
            }
        });
    }

    @Test
    public void testExechanger() throws Exception{
        final Exchanger<String> exechanger = new Exchanger<>();
        String str = exechanger.exchange("x");
    }

    @Test
    public void testSemaphore(){
        /*设置公平策略为true 可以保证先进先出*/
        final Semaphore semaphore = new Semaphore(10,true);
    }

    @Test
    public void testPhaser(){
        final Phaser phaser = new Phaser(1);
        phaser.register();
    }

}
