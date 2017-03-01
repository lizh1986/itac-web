package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * GGYR数据库映射的对象
 * @author lizh18
 *
 */
public class GGYREntity implements Serializable {

	private static final long serialVersionUID = -4299550900696673035L;

	private String idocNum;
	private String mo;
	private String ggyr;
	private Date pssd;
	private Date rpssd;
	private Date stamp;
	private Date created;
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
	public Date getPssd() {
		return pssd;
	}
	public void setPssd(Date pssd) {
		this.pssd = pssd;
	}
	public Date getRpssd() {
		return rpssd;
	}
	public void setRpssd(Date rpssd) {
		this.rpssd = rpssd;
	}
	public Date getStamp() {
		return stamp;
	}
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
