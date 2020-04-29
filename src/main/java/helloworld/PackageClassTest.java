package helloworld;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PackageClassTest {

    /*为了能将基本类型视为对象进行处理，并能连接相关的方法，
    Java为每个基本类型都提供了包装类 下面依次介绍这些类型的常见用法
    自动装箱 自动拆箱 以及类型之间的转换 parseXXX
    */
    @Test
    public void testInteger() {
        /*1.构造方法*/
        System.out.println(new Integer(3));
        System.out.println(new Integer("3"));
        /*2.常用方法*/
        System.out.println(new Integer(1).byteValue());//以byte类型返回该Integer的值
        System.out.println(new Integer(1).compareTo(2));//比较两个Integer对象。如果这两个值相等，则返回0；
        System.out.println(new Integer(1).equals(2));//比较此对象与指定的对象是否相等
        System.out.println(new Integer(1).intValue());//int );//以int型返回此Integer对象
        System.out.println(new Integer(1).shortValue());//short );//以short 型返回此Integer对象
        System.out.println(Integer.toString(1));//返回一个表示该Integer值String对象
        System.out.println(Integer.valueOf(2));//Integer 返回保存指定的String值的Integer 对象
        System.out.println(Integer.parseInt("2"));//int返回包含在由str指定的字符串中的数字的等价整数值
        /*3.常量*/
        System.out.println(Integer.MAX_VALUE);//表示int类型可取的最大值
        System.out.println(Integer.MIN_VALUE);//表示int类型可取的最小值
        System.out.println(Integer.SIZE);//用来以二进制补码形式表示int值的位数。
        System.out.println(Integer.TYPE);//表示基本类型int的Class实例。

    }

    @Test
    public void testBoolean() {
        /*1.构造方法*/
        System.out.println(new Boolean(true));
        System.out.println(new Boolean("ok"));
        /*2.常用方法*/
        System.out.println(new Boolean(true).booleanValue());//将Boolean对象的值以对应的boolean值
        System.out.println(new Boolean(true).equals(false));//返回判断调用该方法的对象与obj是否相等
        System.out.println(Boolean.parseBoolean("true"));//将字符串参数解析为boolean值
        System.out.println(Boolean.toString(true));//返回表示该boolean值的String对象
        System.out.println(Boolean.valueOf(true));////返回一个用指定的字符串表示值的boolean值
        /*3.常量*/
        System.out.println(Boolean.TRUE);//对应基值true的Boolean对象。
        System.out.println(Boolean.FALSE);//对应基值false的Boolean对象。
        System.out.println(Boolean.TYPE);//基本类型boolean的Class对象。
    }

    @Test
    public void testByte() {
        /*1.构造方法*/
        System.out.println(new Byte("3"));
        System.out.println(new Byte((byte) 1));
        /*2.常用方法*/
        System.out.println(new Byte("3").byteValue());//以一个byte 值返回Byte对象
        System.out.println(new Byte("3").compareTo(new Byte("3")));//在数字上比较两个Byte对象
        System.out.println(new Byte("3").doubleValue());//以一个double值返回此Byte的值
        System.out.println(new Byte("3").intValue());//以一个int值返回此Byte的值
        System.out.println(Byte.parseByte("3"));//将 String型参数解析成等价的字节(byte形式
        System.out.println(Byte.toString((byte) 1));//返回表示此Byte的值的String对象
        System.out.println(Byte.valueOf("3"));//返回一个保持指定String所给出的值的Byte对象
        System.out.println(new Byte("3").equals(new Byte("3")));//将此对象与指定对象比较，如果调用该方法的对象与obj相等
        /*3.常量*/
        System.out.println(Byte.MAX_VALUE);//表示int类型可取的最大值
        System.out.println(Byte.MIN_VALUE);//表示int类型可取的最小值
        System.out.println(Byte.SIZE);//用来以二进制补码形式表示int值的位数。
        System.out.println(Byte.TYPE);//表示基本类型int的Class实例。
    }

    @Test
    public void testCharacter() {
        /*1.构造方法*/
        System.out.println(new Character('s'));
        /*2.常用方法*/
        System.out.println(new Character('s').charValue());//返回此Character对象的值
        System.out.println(new Character('s').compareTo(new Character('s')));//根据数字比较两个Character对象，若这两个对象相等则返回0
        System.out.println(new Character('s').equals(new Character('s')));//将调用该方法的对象与指定的对象相比较
        System.out.println(Character.toUpperCase('s'));//将字符参数转换为大写
        System.out.println(Character.toLowerCase('s'));// 将字符参数转换为小写
        System.out.println(Character.toString('s'));//返回一个表示指定char值的String对象
        System.out.println(Character.isUpperCase('s'));//判断指定字是否为大写字符
        System.out.println(Character.isLowerCase('s')); //判断指定字符是否为小写字符
        /*3.常量*/
        System.out.println(Character.CONNECTOR_PUNCTUATION);//返回byte型值，表示Unicode规范中的常规类别“Pc”。
        System.out.println(Character.UNASSIGNED);//返回byte型值，表示Unicode规范中的常规类别“Cn”。
        System.out.println(Character.TITLECASE_LETTER);//返回byte型值，表示Unicode规范中的常规类别“Lt”。
    }


    @Test
    public void testDouble() {
        /*1.构造方法*/
        System.out.println(new Double("3"));
        System.out.println(new Double(3.0));
        /*2.常用方法*/
        System.out.println(new Double(3.0).byteValue());//以byte形式返回Double对象值(通过强制转换)
        System.out.println(new Double(3.0).compareTo(new Double(3.0)));//根据数字比较两个Character对象，若这两个对象相等则返回0
        System.out.println(new Double(3.0).equals(new Double(3.0)));// 将此对象与指定的对象相比较
        System.out.println(new Double(3.0).intValue());// 形式返回double值
        System.out.println(new Double(3.0).isNaN());//如果此double值是数字(NaN)值，则返回true；否则返回false
        System.out.println(Double.toString(3.0));//返回此Double对象的字符串表示形式
        System.out.println(Double.valueOf("3.0"));//返回保存用参数字符串str表示的double值的Double对象
        System.out.println(new Double(3.0).doubleValue());//以double 形式返回此Double对象
        System.out.println(new Double(3.0).longValue());//以long形式返回此double的值(通过强制转换long类型)
        /*3.常量*/
        System.out.println(Double.MAX_VALUE);//返回int值，表示有限double变量可能具有的最大指数。
        System.out.println(Double.MIN_VALUE);//返回int值，表示标准化double变量可能具有的最小指数。
        System.out.println(Double.NEGATIVE_INFINITY);//返回double值，表示保存double类型的负无穷大值的常量。
        System.out.println(Double.POSITIVE_INFINITY);//返回 double值，表示保存double类型的正无穷大值的常量。
    }

    @Test
    public void testNumber() {
        /*BigDecimal BigInteger在涉及大数据,金融方面用途比较广泛 保证精度*/
        BigDecimal b1 = new BigDecimal(2 ^ 32);
        BigDecimal b2 = new BigDecimal(2 ^ 32);
        /*加法 add()函数     减法subtract()函数
        乘法multiply()函数    除法divide()函数    绝对值abs()函数*/
        System.out.println(b1.add(b2));
        System.out.println(b1.subtract(b2));
        System.out.println(b1.multiply(b2));
        System.out.println(b1.divide(b2));
        BigInteger b3 = new BigInteger("123");
        BigInteger b4 = new BigInteger("1234");
        System.out.println(b3.add(b4));
    }
}
