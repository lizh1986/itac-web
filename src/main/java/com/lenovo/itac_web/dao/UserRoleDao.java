package com.lenovo.itac_web.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.MenuEntity;
import com.lenovo.itac.entity.UserRoleEntity;

@Repository
public interface UserRoleDao {

	public UserRoleEntity getUserRoleById(String id);
	
	public UserRoleEntity getUserRoleByUserGroupAndPlant(Map<String, String> params);
	
	public List<UserRoleEntity> getUserRoles(Map<String, String> params);

	public void addUserRole(UserRoleEntity userRole);
	
	public void deleteUserRoles(List<String> idList);
	
	public void updateUserRole(UserRoleEntity userRole);
	
	public List<String> getMenuIdsByRoleId(String roleId);
	
	public List<MenuEntity> getMenusByUserGroupAndPlant(Map<String,String> params);
	
	public void addRoleMenuMapping(Map<String, String> params);
	
	public void deleteRoleMenuMapping(Map<String, String> params);
}
