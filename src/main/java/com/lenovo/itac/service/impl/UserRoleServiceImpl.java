package com.lenovo.itac.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.entity.UserRoleEntity;
import com.lenovo.itac.service.UserRoleService;
import com.lenovo.itac_web.dao.UserRoleDao;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public UserRoleEntity getUserRoleById(String id) {
		
		return userRoleDao.getUserRoleById(id);
	}
	
	@Override
	public UserRoleEntity getUserRoleByUserGroupAndPlant(String userGroup, String plant) {
		Map<String, String> params = Maps.newHashMap();
		params.put("userGroup", userGroup);
		params.put("plant", plant);
		return userRoleDao.getUserRoleByUserGroupAndPlant(params);
	}
	
	@Override
	public List<UserRoleEntity> getUserRoles(Map<String, String> params) {
		
		return userRoleDao.getUserRoles(params);
	}

	@Override
	public void addUserRole(UserRoleEntity userRole) {
		userRoleDao.addUserRole(userRole);
	}

	@Override
	public void deleteUserRoles(List<String> idList) {
		userRoleDao.deleteUserRoles(idList);
	}

	@Override
	public void updateUserRole(UserRoleEntity userRole) {
		userRoleDao.updateUserRole(userRole);
	}

	@Override
	public List<String> getMenuIdsByRoleId(String roleId) {
		return userRoleDao.getMenuIdsByRoleId(roleId);
	}

	@Override
	public List<MenuEntity> getMenusByUserGroupAndPlant(String userGroup, String plant) {
		Map<String, String> params = Maps.newHashMap();
		params.put("userGroup", userGroup);
		params.put("plant", plant);
		return userRoleDao.getMenusByUserGroupAndPlant(params);
	}

	@Override
	public void assignPermission(String roleId, String[] menuIds) {
		if (roleId != null && menuIds != null) {
			List<String> menusInDB = getMenuIdsByRoleId(roleId);
			List<String> menuList = Lists.newArrayList();
			menuList.addAll(menusInDB);
			
			for (String menuId : menuIds) {
				if (!menusInDB.contains(menuId)) {
					Map<String, String> mapping = Maps.newHashMap();
					mapping.put("roleId", roleId);
					mapping.put("menuId", menuId);
					userRoleDao.addRoleMenuMapping(mapping);
				} else {
					menuList.remove(menuId);
				}
			}
			
			for (String toDelete : menuList) {
				Map<String, String> params = Maps.newHashMap();
				params.put("roleId", roleId);
				params.put("menuId", toDelete);
				userRoleDao.deleteRoleMenuMapping(params);
			}
		}
	}
}
