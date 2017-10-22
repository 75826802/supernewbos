package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuRepository;
import cn.itcast.bos.dao.system.PermissionRepository;
import cn.itcast.bos.dao.system.RoleRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private RoleRepository repository;
	@Override
	public List<Role> findByUser(User user) {
		if (user.equals("admin")) {
			return repository.findAll();
		}else{
			return repository.findByUser(user.getId());
		}
		
	}
	@Override
	public List<Role> findAll() {
		
		return repository.findAll();
	}
	@Override
	public void saveSole(Role role, String[] permssionIds, String menuIds) {
		//保存角色信息
		repository.save(role);
		//关联权限
		if (permssionIds!=null) {
			for (String permssionId : permssionIds) {
				Permission permssion = permissionRepository.findOne(Integer.parseInt(permssionId));
				role.getPermissions().add(permssion);
			}
		}
		//关联菜单
		if (menuIds!=null) {
			String[] menuIdArray = menuIds.split(",");
			for (String menuId : menuIdArray) {
				Menu menu = menuRepository.findOne(Integer.parseInt(menuId));
				role.getMenus().add(menu);
			}
		}
	}

}
