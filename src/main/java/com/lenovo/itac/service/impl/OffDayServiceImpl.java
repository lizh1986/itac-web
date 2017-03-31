package com.lenovo.itac.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.lenovo.itac.dao.OffDayDao;
import com.lenovo.itac.entity.OffDay;
import com.lenovo.itac.service.OffDayService;

@Service
public class OffDayServiceImpl implements OffDayService {

	@Autowired
	private OffDayDao offDayDao;
	
	@Override
	public void insert(OffDay offday) {
		// TODO Auto-generated method stub
		offDayDao.insert(offday);
	}

	@Override
	public void delete(Date date) {
		// TODO Auto-generated method stub
		offDayDao.delete(date);
	}

	@Override
	public List<String> queryOffDays(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		Map<String, Object> params = Maps.newHashMap();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		return offDayDao.queryOffDays(params);
	}

}
