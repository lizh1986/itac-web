package com.lenovo.itac.http.response;

import java.io.Serializable;

public class ResponseEntity implements Serializable {
	
	private static final long serialVersionUID = 6039015411175596805L;

	private static final String CODE_OK = "200";
	
	private String code = CODE_OK;
	private Object data;
	
	public ResponseEntity() {
		this.data = new Object();
	}
	
	public ResponseEntity(String code) {
		this.code = code;
		this.data = new Object();
	}
	
	public ResponseEntity(Object data) {
		this.data = data;
	}
	
	public ResponseEntity(String code, Object data) {
		this.code = code;
		this.data = data;
	}
	
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
}
