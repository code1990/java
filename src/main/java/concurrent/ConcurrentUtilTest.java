package concurrent;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author issuser
 * @date 2019-08-30 22:22
 * <p>
 * BlockingQueue 是Queue的子接口
 * ArrayBlockingQueue DelayQueue LinkedBlockingQueue PriorityBlockingQueue SynchronousQueue
 * LinkedBlockingQueue LinkedTransferQueue
 * <p>
 * ConcurrentMap concurrentHashMap concurrentNavigableMap concurrentSkipListMap
 * <p>
 * atomic 支持单个变量上镜像无锁及线程安全的操作
 * <p>
 * 并行是多个线程运行时的行为 并发是宽泛的并行
 * <p>
 * Fork/Join框架 有特定的线程吃构成 可以执行任务
 */
public class ConcurrentUtilTest {

    /*Collections.synchronizedList 传入arrayList可以成为线程安全的*/

    @Test
    public void testBlockingQueue() throws Exception {
        final BlockingQueue<Character> bq = new ArrayBlockingQueue<Character>(26);
        final ExecutorService service = Executors.newFixedThreadPool(2);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                for (char c = 'A'; c <= 'Z'; c++) {
                    try {
                        bq.put(c);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        service.execute(r);
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                try {
                    bq.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        service.execute(r2);
    }

    @Test
    public void testConcurrentHashMap() {
        ConcurrentHashMap map = new ConcurrentHashMap();
    }


    @Test
    public void testAtomic() {
        AtomicInteger integer = new AtomicInteger(1);
    }

    @Test
    public void testForkJoin() {
        ForkJoinPool pool = new ForkJoinPool();
//        pool.invoke(new FutureTask<String>());
    }

    @Test
    public void testCompletionService() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletionService<BigDecimal> cs = new ExecutorCompletionService<>(es);
//        es.submit();
        Future<BigDecimal> result = cs.take();
    }
}
