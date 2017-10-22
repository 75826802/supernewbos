package cn.itcast.bos.service.base;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierDao dao;
	
	@Override
	public void save(Courier courier) {
		dao.save(courier);

	}

	@Override
	@RequiresPermissions("courier:list")
	public Page<Courier> pagequery(Specification<Courier> specification, Pageable pageable) {
	
		return dao.findAll(specification,pageable);
	}

}
