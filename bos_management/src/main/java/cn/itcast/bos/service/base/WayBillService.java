package cn.itcast.bos.service.base;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.take_delivery.WayBill;


public interface WayBillService {
	public void save(WayBill model);

	public Page<WayBill> findAll(Pageable pageable);

	public Page<WayBill> findPageData(WayBill model, Pageable pageable);

}
