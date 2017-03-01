package com.lenovo.itac.model;

import java.io.Serializable;
import java.util.List;

/**
 * 前台展示的仅包括MO特有的信息的属性，MO在某个work step的信息定义在MoAndWSModel中
 * @author lizh18
 *
 */
public class MoInfoModel implements Serializable {

	private static final long serialVersionUID = -1560156027766945626L;
	
	private String mo;
	
	private int quantity;
	
	private String mtm;
	
	private String creationTime;
	
	private List<MoAndWSModel> children;

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
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

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public List<MoAndWSModel> getChildren() {
		return children;
	}

	public void setChildren(List<MoAndWSModel> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "MoInfoModel [mo=" + mo + ", quantity=" + quantity + ", mtm=" + mtm + ", creationTime=" + creationTime
				+ ", children=" + children + "]";
	}
}
