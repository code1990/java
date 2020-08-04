package base.course6;

/**
 * 排序算法
 * 1.冒泡排序
 * 2.直接选择排序
 * 3.希尔排序
 * 4.堆排序
 *
 * 冒泡排序 两两比较 交换彼此位置 是数据的结果为有序的 为从小到大 或者从大到小
 * 交换次数 n(n-1)/2
 */
public class BubbleSort {

    public static void main(String[] args) {

        //排序数组
        int[] intArray = {12, 11, 45, 6, 4, 8, 43, 40, 57, 3, 22};

        System.out.println("排序前的数组:");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + " ");
        }
        System.out.println();

        // 冒泡排序
        int temp;
        for (int i = 0; i < intArray.length; i++) {

            for (int j = i + 1; j < intArray.length; j++) {

                //当后一个数大于前一个，交换位置
                if (intArray[i] < intArray[j]) {
                    temp = intArray[i];
                    intArray[i] = intArray[j];
                    intArray[j] = temp;
                }
            }
        }

        System.out.println("排序后的数组:");
        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + " ");
        }
        System.out.println();
    }
}
