package com.lenovo.itac.model;

public class NextWorkStepOfSN {

	private String sn;
	
	private String nextWorkStep;
	
	private String desc;
	
	public NextWorkStepOfSN(String sn, String nextWorkStep) {
		this.sn = sn;
		this.nextWorkStep = nextWorkStep;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getNextWorkStep() {
		return nextWorkStep;
	}

	public void setNextWorkStep(String nextWorkStep) {
		this.nextWorkStep = nextWorkStep;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
