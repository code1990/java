import org.junit.Test;

import java.util.Arrays;

/**
 * @author issuser
 * @date 2019-08-23 17:42
 */
public class ReflectTest {
    private static final String classPath = "ReflectEntityTest";
    private Class clazz;

    {
        try {
            clazz = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFields() throws Exception {
        System.out.println(clazz.getPackage());//类路径类
        System.out.println(clazz.getName());//类名称继承类
        System.out.println(clazz.getSuperclass());//集成类接口
        System.out.println(Arrays.toString(clazz.getInterfaces()));//所有的接口构造方法
        System.out.println(Arrays.toString(clazz.getConstructors()));//public的构造方法构造方法

        System.out.println(clazz.getConstructor(new Class[]{String.class}));//public的有参构造方法构造方法
        System.out.println(Arrays.toString(clazz.getDeclaredConstructors()));//所有的构造方法构造方法
        Class[] cArg = new Class[1];
        cArg[0] = String.class;
        System.out.println(clazz.getDeclaredConstructor(cArg));//所有的有参构造方法方法
        System.out.println(Arrays.toString(clazz.getMethods()));//public的方法方法
        System.out.println(clazz.getMethod("setString", String.class));//public的有参方法方法
        System.out.println(Arrays.toString(clazz.getDeclaredMethods()));//所有的方法方法
        System.out.println(clazz.getDeclaredMethod("setString", String.class));//所有的有参方法成员变量
        System.out.println(Arrays.toString(clazz.getFields()));//public的成员变量成员变量
        /*设置字段属性可见*/
        for (int i = 0; i < clazz.getFields().length; i++) {
            clazz.getFields()[i].setAccessible(true);
        }
        System.out.println(clazz.getField("publicString"));//public的有参变量成员变量
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));//所有的成员变量成员变量
        System.out.println(clazz.getDeclaredField("string"));//所有的有参变量内部类
        System.out.println(Arrays.toString(clazz.getClasses()));//public的内部类内部类
        System.out.println(Arrays.toString(clazz.getDeclaredClasses()));//所有的内部类内部类的声明类
        System.out.println(clazz.getDeclaringClass());//
    }

    @Test
    public void getMethods() {

    }
}
