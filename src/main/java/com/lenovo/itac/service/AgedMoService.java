package com.lenovo.itac.service;

import java.util.List;

import com.lenovo.itac.entity.AgedMoEntity;

public interface AgedMoService {
	
	public List<AgedMoEntity> queryByMos(String[] mos);
}
