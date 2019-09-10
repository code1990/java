import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author issuser
 * @date 2019-08-23 17:42
 
 包getPackage()类路径类getName()类名称继承类getSuperClass()集成类接口getInerfaces()所有的接口构造方法getConstrcutors()public的构造方法构造方法getConstrcutors(Class<?> param)public的有参构造方法构造方法getDeclaredConstructors()所有的构造方法构造方法getDeclaredConstructors所有的有参构造方法方法getMethods()public的方法方法getMethod(String name,Class<?> param)public的有参方法方法getDeclaredMethods()所有的方法方法getDeclaredMethods(Class<?> param)所有的有参方法成员变量getFields()public的成员变量成员变量getField(String name)public的有参变量成员变量getDeclaredFileds()所有的成员变量成员变量getDeclaredField(String name)所有的有参变量内部类getClasses()public的内部类内部类getDeclaredClasses()所有的内部类内部类的声明类getDeclaringClass()返回内部类的成员类
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
    public void getClassInfo() throws Exception {
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

    /*构造器常用的方法*/
    @Test
    public void testConstructor() throws Exception {

        Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class});
        /*可变数量的参数*/
        System.out.println(constructor.isVarArgs());
        /*获取参数类型*/
        System.out.println(Arrays.toString(constructor.getParameterTypes()));
        /*获取异常类型*/
        System.out.println(Arrays.toString(constructor.getExceptionTypes()));
        /*新建一个实例*/
//        constructor.newInstance(String.class);
        /*设置可见*/
        constructor.setAccessible(true);
        /*获取构造方法采用的修饰符的整数*/
        int modifier = constructor.getModifiers();
        /**/
        System.out.println(Modifier.isPublic(modifier));
        System.out.println(Modifier.isPrivate(modifier));
        System.out.println(Modifier.isProtected(modifier));
        System.out.println(Modifier.isStatic(modifier));
        System.out.println(Modifier.isFinal(modifier));
        System.out.println(Modifier.isAbstract(modifier));
        System.out.println(Modifier.toString(modifier));
    }

    @Test
    public void testField() throws Exception {
        Field field = clazz.getField("publicString");
        System.out.println(field.getName());
        System.out.println(field.getType());
        field.setAccessible(true);
        System.out.println(field.getModifiers());
    }

    @Test
    public void getMethod() throws Exception {
        Method method = clazz.getMethod("setString", String.class);
        System.out.println(method.getName());
        System.out.println(method.getParameterTypes());
        System.out.println(method.getReturnType());
        System.out.println(method.getExceptionTypes());
        System.out.println(method.isVarArgs());
        System.out.println(method.getModifiers());
        /*调用回调方法设置参数*/
        method.invoke(new ReflectEntityTest(),"1");
    }
}
