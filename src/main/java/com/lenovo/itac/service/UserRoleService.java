package com.lenovo.itac.service;

import java.util.List;
import java.util.Map;

import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.entity.UserRoleEntity;

public interface UserRoleService {

	public UserRoleEntity getUserRoleById(String id);
	
	public UserRoleEntity getUserRoleByUserGroupAndPlant(String userGroup, String plant);
	
	public List<UserRoleEntity> getUserRoles(Map<String, String> params);

	public void addUserRole(UserRoleEntity userRole);
	
	public void deleteUserRoles(List<String> idList);
	
	public void updateUserRole(UserRoleEntity userRole);
	
	public List<String> getMenuIdsByRoleId(String roleId);
	
	public List<MenuEntity> getMenusByUserGroupAndPlant(String userGroup, String plant);
	
	public void assignPermission(String roleId, String[] menuIds);
}
