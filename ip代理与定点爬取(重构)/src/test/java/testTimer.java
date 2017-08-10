import java.util.Calendar;
import java.util.Date;

import static java.lang.System.out;

/**
 * Created by hg_yi on 17-8-9.
 */
public class testTimer {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //设置任务开始执行时间
        calendar.set(year,month,day, 6, 0, 0);
        Date date = calendar.getTime();

        out.println(date);
    }
}
