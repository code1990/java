import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author issuser
 * @date 2019-08-23 22:44
 * @Override 标记子类重写父类的方法
 * @Deparecated 过期操作符
 * @SuppressWarnings 压制警告 使用了不安全的操作 编译出现安全警告
 */
public class AnnotationTest {
    @Deprecated
    public void fun(){

    }
    @Test
    public void getConstructor(){
        Constructor[] constructors = AnnotationClassTest.class.getDeclaredConstructors();
        for (int i = 0; i <constructors.length ; i++) {
            Constructor constructor = constructors[i];
            if(constructor.isAnnotationPresent(ConstructorAnnotaion.class)){
                ConstructorAnnotaion c = (ConstructorAnnotaion)constructor.getAnnotation(ConstructorAnnotaion.class);
                System.out.println(c.value());
            }
        }
    }
    @Test
    public void getField(){
        Field[] fields = AnnotationClassTest.class.getDeclaredFields();
        for (int i = 0; i <fields.length ; i++) {
            Field field = fields[i];
            if(field.isAnnotationPresent(FieldMethodParamterAnnotaion.class)){
                FieldMethodParamterAnnotaion c = (FieldMethodParamterAnnotaion)field.getAnnotation(FieldMethodParamterAnnotaion.class);
                System.out.println(c.describe());
                System.out.println(c.type());
            }
        }
    }
    @Test
    public void getMethod(){
        Method[] methods = AnnotationClassTest.class.getDeclaredMethods();
        for (int i = 0; i <methods.length ; i++) {
            Method method = methods[i];
            if(method.isAnnotationPresent(FieldMethodParamterAnnotaion.class)){
                FieldMethodParamterAnnotaion c = (FieldMethodParamterAnnotaion)method.getAnnotation(FieldMethodParamterAnnotaion.class);
                System.out.println(c.describe());
                System.out.println(c.type());
            }
        }
    }
}
