package com.lenovo.itac.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.KitOn3FEntity;

@Repository
public interface KitOn3FDao {

	public List<KitOn3FEntity> query(Map<String, Object> params);
}
