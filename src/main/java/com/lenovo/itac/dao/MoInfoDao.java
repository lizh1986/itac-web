package com.lenovo.itac.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.MoInfoEntity;
import com.lenovo.itac.entity.SnInfoEntity;

@Repository
public interface MoInfoDao {

	public MoInfoEntity queryMoInfoByMo(String mo);
	
	public List<MoInfoEntity> queryMoAndWSByMo(String mo);
	
	public List<SnInfoEntity> querySnInfoByMos(String mo);
}
