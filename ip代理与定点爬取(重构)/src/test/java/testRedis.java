import IPModel.IPMessage;
import database.MyRedis;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by hg_yi on 17-8-9.
 */
public class testRedis {
    public static void main(String[] args) {
        List<IPMessage> ipMessages = new ArrayList<>();
//        IPMessage ipMessage = new IPMessage();

//        ipMessage.setIPAddress("175.172.212.178");
//        ipMessage.setIPPort("80");
//        ipMessage.setIPType("HTTPS");
//        ipMessage.setIPSpeed("3.837秒");
//
//        ipMessages.add(ipMessage);
//
        MyRedis redis = new MyRedis();
//        redis.setIPToList(ipMessages);
        IPMessage ipMessage = redis.getIPByList();

        out.println(ipMessage.getIPAddress());
        out.println(ipMessage.getIPPort());

        redis.close();
    }
}
