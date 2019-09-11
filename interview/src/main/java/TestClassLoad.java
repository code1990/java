/**
 * @author issuser
 * @date 2019-09-11 22:14
 *
 * *************************
 * father static {}
 * father {}
 * father constructor
 * *************************
 * Son static {}
 * father {}
 * father constructor
 * Son {}
 * Son constructor
 */
public class TestClassLoad {
    public static void main(String[] args) {
        System.out.println("*************************");

        Father father = new Father();
        System.out.println("*************************");
        Son son = new Son();

    }
}

class Father {
    public Father() {
        System.out.println("father constructor");
    }

    {
        System.out.println("father {}");
    }

    static {
        System.out.println("father static {}");
    }
}

class Son extends Father {
    public Son() {
        System.out.println("Son constructor");
    }

    {
        System.out.println("Son {}");
    }

    static {
        System.out.println("Son static {}");
    }
}