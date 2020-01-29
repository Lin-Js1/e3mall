package cn.e3mall.jedis;

import cn.e3mall.common.jedis.JedisClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

public class JedisTest {

    @Test
    public void testJedis()throws Exception{
        //创建一个连接Jedis对象，参数：host、port
        Jedis jedis = new Jedis("192.168.25.129",6379);
        //直接使用jedis操作redis，所有jedis命令都对应一个方法
        jedis.set("test123","my first jedis test");
        String string = jedis.get("test123");
        System.out.println(string);
        //关闭连接
        jedis.close();
    }

    @Test
    public void testJedisPool()throws Exception{
        //创建一个连接池对象，两个参数host，port
        JedisPool jedisPool = new JedisPool("192.168.25.129",6379);
        //从连接池获得一个连接，就是一个jedis对象
        Jedis jedis = jedisPool.getResource();
        //使用jedis操作redis
        String string = jedis.get("test123");
        System.out.println(string);
        //关闭连接，每次使用完毕后关闭连接，连接池回收资源
        jedis.close();
        //关闭连接池
        jedisPool.close();
    }

    @Test
    public void testJedisClient()throws Exception{
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        //从容器中获得JedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("mytest","jedisClient");
        String s = jedisClient.get("mytest");
        System.out.println(s);
    }
}
