package com.lenovo.itac_web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.MenuEntity;

@Repository
public interface MenuDao {

	public List<MenuEntity> getFirstMenu();
	
	public List<MenuEntity> getSecondMenuByParentId(String parentId);
	
	public MenuEntity getMenuById(String id);
}
