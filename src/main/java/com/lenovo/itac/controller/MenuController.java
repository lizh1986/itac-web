package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.entity.MenuXmlParser;
import com.lenovo.itac.http.response.ResponseEntity;

@RestController
public class MenuController {

	@RequestMapping("/nav/first")
	public ResponseEntity getFirstMenu(HttpServletRequest request) {
		List<MenuEntity> entities = MenuXmlParser.getFirstMenus();
		
		ResponseEntity response = new ResponseEntity();
		response.setData(entities);
		return response;
	}
	
	@RequestMapping("/nav/second")
	public List<MenuEntity> getSecondMenu(HttpServletRequest request) {
		
		String parentId = request.getParameter("id");
		System.out.println(parentId);
		
		List<MenuEntity> entities = MenuXmlParser.getSecondMenusByParentId(parentId);
		
		return entities;
	}
}
