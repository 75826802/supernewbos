package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.UserRepository;
import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository repository;
	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username);

	}
	@Override
	public List<User> findAll() {
		
		return repository.findAll();
	}
	@Override
	public void save(User user, String[] roleIds) {
		//保存用户
		repository.save(user);
		//赋予角色
		if (roleIds!=null) {
			for (String roleId : roleIds) {
				Role role = roleRepository.findOne(Integer.parseInt(roleId));
				user.getRoles().add(role);
			}
		}
		
	}

}
