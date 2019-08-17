import java.util.Date;

/**
 * @author issuser
 * @date 2019-08-17 20:25
 */
public final class ObjectHighTest {

    public static final long FINAL_LENGTH = 1000L;

    public static final void getInfo() {
        System.out.println("final method");
    }

    public ObjectHighTest() {
        System.out.println("final class");
    }

    public static void main(String[] args) {
        /*1.package 包 包的主要作用就是为了防止文件名冲突 同一个包下的类相互引入不需要导入包名*/
        Date utilDate = new Date();
        System.out.println(utilDate.getTime());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println(sqlDate.getTime());
        /*2.final修饰的变量是常量 常量不能被修改*/
        /*final修饰的方法不能被重写*/
        /*final修饰的类不能被继承 String被final修饰 所以不能被继承*/
        System.out.println(ObjectHighTest.FINAL_LENGTH);
        ObjectHighTest.getInfo();
        /*6匿名内部类的使用*/
        new Thread() {
            @Override
            public void run() {
                System.out.println("I am thread, I am anonymous class at here");
            }
        }.start();

    }

    /*5.内部类 声明在其他类的内部*/
    static class InnerStaticClass {
        void getInfo() {
            System.out.println("I am static inner class");
        }
    }
}

class FriendClass {
    void getInfo() {
        System.out.println("一个Java文件只能有一个public的类,这是编译的入口文件.其他与之同名文件下的类,称之为伴生类");
    }
}