package cn.itcast.bos.jedis.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	@Test
	public void testRedis(){
		//连接localhost默认端口6379
		Jedis jedis = new Jedis("localhost");
		
		jedis.setex("company",30,"稻草人科技有限公司");
		
		System.out.println(jedis.get("company"));
		
	}
}
