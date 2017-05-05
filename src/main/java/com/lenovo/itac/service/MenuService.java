package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.MenuEntity;

public interface MenuService {
	
	public List<MenuEntity> loadMenus();
	
	public List<MenuEntity> getFirstMenu();
	
	public List<MenuEntity> getSecondMenuByParentId(String parentId);
	
	public void sortByPosition(List<MenuEntity> menuList);
	
	public MenuEntity getMenuById(String id);
}
