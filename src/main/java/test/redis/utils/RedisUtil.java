package test.redis.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author zhangxuan
 * @date 2018/10/26
 * @time 19:57
 */

public final class RedisUtil {

    //Redis 服务器 IP
    private static String ADDR = "192.168.1.224";

    //Redis 的端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = "123456";

    //可用连接实例的最大数目， 默认值为 8；
    //如果赋值为-1， 则表示不限制； 如果 pool 已经分配了 maxActive 个 jedis 实例，
    // 则此时 pool 的状态为 exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;

    //控制一个 pool 最多有多少个状态为 idle(空闲的)的 jedis 实例， 默认值也是 8。
    private static int MAX_IDLE = 200;

    //等待可用连接的最大时间， 单位毫秒， 默认值为-1， 表示永不超时。 如果超过等待时间， 则直接抛出 JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;

    //在 borrow 一个 jedis 实例时， 是否提前进行 validate 操作； 如果为 true， 则得到的 jedis 实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * 初始化 Redis 连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT,
                    AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*** 获取 Jedis 实例*
     @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放 jedis 资源* @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.close();
        }
    }
}