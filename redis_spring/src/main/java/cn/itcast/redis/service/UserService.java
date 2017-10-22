package cn.itcast.redis.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.itcast.redis.domain.User;

@Service
public class UserService {

	@Cacheable(value = "all_users")
	public List<User> findAllUsers() {
		System.out.println("查询所有用户...");
		List<User> users = new ArrayList<User>();
		User user1 = new User();
		user1.setId(1);
		user1.setName("张三");
		users.add(user1);

		User user2 = new User();
		user2.setId(2);
		user2.setName("李四");
		users.add(user2);

		return users;
	}

	@CacheEvict("all_users")
	public void saveUser() {
		System.out.println("保存用户...");
	}

	public List<User> findByCity(String city) {
		if ("北京".equals(city)) {
			List<User> users = new ArrayList<User>();
			User user1 = new User();
			user1.setId(1);
			user1.setName("Jame");
			users.add(user1);

			User user2 = new User();
			user2.setId(2);
			user2.setName("Jor");
			users.add(user2);
			return users;
		} else if ("上海".equals(city)) {
			List<User> users = new ArrayList<User>();
			User user1 = new User();
			user1.setId(3);
			user1.setName("Will");
			users.add(user1);

			User user2 = new User();
			user2.setId(4);
			user2.setName("Wade");
			users.add(user2);
			return users;
		}
		return null;
	}
}
