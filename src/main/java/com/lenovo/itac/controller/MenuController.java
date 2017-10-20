package com.lenovo.itac.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lenovo.itac.service.api.ItacApiService;
import com.lenovo.itac.util.Constants;

@RestController
@RequestMapping("/menu")
public class MenuController {
	
	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	
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
		
		logger.info("Load menus to assign to specific role.");
		return response;
	}
	
	
	@RequestMapping("/first")
	public ResponseEntity getFirstMenu(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String userName = ((LoginUserInfo)request.getSession().getAttribute("user")).getLoginName();
		if (Constants.SUPER_ADMIN.equalsIgnoreCase(userName)) {
			response.setData(menuService.getFirstMenu());
			logger.info("getFirstMenu - The logged user is super admin.");
		} else {
			String plant = ((LoginUserInfo)request.getSession().getAttribute("user")).getPlant();
			logger.info("getFirstMenu - The plant is: {}, The user is: {}", plant, userName);
			if (plant != null) {
				List<MenuEntity> allowMenus = getAllowedMenuList(userName, plant);
				List<MenuEntity> firstMenus = menuService.getFirstMenu();
				
				List<MenuEntity> result = Lists.newArrayList();
				Set<MenuEntity> set = new HashSet<MenuEntity>();
				for (MenuEntity menu : firstMenus) {
					for (MenuEntity inner : allowMenus) {
						if (inner.getId().equals(menu.getId())
								|| (inner.getParentId() != null && inner.getParentId().equals(menu.getId()))) {
							set.add(menu);
							break;
						}
					}
				}
				for (MenuEntity menu : set) {
					result.add(menu);
				}
				logger.info("getFirstMenu - Load first level menus success.");
				
				menuService.sortByPosition(result);
				response.setData(result);
			}
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
			logger.info("getSecondMenu - Load Second level menus success.");
			return entities;
		} else {
			String plant = ((LoginUserInfo)request.getSession().getAttribute("user")).getPlant();
			if (plant != null) {
				logger.info("getSecondMenu - The plant is: {}, The user is: {}", plant, userName);
				
				List<MenuEntity> allowMenus = getAllowedMenuList(userName, plant);
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
				logger.info("getSecondMenu - Load Second level menus success.");
				return result;
			}
			return null;
		}
	}
	
	private List<MenuEntity> getAllowedMenuList(String userName, String plantName) {
		List<String> userGroups = loginService.getUserGroupsByUserName(userName);
		
		List<MenuEntity> allowMenus = Lists.newArrayList();
		List<MenuEntity> menus = null;
		for (String userGroup : userGroups) {
			menus = userRoleService.getMenusByUserGroupAndPlant(userGroup, plantName);
			if (menus != null) {
				allowMenus.addAll(menus);
			}
		}
		if (allowMenus.size() == 0) {
			menus = userRoleService.getMenusByUserGroupAndPlant(Constants.DEFAULT_ROLE, null);
			if (menus != null) {
				allowMenus.addAll(menus);
			}
		}
		
		// 临时方案，需求：需要按照指定用户设置过站的权限。
		if (ItacApiService.excludeUsers.contains(userName.toUpperCase())) {
			MenuEntity entity = menuService.getMenuById("001-005");
			for (MenuEntity e : allowMenus) {
				if (e.getId().equals(entity.getId())) {
					allowMenus.remove(e);
					break;
				}
			}
			allowMenus.add(entity);
		}
		
		return allowMenus;
	}
}
