package cn.itcast.bos.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@ParentPackage("json-default")
@Controller
@Actions
@Namespace("/")
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	// 模型驱动
	private Standard standard = new Standard();

	@Override
	public Standard getModel() {

		return standard;
	}

	@Autowired
	private StandardService service;

	@Action(value = "standard_save", results = {
			@Result(name = "success", type = "redirect", location = "./pages/base/standard.html") })
	public String save() {
		System.out.println("添加收派标准");
		service.save(standard);
		return "success";
	}

	// 属性驱动
	private int page;

	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	//分页列表查询
	@Action(value = "standard_select", results = {
			@Result(name = "success", type = "json")})
	public String select() {
		//调用业务层查询数据结果
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> list = service.findAll(pageable);
		//返回客户端数据 需要total和rows
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", list.getTotalElements());
		map.put("rows",list.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return "success";
	}
	
	//查询收派员的取派标准
	@Action(value="standard_findAll",results={@Result(name="success",type="json")})
	public String findAll(){
		List<Standard> list= service.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return "success";
	}

}
