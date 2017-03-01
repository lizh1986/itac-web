package com.lenovo.itac.dao;

import java.util.Date;
import java.util.List;

import com.lenovo.itac.entity.OffDay;

public interface OffDayDao {
	public void insert(OffDay offday);
	
	public void delete(Date date);
	
	public List<String> queryOffDays(Date startDate, Date endDate);
}
