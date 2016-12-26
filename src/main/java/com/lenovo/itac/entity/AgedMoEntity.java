package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

public class AgedMoEntity implements Serializable {

	private static final long serialVersionUID = -5451262823406623967L;

	private String mo;
	
	private int snNumber;
	
	private Date firstBooking;
	
	private int passed;
	
	private String stationNumber;

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public int getSnNumber() {
		return snNumber;
	}

	public void setSnNumber(int snNumber) {
		this.snNumber = snNumber;
	}

	public Date getFirstBooking() {
		return firstBooking;
	}

	public void setFirstBooking(Date firstBooking) {
		this.firstBooking = firstBooking;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}
}
