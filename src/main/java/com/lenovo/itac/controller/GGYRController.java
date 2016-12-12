package com.lenovo.itac.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.GGYREntity;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.model.GGYRModel;
import com.lenovo.itac.service.GGYRService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping("/ggyr")
public class GGYRController {

	@Autowired
	private GGYRService ggyrService;
	
	@RequestMapping("/query")
	public ResponseEntity query() throws ParseException {
  		
  		List<GGYREntity> entities = ggyrService.query();
  		ResponseEntity entity = new ResponseEntity(convert(entities));
  		
		return entity;
	}
	
	public List<GGYRModel> convert(List<GGYREntity> entities) {
		List<GGYRModel> models = new ArrayList<GGYRModel>();
		
		if (null != entities) {
			for (GGYREntity entity : entities) {
				GGYRModel model = new GGYRModel();
				model.setIdocNum(entity.getIdocNum());
				model.setMo(entity.getMo());
				model.setGgyr(entity.getGgyr());
				model.setPssd(CommonUtils.format(entity.getPssd()));
				model.setRpssd(CommonUtils.format(entity.getRpssd()));
				model.setStamp(CommonUtils.format(entity.getStamp()));
				model.setCreated(CommonUtils.format(entity.getCreated()));
				models.add(model);
			}
		}
		
		return models;
	}
}
