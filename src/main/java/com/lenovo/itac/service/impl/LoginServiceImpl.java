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
			System.setProperty("itac.artes.clusternodes", props.getProperty("itac.artes.clusternodes"));
			System.setProperty("itac.appid", props.getProperty("itac.appid"));
			stationNumber = props.getProperty("itac.station");
			session = Maps.newConcurrentMap();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Override
	public boolean login(String userName, String password) {
		if (Constants.SUPER_ADMIN.equals(userName) && Constants.SUPER_ADMIN_PWD.equals(password)) {
			return true;
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
			if (userName.equals(Constants.SUPER_ADMIN)) {
				return true;
			} else {
				logger.error("Logout error code is :" + result);
				return false;
			}
		}
	}

	@Override
	public List<String> getUserGroupsByUserName(String userName) {
		return loginDao.getUserGroupsByUserName(userName);
	}
}
