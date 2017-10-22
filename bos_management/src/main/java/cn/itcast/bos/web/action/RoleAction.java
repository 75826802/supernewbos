package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.base.RoleService;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	//角色列表显示
	@Autowired
	private RoleService roleService;
	@Action(value="role_list",results={@Result(name="success",type="json")})
	public String save(){
		List<Role> list=roleService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
	//添加角色
	  //属性驱动
	private String[] permssionIds;
	private String menuIds;
	@Action(value="role_save",results={@Result(name="success",type="redirect",location="pages/system/role.html")})
	public String add(){
		roleService.saveSole(model,permssionIds,menuIds);
		return SUCCESS;
	}
}
