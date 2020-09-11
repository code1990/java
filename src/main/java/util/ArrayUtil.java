package util;

import java.util.Arrays;

/**
 * @author 911
 * @date 2020-08-26 11:29
 */
public class ArrayUtil {
    /**
     * 程序的入口main方法
     * @param args
     */
    public static void main(String[] args) {
        String[] strs = { "yw", "sun", "xiao" };
        swarp(strs, 0, 2);
    }
    /**
     * 将传入的任意数组的任意的两个位置进行交换
     * @param t
     * @param i
     * @param j
     */
    public static <T> void swarp(T[] t, int i, int j) {
        System.out.println(">>>>>>>>>>交换前"+Arrays.toString(t));
        T temp = t[i];
        t[i] = t[j];
        t[j] = temp;
        System.out.println(">>>>>>>>>>交换后"+Arrays.toString(t));
    }
}
