package helloworld;

import org.junit.Test;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

/**
 * @author issuser
 * @date 2019-08-22 22:33
 */
/*使用implements Runnable 可以避免单继承
 *  使用callable接口实现多线程 可以返回线程操作完的结果
 * */
public class ThreadTest implements Runnable {
    /*
     * 多个事物同时执行称为并发
     * 线程是在进程上进一步划分的执行单位
     * 进程是程序的一次动态执行过程
     *
     * 实现线程的方式 extends Thread implements Runnable
     * start 启动 run 启动后执行任务
     * 1.线程休眠 Thread,sleep(2000)
     * 2.线程加入 join
     *3.线程中断 stop interrupt
     * 4.线程礼让 yield
     * 5.设置线程优先级 setPriority 优先级低的 获取机会低
     * 6.线程同步 synchronized 有效防止资源冲突
     *
     * 死锁 2个线程都在等待彼此先完成造成程序的停歇状态
     *
     * 线程生命周期
     * suspend  暂时挂起线程
     * resume   恢复挂起线程
     * stop 停止线程
     * */
   private int ticket = 10;

    @Override
    public void run() {

        while (true) {
            /*同步代码块*/
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"卖票"+this.ticket--);
                } else {
                    break;
                }
            }
        }
        /*this.sale()*/
    }
    /*同步方法*/
    public synchronized void sale(){
        if (ticket > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"卖票"+this.ticket--);
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadTest t = new ThreadTest();
        Thread t1 = new Thread(t,"namet1");
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);
        t3.setPriority(Thread.MAX_PRIORITY);
        Thread t4 = new Thread(t);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
//        ExecutorService service = Executors.newFixedThreadPool(5);
//        service.submit(t);
//        service.shutdown();

        /*使用lambada表达式的方式创建*/
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                System.out.println("线程对象-->" + i);
            }
        }).start();


    }

    @Test
    public void testFutureTask() throws Exception {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        /*Futuretask是runnable的子类 所以需要thread类来构造对象*/
        FutureTask<String> task1 = new FutureTask<String>(myThread1);
        FutureTask<String> task2 = new FutureTask<String>(myThread2);
        new Thread(task1).start();
        new Thread(task2).start();
        System.out.println("a线程结果" + task1.get());
        System.out.println("b线程结果" + task2.get());
    }
}

class MyThread implements Callable<String> {
    private int ticket = 10;

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 100; i++) {
            if (this.ticket > 0) {
                System.out.println("售票,ticket=" + this.ticket--);
            }
        }
        return "卖完了";
    }
}