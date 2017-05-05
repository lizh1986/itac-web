package com.lenovo.itac_web.dao;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.TestStationPassingRecord;

@Repository
public interface TestStationPassingDao {
	public void insert(TestStationPassingRecord record);
}
