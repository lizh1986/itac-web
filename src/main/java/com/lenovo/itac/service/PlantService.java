package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.PlantEntity;

public interface PlantService {

	public PlantEntity getPlantByIp(String ip);
	
	public List<PlantEntity> getPlantsByName(String name);
	
	public List<String> getAllPlantNames();
}
