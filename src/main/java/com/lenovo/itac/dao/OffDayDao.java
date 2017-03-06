package com.lenovo.itac.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.OffDay;

@Repository
public interface OffDayDao {
	public void insert(OffDay offday);
	
	public void delete(Date date);
	
	public List<String> queryOffDays(Map<String, Object> params);
}
