package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.base.UserService;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	@Autowired
	private UserService userService;
	@Action(value = "user_login", results = { @Result(name = "success", type = "redirect", location = "index.html"),
			@Result(name = "login", type = "redirect", location = "login.html") })
	public String login() {
		//用户名和密码都已被封装在model中
		//基于shiro实现登陆
		//subject :shiro管理的用户
		Subject subject = SecurityUtils.getSubject();
		//用户名和密码信息加入authentication中认证
		AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),model.getPassword());
		try {
			subject.login(token);
			//登陆成功,将用户信息存入session中
			return SUCCESS;
		} catch (Exception e) {
			//登陆失败
			e.printStackTrace();
			return LOGIN;
		}
		
		
	}
	
	//用户注销退出系统
	@Action(value="user_logout",results={@Result(name="success",type="redirect",location="login.html")})
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return SUCCESS;
	}
	
	//用户列表显示
	@Action(value="user_list",results={@Result(name="success",type="json")})
	public String list(){
		List<User> list=userService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	//用户的添加
		//属性驱动
	private String[] roleIds;
	@Action(value="user_save",results={@Result(name="success",type="redirect",location="pages/system/userlist.html")})
	public String save(){
		userService.save(model,roleIds);
		return SUCCESS;
	}
}
