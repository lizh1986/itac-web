package com.lenovo.itac.entity;

import java.io.Serializable;

public class UserGroupEntity implements Serializable{
	private static final long serialVersionUID = 4424181206745107614L;
	
	private int id;
	
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
