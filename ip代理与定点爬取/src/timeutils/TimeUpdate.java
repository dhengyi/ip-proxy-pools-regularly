package timeutils;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by paranoid on 17-4-13.
 */

public class TimeUpdate {
    public void go() throws Exception {
        // 首先，必需要取得一个Scheduler的引用(设置一个工厂)
        SchedulerFactory sf = new StdSchedulerFactory();

        //从工厂里面拿到一个scheduler实例
        Scheduler sched = sf.getScheduler();

        //真正执行的任务并不是Job接口的实例，而是用反射的方式实例化的一个JobDetail实例
        JobDetail job = newJob(MyTimeJob.class).withIdentity("job1", "group1").build();
        // 定义一个触发器，job 1将每隔执行一次
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").
                withSchedule(cronSchedule("30 04 18 * * ?")).build();

        //执行任务和触发器
        Date ft = sched.scheduleJob(job, trigger);

        //格式化日期显示格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(job.getKey() + " 已被安排执行于: " + sdf.format(ft) + "，" +
                "并且以如下重复规则重复执行: " + trigger.getCronExpression());

        sched.start();
    }

    public static void main(String[] args) throws Exception {
        TimeUpdate test = new TimeUpdate();
        test.go();
    }
}
