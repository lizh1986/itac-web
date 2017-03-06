package com.lenovo.itac.service;

import java.util.List;

public interface LoginService {

	public boolean login(String userName, String password);
	
	public boolean logout(String userName);
	
	/**
	 * 根据登录的用户名从iTAC中获取该用户对应的用户组，这里从数据库中通过用户和用户组的关联获取用户的用户组
	 * @param userName 登录的用户名
	 * @return 登录用户对应的用户组列表
	 */
	public List<String> getUserGroupsByUserName(String userName);
}
