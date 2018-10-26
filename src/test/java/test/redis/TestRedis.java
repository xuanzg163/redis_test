package test.redis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import redis.clients.jedis.Jedis;
import test.redis.utils.RedisUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangxuan
 * @date 2018/10/26
 * @time 15:53
 */

public class TestRedis {

    private Jedis jedis;

    @Before
    public void init() {
         jedis = RedisUtil.getJedis();
    }

    @Test
    public void test01() {
        Jedis jedis = new Jedis("192.168.1.224", 6379);
        jedis.auth("123456");
        jedis.set("idea","hello redis");
        System.out.println(jedis.get("idea"));
    }

    @Test
    public void test02() {
        jedis.set("miku","hatunne ");
        jedis.append("miku","miku");
        System.out.println(jedis.get("miku"));

        System.out.println("============");
        jedis.mset("name1","miku","name2","luka","name3","rin");
        List<String> list = jedis.mget("name1", "name2", "name3");

        list.forEach(t-> System.out.println(t));

        System.out.println("分割线");
    }

    @Autowired
    private RedisTemplate<String,Object> template;

    @Test
    public void test03() {
        ValueOperations<String, Object> stringOperations
                = template.opsForValue();
        stringOperations.set("str", "string 数据类型");
        System.out.println(stringOperations.get("str"));
    }

}
