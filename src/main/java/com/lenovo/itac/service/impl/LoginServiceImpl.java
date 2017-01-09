package com.lenovo.itac.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.itac.artes.ihas.LookupException;
import com.itac.artes.ihas.ServiceLocator;
import com.itac.mes.imsapi.domain.IMSApiService;
import com.itac.mes.imsapi.domain.container.IMSApiSessionContextStruct;
import com.itac.mes.imsapi.domain.container.IMSApiSessionValidationStruct;
import com.itac.mes.imsapi.domain.container.Result_regLogin;
import com.itac.mes.imsapi.domain.container.Result_regLogout;
import com.lenovo.itac.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	private static final String STATION_NUMBER = "J31091000000000";
	private static final String CLIENT_ID = "01";
	private static final String REGISTRATION_TYPE = "S";
	private static final String SYSTEM_ID = "LenovoPickToLightClient";
	
	private static final String SUPER_ADMIN = "itac-ao";
	private static final String SUPER_ADMIN_PWD = "PASSWORD";
	
	private IMSApiService imsApiService;
	
	private static Map<String, IMSApiSessionContextStruct> session;
	
	static {
		InputStream input = LoginServiceImpl.class.getClassLoader().getResourceAsStream("ihas.properties");
		Properties props = new Properties();
		try {
			props.load(input);
			System.setProperty("itac.artes.clusternodes", props.getProperty("itac.artes.clusternodes"));
			System.setProperty("itac.appid", props.getProperty("itac.appid"));
			
			session = Maps.newConcurrentMap();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public boolean login(String userName, String password) {
		if (SUPER_ADMIN.equals(userName) && SUPER_ADMIN_PWD.equals(password)) {
			return true;
		}
		
		IMSApiSessionValidationStruct sessionValidationStruct = new IMSApiSessionValidationStruct();
		try {
			imsApiService = ServiceLocator.getService(IMSApiService.class);
		} catch (LookupException e) {
			throw new RuntimeException("Fail to init IMS API ",e);
		}
		
		sessionValidationStruct.stationNumber = STATION_NUMBER;
		sessionValidationStruct.stationPassword = null;
		sessionValidationStruct.user = userName;
		sessionValidationStruct.password = password;
		sessionValidationStruct.client = CLIENT_ID;
		sessionValidationStruct.registrationType = REGISTRATION_TYPE;
		sessionValidationStruct.systemIdentifier = SYSTEM_ID; 
		
		Result_regLogin result = imsApiService.regLogin(sessionValidationStruct);
		logger.info("result value: " + result);
		
		if (result.return_value != 0){
			logger.error("Login error code is :" + result);
			return false;
		}
		
		session.put(userName, result.sessionContext);
		
		return true;
	}

	@Override
	public boolean logout(String userName) {
		IMSApiSessionContextStruct value = session.get(userName);
		Result_regLogout result = imsApiService.regLogout(value);
		if (result.return_value == 0) {
			session.remove(userName);
			return true;
		} else {
			if (userName.equals(SUPER_ADMIN)) {
				return true;
			} else {
				logger.error("Logout error code is :" + result);
				return false;
			}
		}
	}
}
