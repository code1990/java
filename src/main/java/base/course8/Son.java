package base.course8;

/**
 *
 * 构造方法 创建类的模板
 *
 *
 */

//子类继承父类 可以避免重复代码的编写
//    可以很快获取父类的方法和属性
public class Son  extends Father{

    //定义成员变量
    //下面是子类单独的成员变量 拓展出来的
    private String name; // 实例化对象时，默认值是null

    /**
     * 无参数的构造方法
     */
    Son() {
        System.out.println("我是二儿子！");
    }

    /**
     * 有参数的构造方法
     */
    Son(String name) {
        // this 表示当前对象 即为当前类Son，Son的实例
        this();  //调用无参数的构造方法
        this.name = name;  // 调用成员变量
    }

    public void say() {
        System.out.println("我叫：" + name);
    }

    public static void main(String[] args) {
        Son s = new Son("王小二");
        s.say();
    }


}
