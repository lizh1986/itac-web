package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.lenovo.itac.entity.LoginUserInfo;
import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.LoginService;
import com.lenovo.itac.service.MenuService;
import com.lenovo.itac.service.UserRoleService;
import com.lenovo.itac.util.Constants;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@RequestMapping("/load")
	public ResponseEntity loadMenus(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		List<MenuEntity> menus = menuService.loadMenus();
		response.setData(menus);
		return response;
	}
	
	
	@RequestMapping("/first")
	public ResponseEntity getFirstMenu(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String userName = ((LoginUserInfo)request.getSession().getAttribute("user")).getLoginName();
		if (userName.equalsIgnoreCase(Constants.SUPER_ADMIN)) {
			response.setData(menuService.getFirstMenu());
		} else {
			List<String> userGroups = loginService.getUserGroupsByUserName(userName);
			
			List<MenuEntity> allowMenus = Lists.newArrayList();
			List<MenuEntity> menus = null;
			for (String userGroup : userGroups) {
				menus = userRoleService.getMenusByRoleName(userGroup);
				if (menus != null) {
					allowMenus.addAll(menus);
				}
			}
			
			List<MenuEntity> firstMenus = menuService.getFirstMenu();
			
			List<MenuEntity> result = Lists.newArrayList();
			for (MenuEntity menu : firstMenus) {
				for (MenuEntity inner : allowMenus) {
					if (inner.getId().equals(menu.getId())
							|| inner.getParentId().equals(menu.getId())) {
						result.add(menu);
					}
					break;
				}
			}
			menuService.sortByPosition(result);
			
			response.setData(result);
		}
		return response;
	}
	
	@RequestMapping("/second")
	public List<MenuEntity> getSecondMenu(HttpServletRequest request) {
		String parentId = request.getParameter("id");
		
		String userName = ((LoginUserInfo)request.getSession().getAttribute("user")).getLoginName();
		if (userName.equalsIgnoreCase(Constants.SUPER_ADMIN)) {
			List<MenuEntity> entities = menuService.getSecondMenuByParentId(parentId);
			menuService.sortByPosition(entities);
			return entities;
		} else {
			List<String> userGroups = loginService.getUserGroupsByUserName(userName);
			
			List<MenuEntity> allowMenus = Lists.newArrayList();
			List<MenuEntity> menus = null;
			for (String userGroup : userGroups) {
				menus = userRoleService.getMenusByRoleName(userGroup);
				if (menus != null) {
					allowMenus.addAll(menus);
				}
			}
			
			List<MenuEntity> entities = menuService.getSecondMenuByParentId(parentId);
			
			List<MenuEntity> result = Lists.newArrayList();
			for (MenuEntity menu : entities) {
				for (MenuEntity inner : allowMenus) {
					if (menu.getId().equals(inner.getId())) {
						result.add(menu);
						break;
					}
				}

			}
			menuService.sortByPosition(result);
			return result;
		}
	}
}
