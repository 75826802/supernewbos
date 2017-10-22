package cn.itcast.bos.web.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.base.WayBillService;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill>{
	//添加快录运单
	private static final Logger logger = Logger.getLogger(WayBillAction.class);
	@Autowired
	private WayBillService wayBillService;
	@Action(value="waybill_save",results={@Result(name="success",type="json")})
	public String save(){
		if (model.getOrder().getId()==null||model.getOrder().getId()==0) {
			model.setOrder(null);
		}
		
		
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			wayBillService.save(model);
			result.put("success",true);
			result.put("msg","保存快录运单成功");
			logger.info("保存运单成功,运单号:"+model.getWayBillNum());
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success",false);
			result.put("msg","保存运单失败");
			logger.error("保存运单失败,运单号:"+model.getWayBillNum());
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
	//快录运单的查询
	@Action(value="wayBill_pagequery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//无条件查询
		Pageable pageable = new PageRequest(page-1,rows,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
		Page<WayBill> pageData = wayBillService.findPageData(model,pageable);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
}
