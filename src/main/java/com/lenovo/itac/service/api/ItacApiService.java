package com.lenovo.itac.service.api;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itac.artes.ihas.ServiceLocator;
import com.itac.mes.imsapi.domain.IMSApiService;
import com.itac.mes.imsapi.domain.container.IMSApiSessionContextStruct;
import com.itac.mes.imsapi.domain.container.IMSApiSessionValidationStruct;
import com.itac.mes.imsapi.domain.container.Result_attribAppendAttributeValues;
import com.itac.mes.imsapi.domain.container.Result_regLogin;
import com.itac.mes.imsapi.domain.container.Result_regLogout;
import com.itac.mes.imsapi.domain.container.Result_trGetNextProductionStep;
import com.itac.mes.imsapi.domain.container.Result_trUploadState;
import com.lenovo.itac.service.api.session.ItacSession;
import com.lenovo.itac.service.impl.LoginServiceImpl;
import com.lenovo.itac.util.Constants;

public class ItacApiService {
	
	private static Logger logger = LoggerFactory.getLogger(ItacApiService.class);
	
	/** 系統的權限是通过用户组来管控的，用户需求需要按照用户来管控，临时方案写死指定的用户，后续做成可配置的页面 */
	public static List<String> excludeUsers = Lists.newArrayList();
	
	private static Map<String, String> testErpGroupAndStation = Maps.newHashMap();
	
	private static final String GET_NEXT_WORKSTEP_PARAM_STATION = "J399COM00000100";

	public static final String CAP_ERP_GROUP = "J310CAP";
	
	private static String stationNumber;
	
	private static IMSApiService imsApiService = null;
	
	static {
		InputStream input = LoginServiceImpl.class.getClassLoader().getResourceAsStream("ihas.properties");
		Properties props = new Properties();
		
		try {
			props.load(input);
			System.setProperty(Constants.ITAC_ARTES_CLUSTERNODES, props.getProperty(Constants.ITAC_ARTES_CLUSTERNODES));
			System.setProperty(Constants.ITAC_APP_ID, props.getProperty(Constants.ITAC_APP_ID));
			stationNumber = props.getProperty(Constants.ITAC_STATION);
			imsApiService = ServiceLocator.getService(IMSApiService.class);
		} catch (Exception e) {
			throw new RuntimeException("Fail to init IMS API ",e);
		}
		
		testErpGroupAndStation.put("J320AVT", "J320AVT4F001A01");
		testErpGroupAndStation.put("J320FLA", "J320FLA4F001A01");
		testErpGroupAndStation.put("J320PCY", "J320PCY4F001A01");
		testErpGroupAndStation.put("J320BUR", "J320BUR4F001A01");
		testErpGroupAndStation.put("J320FVT", "J320FVT4F001A01");
		
		excludeUsers.add("3305LE");
		excludeUsers.add("7184LE");
	}
	
	/**
	 * 调用iTAC的API到服务器进行登录，登录成功则保存Session
	 * @param userName
	 * @param password
	 * @return
	 */
	public static int login(String userName, String password) {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			return -1;
		}
		
		IMSApiSessionValidationStruct sessionValidationStruct = new IMSApiSessionValidationStruct();
		sessionValidationStruct.stationNumber = stationNumber;
		sessionValidationStruct.stationPassword = null;
		sessionValidationStruct.user = userName;
		sessionValidationStruct.password = password;
		sessionValidationStruct.client = Constants.CLIENT_ID;
		sessionValidationStruct.registrationType = Constants.REGISTRATION_TYPE;
		sessionValidationStruct.systemIdentifier = Constants.SYSTEM_ID;
		
		Result_regLogin result = imsApiService.regLogin(sessionValidationStruct);
		if (result.return_value == 0) {
			ItacSession.addSession(userName, result.sessionContext);
		} else {
			logger.error("Failed to login iTAC System, user: {}, Error Code is : {}", userName, result.return_value);
		}
		
		return result.return_value;
	}
	
	/**
	 * 调用iTCA API到服务器注销用户，注销成功则移除Session
	 * @param userName
	 * @return
	 */
	public static int logout(String userName) {
		IMSApiSessionContextStruct value = ItacSession.getSession(userName);
		Result_regLogout result = imsApiService.regLogout(value);
		if (result.return_value == 0) {
			ItacSession.removeSession(userName);
		} else {
			logger.error("Failed to logout, user is {},  error code is: {}", userName, result);
		}
		
		return result.return_value;
	}
	
	private static String[] trGetNextProductionStep(String userName, String serialNumber) {
		IMSApiSessionContextStruct value = ItacSession.getSession(userName);
		if (value != null) {
			Result_trGetNextProductionStep result = imsApiService.trGetNextProductionStep(value, GET_NEXT_WORKSTEP_PARAM_STATION, 
					serialNumber, "1", 0, 0, 0, new String[]{"ERP_GROUP_NUMBER", "WORKSTEP_NUMBER"});
			if (result.return_value == 0) {
				return result.productionStepResultValues;
			} else {
				logger.error("Failed to call trGetNextProductionStep. serial Number: {}, Error Code is {}", serialNumber, result.return_value);
			}
		}
		return null;
	}
	
	/**
	 * 判断某个ERP GROUP 是否是 测试的 ERP GROUP
	 * @param erpGroup
	 * @return
	 */
	public static boolean isTestErpGroup(String erpGroup) {
		return testErpGroupAndStation.keySet().contains(erpGroup);
	}
	
	public static String getNextWorkStep(String userName, String serialNumber) {
		String[] result = trGetNextProductionStep(userName, serialNumber);
		if (result != null && result.length == 2) {
			return result[0];
		}
		return null;
	}
	
	public static int getNextWorkStepNo(String userName, String serialNumber) {
		String[] result = trGetNextProductionStep(userName, serialNumber);
		if (result != null && result.length == 2) {
			return Integer.parseInt(result[1]);
		}
		return 0;
	}
	
	public static int uploadState(String userName, String erpGroup, String serialNumber) {
		IMSApiSessionContextStruct value = ItacSession.getSession(userName);
		if (value != null) {
			String stationNumber = testErpGroupAndStation.get(erpGroup);
			
			// 此API需要传递过站的STATION，所以需要调用getNextWorkStep来得到
			Result_trUploadState result = imsApiService.trUploadState(value, stationNumber, 2, serialNumber, 
					"1", 0, 0, -1, 0, new String[]{"ERROR_CODE", "SERIAL_NUMBER", "SERIAL_NUMBER_STATE"}, 
					new String[]{});
			if (result.return_value != 0) {
				logger.error("Failed to upload state, serial Number: {}, next WorkStep: {}", serialNumber, erpGroup);
			}
			return result.return_value;
		}
		return -1;
	}
	
	public static void setNextWorkStep(String userName, String serialNumber) {
		IMSApiSessionContextStruct value = ItacSession.getSession(userName);
		if (value != null) {
			int nextWorkStepNo = getNextWorkStepNo(userName, serialNumber);
			Result_attribAppendAttributeValues  result = imsApiService.attribAppendAttributeValues(value, GET_NEXT_WORKSTEP_PARAM_STATION, 0,
					serialNumber, "1", -1, 1, new String[]{"ATTRIBUTE_CODE", "ATTRIBUTE_VALUE", "ERROR_CODE"}, 
					new String[]{"NEXT_WORK_STEP", String.valueOf(nextWorkStepNo), "0"});
			if (result.return_value != 0) {
				logger.error("Failed to set next work step for {}", serialNumber);
			}
		}
	}
	
	public static String getStationNumber() {
		return stationNumber;
	}
}
