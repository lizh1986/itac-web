package com.lenovo.itac.model;

import java.io.Serializable;

/**
 * MO下WS对应的属性
 * @author lizh18
 *
 */
public class MoAndWSModel implements Serializable {

	private static final long serialVersionUID = -7862427481333970168L;
	
	private String ws;
	
	private int passed;
	
	private String pending;
	
	private String firstBooking;
	
	private String lastBooking;

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public String getFirstBooking() {
		return firstBooking;
	}

	public void setFirstBooking(String firstBooking) {
		this.firstBooking = firstBooking;
	}

	public String getLastBooking() {
		return lastBooking;
	}

	public void setLastBooking(String lastBooking) {
		this.lastBooking = lastBooking;
	}

	@Override
	public String toString() {
		return "MoAndWSModel [ws=" + ws + ", passed=" + passed + ", pending=" + pending + ", firstBooking="
				+ firstBooking + ", lastBooking=" + lastBooking + "]";
	}
}
