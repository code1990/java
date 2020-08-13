package util;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author 911
 * @date 2020-08-08 15:31
 */
public class DateUtil {

    public static void main(String[] args) {

        SimpleDateFormat sdf = new SimpleDateFormat("y年M月d日 E H时m分s秒", Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //当前时间，貌似多余，其实是为了所有可能的系统一致
        calendar.setTimeInMillis(System.currentTimeMillis());
        System.out.println("当前时间:" + sdf.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println("周一时间:" + sdf2.format(calendar.getTime()));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        System.out.println("周一时间:" + sdf2.format(calendar.getTime()));
    }

    @Test
    public void getInfo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 当前日期
        Calendar instance = Calendar.getInstance();
        // 调整到上周
        instance.add(Calendar.WEDNESDAY,  - 1);
        // 调整到上周1
        instance.set(Calendar.DAY_OF_WEEK, 2);
        //循环打印
        for (int i = 1; i <= 5; i++) {
            System.out.println("星期"  + i + ":" + format.format(instance.getTime()));
            instance.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    @Test
    public void getInfo2() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 当前日期
        Calendar instance = Calendar.getInstance();
        for (int i = 1; i < 100; i++) {
            // 调整到上周
            instance.add(Calendar.WEDNESDAY,  - i);
            // 调整到上周1
            instance.set(Calendar.DAY_OF_WEEK, i);
            //循环打印
            for (int j = 1; j <= 5; j++) {
                System.out.println("星期"  + j + ":" + format.format(instance.getTime()));
                instance.add(Calendar.DAY_OF_WEEK, 1);
            }
        }
//        for (int i = 1; i < 100; i++) {
//            // 调整到上周
//            instance.add(Calendar.WEDNESDAY,  + i);
//            // 调整到上周1
//            instance.set(Calendar.DAY_OF_WEEK, 2);
//            //循环打印
//            for (int j = 1; j <= 5; j++) {
//                System.out.println("星期"  + j + ":" + format.format(instance.getTime()));
//                instance.add(Calendar.DAY_OF_WEEK, 1);
//            }
//        }
    }

}
