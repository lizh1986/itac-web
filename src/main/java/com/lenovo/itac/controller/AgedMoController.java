package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.lenovo.itac.entity.AgedMoEntity;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.model.AgedMoModel;
import com.lenovo.itac.service.AgedMoService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping(value="/agedmo")
public class AgedMoController {

	@Autowired
	private AgedMoService agedMoService;
	
	@RequestMapping(value="/query")
	public ResponseEntity query(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String mos = request.getParameter("mos");
		if (StringUtils.isEmpty(mos)) {
			response.setCode(ResponseEntity.CODE_INFO_MO_EMPTY);
		} else {
			String[] arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			List<AgedMoEntity> agedMos = agedMoService.queryByMos(arr);
			List<AgedMoModel> models = convert(agedMos);
			
			response.setData(models);
			response.setTotal(models.size());
		}
		
		return response;
	}
	
	private List<AgedMoModel> convert(List<AgedMoEntity> entities) {
		List<AgedMoModel> models = Lists.newArrayList();
		if (entities != null) {
			for (AgedMoEntity entity : entities) {
				AgedMoModel model = new AgedMoModel();
				model.setMo(entity.getMo());
				model.setCreated(CommonUtils.format(entity.getCreated()));
				model.setAged(CommonUtils.calcDuration(entity.getCreated()));
				models.add(model);
			}
		}
		
		return models;
	}
}
