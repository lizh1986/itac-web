package com.lenovo.itac.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.itac.artes.ihas.LookupException;
import com.itac.artes.ihas.ServiceLocator;
import com.itac.mes.imsapi.domain.IMSApiService;
import com.itac.mes.imsapi.domain.container.IMSApiSessionContextStruct;
import com.itac.mes.imsapi.domain.container.IMSApiSessionValidationStruct;
import com.itac.mes.imsapi.domain.container.Result_regLogin;
import com.lenovo.itac.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	private static final String STATION_NUMBER = "J31091000000000";
	private static final String CLIENT_ID = "01";
	private static final String REGISTRATION_TYPE = "S";
	private static final String SYSTEM_ID = "LenovoPickToLightClient";
	
	private IMSApiService imsApiService;
	private IMSApiSessionContextStruct sessionContext;
	
	@Override
	public boolean login(String userName, String password) {
		
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
			logger.error("Login erro code is :" + result);
			return false;
		}
		
		this.sessionContext = result.sessionContext;
		
		return true;
	}

	@Override
	public void logout() {
		imsApiService.regLogout(sessionContext);
	}
}
