package util;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        instance.add(Calendar.WEDNESDAY, -1);
        // 调整到上周1
        instance.set(Calendar.DAY_OF_WEEK, 2);
        //循环打印
        for (int i = 1; i <= 5; i++) {
            System.out.println("星期" + i + ":" + format.format(instance.getTime()));
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
            instance.add(Calendar.WEDNESDAY, -i);
            // 调整到上周1
            instance.set(Calendar.DAY_OF_WEEK, i);
            //循环打印
            for (int j = 1; j <= 5; j++) {
                System.out.println("星期" + j + ":" + format.format(instance.getTime()));
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

    public static List<String> getDate(int year) {
        List<String> list = new ArrayList<>();
        boolean flag = false;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            flag = true;
        }
        String start = "01";
        String end = "";
        String month = "";
        for (int i = 1; i <= 12; i++) {
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                end = "31";
            } else if (i == 4 || i == 6 || i == 9 || i == 11) {
                end = "30";
            } else {
                if (flag) {
                    end = "29";
                } else {
                    end = "28";
                }
            }
            month = i + "";
            if (i <= 9) {
                month = "0" + i;
            }
            String info = year + "-" + month + "-" + start + "\t" + year + "-" + month + "-" + end;
            list.add(info);
            System.out.println(info);
        }
        return list;
    }

    public static String convertDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy年MM月dd日").parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDate2(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy/MM/dd").parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getYesterday() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = (Date) calendar.getTime();
        return df.format(date);
    }

    public static int getWeekOfYear(String today) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getWeek(String dates) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = f.parse(dates);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0) {
            w = 7;
        }
        return w;
    }

    public static int getYear(String dates){
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = f.parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.YEAR);
    }
}
