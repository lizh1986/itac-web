package com.lenovo.itac.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.LoginUserInfo;
import com.lenovo.itac.http.response.ResponseEntity;

@RestController
public class LoginController {

	@RequestMapping(value = "/login")
	public ResponseEntity login(LoginUserInfo info, HttpServletRequest request) {
		String loginName = info.getLoginName();
		String password = info.getPassword();
		if ("admin".equalsIgnoreCase(loginName) && "admin".equalsIgnoreCase(password)) {
			return new ResponseEntity();
		}
		return new ResponseEntity();
	}
}
