import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author issuser
 * @date 2019-08-24 15:35
 */
public class Java8Test {
    /*1.可变参数*/
    public static int add(int... data) {
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += i;
        }
        return sum;
    }

    /*2.foreach*/
    @Test
    public void testForeach() {
        for (char c : new String("hello").toCharArray()) {
            System.out.println(c);
        }
    }

    /*3.静态导入 类的所有方法都为静态方法 必须先引入 后使用*/
    @Test
    public void testStaticImport() {
        System.out.println(Math.random());
    }

    /*4.泛型*/
    /*5.枚举*/
    /*6.注解*/
    /*7.接口定义加强 接口定义抽象方法+全局常量
     * 即可以定义普通方法和静态方法*/
    /*8.lambda表达式  解决匿名内部类的复杂定义问题*/
    /*9方法的引用 相当于给方法起别名
     * 引用静态方法 类名称::static 方法名称
     * 某个对象的方法 对象::普通方法
     * 特定类 特定类::普通方法
     * 构造方法 特定类::new
     * */
    /*10.内建函数式接口 提供四个核心接口实现操作的统一
     *Function Consumer Supplier Predicate
     * */
    @Test
    public void testLambda() {
        /*lambda*/
        fun(() -> System.out.println("123"));
        /*匿名内部类*/
        fun(new InterfaceTest() {
            @Override
            public void print() {
                System.out.println("123");
            }
        });
    }

    public static void fun(InterfaceTest test) {
        test.print();
    }

    @Test
    public void testMethodQuote() {
        /*方法的引用必须是函数式接口*/
        /*把toUpperCase方法交给upper*/
        MethodQuote<String> msg = "Hello"::toUpperCase;
        System.out.println(msg.upper());
    }

    @Test
    public void testCoreInterface() {
        Function<String, Boolean> fun = "Hello"::startsWith;
        /*相当于对象调用startsWith*/
        System.out.println(fun.apply("H"));
        Consumer<String> consumer = System.out::print;
        consumer.accept("HelloWorld");
        Supplier<String> supplier = "Hello"::toUpperCase;
        System.out.println(supplier.get());
        Predicate<String> pre = "hello"::equalsIgnoreCase;
        System.out.println(pre.test("Hello"));
    }

}

interface InterfaceTest {
    default void fun() {
        System.out.println("213");
    }

    static void get() {
        System.out.println("123");
    }

    public void print();
}

/*函数时接口 接口只允许定义一个抽象方法*/
@FunctionalInterface
interface IFunctionInterface {
    public void print();
}

/*方法的引用*/
@FunctionalInterface
interface MethodQuote<T> {
    public T upper();
}