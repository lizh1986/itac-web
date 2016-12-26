package com.lenovo.itac.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lenovo.itac.dao.AgedMoDao;
import com.lenovo.itac.entity.AgedMoEntity;
import com.lenovo.itac.service.AgedMoService;
import com.lenovo.itac.util.CommonUtils;

@Service
public class AgedMoServiceImpl implements AgedMoService {

	@Autowired
	private AgedMoDao agedMoDao;
	
	@Override
	public List<AgedMoEntity> queryByMos(String[] mos) {
		if (mos != null) {
			List<String> moList = Lists.newArrayList();
			for (String mo : mos) {
				if (CommonUtils.validateMO(mo)) {
					moList.add(mo);
				}
			}
			
			Map<String, Object> params = Maps.newHashMap();
			params.put("stationNumber", CommonUtils.BUILD_DONE_STATION);
			params.put("mos", moList);
			
			List<AgedMoEntity> entities = agedMoDao.queryByMos(params);
			
			moList.clear();
			Iterator<AgedMoEntity> it = entities.iterator();
			while(it.hasNext()) {
				AgedMoEntity entity = it.next();
				if (entity.getSnNumber() == entity.getPassed()) {
					it.remove();
				} else {
					moList.add(entity.getMo());
				}
			}
			
			List<AgedMoEntity> firstBookings = agedMoDao.queryFirstBookingByMos(moList);
			
			return firstBookings;
		}
		
		return null;
	}

}
