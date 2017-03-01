package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

public class SnInfoEntity implements Serializable {

	private static final long serialVersionUID = 836975668105388793L;

	/** 生产订单 */
	private String mo;
	/** 生产订单下的主机序列号 */
	private String sn;
	/** 主机所停在的工序 */
	private String workStep;
	/** 最后一个pass的主机的pass时间 */
	private Date lastWsTime;

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

	public Date getLastWsTime() {
		return lastWsTime;
	}

	public void setLastWsTime(Date lastWsTime) {
		this.lastWsTime = lastWsTime;
	}

	@Override
	public String toString() {
		return "SnInfoEntity [mo=" + mo + ", sn=" + sn + ", workStep=" + workStep + ", lastWsTime=" + lastWsTime + "]";
	}
	
}
