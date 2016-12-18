package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.GGYREntity;

public interface GGYRService {
	
	public int getTotalCount(String[] mos);
	
	public List<GGYREntity> queryByMOs(int page, int rows, String[] mos);
	
	public List<String> notExists(String[] mos);
}
