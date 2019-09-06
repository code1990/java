package java8;

import org.junit.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * @author issuser
 * @date 2019-09-06 23:36
 * 如何把程序写得易读？如何明确地表明程序的意图？如何让高性能程序易于编码
 * 1.2　什么是函数式编程
 * 每个人对函数式编程的理解不尽相同。但其核心是：在思考问题时，使用不可变值和函
 * 数，函数对一个值进行处理，映射成另一个值。
 *
 * Lambda 表达式是一个匿名方法，将行为像数据一样进行传递。
 * 函数接口指仅具有单个抽象方法的接口，用来表示 Lambda表达式的类型。
 */
public class ch2Lambda {

    @Test
    public void innerClass(){
        /*使用匿名内部类的方式来实现行为与按钮单机事件的绑定*/
        new JButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button clicked");
            }
        });
    }

    @Test
    public void firstLambda(){
        /*使用匿名函数来实现行为与按钮单机事件的绑定*/
        /*event是参数 ->分割主体与代码块*/
        new JButton().addActionListener(event-> System.out.println("button clicked"));
    }

    @Test
    public void lambdaTest(){
        /*使用()表示没有参数*/
        Runnable r = ()-> System.out.println("Hello World");
        /*没有参数 省去参数*/
        ActionListener a =event -> System.out.println("button clicked");
        /*表示一个代码块*/
        Runnable mr =()->{
            System.out.println("123");
            System.out.println("123");
        };
        /*参数类型直接推断*/
        BinaryOperator<Long> add =(x,y)->x+y;
        /*参数类型直接声明*/
        BinaryOperator<Long> add2 =(Long x,Long y)->x+y;
        /*目标类型是指Lambda 表达式所在上下文环境的类型*/
        /*右边没有声明类型 根据上下文判断类型*/
        final String[] strArray = {"Hello","World"};
    }

    @Test
    public void finalVariable(){
        /*lambda表达式引用的是值 不是变量 final只能给变量赋值一次*/
        /*lambda只能引用成为事实的final变量*/
        /*Lambda 表达式都是静态类型*/
        String name = getUserName();
        new JButton().addActionListener(event-> System.out.println(name));
    }

    public static String getUserName(){
        return System.currentTimeMillis()+"";
    }

    @Test
    public void testInterfaceFunction(){
        /*函数接口是只有一个抽象方法的接口，用作Lambda 表达式的类型*/
        /*Java重要的接口函数
        * Predicate<T> consumer<T> supplier<T> Function<T> UnaryOperator<T> BinaryOperator<T>
        * */
    }

    @Test
    public void testTypeRefer(){
        /*Java7的菱形操作符 推断出泛型参数的类型*/
        /*可以省去构造函数的泛型类型*/
        Map<String,Integer> map = new HashMap<>();
        Predicate<Integer> a = x->x>5;
        BinaryOperator<Long> b = (x,y)->x+y;
    }
}
