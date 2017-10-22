package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuRepository;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;
	@Override
	public List<Menu> findAll() {
		
		return menuRepository.findAll();
	}
	@Override
	public void save(Menu model) {
		if (model.getParentMenu()!=null&&model.getParentMenu().getId()==0) {
			model.setParentMenu(null);
		}
		menuRepository.save(model);
		
	}
	@Override
	public List<Menu> findByUser(User user) {
		
		if (user.getUsername().equals("admin")) {
			return menuRepository.findAll();
		}else{
			return menuRepository.findByUser(user.getId());
		}
	}

}
