package java8;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * @author issuser
 * @date 2019-09-07 15:43
 *
 *
 * Java 8 中的另一个变化是引入了默认方法和接口的静态方法，它改变了人们认识类库的方
 * 式，接口中的方法也可以包含代码体了。
 *
 * 装箱类型属于普通的Java 类，只不过是对基本类型的一种封装。
 *
 * 只有装箱类型才能作为泛型参数
 *
 * 将基本类型转换为装箱类型，称为装箱，反之则称为拆箱
 *
 * 对基本类型做特殊处理的方法在命名上有明确的规范。如果方法返回类型为基本类型，则
 * 在基本类型前加To，如图4-1 中的ToLongFunction。如果参数是基本类型，则不加前缀只
 * 需类型名即可，如图4-2 中的LongFunction。如果高阶函数使用基本类型，则在操作后加
 * 后缀To 再加基本类型，如mapToLong。
 *
 * 这些基本类型都有与之对应的Stream，以基本类型名为前缀，如LongStream
 *
 * 应尽可能多地使用对基本类型做过特殊处理的方法，进而改善性能。这些特殊
 * 的Stream 还提供额外的方法，避免重复实现一些通用的方法，让代码更能体现出数值计算
 * 的意图。
 *
 * BinaryOperator 是一种特殊的BiFunction 类型，参数的类型和返回值的类型相同。
 *
 * Lambda 表达式的类型就是对应的函数接口类型，因此，将Lambda 表达式作为参数
 * 传递时，情况也依然如此
 *
 * 总而言之，Lambda 表达式作为参数时，其类型由它的目标类型推导得出，推导过程遵循
 * 如下规则：
 * 如果只有一个可能的目标类􀅖 型，由相应函数接口里的参数类型推导得出；
 * 􀅖 如果有多个可能的目标类型，由最具体的类型推导得出；
 * 􀅖 如果有多个可能的目标类型且最具体的类型不明确，则需人为指定类型。
 *
 * 为了提高Stream 对象可操作性而引入的各种新接
 * 口，都需要有Lambda 表达式可以实现它。它们存在的意义在于将代码块作为数据打包起
 * 来。因此，它们都添加了@FunctionalInterface 注释。
 *
 * 接口中这样的方法叫作默认方法，在任何
 * 接口中，无论函数接口还是非函数接口，都可以使用该方法。
 *
 * 重点就在于代码段前面的新关键字default。这
 * 个关键字告诉javac 用户真正需要的是为接口添加一个新方法。除了添加了一个新的关键
 * 字，默认方法在继承规则上和普通方法也略有区别。
 *
 * 此默认方法只能通过调用子类的方法来修改子类本身，避免了对子类的实现做出各种假设。
 *
 * 任何时候，一旦与类中定义的方法产生冲突，都要优先选择类中定义的方法
 *
 * 类中重写的方法优先级高于接口中定义的默认方法
 *
 * 让类中重写方法的优先级高于默认方法能简化很多继承问题。
 *
 * 接口允许多重继承，因此有可能碰到两个接口包含签名相同的默认方法的情况
 *
 * 三定律
 * 如果对默认方法的工作原理，特别是在多重继承下的行为还没有把握，如下三条简单的定
 * 律可以帮助大家。
 * 1. 类胜于接口。如果在继承链中有方法体或抽象的方法声明，那么就可以忽略接口中定义的方法。
 * 2. 子类胜于父类。如果一个接口继承了另一个接口，且两个接口都定义了一个默认方法，那么子类中定义的方法胜出。
 * 3. 没有规则三。如果上面两条规则不适用，子类要么需要实现该方法，要么将该方法声明为抽象方法。
 * 其中第一条规则是为了让代码向后兼容。
 *
 * Stream 是个接口，Stream.of 是接口的静态方法。
 *
 * 类是一个放置工具方法的好地方
 *
 * Optional 是为核心类库新设计的一个数据类型，用来替换null 值
 * 使用Optional 对象有两个目的：首先，Optional 对象鼓励程序员适时检查
 * 变量是否为空，以避免代码缺陷；其次，它将一个类的API 中可能为空的值文档化，这比
 * 阅读实现代码要简单很多。
 */
public class ch4ClassLibary {

    @Test
    public void testParentDefaultMethodUse(){
        Parent parent = new ParentImpl();
        parent.welcome();
        Assert.assertEquals("Parent Hi",parent.getLastMessage());
    }

    @Test
    public void testOptional(){
        Optional<String> a = Optional.of("a");
        Assert.assertEquals("a",a.get());
        Optional optional = Optional.empty();
        Optional optional1 = Optional.ofNullable(null);
        Assert.assertEquals(optional,optional1);
        /*orElse该方法提供了一个备选值*/
        Assert.assertEquals("b",optional.orElse("b"));
        /*orElseGet只有在Optional 对象真正为空时才会调用*/
        Assert.assertEquals("c",optional.orElseGet(()->"c"));
    }
}

interface Parent {
    public void message(String body);

    public default void welcome() {
        message("Parent Hi");
    }

    public String getLastMessage();
}

class ParentImpl implements Parent{
    @Override
    public void message(String body) {

    }

    @Override
    public String getLastMessage() {
        return null;
    }
}
interface Child extends Parent{
    @Override
    default void welcome() {
        message("Child Hi");
    }
}