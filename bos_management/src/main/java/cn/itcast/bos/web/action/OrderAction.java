package cn.itcast.bos.web.action;

import java.util.HashMap;
import java.util.Map;


import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.service.base.OrderService;

@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order>{
	@Autowired
	private OrderService orderService;
	//通过订单号查询订单信息
	@Action(value="order_findByOrderNum",results={@Result(name="success",type="json")})
	public String findByOrderNum(){
		//调用业务层处理业务
		Order order = orderService.findByOrderNum(model.getOrderNum());
		
		Map<String,Object> result = new HashMap<String, Object>();
		//没有订单号
		if (order==null) {
			result.put("success",false);
		}else{
			result.put("success",true);
			result.put("orderData",order);
		}
		ActionContext.getContext().getValueStack().push(result);		
		return SUCCESS;
		
	}
}
