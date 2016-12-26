package com.lenovo.itac.entity;

import java.io.Serializable;

public class BuildDoneEntity implements Serializable {

	private static final long serialVersionUID = -7588053678594096650L;

	private String mo;
	
	private String sn;
	
	private String status;
	
	private String stationNumber;

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}
}
