package base.course8;
//父类的 构造器 构造方法 构造函数 都是一个意思 都是类的模板
public class Father{

    // 根据参数的不同 表现为类的重载 参数个数不同 参数类型不同 即为类的重载
    //类的重写 方法相同 方法的实现不同 叫 猫叫  狗叫
    //类的重写 和类的重载 表现为类的多态 多态 事物的多种形态

    //父类的 无参数构造器
    public Father() {
        System.out.println("调用了无参的构造函数.");
    }
//  父类的 有参数构造器
    public Father(String msg) {
        System.out.println("调用了有参的构造函数： " + msg);
    }

    public static void main(String args[]) {
        Father f1 = new Father();
        Father f2 = new Father("Hello");
    }


}
