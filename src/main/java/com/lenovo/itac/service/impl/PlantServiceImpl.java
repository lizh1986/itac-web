package com.lenovo.itac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.itac.entity.PlantEntity;
import com.lenovo.itac.service.PlantService;
import com.lenovo.itac_web.dao.PlantDao;

@Service
public class PlantServiceImpl implements PlantService {

	@Autowired
	private PlantDao plantDao;
	
	@Override
	public PlantEntity getPlantByIp(String ip) {
		return plantDao.getPlantByIp(ip);
	}

	@Override
	public List<PlantEntity> getPlantsByName(String name) {
		return plantDao.getPlantsByName(name);
	}

	@Override
	public List<String> getAllPlantNames() {
		return plantDao.getAllPlantNames();
	}

}
