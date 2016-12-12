package com.lenovo.itac.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.http.response.ResponseEntity;

@RestController
public class MenuController {

	@RequestMapping("/nav/first")
	public ResponseEntity getFirstMenu(HttpServletRequest request) {
		List<MenuEntity> entities = new ArrayList<MenuEntity>();
		
		MenuEntity entity = new MenuEntity();
		entity.setId("001");
		entity.setText("test");
		entity.setIconCls(".icon-brash");
		
		Set<MenuEntity> children = new HashSet<MenuEntity>();
		entity.setChildren(children);
		
		MenuEntity child1 = new MenuEntity();
		child1.setId("001-001");
		child1.setText("test1");
		child1.setIconCls(".icon-brash");
		children.add(child1);
		
		MenuEntity child2 = new MenuEntity();
		child2.setId("001-002");
		child2.setText("test2");
		child2.setIconCls(".icon-brash");
		children.add(child2);
		
		entities.add(entity);
		return (new ResponseEntity(entities));
	}
	
	@RequestMapping("/nav/second")
	public List<MenuEntity> getSecondMenu(HttpServletRequest request) {
		List<MenuEntity> entities = new ArrayList<MenuEntity>();
		
		String parentId = request.getParameter("id");
		System.out.println(parentId);
		
		MenuEntity entity = new MenuEntity();
		entity.setId("001-001");
		entity.setText("MO Status");
		entity.setUrl("html/mo-status.html");
		entity.setIconCls(".icon-brash");
		
		MenuEntity entity2 = new MenuEntity();
		entity2.setId("001-002");
		entity2.setText("GGYR");
		entity2.setUrl("html/ggyr.html");
		entity2.setIconCls(".icon-brash");
		
		MenuEntity entity3 = new MenuEntity();
		entity3.setId("001-002");
		entity3.setText("Build Done Fail List");
		entity3.setUrl("html/builddone.html");
		entity3.setIconCls(".icon-brash");
		
		entities.add(entity);
		entities.add(entity2);
		entities.add(entity3);
		return entities;
	}
}
