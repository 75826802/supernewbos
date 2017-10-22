package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaService {


	void save(FixedArea fixedArea);

	Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable);

}
