import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author issuser
 * @date 2019-08-29 22:38
 */
public class ThreadGroupAndLocalTest {

    /*线程组 定义一组线程 是非线程安全的*/
    @Test
    public void testThreadGroup(){
        ThreadGroup tg = new ThreadGroup("name");
        System.out.println(tg.activeCount());
        System.out.println(tg.activeGroupCount());
    }

    /*threadlocal线程局部变量 每一个线程访问单独的存储槽*/
    private static volatile ThreadLocal<String> userId = new ThreadLocal<>();
    @Test
    public void tesstThreadLocal(){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                System.out.println(name);
                if("A".equals(name)){
                    userId.set("A");
                }else {
                    userId.set("B");
                }
                System.out.println(userId.get());
            }
        };

        Thread threadA = new Thread(r);
        threadA.setName("A");
        Thread threadB = new Thread(r);
        threadA.setName("B");
        threadA.start();
        threadB.start();
    }

    private static final InheritableThreadLocal<Integer> intVal = new InheritableThreadLocal<>();
    @Test
    public void testInheritableThreadLocal(){
        /*InheritableThreadLocal是threadLocal的子线程 可以把父线程传递给子线程*/
        Runnable r =()->{
            intVal.set(new Integer(10));
            Runnable rc=()->{
                Thread thd = Thread.currentThread();
                String name = thd.getName();
                System.out.printf("%s%d%n",name,intVal.get());
            };
            Thread threadChild = new Thread(rc);
            threadChild.setName("child");
            threadChild.start();
        };
        new Thread(r).start();
    }

    /*Timer TimerTask 任务执行线程*/
    @Test
    public void testTimerTask(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,0,1000);
    }

    /*Timer使用与大规模并发定时任务*/
    /*TimerTask是定时任务 */
}
