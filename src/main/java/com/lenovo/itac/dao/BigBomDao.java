package com.lenovo.itac.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.BigBomEntity;

@Repository
public interface BigBomDao {

	public List<BigBomEntity> getAll();
}
