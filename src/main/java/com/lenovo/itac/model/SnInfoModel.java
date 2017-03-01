package com.lenovo.itac.model;

import java.io.Serializable;

public class SnInfoModel implements Serializable {

	private static final long serialVersionUID = 639833250519864309L;

	/** 生产订单 */
	private String mo;
	/** 生产订单下的主机序列号 */
	private String sn;
	/** 主机所停在的工序 */
	private String workStep;
	/** 最后一个pass的主机的pass时间 */
	private String lastWsTime;
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
	public String getWorkStep() {
		return workStep;
	}
	public void setWorkStep(String workStep) {
		this.workStep = workStep;
	}
	public String getLastWsTime() {
		return lastWsTime;
	}
	public void setLastWsTime(String lastWsTime) {
		this.lastWsTime = lastWsTime;
	}
	
}
