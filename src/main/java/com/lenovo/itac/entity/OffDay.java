package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

public class OffDay implements Serializable{

	private static final long serialVersionUID = -5976704738415267834L;

	private Date offDay;
	private Date created;
	public Date getOffDay() {
		return offDay;
	}
	public void setOffDay(Date offDay) {
		this.offDay = offDay;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
}
