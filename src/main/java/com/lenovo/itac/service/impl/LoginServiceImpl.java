package com.lenovo.itac.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.itac.artes.ihas.LookupException;
import com.itac.artes.ihas.ServiceLocator;
import com.itac.mes.imsapi.domain.IMSApiService;
import com.itac.mes.imsapi.domain.container.IMSApiSessionContextStruct;
import com.itac.mes.imsapi.domain.container.IMSApiSessionValidationStruct;
import com.itac.mes.imsapi.domain.container.Result_regLogin;
import com.itac.mes.imsapi.domain.container.Result_regLogout;
import com.lenovo.itac.dao.LoginDao;
import com.lenovo.itac.entity.UserGroupEntity;
import com.lenovo.itac.service.LoginService;
import com.lenovo.itac.util.Constants;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	private IMSApiService imsApiService;
	
	private static String stationNumber;
	
	private static Map<String, IMSApiSessionContextStruct> session;
	
	@Autowired
	private LoginDao loginDao;
	
	static {
		InputStream input = LoginServiceImpl.class.getClassLoader().getResourceAsStream("ihas.properties");
		Properties props = new Properties();
		try {
			props.load(input);
			System.setProperty(Constants.ITAC_ARTES_CLUSTERNODES, props.getProperty(Constants.ITAC_ARTES_CLUSTERNODES));
			System.setProperty(Constants.ITAC_APP_ID, props.getProperty(Constants.ITAC_APP_ID));
			stationNumber = props.getProperty(Constants.ITAC_STATION);
			session = Maps.newConcurrentMap();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public int login(String userName, String password) {
		if (Constants.SUPER_ADMIN.equals(userName) && Constants.SUPER_ADMIN_PWD.equals(password)) {
			return 0;
		}
		
		IMSApiSessionValidationStruct sessionValidationStruct = new IMSApiSessionValidationStruct();
		try {
			imsApiService = ServiceLocator.getService(IMSApiService.class);
		} catch (LookupException e) {
			throw new RuntimeException("Fail to init IMS API ",e);
		}
		
		sessionValidationStruct.stationNumber = stationNumber;
		sessionValidationStruct.stationPassword = null;
		sessionValidationStruct.user = userName;
		sessionValidationStruct.password = password;
		sessionValidationStruct.client = Constants.CLIENT_ID;
		sessionValidationStruct.registrationType = Constants.REGISTRATION_TYPE;
		sessionValidationStruct.systemIdentifier = Constants.SYSTEM_ID; 
		
		Result_regLogin result = imsApiService.regLogin(sessionValidationStruct);
		logger.info("result value: " + result);
		
		if (result.return_value == 0) {
			session.put(userName, result.sessionContext);
		}
		
		return result.return_value;
	}

	@Override
	public int logout(String userName) {
		if (Constants.SUPER_ADMIN.equalsIgnoreCase(userName)) {
			return 0;
		} else {
			IMSApiSessionContextStruct value = session.get(userName);
			Result_regLogout result = imsApiService.regLogout(value);
			if (result.return_value == 0) {
				session.remove(userName);
			} else {
				logger.error("Logout error code is :" + result);
			}
			return result.return_value;
		}
	}

	@Override
	public List<String> getUserGroupsByUserName(String userName) {
		return loginDao.getUserGroupsByUserName(userName);
	}

	@Override
	public List<UserGroupEntity> getAllUserGroups() {
		return loginDao.getAllUserGroups();
	}
}
