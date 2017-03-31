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

import com.lenovo.itac.entity.BuildDoneEntity;
import com.lenovo.itac.export.BuildDoneExporter;
import com.lenovo.itac.export.Exporter;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.BuildDoneService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping("/builddone")
public class BuildDoneController {
	
	private static Logger logger = LoggerFactory.getLogger(BuildDoneController.class);
	
	@Autowired
	private BuildDoneService buildDoneService;

	@RequestMapping(value="/query", method=RequestMethod.POST)
	public ResponseEntity queryBuildDoneFailList(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String mos = request.getParameter("mos");
		logger.info("query - the build done query condition is: {}", mos);
		
		if (StringUtils.isEmpty(mos)) {
			response.setCode(ResponseEntity.CODE_INFO_MO_EMPTY);
		} else {
			String[] arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			List<BuildDoneEntity> entities = buildDoneService.queryBuildDoneByMos(arr);
			
			response.setData(entities);
			response.setTotal(entities.size());
		}
		logger.info("query - query aged mo list success.");
		
		return response;
	}
	
	@RequestMapping(value="/export", method=RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response) {
		String mos = request.getParameter("mos");
		logger.info("query - the build done query condition is: {}", mos);
		
		if (!StringUtils.isEmpty(mos)) {
			String[] arr = mos.split(CommonUtils.CHARACTER_COMMA);
			List<BuildDoneEntity> entities = buildDoneService.queryBuildDoneByMos(arr);
			
			Exporter exporter = new BuildDoneExporter(entities);
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
}
