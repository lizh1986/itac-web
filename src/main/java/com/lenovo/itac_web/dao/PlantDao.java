package com.lenovo.itac_web.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.PlantEntity;

@Repository
public interface PlantDao {
	
	public PlantEntity getPlantByIp(String ip);
	
	public List<PlantEntity> getPlantsByName(String name);
	
	public List<String> getAllPlantNames();
}
