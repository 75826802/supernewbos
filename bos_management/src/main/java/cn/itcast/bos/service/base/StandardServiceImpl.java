package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@Service
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardDao dao;
	
	@Override
	@CacheEvict(value="standard",allEntries=true)
	public void save(Standard standard) {
		dao.save(standard);
	}


	@Override
	public Page<Standard> findAll(Pageable pageable) {
		
		return dao.findAll(pageable);
	}


	@Override
	@Cacheable("standard")
	public List<Standard> findAll() {
		
		return dao.findAll();
	}

}
