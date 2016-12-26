package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.BuildDoneEntity;

public interface BuildDoneService {

	public List<BuildDoneEntity> queryBuildDoneByMos(String[] mos);
}
