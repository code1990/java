import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author 911
 * @date 2020-11-21 11:38
 */
//按照方法名顺序执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FundUpdate {

    @Test
    public void test01(){
        System.out.println(1);
    }
    @Test
    public void test02(){
        System.out.println(2);
    }
}
