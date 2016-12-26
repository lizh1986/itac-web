package com.lenovo.itac.dao;

import java.util.List;
import java.util.Map;

import com.lenovo.itac.entity.AgedMoEntity;

public interface AgedMoDao {

	public List<AgedMoEntity> queryByMos(Map<String, Object> params);
	
	public List<AgedMoEntity> queryFirstBookingByMos(List<String> mos);
}
