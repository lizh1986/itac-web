package com.lenovo.itac.dao;

import java.util.List;

import com.lenovo.itac.entity.BuildDoneEntity;

public interface BuildDoneDao {

	public List<BuildDoneEntity> queryBuildDoneByMos(List<String> moList);
	
	public List<String> querySNWithLotNumber(List<String> snList);
}
