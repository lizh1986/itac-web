package com.lenovo.itac.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lenovo.itac.entity.UserGroupEntity;

@Repository
public interface LoginDao {

	public List<String> getUserGroupsByUserName(String userName);
	
	public List<UserGroupEntity> getAllUserGroups();
}
