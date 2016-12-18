package com.lenovo.itac.http.response;

import java.io.Serializable;

public class ResponseEntity implements Serializable {
	
	private static final long serialVersionUID = 6039015411175596805L;

	private static final String CODE_OK = "200";
	
	/** 返回的消息码 */
	private String code = CODE_OK;
	
	/** 返回的查询数据 */
	private Object data;
	
	/** 返回的消息，包括异常信息，或者查询不到的记录 */
	private String msg;
	
	/** 返回总的查询结果数量 */
	private int total;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
