package com.lenovo.itac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.itac.dao.LoginDao;
import com.lenovo.itac.entity.UserGroupEntity;
import com.lenovo.itac.service.LoginService;
import com.lenovo.itac.service.api.ItacApiService;
import com.lenovo.itac.util.Constants;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	
	@Override
	public int login(String userName, String password) {
		if (Constants.SUPER_ADMIN.equals(userName) && Constants.SUPER_ADMIN_PWD.equals(password)) {
			return 0;
		}
		
		return ItacApiService.login(userName, password);
	}

	@Override
	public int logout(String userName) {
		if (Constants.SUPER_ADMIN.equalsIgnoreCase(userName)) {
			return 0;
		} 
		
		return ItacApiService.logout(userName);
		
	}

	@Override
	public List<String> getUserGroupsByUserName(String userName) {
		return loginDao.getUserGroupsByUserName(userName);
	}

	@Override
	public List<UserGroupEntity> getAllUserGroups() {
		return loginDao.getAllUserGroups();
	}
}
