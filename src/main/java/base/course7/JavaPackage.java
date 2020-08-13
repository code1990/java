package base.course7;
import base.course3.JavaType;
public class JavaPackage {

    public static void main(String[] args) {

        //调用其他包中的类的方式
        // 1 直接写全名 类改动最小 这个类很少使用 或者类重名了
        // 2.直接import 简洁

        //如下所示 实例化对象的方式
        //不import 则需要写全名
        base.course2.HelloWorld h = new base.course2.HelloWorld();
//        base.course3.HelloWorld h2 = new base.course3.HelloWorld();

        //import 则可以写简称 直呼其名
        JavaType j = new JavaType();
    }

}
