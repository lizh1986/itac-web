package com.lenovo.itac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.service.MenuService;
import com.lenovo.itac_web.dao.MenuDao;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<MenuEntity> loadMenus() {
		List<MenuEntity> firstMenus = getFirstMenu();
		if (null != firstMenus) {
			List<MenuEntity> secondMenus;
			for (MenuEntity menu : firstMenus) {
				secondMenus = getSecondMenuByParentId(menu.getId());
				menu.setChildren(secondMenus);
			}
		}
		
		return firstMenus;
	}
	
	@Override
	public List<MenuEntity> getFirstMenu() {
		List<MenuEntity> firstMenus = menuDao.getFirstMenu();
		sortByPosition(firstMenus);
		return firstMenus;
	}

	@Override
	public List<MenuEntity> getSecondMenuByParentId(String parentId) {
		List<MenuEntity> secondMenus = menuDao.getSecondMenuByParentId(parentId);
		sortByPosition(secondMenus);
		return secondMenus;
	}

	/**
	 * 从小到大的顺序排序
	 * @param unsortedList
	 * @return
	 */
	public void sortByPosition(List<MenuEntity> menuList) {
		if (null != menuList) {
			MenuEntity temp;
			for (int outer = 0; outer < menuList.size(); outer++) {
				temp = menuList.get(outer);
				for (int inner = outer + 1; inner < menuList.size(); inner++) {
					if (temp.getPosition() > menuList.get(inner).getPosition()) {
						temp = menuList.get(inner);
						menuList.set(inner, menuList.get(outer));
						menuList.set(outer, temp);
					}
				}
			}
		}
	}

	@Override
	public MenuEntity getMenuById(String id) {
		return menuDao.getMenuById(id);
	}
}
