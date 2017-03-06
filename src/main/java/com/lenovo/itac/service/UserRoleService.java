package com.lenovo.itac.service;

import java.util.List;
import java.util.Map;

import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.entity.UserRoleEntity;

public interface UserRoleService {

	public UserRoleEntity getUserRoleById(String id);
	
	public UserRoleEntity getUserRoleByName(String name);
	
	public List<UserRoleEntity> getUserRoles(Map<String, String> params);

	public void addUserRole(UserRoleEntity userRole);
	
	public void deleteUserRoles(List<String> idList);
	
	public void updateUserRole(UserRoleEntity userRole);
	
	public List<String> getMenuIdsByRoleId(String roleId);
	
	public List<MenuEntity> getMenusByRoleName(String roleName);
	
	public void assignPermission(String roleId, String[] menuIds);
}
