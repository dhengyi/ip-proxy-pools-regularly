package htmlparse;

import IPModel.IPMessage;
import ipfilter.IPFilter;
import ipfilter.IPUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by hg_yi on 17-8-3.
 */
public class IPPool {
    //成员变量（非线程安全）
    private List<IPMessage> ipMessages;

    public IPPool(List<IPMessage> ipMessages) {
        this.ipMessages = ipMessages;
    }

    public void getIP(List<String> urls) {
        String ipAddress;
        String ipPort;

        for (int i = 0; i < urls.size(); i++) {
            /** 随机挑选代理IP(仔细想了想，本步骤由于其他线程有可能在位置确定之后对ipMessages数量进行
             * 增加，虽说不会改变已经选择的ip代理的位置，但合情合理还是在对共享变量进行读写的时候要保证
             * 其原子性，否则极易发生脏读)
             */
            //每个线程先将自己抓取下来的ip保存下来并进行过滤与检测
            List<IPMessage> ipMessages1 = new ArrayList<>();
            String url = urls.get(i);

            synchronized (ipMessages) {
                int rand = (int) (Math.random()*ipMessages.size());
                out.println("当前线程 " + Thread.currentThread().getName() + " rand值: " + rand +
                        " ipMessages 大小: " + ipMessages.size());

                ipAddress = ipMessages.get(rand).getIPAddress();
                ipPort = ipMessages.get(rand).getIPPort();
            }

            //这里要注意Java中非基本类型的参数传递方式，实际上都是同一个对象
            boolean status = URLFecter.urlParse(url, ipAddress, ipPort, ipMessages1);
            //如果ip代理池里面的ip不能用，则切换下一个IP对本页进行重新抓取
            if (status == false) {
                i--;
                continue;
            } else {
                out.println("线程：" + Thread.currentThread().getName() + "已成功抓取 " +
                url + " ipMessage1：" + ipMessages1.size());
            }

            //对ip重新进行过滤，只要速度在两秒以内的并且类型为HTTPS的
            ipMessages1 = IPFilter.Filter(ipMessages1);

            //对ip进行质量检测，将质量不合格的ip在List里进行删除
            IPUtils.IPIsable(ipMessages1);

            //将质量合格的ip合并到共享变量ipMessages中，进行合并的时候保证原子性
            synchronized (ipMessages) {
                out.println("线程" + Thread.currentThread().getName() + "已进入合并区 " +
                "待合并大小 ipMessages1：" + ipMessages1.size());
                ipMessages.addAll(ipMessages1);
            }
        }
    }
}
