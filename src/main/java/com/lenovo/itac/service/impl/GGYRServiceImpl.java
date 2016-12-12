package com.lenovo.itac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.itac.dao.GGYRDao;
import com.lenovo.itac.entity.GGYREntity;
import com.lenovo.itac.service.GGYRService;

@Service
public class GGYRServiceImpl implements GGYRService{

	@Autowired
	private GGYRDao ggyrDao;
	
	@Override
	public List<GGYREntity> query() {
		// TODO Auto-generated method stub
		return ggyrDao.query();
	}

}
