package cn.itcast.bos.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.constant.Constants;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.crm.domain.Customer;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2077478604403899144L;
	private String sendAreaInfo;// 发件人
	private String recAreaInfo;// 收件人

	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}

	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

	@Action(value = "order_add", results = { @Result(name = "success", type = "redirect", location = "index.html") })
	public String add() {
		String[] sendAreaData = sendAreaInfo.split("/");
		// 发件人省市区信息
		Area sendArea = new Area();
		sendArea.setProvince(sendAreaData[0]);
		sendArea.setCity(sendAreaData[1]);
		sendArea.setDistrict(sendAreaData[2]);
		String[] recAreaData = recAreaInfo.split("/");
		// 收件人省市区信息
		Area recArea = new Area();
		recArea.setProvince(recAreaData[0]);
		recArea.setCity(recAreaData[1]);
		recArea.setDistrict(recAreaData[2]);

		model.setSendArea(sendArea);
		model.setRecArea(recArea);
		// 关联当前登录客户
		Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
		model.setCustomer_id(customer.getId());
		System.out.println(customer.getId());
		// 调用webservice,将数据传送给bos_management
		WebClient.create(Constants.BOS_MANAGEMENT_URL + "/services/orderService/order").type(MediaType.APPLICATION_JSON)
				.post(model);

		return SUCCESS;
	}
	


}
