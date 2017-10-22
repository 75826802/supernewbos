package cn.itcast.bos.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository dao;
	@Override
	public void save(FixedArea fixedArea) {
		dao.save(fixedArea);
	}
	
	@Override
	public Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable) {
		
		return dao.findAll(specification,pageable);
	}

}
