import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * @author issuser
 * @date 2019-08-16 22:35
 */
public class ArrayTest {

    public static void main(String[] args) {
        /*1一维数组的使用 方式一先声明后初始化 方式二声明的同时初始化*/
        int[] array1 = new int[3];
        array1[0] = 1;
        int[] array2 = new int[]{1, 12, 3, 14, 9};
        /*2二维数组的使用 同理 二维数组的长度表示行 如下三行四列*/
        int[][] array3 = new int[3][4];
        array3[0][0] = 123;
        int[][] array4 = {{1}, {2, 3}, {4, 5, 6}};
        System.out.println(array4.length);

        /*3,数组的遍历*/
        for (int i = 0; i < array4.length; i++) {
            for (int j = 0; j < array4[i].length; j++) {
                System.out.println(array4[i][j]);
            }
        }
        /*4.数组的填充 Arrays是数组的工具类*/
        Arrays.fill(array1, 8);
        for (int x : array1) {
            System.out.println(x);
        }
        /*5.数组的排序 默认从小到大排列*/
        Arrays.sort(array2);
        System.out.println(Arrays.toString(array2));
        /*6.数组的复制*/
        int[] intsCopy = Arrays.copyOf(array2, 10);
        System.out.println(Arrays.toString(intsCopy));
        System.arraycopy(array2, 0, intsCopy, 3, 5);
        System.out.println(Arrays.toString(array2));
        /*7.数组值的查找 找到则返回索引值否则返回-1*/
        System.out.println(Arrays.binarySearch(array2, 2));

    }

    @Test
    public void testArrays() {
        /*数组排序*/
        Arrays.sort(new int[]{1, 6, 0, 2, 4, 7, 3});
        /*数组比较*/
        System.out.println(Arrays.equals(new int[]{1}, new int[1]));
        /*数组填充 10个长度的数组全部填充为3*/
        Arrays.fill(new int[10], 3);
    }

    @Test
    public void testSort() {
        int[] sortArray = new int[20];
        for (int i = 0; i < 20; i++) {
            int random = (int) (Math.random() * 100);
            sortArray[i] = random;
        }
        int[] tempArray = Arrays.copyOf(sortArray, sortArray.length);
        System.out.println(Arrays.toString(sortArray));
        System.out.println(Arrays.toString(bubbleSort(sortArray)));
        System.out.println(Arrays.toString(tempArray));
        System.out.println(Arrays.toString(selectSort(tempArray)));
        System.out.println(Arrays.toString(reverseSort(sortArray)));
        System.out.println(Arrays.toString(revertSort(sortArray)));
    }

    /*冒泡排序*/
    public static int[] bubbleSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j < array.length - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    /*直接选择排序*/
    public static int[] selectSort(int[] array) {
        int index = 0;
        for (int i = 1; i < array.length; i++) {
            for (int j = 0; j <= array.length - i - 1; j++) {
                if (array[j] > array[index]) {
                    index = j;
                }
            }
            int temp = array[array.length - i];
            array[array.length - i] = array[index];
            array[index] = temp;
        }
        return array;
    }

    /*反转排序*/
    public static int[] reverseSort(int[] array) {
        int[] arrays = new int[array.length];
        Stack stack = new Stack();
        for (int i : array) {
            stack.push(i);
        }
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = new Integer(stack.pop().toString());
        }
        return arrays;
    }

    /*反转排序2*/
    public static int[] revertSort(int[] array) {
        int temp = 0;
        int length = array.length;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[i];
            array[i] = array[length - 1 - i];
            array[length - 1 - i] = temp;
        }
        return array;
    }
}
