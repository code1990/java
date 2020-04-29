package helloworld;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author issuser
 * @date 2019-08-24 13:40
 * <p>
 * 一个对象如果要使用sort排序必须实现compareable接口 重写compareTo方法
 * comparator比较器  单独定义比较器类 意味着需要的时候才会定义比较器
 * compareable接口要求类定义 的时候是先该接口
 */

public class CompareTest implements Comparable<CompareTest> {

    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(CompareTest o) {
        if (this.price > o.price) {
            return 1;
        } else if (this.price < o.price) {
            return -1;
        } else {
            return 0;
        }
    }

    public CompareTest(double price) {
        this.price = price;
    }

    @Test
    public void testSort() {
        Arrays.sort(new CompareTest[]{new CompareTest(20.12), new CompareTest(10.12), new CompareTest(60.12)});
    }
}

class CompareTestComparator implements Comparator<CompareTest> {
    @Override
    public int compare(CompareTest o1, CompareTest o2) {
        if (o1.getPrice() > o2.getPrice()) {
            return 1;
        } else if (o1.getPrice() < o2.getPrice()) {
            return -1;
        } else {
            return 0;
        }

    }
}