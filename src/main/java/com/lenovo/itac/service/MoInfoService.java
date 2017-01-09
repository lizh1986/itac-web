package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.SnInfoEntity;
import com.lenovo.itac.model.MoInfoModel;

public interface MoInfoService {

	public List<MoInfoModel> queryMoInfoByMos(String[] arr);
	
	public List<SnInfoEntity> querySnInfoByMos(String[] arr);
}
