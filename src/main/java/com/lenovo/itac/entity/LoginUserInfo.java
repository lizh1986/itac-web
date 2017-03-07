package com.lenovo.itac.entity;

import java.io.Serializable;

public class LoginUserInfo implements Serializable {

	private static final long serialVersionUID = -7705340855974416625L;

	private String loginName;
	
	private String password;
	
	private String plant;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
}
