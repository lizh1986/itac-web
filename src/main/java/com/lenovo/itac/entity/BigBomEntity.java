package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

public class BigBomEntity implements Serializable {

	private static final long serialVersionUID = 1744863375285185602L;

	private String workOrder;
	
	private String partNumber;
	
	private int quantity;
	
	private Date createTime;

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
