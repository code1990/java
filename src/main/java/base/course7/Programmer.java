package base.course7;

/**
 * class 即为类
 * 类 的实例 即为对象 Object
 * 类 》》》 人类》》》抽象》》》面向对象》》》源自面向过程》》》
 * 面向过程的语言 c++ c 突出表现 指针
 * 面向对象的语言 java php python kotlin swift go R
 *
 * 行为和属性 都是类的成员 类的一部分
 * 类的行为 即为方法 成员方法
 * 类的表象 即为属性 成员变量
 *
 * 为了保护类 不被篡改 一般类的属性声明为private 这样的方式被称之为封装
 * 提供设置属性的方法 该方法为public  公开化 便于使用
 *
 * 创建类的模板称之为 类的构造器 构造器必须与类同名
 *
 * 面向对象的四大特征
 * 封装：把类的属性 私有化
 *
 * 继承 子类获取父类的行为和属性 目的是为了减少写重复代码
 *
 * 多态 子类与父类的表现不同 比如 鸟 分为老鹰 和麻雀
 *
 * 抽象 类是实例的抽象 小明 和 人类 之间的关系
 */
public class Programmer {

    /**
     /声明各类变量来描述程序类的属性
     */
    //private: 只有本类可见。
    //protected：本类、子类、同一包的类可见。
    //default(无修饰符)：本类、同一包的类可见。
    //public：对任何类可见。
    protected String name;
    protected String sex;
    protected int age;
    protected String address;

    public void eat(){
        System.out.println("我在吃饭！");
    }

    public void sleep(){
        System.out.println("我在睡觉！");
    }

    public void code(){
        System.out.printf("我在写代码！");
    }


}
