package com.lenovo.itac.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.lenovo.itac.entity.AgedMoEntity;
import com.lenovo.itac.export.AgedMoExporter;
import com.lenovo.itac.export.Exporter;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.model.AgedMoModel;
import com.lenovo.itac.service.AgedMoService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping(value="/agedmo")
public class AgedMoController {

	private static Logger logger = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private AgedMoService agedMoService;
	
	@RequestMapping(value="/query")
	public ResponseEntity query(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String mos = request.getParameter("mos");
		logger.info("query - the aged mo query condition is: {}", mos);
		
		if (StringUtils.isEmpty(mos)) {
			response.setCode(ResponseEntity.CODE_INFO_MO_EMPTY);
		} else {
			String[] arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			List<AgedMoEntity> agedMos = agedMoService.queryByMos(arr);
			List<AgedMoModel> models = convert(agedMos);
			
			response.setData(models);
			response.setTotal(models.size());
		}
		
		logger.info("query - query aged mo list success.");
		
		return response;
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response) {
		String mos = request.getParameter("mos");
		logger.info("query - the aged mo query condition is: {}", mos);
		
		if (!StringUtils.isEmpty(mos)) {
			String[] arr = mos.split(CommonUtils.CHARACTER_COMMA);
			List<AgedMoEntity> agedMos = agedMoService.queryByMos(arr);
			List<AgedMoModel> models = convert(agedMos);
			
			Exporter exporter = new AgedMoExporter(models);
			HSSFWorkbook wb = exporter.export();
			
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setContentType(request.getServletContext().getMimeType(exporter.getExportFileName()));
	        response.setHeader("Content-Disposition", "attachment;filename=" + exporter.getExportFileName());
	        
	        OutputStream os = null;
	        BufferedOutputStream bos = null;
	        try {
				os = response.getOutputStream();
				bos = new BufferedOutputStream(os);
				bos.flush();
				wb.write(bos);
				
			} catch (IOException e) {
				logger.info("Failed to export mo info", e);
			} finally {
				try {
					CommonUtils.close(bos);
					CommonUtils.close(os);
					CommonUtils.close(wb);
				} catch (Exception e) {
					logger.error("Failed to export mo info.", e);
				}
			}
		}
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
