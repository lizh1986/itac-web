package com.lenovo.itac.entity;

import java.io.Serializable;

public class KitOn3FEntity implements Serializable {

	private static final long serialVersionUID = -1683826427837914482L;

	private String serialNumber;
	
	private String containerNumber;
	
	private String partNumber;

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
}
