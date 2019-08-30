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
        Callable<BigDecimal> callable;
        callable = new Callable<BigDecimal>() {
            @Override
            public BigDecimal call() throws Exception {
                return new BigDecimal(1.0);
            }
        };
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
}
