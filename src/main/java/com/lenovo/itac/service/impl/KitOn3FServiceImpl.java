package com.lenovo.itac.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.lenovo.itac.dao.KitOn3FDao;
import com.lenovo.itac.entity.KitOn3FEntity;
import com.lenovo.itac.service.KitOn3FService;

@Service
public class KitOn3FServiceImpl implements KitOn3FService {

	@Autowired
	private KitOn3FDao kitOn3FDao;

	@Override
	public List<KitOn3FEntity> query(Date from, Date to) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("from", from);
		params.put("to", to);
		
		return kitOn3FDao.query(params);
	}
}
