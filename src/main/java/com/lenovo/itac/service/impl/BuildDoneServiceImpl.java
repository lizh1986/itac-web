package com.lenovo.itac.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.lenovo.itac.dao.BuildDoneDao;
import com.lenovo.itac.entity.BuildDoneEntity;
import com.lenovo.itac.service.BuildDoneService;
import com.lenovo.itac.util.CommonUtils;

@Service
public class BuildDoneServiceImpl implements BuildDoneService {

	@Autowired
	private BuildDoneDao buildDoneDao;
	@Override
	public List<BuildDoneEntity> queryBuildDoneByMos(String[] mos) {
		List<String> moList = null;
		
		if (null != mos) {
			moList = Lists.newArrayList();
			for (String mo : mos) {
				String temp = mo.trim();
				if (CommonUtils.validateMO(temp)) {
					moList.add(temp);
				}
			}
		}
		
		List<BuildDoneEntity> entities = buildDoneDao.queryBuildDoneByMos(moList);
		// result 为最终要返回的结果集
		List<BuildDoneEntity> result = Lists.newArrayList();
		if (entities != null) {
			// snList 为已经过了Build Done工位的SN集合
			List<String> snList = Lists.newArrayList();
			Iterator<BuildDoneEntity> it = entities.iterator();
			while(it.hasNext()) {
				BuildDoneEntity e = it.next();
				if(!CommonUtils.BUILD_DONE_STATION.equalsIgnoreCase(e.getStationNumber())
						&& !CommonUtils.RT_DONE_STATION.equalsIgnoreCase(e.getStationNumber())) {
					BuildDoneEntity dest = copy(e);
					dest.setStatus(String.format(CommonUtils.BUILD_DONE_STATUS_FORMAT, dest.getStationNumber().substring(0, 7)));
					result.add(dest);
					it.remove();
				} else {
					snList.add(e.getSn());
				}
			}
			//过滤后，entities中的是过了Build Done的
			if (!snList.isEmpty()) {
				List<String> snWithLotNumber = buildDoneDao.querySNWithLotNumber(snList);
				Iterator<BuildDoneEntity> i = entities.iterator();
				while(i.hasNext()) {
					BuildDoneEntity e = i.next();
					if (snWithLotNumber.contains(e.getSn())) {
						i.remove();
					} else {
						BuildDoneEntity dest = copy(e);
						dest.setStatus(CommonUtils.BUILD_DONE_STATUS_WITHOUT_LOT_NUMBER);
						result.add(dest);
					}
				}
			}
			
			return result;
		}
		
		return null;
	}
	
	private BuildDoneEntity copy(BuildDoneEntity src) {
		BuildDoneEntity dest = new BuildDoneEntity();
		if (src != null) {
			dest.setMo(src.getMo());
			dest.setSn(src.getSn());
			dest.setStationNumber(src.getStationNumber());
			dest.setStatus(src.getStatus());
		}
		return dest;
	}

}
