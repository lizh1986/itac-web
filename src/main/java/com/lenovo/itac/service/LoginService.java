package com.lenovo.itac.service;

public interface LoginService {

	public boolean login(String userName, String password);
	
	public void logout();
}
