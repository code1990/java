import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author issuser
 * @date 2019-08-21 23:44
 */
public class TypeTest extends ExtendClass implements i {
    /*泛型的高级用法*/
    /*1.限制泛型的可以使用的类型 如下 T必须是List的子类*/
    class innerClass<T extends List> {

    }

    @Test
    public void testInfo() {
        /*泛型限制向下造型*/
        /* A<? extends List> a =null*/
        /*泛型限制向上造型*/
        /* A<? super List> a =null*/
    }

    /*继承泛型类实现泛型接口*/
}

class ExtendClass<T1> {

}

interface i<T1> {

}
