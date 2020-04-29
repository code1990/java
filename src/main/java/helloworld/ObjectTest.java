package helloworld;

/**
 * @author issuser
 * @date 2019-08-17 16:08
 */
public class ObjectTest {

    public static void main(String[] args) {
        /*1.类 类是一类事务的简称 类是客观事物的抽象和升华
         * 对象 万物皆对象 对象是类的实例 是客观存在的实体
         * 封装 把类的属性和方法隐藏起来 对外保证数据的完整性 这就是封装
         * 继承 子类继承父类可以实现代码的复用 子类继承父类 可以获取父类非private的属性和方法
         * 多态 事务的多种形态 可以以统一的风格处理复杂的事务 多态的实现依赖于接口和抽象类*/

        /*7.对象的使用和访问*/
        /*obj是引用类型,存放的是对象的引用,即为内存地址*/
        ObjectTest obj = new ObjectTest();
        obj.setAge(12);
        obj.setName("tomcat");
        System.out.println(obj.toString());
        System.out.println(ObjectTest.PI);
        System.out.println(ObjectTest.getInfo(obj));

        /*对象的比较和销毁*/
        /*==比较地址是否相同 equals比较内容是否相同*/
        /*对象的销毁:1.超过其作用范围 2.赋值为null*/
        ObjectTest obj2 = obj;
        System.out.println(obj.equals(obj2));
        System.out.println(obj == obj2);
        ObjectTest obj3 = new ObjectTest("tomcat", 12);
        System.out.println(obj.equals(obj3));
        System.out.println(obj == obj3);
        obj2 = null;
        System.out.println("obj2=null后被销毁");

    }

    /*2类的成员变量和成员方法*/
    /*使用private修饰符修饰类的属性 需要对外提供访问该属性的get/set方法
     * private修饰符 在本类中可见
     * protected 修饰符在同一个包下可见
     * public 修饰符任意可见*/
    private String name;
    private int age;

    /*类的成员方法*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        /*this关键字表示当前对象*/
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /*3构造方法 创建对象的方法*/
    public ObjectTest(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ObjectTest() {
    }

    /*4.静态方法和属性*/
    public static final double PI = 3.14;

    public static String getInfo(ObjectTest obj) {
        return obj.toString();
    }

    /*toString返回的是对象的内存地址,重写tostring方法*/
    @Override
    public String toString() {
        return "ObjectTest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
