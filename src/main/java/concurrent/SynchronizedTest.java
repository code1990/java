package concurrent;

import org.junit.Test;

/**
 * @author issuser
 * @date 2019-08-29 20:45
 * <p>
 * 1竞态条件 当多线程交叉时候就会产生竞态条件
 * 2数据竞争 2条以上的线程并发访问同一块内存区域
 * 3缓存变量 每一个线程都有自己的变量拷贝 写入变量 写入的是自己的变量
 * 4.同步是JVM的一块临界区 线程必须以一次一条线程的方式访问一段代码块
 * <p>
 * 避免同步方法或者同步代码快中调用同步方法同步代码块 避免死锁
 * 死锁 线程1等待线程2互斥持有的资源 二者都无法执行
 * 活锁 线程一直重试失败
 * 饿锁 线程一直被无限延迟
 */
public class SynchronizedTest {
    /*1.同步方法*/
    private static int counter;

    public static synchronized int getId() {
        return counter++;
    }

    /*2同步块*/
    Runnable r = () -> {
        synchronized (new Object()) {
            System.out.println("同步代码块");
        }
    };

    /*3.volatile可见性强制线程从主内存最后获取变量 而不是访问缓存*/
    /*被final修饰的属性不需要担心同步问题*/
    private volatile boolean stopped;

    @Test
    public void testVolatile() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (!stopped) {
                    System.out.println("running");
                }
            }
        };

    }
}
