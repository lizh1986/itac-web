package com.lenovo.itac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.lenovo.itac.dao.MoInfoDao;
import com.lenovo.itac.entity.MoInfoEntity;
import com.lenovo.itac.entity.SnInfoEntity;
import com.lenovo.itac.model.MoAndWSModel;
import com.lenovo.itac.model.MoInfoModel;
import com.lenovo.itac.service.MoInfoService;
import com.lenovo.itac.util.CommonUtils;

@Service
public class MoInfoServiceImpl implements MoInfoService {

	@Autowired
	private MoInfoDao moInfoDao;
	
	@Override
	public List<MoInfoModel> queryMoInfoByMos(String[] arr) {
		if (arr != null) {
			List<MoInfoModel> models = Lists.newArrayList();
			for (String mo : arr) {
				MoInfoEntity info = moInfoDao.queryMoInfoByMo(mo);
				List<MoInfoEntity> children = moInfoDao.queryMoAndWSByMo(mo);
				
				MoInfoModel model = convert(info, children);
				if (model != null) {
					models.add(model);
				}
			}
			return models;
		}
		
		return null;
	}
	
	private MoInfoModel convert(MoInfoEntity info, List<MoInfoEntity> children) {
		if (info != null) {
			MoInfoModel model = new MoInfoModel();
			model.setMo(info.getMo());
			model.setMtm(info.getMtm());
			model.setCreationTime(CommonUtils.format(info.getCreateTime()));
			model.setQuantity(info.getQuantity());
			List<MoAndWSModel> list = Lists.newArrayList();
			model.setChildren(list);
			
			if (children != null) {
				// 是否设置好了第一个必过站
				boolean firstReq = false;
				for (MoInfoEntity entity : children) {
					MoAndWSModel child = new MoAndWSModel();
					child.setWs(entity.getWorkStep());
					child.setPassed(entity.getPassed());
					child.setMo(" ");
					if (!firstReq && "R".equalsIgnoreCase(entity.getPassStationReq())) {
						child.setPending(info.getQuantity() - entity.getPassed() + "," + entity.getPending());
						firstReq = true;
					} else {
						child.setPending(String.valueOf(entity.getPending()));
					}
					
					child.setFirstBooking(CommonUtils.format(entity.getFirstBooking()));
					child.setLastBooking(CommonUtils.format(entity.getLastBooking()));
					//不展示RT Done的记录
					if (!CommonUtils.RT_DONE_STATION.startsWith(child.getWs())) {
						list.add(child);
					}
				}
			}
			return model;
		}
		return null;
	}

	@Override
	public List<SnInfoEntity> querySnInfoByMos(String[] arr) {
		if (arr != null) {
			List<SnInfoEntity> snList = Lists.newArrayList();
			for (String mo : arr) {
				List<SnInfoEntity> infos = moInfoDao.querySnInfoByMos(mo);
				snList.addAll(infos);
			}
			return snList;
		}
		
		return null;
	}

}
