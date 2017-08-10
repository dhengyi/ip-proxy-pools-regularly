package database;

import IPModel.IPMessage;
import IPModel.SerializeUtil;
import redis.clients.jedis.Jedis;

import java.util.List;

import static java.lang.System.out;

/**
 * Created by hg_yi on 17-8-9.
 */
public class MyRedis {
    Jedis jedis = RedisDB.getJedis();

    //将ip信息保存在Redis列表中
    public void setIPToList(List<IPMessage> ipMessages) {
        for (IPMessage ipMessage : ipMessages) {
            //首先将ipMessage进行序列化
            byte[] bytes = SerializeUtil.serialize(ipMessage);

            jedis.rpush("IPPool".getBytes(), bytes);
        }
    }

    //将Redis中保存的对象进行反序列化
    public IPMessage getIPByList() {
        int rand = (int)(Math.random()*jedis.llen("IPPool"));

        Object o = SerializeUtil.unserialize(jedis.lindex("IPPool".getBytes(), 0));
        if (o instanceof IPMessage) {
            return (IPMessage)o;
        } else {
            out.println("不是IPMessage的一个实例~");
            return null;
        }
    }

    public void deleteKey(String key) {
        jedis.del(key);
    }

    public void close() {
        RedisDB.close(jedis);
    }
}
