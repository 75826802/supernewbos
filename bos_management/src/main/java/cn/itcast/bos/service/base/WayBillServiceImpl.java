package cn.itcast.bos.service.base;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.WayBillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.base.WayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
	@Autowired
	private WayBillRepository repository;
	@Autowired
	private WayBillIndexRepository indexRepository;

	@Override
	public void save(WayBill model) {
		WayBill waybill = repository.findByWayBillNum(model.getWayBillType());
		if (waybill == null || waybill.getId() == null) {
			// 订单不存在
			repository.save(model);
			indexRepository.save(model);
		} else {
			// 订单存在
			try {
				Integer id = waybill.getId();
				BeanUtils.copyProperties(waybill, model);
				// 页面传过来的参数中并没有运单id,当model复制给waybill是没有运单id的,所以手动给waybill的id赋值
				waybill.setId(id);
				indexRepository.save(waybill);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}

		}

	}

	@Override
	public Page<WayBill> findAll(Pageable pageable) {

		return repository.findAll(pageable);
	}

	@Override
	public Page<WayBill> findPageData(WayBill wayBill, Pageable pageable) {
		// 判断wayBill中的条件是否存在
		if (StringUtils.isBlank(wayBill.getWayBillNum()) && StringUtils.isBlank(wayBill.getSendAddress())
				&& StringUtils.isBlank(wayBill.getRecAddress()) && StringUtils.isBlank(wayBill.getSendProNum())
				&& (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
			//无条件查询,查询数据库
			return repository.findAll(pageable);			
		} else {
			
			BoolQueryBuilder query =new BoolQueryBuilder();
			if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
				//运单号查询
				QueryBuilder temquery = new TermQueryBuilder("WayBillNum",wayBill.getWayBillNum());
				query.must(temquery);
			}
			if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
				// 发货地 模糊查询
				// 情况一： 输入"北" 是查询词条一部分， 使用模糊匹配词条查询
				QueryBuilder wildcard=new WildcardQueryBuilder("sendAddress","*"+wayBill.getSendAddress()+"*");
				// 情况二： 输入"北京市海淀区" 是多个词条组合，进行分词后 每个词条匹配查询
			}
		}
		return null;
	}

}
