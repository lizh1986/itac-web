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
		entity.setText("Query");
		entity.setIconCls(".icon-brash");
		entities.add(entity);
		
		MenuEntity settings = new MenuEntity();
		settings.setId("002");
		settings.setText("Settings");
		settings.setIconCls(".icon.brash");
		
		entities.add(settings);
		
		ResponseEntity response = new ResponseEntity();
		response.setData(entities);
		return response;
	}
	
	@RequestMapping("/nav/second")
	public List<MenuEntity> getSecondMenu(HttpServletRequest request) {
		List<MenuEntity> entities = new ArrayList<MenuEntity>();
		
		String parentId = request.getParameter("id");
		System.out.println(parentId);
		if (parentId.equals("001")) {
			MenuEntity entity = new MenuEntity();
			entity.setId("001-001");
			entity.setText("MO Info");
			entity.setUrl("jsp/mo.jsp");
			entity.setIconCls(".icon-brash");
			entities.add(entity);
			
			MenuEntity entity2 = new MenuEntity();
			entity2.setId("001-002");
			entity2.setText("GGYR");
			entity2.setUrl("jsp/ggyr.jsp");
			entity2.setIconCls(".icon-brash");
			entities.add(entity2);
			
			MenuEntity entity3 = new MenuEntity();
			entity3.setId("001-002");
			entity3.setText("Build Done Fail List");
			entity3.setUrl("jsp/builddone.jsp");
			entity3.setIconCls(".icon-brash");
			entities.add(entity3);
			
			MenuEntity entity4 = new MenuEntity();
			entity4.setId("001-002");
			entity4.setText("Aged MO List");
			entity4.setUrl("jsp/agedmo.jsp");
			entity4.setIconCls(".icon-brash");
			entities.add(entity4);
		} else if (parentId.equals("002")) {
			MenuEntity entity = new MenuEntity();
			entity.setId("002-001");
			entity.setText("Work Day Calendar");
			entity.setUrl("jsp/workdaycalendar.jsp");
			entity.setIconCls(".icon-brash");
			entities.add(entity);
		}
		
		return entities;
	}
}
