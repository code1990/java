package helloworld;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author issuser
 * @date 2019-08-24 13:22
 */
public class DateTest {
    @Test
    public void testDate() {
        long current = System.currentTimeMillis();
        System.out.println(current);
        Date date = new Date(current);
        System.out.println(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println(sdf.format(date));
        String str = "2019-08-24 13:26:16:553";
        try {
            Date date1 = sdf.parse(str);
            System.out.println(date1.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCalendar() {
        Calendar cal = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(Calendar.YEAR).append("-");
        /*月份从0开始*/
        sb.append(Calendar.MONTH + 1).append("-");
        sb.append(Calendar.DAY_OF_MONTH).append("-");
        sb.append(Calendar.HOUR_OF_DAY).append(":");
        sb.append(Calendar.MINUTE).append(":");
        sb.append(Calendar.SECOND);
        System.out.println(sb.toString());
    }
}
