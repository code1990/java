import org.junit.Test;

import java.io.File;

/**
 * @author issuser
 * @date 2019-08-19 22:22
 */
public class ExceptionTest {
    /*1.异常概述
    * 1.异常是程序运行期间,终止正常的指令流
    * 2.Throwable是所有的异常类父类
    * 3.Exception和Error是异常类的子类
    * 4.Error是java系统内部错误或者内存泄露
    * 5.Exception是非致命性错误 通过捕获程序可以正常执行
    *
    * Exception主要分为RuntimeException和非RuntimeException 其中编辑器提示报错的叫受检异常
    *
    * 在开发过程中常见的RuntimeException类型的异常主要有以下几种。
    1. ArithmeticException：数学计算异常。
    2. NullPointerException：空指针异常。
    3. NegativeArraySizeException：负数组长度异常。
    4. ArrayOutOfBoundsException：数组索引越界异常。
    5. ClassNotFoundException：类文件未找到异常。
    6. ClassCastException：类型强制转换异常。
    7. SecurityException：违背安全原则异常。

    其他非RuntimeException类型的常见异常主要有以下几种。
    1. NoSuchMethodException：方法未找到异常。
    2. IOException：输入输出异常。
    3. EOFException：文件已结束异常。
    4. FileNotFoundException：文件未找到异常。
    5. NumberFormatException：字符串转换为数字异常。
    6. SQLException：操作数据库异常
    * */

    /*2.异常的一般处理流程
     * 使用try-catch代码主动捕获异常
     * 使用throws将异常抛给JVM处理
     * 使用throw主动异常暴露出来
     * finally修饰的代码块一定被执行*/
    public static void main(String[] args) throws Exception {
        try {
            int i = 0;
            System.out.println(10 / i);
            throw new RuntimeException();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally修饰的代码块一定被执行");
        }

    }
}

/*3.自定义异常类 添加一个有参数构造器*/
class MyException extends Exception {
    public MyException(String message) {
        super(message);
        System.out.println("我是自定义异常");
    }
}