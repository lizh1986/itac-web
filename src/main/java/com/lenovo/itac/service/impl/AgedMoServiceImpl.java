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
			
			// entities中存在某个MO的所有SN都未过Build Done，这种情况下查询出的MO为NULL，这种情况继续计算
			// 两种情况继续计算：（1）MO为NULL；（2）SN = PASSED
			Iterator<String> it = moList.iterator();
			while(it.hasNext()) {
				String mo = it.next();
				for (AgedMoEntity e : entities) {
					if (e.getMo() == null || !e.getMo().equals(mo))
						continue;
					else {
						if (e.getSnNumber() == e.getPassed()) {
							it.remove();
						}
					}
					
				}
			}
			
			if (!moList.isEmpty()) {
				List<AgedMoEntity> firstBookings = agedMoDao.queryFirstBookingByMos(moList);
				return firstBookings;
			}
		}
		
		return null;
	}

}
