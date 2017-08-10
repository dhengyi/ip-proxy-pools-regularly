package timeutils;

import IPModel.IPMessage;
import database.MyRedis;
import htmlparse.IPPool;
import htmlparse.IPThread;
import htmlparse.URLFecter;
import ipfilter.IPFilter;
import ipfilter.IPUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static java.lang.System.out;

/**
 * Created by paranoid on 17-4-13.
 *
 * ip代理池里面最少保存200个代理ip
 *
 * 多线程主要考虑的就是合理的任务分配以及线程安全性。
 *
 * implements Job
 */

public class MyTimeJob extends TimerTask {
    MyRedis redis = new MyRedis();

    @Override
    public void run() {
        //首先清空redis数据库中的key
        redis.deleteKey("IPPool");

        //存放爬取下来的ip信息
        List<IPMessage> ipMessages = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        //对创建的子线程进行收集
        List<Thread> threads = new ArrayList<>();

        //首先使用本机ip爬取xici代理网第一页
        ipMessages = URLFecter.urlParse(ipMessages);

        //对得到的IP进行筛选，将IP速度在两秒以内的并且类型是https的留下，其余删除
        ipMessages = IPFilter.Filter(ipMessages);

        //对拿到的ip进行质量检测，将质量不合格的ip在List里进行删除
        IPUtils.IPIsable(ipMessages);

        //构造种子url(4000条ip)
        for (int i = 2; i <= 41; i++) {
            urls.add("http://www.xicidaili.com/nn/" + i);
        }

        /**
         * 对urls进行解析并进行过滤,拿到所有目标IP(使用多线程)
         *
         * 基本思路是给每个线程分配自己的任务，在这个过程中List<IPMessage> ipMessages
         * 应该是共享变量，每个线程更新其中数据的时候应该注意线程安全
         */
        IPPool ipPool = new IPPool(ipMessages);
        for (int i = 0; i < 20; i++) {
            //给每个线程进行任务的分配
            Thread IPThread = new IPThread(urls.subList(i*2, i*2+2), ipPool);
            threads.add(IPThread);
            IPThread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(IPMessage ipMessage : ipMessages){
            out.println(ipMessage.getIPAddress());
            out.println(ipMessage.getIPPort());
            out.println(ipMessage.getIPType());
            out.println(ipMessage.getIPSpeed());
        }

        //将爬取下来的ip信息写进Redis数据库中(List集合)
        redis.setIPToList(ipMessages);

        //从redis数据库中随机拿出一个IP
        IPMessage ipMessage = redis.getIPByList();
        out.println(ipMessage.getIPAddress());
        out.println(ipMessage.getIPPort());
        redis.close();
    }
}
