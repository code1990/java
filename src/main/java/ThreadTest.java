import sun.nio.ch.ThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author issuser
 * @date 2019-08-22 22:33
 */
public class ThreadTest implements Runnable {
    /*
     * 多个事物同时执行称为并发
     * 线程是等待完成的事情
     * 进程是应用程序
     *
     * 实现线程的方式 extends Thread implements Runnable
     * start 启动 run 启动后执行任务
     * 1.线程休眠 Thread,sleep(2000)
     * 2.线程加入 join
     *3.线程中断 stop interrupt
     * 4.线程礼让 yield
     * 5.设置线程优先级 setPriority 优先级低的 获取机会低
     * 6.线程同步 synchronized 有效防止资源冲突
     * */
    int num = 10;

    public void run() {
        while (true) {
            synchronized ("") {
                if (num > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("tickets" + --num);
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadTest t = new ThreadTest();
//        Thread t1 = new Thread(t);
//        Thread t2 = new Thread(t);
//        Thread t3 = new Thread(t);
//        Thread t4 = new Thread(t);
//        t1.start();
//        t2.start();
//        t3.start();
//        t4.start();
        ExecutorService service = Executors.newFixedThreadPool(5);
        service.submit(t);
        service.shutdown();


    }
}
