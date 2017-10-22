package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillRepository extends JpaRepository<WayBill,Integer>{

	WayBill findByWayBillNum(String wayBillType);

}
