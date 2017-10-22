package cn.itcast.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.base.PermissionService;
import cn.itcast.bos.service.base.RoleService;
import cn.itcast.bos.service.base.UserService;

public class BosRealm extends AuthorizingRealm {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;
	
	@Override
	// 授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		System.out.println("shiro 授权");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//根据当前登录用户查询对应角色和权限
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		//调用业务层,查看角色
		List<Role> roles=roleService.findByUser(user);
		for (Role role : roles) {
			authorizationInfo.addRole(role.getKeyword());
		}
		//调用业务层,查看权限
		List<Permission> permissions = permissionService.findByUser(user);
		for (Permission permission : permissions) {
			authorizationInfo.addStringPermission(permission.getKeyword());;
		}
		return authorizationInfo;
	}

	@Override
	// 认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		System.out.println("shiro 认证管理");
		// token向下转型
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		//根据用户名查询用户信息
		User user = userService.findByUsername(upToken.getUsername());
		if (user==null) {
			//用户名不存在
			return null;
		}else{
			//用户名存在
			//第一个参数保存在subject中的信息,第二个参数如果返回null说明用户不存在,报用户名,第三个参数realm名称
			return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
		}
	
	}

}
