package com.lenovo.itac.service;

import java.util.Date;
import java.util.List;

import com.lenovo.itac.entity.KitOn3FEntity;

public interface KitOn3FService {

	List<KitOn3FEntity> query(Date from, Date to);
}
