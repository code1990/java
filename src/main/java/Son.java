/**
 * @author issuser
 * @date 2019-08-17 19:14
 */
public class Son extends Parent implements drawString {
    public Son(String name, String age) {
        super(name, age);
        super.doSomethingPublic();
        super.doSomethingProtected();
        /*子类中无法调用父类非private的方法和属性*/
    }

    public Son() {
        /*父类的无参数构造方法自动被调用 所以不需要super.xx()*/
    }

    /*4.方法的重写 方法名称相同 方法的实现不同*/
    @Override
    public void doSomethingPublic() {
        System.out.println("son do something public");
    }

    /*4.方法的重载 方法名相同 参数不同 个数不同 方法的实现可相同 可不同*/
    public void doSomethingPublic(Son obj) {
        System.out.println("son do something public");
    }

    /*重载参数不同 产生变长参数*/
    public int sum(int... a) {
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
        }
        return sum;
    }

    @Override
    public void drawSomething() {
        System.out.println("implements interface drawSomething");
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.doSomethingPublic();
        /*2.向上转型  把子类看成是父类的一个特殊对象 向下转型 不建议使用*/
        Parent p = (Parent) son;
        p.doSomethingPublic();
        Parent parent = new Parent();
        parent.doSomethingPublic();
//        Son obj = (Son)parent;
        /*3.使用instanceof判断一个对象是不是一个类的实例*/
//        if(p instanceof Son){
//            Son obj2 = (Son)parent;
//            System.out.println(obj2);
//        }
        System.out.println(son.sum(1, 2, 3, 4));
        /*5.多态 子类向上造型 和 方法的重载都是多态的表现形式*/
    }
}

class Parent extends GrandParent {
    private String name;
    private String age;

    public Parent(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public Parent() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void doSomethingPublic() {
        System.out.println("Parent do something public");
    }

    protected void doSomethingProtected() {
        System.out.println("Parent do something protected");
    }

    private void doSomethingPrivate() {
        System.out.println("Parent do something private");
    }

    @Override
    public String toString() {
        return "Parent{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}

/*6抽象类和接口 都不能被实例化 区别如下
* （1）抽象类可以有构造方法，接口中不能有构造方法。

（2）抽象类中可以有普通成员变量，接口中没有普通成员变量

（3）抽象类中可以包含静态方法，接口中不能包含静态方法
* */
abstract class GrandParent {
    public static final double PI = 3.14;

    /*抽象方法没有实现*/
    abstract void doSomethingPublic();

    /*一般方法有实现*/
    void doSomethingDeault() {
        System.out.println("GrandParent doSomethingDeault");
    }
}

/*接口不能被实例化  没有构造方法 没有方法的实现 不能定义常量*/
interface drawString {
    public double PI = 3.14;

    void drawSomething();
}