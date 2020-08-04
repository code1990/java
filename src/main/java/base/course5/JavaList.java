package base.course5;

/**
 * 数据结构
 *
 * 堆 heap 存放类的实例 即为 对象
 * 栈 stack 容器  先进后出 即为数据
 * 队列 queue 排队 先进先出
 *
 * 线性和非线性
 * 线性 存储连续 数组 堆栈
 * 非线性 tree graph 树 和图 字典
 *
 * Array 数组 集合
 * Set 不可以重复的集合
 * ArrayList 动态数组
 * String 字符串
 * StringBuffer 动态字符串
 * HashMap 字典 键值对
 *
 * 根据算法不同
 * HashMap HashSet 基于Hash算法实现 hash算法可以保证数据唯一不重复
 * LinkedList 基于链表 链表分为 数据域和指针域 指针指向下一个变量
 * ArrayList  基于数组实现
 */
public class JavaList {

    public static void main(String[] args) {

        String[] group = new String[5];
        group[0] = "小明";
        group[1] = "老师";
        group[2] = "老王";
        group[3] = "张三";
        group[4] = "李四";

        //创建数组的方式 如下2种方式
        //String[] array1 = new String[3];
        //String[] array2 = {1,2,3},
        String[] fruits = {"bananas", "apples", "pears", "oranges"};
        for (int i = 0; i < group.length; i++) {
            System.out.println(group[i]);
        }
    }
}
