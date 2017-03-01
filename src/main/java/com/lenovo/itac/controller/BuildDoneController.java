package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.BuildDoneEntity;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.BuildDoneService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping("/builddone")
public class BuildDoneController {
	
	@Autowired
	private BuildDoneService buildDoneService;

	@RequestMapping(value="/query", method=RequestMethod.POST)
	public ResponseEntity queryBuildDoneFailList(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String mos = request.getParameter("mos");
		if (StringUtils.isEmpty(mos)) {
			response.setCode(ResponseEntity.CODE_INFO_MO_EMPTY);
		} else {
			String[] arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			List<BuildDoneEntity> entities = buildDoneService.queryBuildDoneByMos(arr);
			
			response.setData(entities);
			response.setTotal(entities.size());
		}
		
		
		return response;
	}
}
