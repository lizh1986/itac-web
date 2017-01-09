package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

public class MoInfoEntity implements Serializable {

	private static final long serialVersionUID = -5621163528237936441L;

	/** 生产订单 */
	private String mo;
	
	/** 工序的编号 */
	private int wsNo;

	/** 主机所停在的工序 */
	private String workStep;
	
	/** 是否是必过站，值为 N 或者 R */
	private String passStationReq;
	
	/** 订单下主机的数量 */
	private int quantity;
	
	/** MTM： Machine Type Model，具体配置 */
	private String mtm;
	
	/** 一个订单中，已经通过某工位的主机的数量 */
	private int passed;
	
	/** 一个订单中，停留在某个工位的主机的数量 */
	private int pending;
	
	/** 订单产生的时间 */
	private Date createTime;
	
	/** 一个订单中，第一台主机在某个工位开始的时间 */
	private Date firstBooking;
	
	/** 一个订单中，最后一台主机在某个工位结束的时间，或者最近的一个主机在某个工位的结束时间 */
	private Date lastBooking;

	public String getWorkStep() {
		return workStep;
	}

	public void setWorkStep(String workStep) {
		this.workStep = workStep;
	}

	public String getPassStationReq() {
		return passStationReq;
	}

	public void setPassStationReq(String passStationReq) {
		this.passStationReq = passStationReq;
	}

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public int getWsNo() {
		return wsNo;
	}

	public void setWsNo(int wsNo) {
		this.wsNo = wsNo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getMtm() {
		return mtm;
	}

	public void setMtm(String mtm) {
		this.mtm = mtm;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFirstBooking() {
		return firstBooking;
	}

	public void setFirstBooking(Date firstBooking) {
		this.firstBooking = firstBooking;
	}

	public Date getLastBooking() {
		return lastBooking;
	}

	public void setLastBooking(Date lastBooking) {
		this.lastBooking = lastBooking;
	}

	@Override
	public String toString() {
		return "MoInfoEntity [mo=" + mo + ", wsNo=" + wsNo + ", workStep=" + workStep + ", passStationReq="
				+ passStationReq + ", quantity=" + quantity + ", mtm=" + mtm + ", passed=" + passed + ", pending="
				+ pending + ", createTime=" + createTime + ", firstBooking=" + firstBooking + ", lastBooking="
				+ lastBooking + "]";
	}
}
