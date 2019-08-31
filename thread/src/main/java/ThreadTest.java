import org.junit.Test;

import java.util.concurrent.Callable;

/**
 * @author issuser
 * @date 2019-08-29 19:52
 *
 * 线程是操作系统动态执行的基本单元
 * 进程是基本的分配单元
 * 这些封装了执行序列的线程对象成为runnable
 *
 * java虚拟机为每一个线程分配独立的JVM栈控件避免干扰
 *
 * runnable接口为线程对象提供执行代码
 * 并行是一种发生在至少有2个线程同时执行的场景
 * 并发是一种至少存在2个线程前进的场景
 *
 */
public class ThreadTest {

    /*1.创建线程对象*/
    @Test
    public void testCreateThread() throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("使用runnable匿名接口类的方式创建线程");
            }
        };

        Runnable r2 = ()-> System.out.println("使用java8 lambada表达式来创建");

        MyThread myThread = new MyThread();
        myThread.start();
        Thread.sleep(1000);
        new Thread(r).start();
        Thread.sleep(1000);
        new Thread(r2).start();
    }

    /*2.获取线程的状态*/
    @Test
    public void testGetThreadInfo()throws InterruptedException{
        /*1.设置获取线程名称*/
        Runnable r = ()-> System.out.println("使用java8 lambada表达式来创建");
        Thread t1 = new Thread(r,"thread t1");
        System.out.println(t1.getName());
        Thread t2 = new Thread(r);
        t2.setName("thread t2");
        System.out.println(t2.getName());
        /*2.获取一个线程的存活状态*/
        System.out.println(t1.isAlive());
        /*3.获取一个线程执行状态*/
        System.out.println(t1.getState());
        System.out.println(Thread.State.valueOf("NEW"));
        /*4.设置线程优先级*/
        System.out.println(t1.getPriority());
        t1.setPriority(10);
        /*5.设置和获取守护线程的状态*/
        System.out.println(t1.isDaemon());
        t1.setDaemon(true);
        /*6.启动线程*/
        t1.start();
        /*7.线程是否中断*/
        System.out.println(t1.isInterrupted());
        t1.interrupt();
        System.out.println(Thread.interrupted());
        /*8.线程等待*/
        /*t2.join()*/
        t2.join(1000);
        t2.join(1000,10);
        /*9.线程休眠*/
        Thread.sleep(1000);
        Thread.sleep(1000,10);
    }
}
class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println("使用继承的方式来创建线程");
    }
}
/*使用callable可以有返回值 可以抛出异常*/
class CallThread implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        return 200;
    }
}