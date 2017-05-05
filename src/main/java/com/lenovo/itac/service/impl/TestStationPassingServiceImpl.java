package com.lenovo.itac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lenovo.itac.entity.TestStationPassingRecord;
import com.lenovo.itac.service.TestStationPassingService;
import com.lenovo.itac_web.dao.TestStationPassingDao;

@Service
public class TestStationPassingServiceImpl implements TestStationPassingService {
	
	@Autowired
	private TestStationPassingDao testStationPassingDao;
	
	public void insert(TestStationPassingRecord record) {
		testStationPassingDao.insert(record);
	}
}
