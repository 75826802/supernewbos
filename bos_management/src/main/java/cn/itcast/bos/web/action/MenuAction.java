package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.base.MenuService;

@Controller
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
	//菜单列表显示
	@Autowired
	private MenuService menuService;
	@Action(value="menu_list",results={@Result(name="success",type="json")})
	public String list(){
		List<Menu> list=menuService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
	//添加菜单
	@Action(value="menu_save",results={@Result(name="success",type="redirect",location="pages/system/menu.html")})
	public String menuAdd(){
		menuService.save(model);
		return SUCCESS;
	}
	//根据登陆的不同用户动态生成菜单
	@Action(value="menu_showmenu",results={@Result(name="success",type="json")})
	public String findByUser(){
		//获取当前登陆用户
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		List<Menu> menu=menuService.findByUser(user);
		ActionContext.getContext().getValueStack().push(menu);
		return SUCCESS;
	}
}
