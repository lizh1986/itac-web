package com.lenovo.itac.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class MenuEntity implements Serializable {
	
	private static final long serialVersionUID = 5189083154051704671L;

	private String id;
	
	private String text;
	
	private String url;
	
	private String iconCls;
	
	private String parentId;
	
	private List<MenuEntity> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<MenuEntity> getChildren() {
		return children;
	}

	public void setChildren(List<MenuEntity> children) {
		this.children = children;
	}
	
	public Map<String, Object> getAttributes() {
		if (StringUtils.isNotEmpty(this.getUrl())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("href", this.getUrl());
			return map;
		}
		return null;
	}

	public String toString() {
		return "id:" + id + "-" +
			"text:" + text + "-" +
			"url:" + url + "-" +
			"iconCls:" + iconCls +
			"parentId:" + parentId;
	}
}
