package ipfilter;

import IPModel.IPMessage;

import java.util.*;

import static java.lang.System.out;

/**
 * Created by paranoid on 17-4-14.
 * 对得到的IP进行筛选，将IP速度在两秒以内的并且类型是https的留下，其余删除
 */

public class IPFilter {
    //对IP进行过滤
    public static List<IPMessage> Filter(List<IPMessage> ipMessages1) {
        List<IPMessage> newIPMessages = new ArrayList<>();

        for (int i = 0; i < ipMessages1.size(); i++) {
            String ipType = ipMessages1.get(i).getIPType();
            String ipSpeed = ipMessages1.get(i).getIPSpeed();

            ipSpeed = ipSpeed.substring(0, ipSpeed.indexOf('秒'));
            double Speed = Double.parseDouble(ipSpeed);

            if (ipType.equals("HTTPS") && Speed <= 2.0) {
                newIPMessages.add(ipMessages1.get(i));
            }
        }

        return newIPMessages;
    }
}
