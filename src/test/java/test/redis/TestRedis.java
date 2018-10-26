package test.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author zhangxuan
 * @date 2018/10/26
 * @time 15:53
 */

public class TestRedis {

    @Test
    public void test01() {
        Jedis jedis = new Jedis("192.168.1.224", 6379);
        jedis.auth("123456");
        jedis.set("idea","hello redis");
        System.out.println(jedis.get("idea"));
    }
}
