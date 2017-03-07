package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.PlantService;

@RestController
@RequestMapping("/plant")
public class PlantController {

	@Autowired
	private PlantService plantService;
	
	@RequestMapping("/query")
	public ResponseEntity query(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		List<String> plantNames = plantService.getAllPlantNames();
		response.setData(plantNames);
		return response;
	}
}
