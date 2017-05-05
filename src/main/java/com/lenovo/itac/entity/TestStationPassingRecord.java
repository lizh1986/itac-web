package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

public class TestStationPassingRecord implements Serializable {

	private static final long serialVersionUID = 2562949452424153639L;

	private String id;
	
	private String serialNumber;
	
	private String nextWorkStep;
	
	private String operator;
	
	private Date time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getNextWorkStep() {
		return nextWorkStep;
	}

	public void setNextWorkStep(String nextWorkStep) {
		this.nextWorkStep = nextWorkStep;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
