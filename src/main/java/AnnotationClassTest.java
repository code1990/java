import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author issuser
 * @date 2019-08-23 22:05
 */
public class AnnotationClassTest {
    @FieldMethodParamterAnnotaion(describe = "编号",type = int.class)
    int id;
    @ConstructorAnnotaion("默认构造方法")
    public AnnotationClassTest() {

    }

    public AnnotationClassTest(@FieldMethodParamterAnnotaion(describe = "编号",type = int.class)int id) {
        this.id = id;
    }
    @FieldMethodParamterAnnotaion(describe = "获取编号",type = int.class)
    public int getId() {
        return id;
    }
    @FieldMethodParamterAnnotaion(describe = "设置编号")
    public void setId(int id) {
        this.id = id;
    }

}
