package com.lenovo.itac.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.GGYREntity;

@Repository
public interface GGYRDao {

	public int getTotalCount(Map<String, Object> params);
	
	public List<GGYREntity> queryByPage(Map<String, Object> params);
	
	public List<String> queryByList(List<String> mos);
	
	public List<GGYREntity> queryByMos(List<String> mos);
}
