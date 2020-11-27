import org.junit.Test;
import util.TxtUtil;

import java.io.File;
import java.util.List;

/**
 * @author 911
 * @date 2020-11-16 12:59
 * <p>
 * 扭亏算法
 */
public class DaoLose {

    @Test
    public void getInfo111() {
        List<String> list = TxtUtil.readTxt(new File("lose"));
        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split("\t");
            String code = array[0];
            Double price = new Double(array[1]);
            Double num = new Double(array[2]);
            Double currentValue = new Double(array[2]);
        }
    }

    public double addMoney(double value, double price, double num) {
        //当前的损失的百分比
        double losePercent = (num * (value - price)) / (num * price);
        double x = losePercent / 0.01;
        double earnMoney = (num * price) * Math.abs(losePercent + 0.01);
        return earnMoney / (1 + 0.01) * 7;
    }

    @Test
    public void getInfo1111() {
        //当前的估值
        double value = 0.9094;
        //当前的价格
        double price = 0.9888;
        //当前的数量
        double num = 15226.50;
        //当前的损失的百分比
        double losePercent = (num * (value - price)) / (num * price);
        double x = losePercent / 0.01;
        double earnMoney = (num * price) * Math.abs(losePercent + 0.01);
        double addMoney = earnMoney / (1 + 0.01) * 7;
        System.out.println(addMoney);
    }
}
