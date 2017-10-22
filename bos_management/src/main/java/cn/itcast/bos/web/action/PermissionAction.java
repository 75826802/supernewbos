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

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.base.PermissionService;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission> {

	@Autowired
	private PermissionService permissionService;
	//权限列表显示
	@Action(value="permission_list",results={@Result(name="success",type="json")})
	public String permissionList(){
		List<Permission> list=permissionService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return "success";
	}
	
	//全线数据添加
	@Action(value="permission_add",results={@Result(name="success",type="redirect",location="pages/system/permission.html")})
	public String save(){
		permissionService.save(model);
		return "success";
	}
	
}
