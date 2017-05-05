package com.lenovo.itac.service.api.session;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.itac.mes.imsapi.domain.container.IMSApiSessionContextStruct;

public class ItacSession {

	private static Map<String, IMSApiSessionContextStruct> sessionMap = Maps.newConcurrentMap();
	
	/**
	 * 用户登录后，向SessionMap中增加用户的Session
	 * @param key
	 * @param session
	 */
	public static void addSession(String key, IMSApiSessionContextStruct session) {
		if (StringUtils.isEmpty(key) || session == null) {
			return;
		}
		
		sessionMap.put(key, session);
	}
	
	/**
	 * 获取某个用户的Session，用于调用其他API的操作
	 * @param key
	 * @return
	 */
	public static IMSApiSessionContextStruct getSession(String key) {
		return sessionMap.get(key);
	}
	
	/**
	 * 移除某个用户的Session，用于注销该用户
	 * @param key
	 * @return
	 */
	public static IMSApiSessionContextStruct removeSession(String key) {
		return sessionMap.remove(key);
	}
}
