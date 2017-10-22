package cn.itcast.bos.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Controller
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Actions
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

	// 模型驱动
	private Courier courier = new Courier();

	@Override
	public Courier getModel() {
		return courier;
	}

	@Autowired
	private CourierService service;

	
	@Action(value = "courier_save", results = {
			@Result(name = "success", type = "redirect", location = "./pages/base/courier.html") })
	@RequiresPermissions("courier_save")
	public String addCourier() {
		service.save(courier);
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

	@Action(value = "courier_pagequery", results = { @Result(name = "success", type = "json") })
	public String pagequery() {
		// 封装pageable对象
		Pageable pageable = new PageRequest(page - 1, rows);
		// 根据查询条件构建specification条件查询对象（类似hibernate的QBC查询）
		Specification<Courier> specification = new Specification<Courier>() {
			// root:参数，获取条件表达式
			// query:参数，构造简单条件查询返回 提供where方法
			// cb:参数，构造predicate对象，条件对象，构造复杂查询效果

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 当前查询root根对象courier
				List<Predicate> list = new ArrayList<Predicate>();
				// 单表查询（查询当前对象 对应数据表）
				// 根据快递员工号查询（等值查询）
				if (StringUtils.isNotBlank(courier.getCourierNum())) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
					list.add(p1);
				}
				// 所属单位查询（模糊查询）
				if (StringUtils.isNotBlank(courier.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), "%" + courier.getCompany() + "%");
					list.add(p2);
				}
				// 快递员类型查询（等值查询）
				if (StringUtils.isNotBlank((courier.getType()))) {
					Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
					list.add(p3);
				}
				// 多表查询（查询当前对象关联对象的数据）
				// standard数据表中名称模糊查询
				Join<Object, Object> standardRoot = root.join("standard", JoinType.INNER);
				if (courier.getStandard().getName() != null
						&& StringUtils.isNotBlank(courier.getStandard().getName())) {
					Predicate p4 = cb.like(standardRoot.get("name").as(String.class),
							"%" + courier.getStandard().getName() + "%");
					list.add(p4);
				}

				return cb.and(list.toArray(new Predicate[0]));
			}
		};

		Page<Courier> list = service.pagequery(specification,pageable);
		// 将返回page对象 转换成datagrid需要格式
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totale", list.getTotalElements());
		map.put("rows", list.getContent());
		ActionContext.getContext().getValueStack().push(map);
		return "success";
	}

}
