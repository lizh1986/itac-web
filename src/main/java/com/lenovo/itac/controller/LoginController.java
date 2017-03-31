package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.LoginUserInfo;
import com.lenovo.itac.entity.PlantEntity;
import com.lenovo.itac.http.response.ResponseCode;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.LoginService;
import com.lenovo.itac.service.PlantService;
import com.lenovo.itac.util.CommonUtils;
import com.lenovo.itac.util.Constants;

@RestController
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PlantService plantService;
	
	@RequestMapping(value = "/login")
	public ResponseEntity doLogin(LoginUserInfo info, HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String loginName = info.getLoginName();
		String password = info.getPassword();
		logger.info("doLogin - loginName:{}, password:******", loginName);
		
		int result = loginService.login(loginName, password);
		if (result == 0) {
			String clusterNodes = System.getProperty(Constants.ITAC_ARTES_CLUSTERNODES);
			List<String> ips = CommonUtils.getIpsFromString(clusterNodes);
			if (ips != null) {
				PlantEntity plant = null;
				for (String ip : ips) {
					plant = plantService.getPlantByIp(ip);
					if (plant != null) {
						info.setPlant(plant.getName());
						break;
					}
				}
			}
			
			HttpSession session = request.getSession();
			if (session.getAttribute("user") == null) {
				session.setAttribute("user", info);
			}
		} else {
			response.setCode(ResponseCode.FAILED_TO_LOG_IN);
			response.setMsg(String.format(ResponseCode.FAILED_TO_LOG_IN_MSG, result));
			logger.error("User: {} failed to log out. the error code is : {}", loginName, result);
		}
		return response;
	}
	
	@RequestMapping(value="/logout")
	public ResponseEntity doLogout(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		LoginUserInfo info = (LoginUserInfo)request.getSession().getAttribute("user");
		if (info == null) {
			response.setCode(ResponseCode.SESSION_TIME_OUT);
			logger.error("User can not be found in session.");
		} else {
			int result = loginService.logout(info.getLoginName());
			if (result == 0) {
				request.getSession().removeAttribute("user");
				logger.info("User: {} Succeed to log out.", info.getLoginName());
			} else {
				response.setCode(ResponseCode.FAILED_TO_LOG_OUT);
				response.setMsg(String.format(ResponseCode.FAILED_TO_LOG_OUT_MSG, result));
				logger.error("User: {} failed to log out. the error code is : {}", info.getLoginName(), result);
			}
		}
		
		return response;
	}
}
