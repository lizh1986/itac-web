package com.lenovo.itac.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lenovo.itac.entity.LoginUserInfo;
import com.lenovo.itac.entity.UserGroupEntity;
import com.lenovo.itac.entity.UserRoleEntity;
import com.lenovo.itac.http.response.ResponseCode;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.LoginService;
import com.lenovo.itac.service.UserRoleService;

@RestController
@RequestMapping("/role")
public class UserRoleController {

	private static Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/getRoles")
	public ResponseEntity getUserRoles(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String name = request.getParameter("name");
		
		Map<String, String> params = Maps.newHashMap();
		params.put("name", name);
		String plant = ((LoginUserInfo)request.getSession().getAttribute("user")).getPlant();
		params.put("plant", plant);
		
		logger.info("getUserRoles - the condition is - name:{}, plant: {}", name, plant);
		List<UserRoleEntity> roles = userRoleService.getUserRoles(params);
		if (null != roles) {
			response.setData(roles);
			response.setTotal(roles.size());
		}
		
		return response;
	}
	
	@RequestMapping("/getRole")
	public ResponseEntity getUserRoleById(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String id = request.getParameter("id");
		logger.info("getUserRoleById - the condition is - id:{}", id);
		if (!StringUtils.isEmpty(id)) {
			UserRoleEntity role = userRoleService.getUserRoleById(id);
			response.setData(role);
		} else {
			logger.error("getUserRoleById - " + "id is null");
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
		}
		
		return response;
	}
	
	@RequestMapping(value="/addRole", method=RequestMethod.POST)
	public ResponseEntity addUserRole(UserRoleEntity userRole, HttpServletRequest request) { 
		ResponseEntity response = new ResponseEntity();
		
		if (null == userRole) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			logger.error("addUserRole - " + "userRole is null");
			return response;
		}
		
		String userGroup = userRole.getUserGroup();
		if (!StringUtils.isEmpty(userGroup)) {
			String plant = ((LoginUserInfo)request.getSession().getAttribute("user")).getPlant();
			userRole.setPlant(plant);
			UserRoleEntity existRole = userRoleService.getUserRoleByUserGroupAndPlant(userGroup, plant);
			if (null == existRole) {
				userRoleService.addUserRole(userRole);
			} else {
				response.setCode(ResponseCode.RESPONSE_CODE_RECORD_EXIST);
				logger.error("addUserRole - " + "The same record exists already, {}", existRole);
			}
		} else {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			logger.error("addUserRole - " + "userName is null");
		}
		
		return response;
	}
	
	@RequestMapping(value="/updateRole", method=RequestMethod.POST)
	public ResponseEntity updateUserRole(UserRoleEntity userRole, HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		if (null == userRole) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			logger.error("updateUserRole - " + "userRole is null");
			return response;
		}
		
		String userGroup = userRole.getUserGroup();
		if (!StringUtils.isEmpty(userGroup)) {
			String plant = ((LoginUserInfo)request.getSession().getAttribute("user")).getPlant();
			userRole.setPlant(plant);
			UserRoleEntity existRole = userRoleService.getUserRoleByUserGroupAndPlant(userGroup, plant);
			if (null == existRole) {
				userRoleService.addUserRole(userRole);
			} else {
				userRoleService.updateUserRole(userRole);
			}
		} else {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			logger.error("updateUserRole - " + "userName is null");
		}
		
		return response;
	}
	
	@RequestMapping(value="/deleteRoles", method=RequestMethod.POST)
	public ResponseEntity deleteUserRoles(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String ids = request.getParameter("ids");
		if (ids == null) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			logger.error("deleteUserRole - " + "ids is null");
			return response;
		}
		String[] idArray = ids.split(",");
		
		List<String> idList = Lists.newArrayList();
		List<String> failedList = Lists.newArrayList();
		if (idArray != null) {
			for (String id : idArray) {
				List<String> menuIds = userRoleService.getMenuIdsByRoleId(id);
				if (menuIds == null || menuIds.size() == 0) {
					idList.add(id);
				} else {
					failedList.add(id);
				}
			}
		}
		
		userRoleService.deleteUserRoles(idList);
		
		if (failedList.size() != 0) {
			response.setCode(ResponseCode.RESPONSE_CODE_ROLE_HAS_BEEN_ASSIGNED);
			response.setMsg(ResponseCode.RESPONSE_CODE_ROLE_HAS_BEEN_ASSIGNED_MSG + "\r\n" + failedList);
		}
		
		return response;
	}
	
	@RequestMapping("/getPermissionByRoleId")
	public ResponseEntity getPermissionsByRoleId(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String id = request.getParameter("id");
		if (id == null) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			logger.error("getPermissionsByRoleId - " + "id is null");
			return response;
		}
		List<String> menuIds = userRoleService.getMenuIdsByRoleId(id);
		response.setData(menuIds);
		
		return response;
	}
	
	@RequestMapping("/assignPermission")
	public ResponseEntity assignPermission(String roleId, String[] menuIds) {
		ResponseEntity response = new ResponseEntity();
		userRoleService.assignPermission(roleId, menuIds);
		return response;
	}
	
	@RequestMapping("/getAllUserGroups")
	public ResponseEntity getAllUserGroups() {
		ResponseEntity response = new ResponseEntity();
		List<UserGroupEntity> userGroups = loginService.getAllUserGroups();
		response.setData(userGroups);
		return response;
	}
}
