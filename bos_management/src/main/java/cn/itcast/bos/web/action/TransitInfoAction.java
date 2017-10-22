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

import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.base.transit.TransitInfoService;
@Namespace("/")
@Controller
@ParentPackage("json-default")
@Scope("prototype")
public class TransitInfoAction extends BaseAction<TransitInfo>{
	//属性驱动,接收运单id
	private String wayBillIds;
	
	public void setWayBillIds(String wayBillIds) {
		this.wayBillIds = wayBillIds;
	}
	@Autowired
	private TransitInfoService transitInfo;
	@Action(value="transit_create",results={@Result(name="success",type="json")})
	public String create(){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			transitInfo.createTransits(wayBillIds);
			//成功
			result.put("success",true);
			result.put("msg","开启中转配送成功");
		} catch (Exception e) {
			//失败
			e.printStackTrace();
			result.put("success",false);
			result.put("msg","开启中转配送失败:"+e.getMessage());
		}
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
}
