/**
 * @author issuser
 * @date 2019-08-17 12:48
 */
public class JavaBaseTest {

    public static void main(String[] args) {

        /*Java语言基础*/
        /*1.标识符与关键字 标识符不能使用数字开头*/
        /*关键字是Java中的保留字符 被赋予了特定含义*/

        /*2.Java数据类型分为基本数据类型和引用数据类型*/
        /*基本数据类型分为字符字节 整形长整型短整型 单精度双精度 布尔型*/
        char c = 'a';
        byte b = 1;
        int i = 10;
        short s = 12;
        long l = 120L;
        float f = 12.123F;
        double d = 12.123;
        boolean bool = false;

        /*3.运算符*/
        /*赋值运算,算术运算,自增自减运算,逻辑运算,位运算,三元运算符,算术优先级*/
        int aaa = 0;
        System.out.println(aaa++);
        int bbb = 0;
        System.out.println(++bbb);
        int ccc = 0;
        System.out.println(ccc--);
        int ddd = 0;
        System.out.println(--ddd);

        if (aaa == 0 && bbb == 0) {
            System.out.println("开关与,短路与,前面为false 结束运算");
        }
        if (aaa == 0 || bbb == 0) {
            System.out.println("开关或,短路或,前面为true 结束运算");
        }

        /*4.类型转换与强制转换*/
        int aa = 10;
        double bb = aa;
        System.out.println(bb);
        short cc = (short) aa;
        System.out.println(cc);

        /*5编码规范 带行注释 多行注释 文档注释 驼峰命名法*/
        /*包名 方法名 参数名 变量名 小写 类名首字母大写 常量名全大写*/
    }
}
