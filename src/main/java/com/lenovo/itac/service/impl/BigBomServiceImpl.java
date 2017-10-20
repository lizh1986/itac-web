package com.lenovo.itac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.itac.dao.BigBomDao;
import com.lenovo.itac.entity.BigBomEntity;
import com.lenovo.itac.service.BigBomService;

@Service
public class BigBomServiceImpl implements BigBomService {

	@Autowired
	private BigBomDao bigBomDao;
	
	public List<BigBomEntity> getAll() {
		return bigBomDao.getAll();
	}

}
