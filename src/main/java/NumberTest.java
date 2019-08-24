import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

/**
 * @author issuser
 * @date 2019-08-24 13:00
 */
public class NumberTest {
    @Test
    public void testFormat() {
        DecimalFormat format = new DecimalFormat();
        /*设置格式化的类型*/
        format.applyPattern("###,###.###");
        System.out.println(format.format(123456.789));
        /*设置分组*/
        format.setGroupingSize(2);
        System.out.println(format.format(123456.789));
        /*false表示不设置分组*/
        format.setGroupingUsed(false);
        System.out.println(format.format(123456.789));
    }

    @Test
    public void testMath() {
        /*常量PI*/
        System.out.println(Math.PI);
        /*常量E*/
        System.out.println(Math.E);

        /*三角函数方法*/
        System.out.println(Math.sin(Math.PI));//返回角的三角正弦。
        System.out.println(Math.cos(Math.PI));//返回角的三角余弦。
        System.out.println(Math.tan(Math.PI));//返回角的三角正切。
        System.out.println(Math.asin(Math.PI));//返回一个值的反正弦。
        System.out.println(Math.acos(Math.PI));//返回一个值的反余弦。
        System.out.println(Math.atan(Math.PI));//返回一个值的反正切。
        System.out.println(Math.toRadians(180.0));//将角度转换为弧度。
        System.out.println(Math.toDegrees(Math.PI));//将弧度转换为角度。
        /*指数函数方法*/
        System.out.println(Math.exp(2));//用于获取e的a次方,即取ee。
        System.out.println(Math.log(2));//用于取自然对数,即取lna的值。
        System.out.println(Math.log10(2));//用于取底数为10的对数。
        System.out.println(Math.sqrt(2));//用于取a的平方根,其中a的值不能为负值。
        System.out.println(Math.cbrt(2));//用于取a的立方根。
        System.out.println(Math.pow(2, 2));//用于取a的b次方。
        /*取整函数方法*/
        System.out.println(Math.ceil(2.3));//返回大于等于参数的最小整数。
        System.out.println(Math.floor(2.3));//返回小于等于参数的最大整数。
        System.out.println(Math.rint(2.3));//返回与参数最接近的整数,如果两个同为整数且同样接近,则结果取偶数。
        System.out.println(Math.round(2.3F));//将参数加上0.5后返回与参数最近的整数。
        System.out.println(Math.round(2.3));//将参数加上0.5后返回与参数最近的整数,然后强制转换为长整型。
        /*取最大值、最小值以及平均值函数方法*/
        System.out.println(Math.max(2.3, 2.5));//取a与b之间的最大值。
        System.out.println(Math.min(1, 2));//取a与b之间的最小值,参数为整型。
        System.out.println(Math.min(1L, 2L));//取a与b之间的最小值,参数为长整型。
        System.out.println(Math.min(1.0F, 2.0F));//取a与b之间的最小值,参数为浮点型。
        System.out.println(Math.min(2.3, 2.5));//取a与b之间的最小值,参数为双精度型。
        System.out.println(Math.abs(-2));//返回整型参数的绝对值。
        System.out.println(Math.abs(-2L));//返回长整型参数的绝对值。
        System.out.println(Math.abs(-3F));//返回浮点型参数的绝对值。
        System.out.println(Math.abs(2.3));//返回双精度型参数的绝对值。

    }

    @Test
    public void testRandom() {
        /*Math类的random()方法*/
        System.out.println((int) (Math.random() * 2));//返回大于等于0且小于n的随机数|
        System.out.println(1 + (int) (Math.random() * 2));//返回大于等于m且小于m+n(不包括m+n)的随机数
        System.out.println((char) ('a' + Math.random() * ('z' - 'a' + 1)));//生成a-z之间的字符
        System.out.println((char) ('A' + Math.random() * ('A' - 'Z' + 1)));//A~Z之间的随机字符

        /*实例化一个Random对象创建一个随机数生成器*/
        Random random = new Random();
        System.out.println(random.nextInt());//返回一个随机整数。
        System.out.println(random.nextInt(2));//返回大于等于0且小于n的随机整数。
        System.out.println(random.nextLong());//返回一个随机长整型值。
        System.out.println(random.nextBoolean());//返回一个随机布尔型值。
        System.out.println(random.nextFloat());//返回一个随机浮点型值。
        System.out.println(random.nextDouble());//返回一个随机双精度型值。
        System.out.println(random.nextGaussian());//返回一个概率密度为高斯分布的双精度值。
    }

    @Test
    public void testNumber() {
        /*BigInteger*/
        System.out.println(new BigInteger("4").add(new BigInteger("2")));//做加法运算。
        System.out.println(new BigInteger("4").subtract(new BigInteger("2")));//做减法运算。
        System.out.println(new BigInteger("4").multiply(new BigInteger("2")));//做乘法运算。
        System.out.println(new BigInteger("4").divide(new BigInteger("2")));//做除法运算。
        System.out.println(new BigInteger("4").remainder(new BigInteger("2")));//做取余操作。
        System.out.println(Arrays.toString(new BigInteger("4").divideAndRemainder(new BigInteger("2"))));//用数组返回余数和商，结果数组中第一个值为商，第二个值为余数。
        System.out.println(new BigInteger("4").pow(2));//进行取参数的exponent次方操作。
        System.out.println(new BigInteger("4").negate());//取相反数。
        System.out.println(new BigInteger("4").shiftLeft(2));//将数字左移n位，如果n为负数，做右移操作。
        System.out.println(new BigInteger("4").shiftRight(2));//将数字右移n位，如果n为负数，做左移操作。
        System.out.println(new BigInteger("4").and(new BigInteger("2")));//做与操作。
        System.out.println(new BigInteger("4").or(new BigInteger("2")));//做或操作。
        System.out.println(new BigInteger("4").compareTo(new BigInteger("2")));//做数字比较操作。
        System.out.println(new BigInteger("4").equals(new BigInteger("2")));//当参数x是BigInteger类型的数字并且数值相等时，返回true。
        System.out.println(new BigInteger("4").min(new BigInteger("2")));//返回较小的数值。
        System.out.println(new BigInteger("4").max(new BigInteger("2")));//返回较大的数值。

        /*BigDecimal*/
        System.out.println(new BigDecimal(0.00));//实例化时将双精度型转换为BigDecimal类型。
        System.out.println(new BigDecimal("0.00"));//实例化时将字符串形式转换为BigDecimal类型。
        System.out.println(new BigDecimal(4.0).add(new BigDecimal(2.0)));//做加法操作。
        System.out.println(new BigDecimal(4.0).subtract(new BigDecimal(2.0)));//做减法操作。
        System.out.println(new BigDecimal(4.0).multiply(new BigDecimal(2.0)));//做乘法操作。
        System.out.println(new BigDecimal(4.0).divide(new BigDecimal(2.0), 2, BigDecimal.ROUND_DOWN));//做除法操作，方法中3个参数分别代表除数、商的小数点后的位数、近似处理模式。
    }

}
