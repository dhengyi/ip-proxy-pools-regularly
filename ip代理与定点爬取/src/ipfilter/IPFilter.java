package ipfilter;

import IPModel.IPMessage;

import java.util.*;

import static java.lang.System.loadLibrary;
import static java.lang.System.out;

/**
 * Created by paranoid on 17-4-14.
 * 对于Java已经规定的常用的类如String我们不可能对它进行重新编译，在不能使用Comparable
 * 的情况下我们需要自己操作Comparator，重新定义它的compare方法.
 *
 * String的compareTo方法自动升序排列.
 */

public class IPFilter {
    //对IP进行过滤，选取1000个IP中速度排名前六百的IP(升序)，其余的舍弃
    public static List<IPMessage> Filter(List<IPMessage> list) {
        List<IPMessage> newlist = new ArrayList<>();

        Collections.sort(list, new Comparator<IPMessage>() {
            @Override
            public int compare(IPMessage o1, IPMessage o2) {
                return o1.getIPSpeed().compareTo(o2.getIPSpeed());
            }
        });

        //只返回容器中前100的对象
        for(int i = 0; i < list.size(); i++) {
            if(i < 100) {
                newlist.add(list.get(i));
            }else {
                break;
            }
        }

        return newlist;
    }
}
