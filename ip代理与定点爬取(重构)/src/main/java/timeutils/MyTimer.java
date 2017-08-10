package timeutils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by paranoid on 17-4-13.
 */

public class MyTimer {
    public static void main(String[] args) {
        MyTimeJob job = new MyTimeJob();
        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        //设置定时任务，从现在开始，每24小时执行一次
        timer.schedule(job, date, 24*60*60*1000);
    }
}
