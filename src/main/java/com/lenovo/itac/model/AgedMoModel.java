package com.lenovo.itac.model;

import java.io.Serializable;

public class AgedMoModel implements Serializable {

	private static final long serialVersionUID = 6402670693809400016L;

	private String mo;
	
	private String firstBooking;

	private String aged;
	
	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public String getFirstBooking() {
		return firstBooking;
	}

	public void setFirstBooking(String firstBooking) {
		this.firstBooking = firstBooking;
	}

	public String getAged() {
		return aged;
	}

	public void setAged(String aged) {
		this.aged = aged;
	}
}
