package timeutils;

import IPModel.IPMessage;
import htmlparse.IPPool;
import htmlparse.IPThread;
import htmlparse.URLFecter;
import ipfilter.IPFilter;
import ipfilter.IPUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class MyTimeJob  {
    public static void main(String[] args) {

//    public void execute(JobExecutionContext argv) throws JobExecutionException {
        //存放爬取下来的ip信息
        List<IPMessage> ipMessages = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        //对创建的子线程进行收集
        List<Thread> threads = new ArrayList<>();

        //首先使用本机ip爬取xici代理网第一页
        ipMessages = URLFecter.urlParse(ipMessages);

        for(IPMessage ipMessage : ipMessages){
            out.println(ipMessage.getIPAddress());
            out.println(ipMessage.getIPPort());
            out.println(ipMessage.getIPType());
            out.println(ipMessage.getIPSpeed());
        }

        //对得到的IP进行筛选，将IP速度在两秒以内的并且类型是https的留下，其余删除
        ipMessages = IPFilter.Filter(ipMessages);

        //对拿到的ip进行质量检测，将质量不合格的ip在List里进行删除
        IPUtils.IPIsable(ipMessages);

        for(IPMessage ipMessage : ipMessages){
            out.println(ipMessage.getIPAddress());
            out.println(ipMessage.getIPPort());
            out.println(ipMessage.getIPType());
            out.println(ipMessage.getIPSpeed());
        }

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

        //得到所需要的数据
//        for (k = 0, j = 0; j < Urls.size(); k++) {
//            url = Urls.get(j);
//
//            IPAddress = ipMessages.get(k).getIPAddress();
//            IPPort = ipMessages.get(k).getIPPort();
//            //每次爬取前的大小
//            int preIPMessSize = ipMessages.size();
//            try {
//                ipMessages = URLFecter.urlParse(url, IPAddress, IPPort, ipMessages);
//                //每次爬取后的大小
//                int lastIPMessSize = ipMessages.size();
//                if(preIPMessSize != lastIPMessSize){
//                    j++;
//                }
//
//                //对IP进行轮寻调用
//                if (k >= ipMessages.size()) {
//                    k = 0;
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }



        //将得到的IP存储在数据库中(每次先清空数据库)
//        try {
//            DataBaseDemo.delete();
//            DataBaseDemo.add(ipMessages);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
