package com.lenovo.itac.entity;

import java.io.Serializable;

public class PlantEntity implements Serializable {
	private static final long serialVersionUID = -2272275622661825505L;

	private String id;
	private String ip;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
