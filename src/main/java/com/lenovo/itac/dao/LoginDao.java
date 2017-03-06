package com.lenovo.itac.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {

	public List<String> getUserGroupsByUserName(String userName);
}
