package base.course3;
// 显示引入HashMap类 它是java的工具类 它是字典类型key-value映射关系
/**
 * Hash是一种算法 可以保证数据不重复
 * HashMap是java的常见的存放数据的容器 key-value 键值对 存放字典 查询方便
 * 不允许key为null map.put(null,null) 是错误的写法
 * HashSet是不允许存放重复的数据 去重 是线程安全的 效率比较低
 * 线程安全就是买东西 排队 线程不安全就是抢东西 无秩序 无法管理
 * LinkdedList 链表 线性的数据结构
 * Array  数组  线性的数据结构 高一的 数学 集合 需要声明长度 是可以重复的
 * ArrayList 动态数据  存放的个数 不需要声明
 * Set 数组 不可以重复的数组
 * Map 字典 键值对
 */

import java.util.HashMap;

/**
 * java 8种 基本的数据类型
 * <p>
 * java 需要显示的声明数据是什么类型 特点  方便阅读 维护  缺点 繁琐
 * python 不需要显示声明数据类型  特点 灵活 对于开发人员的要求较高  缺点 大项目 难于维护
 * <p>
 * byte 字节 一个英文2个字节 一个中文 3个字节 1k=1024字节
 * short  短整型 -2^5 -- +2^5
 * int 整型 -2^16 -- +2^16(最常用)
 * long 长整型 -2^32 --  +2^32 >>>>>>>bigdecimal 大数字 处理其他的情况 主要是金融业 精度要求高 小数位数比较多
 * float 单精度 小数
 * double 双精度 小数 (最常用)
 * char 字符串 比如 A a \n 换行 \t 制表符 一个tab键
 * boolean 布尔值 true or false (最常用)
 * <p>
 * String  字符串 不是基本的数据类型 (最常用)
 * ---------------------
 * 类型转换
 * 基本数据类型 转换为 包装类型
 * char-->
 * 自动转换：byte-->short-->int-->long-->float-->double
 * short a = 1;
 * int b = (int)a; 省略 (int)
 * 强制转换：①会损失精度，产生误差，小数点以后的数字全部舍弃。②容易超过取值范围。
 * int a = (int)1.3; a = 1;精度丢失 不会执行四舍五入
 * 自动装箱 自动拆箱
 * int 基本数据类型 是数值 Integer 包装类型 是类
 * 如下所示
 * byte ----Byte
 * short --- Short
 * int --- Integer
 * long --- Long
 * float --- Float
 * double --- Double
 * char --- Character
 * boolean --- Boolean
 * <p>
 * Integer a = 123;自动转换为int a = 123;
 */
// 公共的类 JavaType
public class JavaType {

    //main方法 程序的入口
    public static void main(String[] args) {

        //声明常量PI与变量R

        //final 是关键字 关键字不能作为变量名称 和常量名称使用 特殊的 不能重名
        //final 修饰的量是常量 不可以被修改 和赋值 公认的
        //final 修饰的方法不能被重写 方法就是行为 重写是一个方法的不同表现形式 例如 叫 喵喵叫 汪汪叫
        //final 修饰的类不能被继承 不能有儿子 String 就是final的 限制用户任意继承

        //常量不能被修改
        //变量可以被任意修改 临时存放 根据用户需要
        final double PI = 3.14; //定义常量
        int R = 5;  //定义变量
        System.out.println("圆周率的面积：" + PI * R * R);

        System.out.println((int) 1.9);
        //基本的数据类型
        short s = 3;  //定义short类型变量s
        int i = 22;   //定义int类型变量
        float f = 1.23f;  //定义float类型变量
        char a = 'a';  //定义字符类型用char
        String string = "hello world"; //定义字符串类型String
        boolean status = true;  //定义布尔类型类型用boolean

        //声明一个数组 数组存放的类型是字符串 数组只能存放3个长度的字符串
        String[] fruits = new String[3];
        //数组下标从0开始 依次赋值
        fruits[0] = "apple";
        fruits[1] = "banana";
        fruits[2] = "grape";

        //声明字符串字典 hashMap<string,string> 存放的key类型是String value类型是String
        //key-- value 键值对
        HashMap<String, String> hm
                = new HashMap<String, String>();
        //添加字典
        hm.put("Jim", "1155689");
        hm.put("Jane", "1255669");
        hm.put("kevin", "1165669");
        //获取Jim对应的值
        System.out.println(hm.get("Jim"));

//        key的数据类型是String value为Integer 包装类
//        下面的案例 目的 用户可以根据自己的实际需要存放不同类型的数据在HashMap里面
//        key ---value类型  根据需要自己调整
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        //自动装箱 数字类型1 自动装箱为 整型类1 后台自动转换
        map.put("tom", 1);

        //声明一个字符串类型的变量name 并赋值为alen
        String name = "alen";
        //声明一个整型的变量age 并赋值为22
        int age = 22;
        //通过person方法来获取个人信息

        //------------------------static or no static begin
        String p = person(name, age);
        System.out.println(p);

        JavaType j = new JavaType();
        String p2 = j.person2(name, age);
        System.out.println(p2);
        //------------------------static or no static end


        //自动装箱自动拆箱
        //String不是基本的数据类型 是类
        //现在要把String的value付给aInt
        //必须使用自动拆箱 把数据拆分出来 系统自动执行
        String str = "123";
        int aInt = Integer.parseInt(str);
    }

    /**
     * 通过给定name age获取个人信息
     *
     * @param name 姓名
     * @param age  年龄
     * @return 个人信息
     */
    //声明一个公共的方法 返回String 方法名为person
    //传递2个参数 name  age 返回个人信息
    //static 表示静态的 不需要通过对象来调用
    //no static需要通过类的实例来调用
//    以下说明：公共的静态的方法，方法的返回值是字符串（string),方法名是person，方法名是为了区分别的方法
//    该方法传入了两个参数，参数的个数和数据类型，根据开发需要自己定义，方法的返回值自己定义。
    public static String person(String name, int age) {
        return "name:" + name + ",age:" + age;
    }

    public String person2(String name, int age) {
        return "name:" + name + ",age:" + age;
    }

    public void testPerson() {
        System.out.println();
//    以上，公共的方法无返回值，方法名为testPerson,参数的个数和数据类型，根据开发需要自己定义，方法的返回值自己定义。
    }

    //方法 有返回值 无返回值
    //方法 静态的 非静态的
//    参数的个数 类型 自主定义


}