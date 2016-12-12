package com.lenovo.itac.model;

import java.io.Serializable;

public class GGYRModel implements Serializable {

	private static final long serialVersionUID = -6308593903581403508L;

	private String idocNum;
	private String mo;
	private String ggyr;
	private String pssd;
	private String rpssd;
	private String stamp;
	private String created;
	
	public String getIdocNum() {
		return idocNum;
	}
	public void setIdocNum(String idocNum) {
		this.idocNum = idocNum;
	}
	public String getMo() {
		return mo;
	}
	public void setMo(String mo) {
		this.mo = mo;
	}
	public String getGgyr() {
		return ggyr;
	}
	public void setGgyr(String ggyr) {
		this.ggyr = ggyr;
	}
	public String getPssd() {
		return pssd;
	}
	public void setPssd(String pssd) {
		this.pssd = pssd;
	}
	public String getRpssd() {
		return rpssd;
	}
	public void setRpssd(String rpssd) {
		this.rpssd = rpssd;
	}
	public String getStamp() {
		return stamp;
	}
	public void setStamp(String stamp) {
		this.stamp = stamp;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	
}
