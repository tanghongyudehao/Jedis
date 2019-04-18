import org.junit.Test;
import redis.clients.jedis.*;

import java.util.LinkedList;
import java.util.List;

public class TestJedis {
    @Test
    public void testJedis(){
        JedisShardInfo jedisShardInfo = new JedisShardInfo("114.116.5.129",6381);
        jedisShardInfo.setPassword("123456");

        Jedis jedis = new Jedis(jedisShardInfo);

        jedis.set("tangtang","hongyu");
    }

    @Test
    public void testJedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        // 集群
        JedisShardInfo jedisShardInfo1 = new JedisShardInfo("114.116.5.129",6381);
        jedisShardInfo1.setPassword("123456");
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(jedisShardInfo1);
        ShardedJedisPool pool = new ShardedJedisPool(config, list);
        ShardedJedis jedis = null;
        try{
             jedis = pool.getResource();
            jedis.set("pool","唐泓宇");
            System.out.println(jedis.get("pool"));
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
