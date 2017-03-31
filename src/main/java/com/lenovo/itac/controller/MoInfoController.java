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
import com.lenovo.itac.entity.SnInfoEntity;
import com.lenovo.itac.export.Exporter;
import com.lenovo.itac.export.MoInfoExporter;
import com.lenovo.itac.export.SnsExporter;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.model.MoInfoModel;
import com.lenovo.itac.model.SnInfoModel;
import com.lenovo.itac.service.MoInfoService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping(value="/mo", method=RequestMethod.POST)
public class MoInfoController {
	
	private static Logger logger = LoggerFactory.getLogger(MoInfoController.class);
	
	@Autowired
	private MoInfoService moInfoService;

	@RequestMapping(value="/querymo", method=RequestMethod.POST)
	public ResponseEntity queryMos(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String mos = request.getParameter("mos");
		logger.info("query - the mo query condition is: {}", mos);
		if (StringUtils.isEmpty(mos)) {
			response.setCode(ResponseEntity.CODE_INFO_MO_EMPTY);
		} else {
			String[] arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			List<MoInfoModel> entities = moInfoService.queryMoInfoByMos(arr);
			
			response.setData(entities);
			response.setTotal(entities.size());
			logger.info("query - query mo list success.");
		}
		
		return response;
	}
	
	@RequestMapping(value="/export/mos", method=RequestMethod.GET)
	public void exportMos(HttpServletRequest request, HttpServletResponse response) {
		String mos = request.getParameter("mos");
		if (!StringUtils.isEmpty(mos)) {
			String[] arr = mos.split(CommonUtils.CHARACTER_COMMA);
			List<MoInfoModel> entities = moInfoService.queryMoInfoByMos(arr);
			
			Exporter exporter = new MoInfoExporter(entities);
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
	
	@RequestMapping(value="/querysn", method=RequestMethod.POST)
	public ResponseEntity querySns(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		String mos = request.getParameter("mos");
		logger.info("querySns - the mo query condition is: {}", mos);
		if (StringUtils.isEmpty(mos)) {
			response.setCode(ResponseEntity.CODE_INFO_MO_EMPTY);
		} else {
			String[] arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			List<SnInfoEntity> entities = moInfoService.querySnInfoByMos(arr);
			List<SnInfoModel> models = convert(entities);
			
			response.setData(models);
			response.setTotal(models.size());
			logger.info("querySns - query sn list success.");
		}
		
		return response;
	}
	
	@RequestMapping(value="/export/sns", method=RequestMethod.GET)
	public void exportSns(HttpServletRequest request, HttpServletResponse response) {
		String mos = request.getParameter("mos");
		logger.info("querySns - the mo query condition is: {}", mos);
		if (!StringUtils.isEmpty(mos)) {
			String[] arr = mos.split(CommonUtils.CHARACTER_COMMA);
			List<SnInfoEntity> entities = moInfoService.querySnInfoByMos(arr);
			List<SnInfoModel> models = convert(entities);
			
			Exporter exporter = new SnsExporter(models);
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
	
	public List<SnInfoModel> convert(List<SnInfoEntity> entities) {
		List<SnInfoModel> models = Lists.newArrayList();
		
		if (null != entities) {
			for (SnInfoEntity e : entities) {
				SnInfoModel m = new SnInfoModel();
				m.setMo(e.getMo());
				m.setSn(e.getSn());
				m.setWorkStep(e.getWorkStep());
				m.setLastWsTime(CommonUtils.format(e.getLastWsTime()));
				models.add(m);
			}
		}
		
		return models;
	}
}
