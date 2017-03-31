package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.UserGroupEntity;

public interface LoginService {

	public int login(String userName, String password);
	
	public int logout(String userName);
	
	/**
	 * 根据登录的用户名从iTAC中获取该用户对应的用户组，这里从数据库中通过用户和用户组的关联获取用户的用户组
	 * @param userName 登录的用户名
	 * @return 登录用户对应的用户组列表
	 */
	public List<String> getUserGroupsByUserName(String userName);
	
	/**
	 * 获取iTAC系统中所有定义的用户组，用于分配菜单权限
	 * @return
	 */
	public List<UserGroupEntity> getAllUserGroups();
}
