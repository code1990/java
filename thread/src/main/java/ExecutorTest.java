import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * @author issuser
 * @date 2019-08-30 13:39
 * concurrent 并发编程工具类
 * atomic 无锁线程安全操作的工具类
 * locks 获取锁对象的工具类
 * sychronizer 同步器
 * <p>
 * Callable接口可以返回值也可以返回异常信息
 * Future接口代表一种异步计算的结果
 * Executor ExecutorService ScheduledExecutorService
 */
public class ExecutorTest {

    @Test
    public void testExecutor() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        /*使用callable可以有返回值 可以抛出异常*/

        Callable<BigDecimal> callable;
        callable = new Callable<BigDecimal>() {
            @Override
            public BigDecimal call() throws Exception {
                return new BigDecimal(1.0);
            }
        };
        /*在主线程中需要执行比较耗时的操作时，但又不想阻塞主线程时，可以把这些作业交给Future 对象在后台完成，
当主线程将来需要时，就可以通过Future 对象获得后台作业的计算结果或者执行状态。*/
        Future<BigDecimal> taskFuture = executor.submit(callable);
        while (!taskFuture.isDone()) {
            try {
                System.out.println("waiting" + taskFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

    }

    @Test
    public void testFutureTask(){
        /*FutureTask用于比较耗时的任务 又不想阻塞主线程*/
        FutureTask<Integer> ft = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 200;
            }
        });
        int result = 0;
        try {
            result = ft.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }


}
