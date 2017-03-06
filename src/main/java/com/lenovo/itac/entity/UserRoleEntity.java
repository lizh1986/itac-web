package com.lenovo.itac.entity;

import java.io.Serializable;

public class UserRoleEntity implements Serializable {

	private static final long serialVersionUID = -2691476719199757772L;

	private String id;
	
	private String name;
	
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "UserRoleEntity [id=" + id + ", name=" + name + ", desc=" + description + "]";
	}
}
