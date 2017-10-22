package cn.itcast.bos.service.base.transit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.TransitInfoRepository;
import cn.itcast.bos.dao.base.WayBillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.domain.transit.TransitInfo;

@Service
@Transactional
public class TransitInfoServiceImpl implements TransitInfoService {

	@Autowired
	private TransitInfoRepository transitInfoRepository;
	@Autowired
	private WayBillRepository wayBillRepository;
	@Override
	public void createTransits(String wayBillIds) {
		if (StringUtils.isNotBlank(wayBillIds)) {
			String[] waybillIdArray = wayBillIds.split(",");
			for (String waybillId : waybillIdArray) {
				WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(waybillId));
				//判断运单是否为未发货
				if (wayBill.getSignStatus()==1) {
					TransitInfo transitInfo = new TransitInfo();
					transitInfo.setWayBill(wayBill);
					transitInfo.setStatus("出入库中转");
					transitInfoRepository.save(transitInfo);
					wayBill.setSignStatus(2);
				}
			}
			
		}
	}

}
