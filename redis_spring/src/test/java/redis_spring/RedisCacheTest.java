package redis_spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.redis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-cache.xml")
public class RedisCacheTest {

	@Autowired
	private UserService userService;

	@Test
	public void testFindAll() {
		System.out.println(userService.findAllUsers());
		System.out.println("-------------------------------");
		userService.saveUser();
		System.out.println(userService.findAllUsers());
	}
}
