package java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author issuser
 * @date 2019-09-07 23:15
 *
 *
 * 并发是两个任务共享时间段，并行则是两个任务在同一时间发生，比如运行在多核CPU
 * 上。如果一个程序要运行两个任务，并且只有一个CPU 给它们分配了不同的时间片，那
 * 么这就是并发，而不是并行
 *
 * 并行化是指为缩短任务执行时间，将一个任务分解成几部分，然后并行执行。这和顺序执
 * 行的任务量是一样的，区别就像用更多的马来拉车，花费的时间自然减少了。实际上，和
 * 顺序执行相比，并行化执行任务时，CPU 承载的工作量更大。
 *
 * 数据并行化。数据并行化是指将数据分成块，为每块
 * 数据分配单独的处理单元
 *
 * 并行化操作流只需改变一个方法调用。如果已经有一个Stream 对象， 调用它的
 * parallel 方法就能让其拥有并行操作的能力。如果想从一个集合类创建一个流，调用
 * parallelStream 就能立即获得一个拥有并行能力的流。
 *
 * 并行化流操作的用武之地是使用简单操作处理大量数据，比如模拟系统。
 *
 * 为了让其在并行化时能工作正常，初值必须为组合函数的恒等值
 * reduce 操作的另一个限制是组合操作必须符合结合律
 * 要避免的是持有锁
 *
 * 如果同时调用了parallel和sequential 方法，最后调用的那个方法起效。
 *
 * 数据并行化是把􀅖 工作拆分，同时在多核 CPU上执行的方式。
 * 􀅖 如果使用流编写代码，可通过调用 parallel 或者 parallelStream 方法实现数据并行化
 * 操作。
 */
public class ch06ParallelStream {

    /*并行求和*/
    private int addIntegers(List<Integer> list) {
        return list.parallelStream().mapToInt(i -> i).sum();
    }

    /*使用并行化数组操作初始化数组
    *
    * parallelPrefix 任意给定一个函数，计算数组的和
parallelSetAll 使用Lambda 表达式更新数组元素
parallelSort 并行化对数组元素排序
    *
    * */
    public static double[] paralleInitialize(int size) {
        double[] array = new double[size];
        Arrays.parallelSetAll(array, i -> i);
        return array;
    }
}

/*蒙特卡洛模拟法*/
class ManualDiceRolls {
    private static final int N = 10000000;
    private final double fraction;
    private final Map<Integer, Double> results;
    private final int numberOfThreads;
    private final ExecutorService executor;
    private final int workPerThread;

    public static void main(String[] args) {
        ManualDiceRolls roles = new ManualDiceRolls();
        roles.simulateDiceRoles();
    }

    public ManualDiceRolls() {
        fraction = 1 / N;
        results = new ConcurrentHashMap<>();
        numberOfThreads = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(numberOfThreads);
        workPerThread = N / numberOfThreads;
    }

    public void simulateDiceRoles() {
        List<Future<?>> futures = submitJobs();
        awaitCompletion(futures);
        printResults();
    }

    private void printResults() {
        results.entrySet().forEach(System.out::println);
    }

    private List<Future<?>> submitJobs() {
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executor.submit(makeJob()));
        }
        return futures;
    }

    private Runnable makeJob() {
        return () -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 0; i < workPerThread; i++) {
                int entry = twoDiceThrows(random);
                accumulateResult(entry);
            }
        };
    }

    private void accumulateResult(int entry) {
        results.compute(entry, (key, previous) -> previous == null ? fraction : previous + fraction);
    }

    private int twoDiceThrows(ThreadLocalRandom random) {
        int firstThrow = random.nextInt(1, 7);
        int secondThrow = random.nextInt(1, 7);
        return firstThrow + secondThrow;
    }

    private void awaitCompletion(List<Future<?>> futures) {
        futures.forEach((future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }));
        executor.shutdown();
    }

}









