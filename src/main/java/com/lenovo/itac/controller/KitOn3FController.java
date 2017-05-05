package com.lenovo.itac.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.KitOn3FEntity;
import com.lenovo.itac.export.Exporter;
import com.lenovo.itac.export.KitOn3FExporter;
import com.lenovo.itac.http.response.ResponseCode;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.KitOn3FService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping("/kit3")
public class KitOn3FController {

	private static Logger logger = LoggerFactory.getLogger(KitOn3FController.class);
	
	@Autowired
	private KitOn3FService kitOn3FService;
	
	@RequestMapping("/validate")
	public ResponseEntity validate(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date from,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date to) {
		ResponseEntity response = new ResponseEntity();
		if (from == null || to == null) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			return response;
		}
		
		if (from.after(to)) {
			response.setCode(ResponseCode.RESPONSE_CODE_TIME_RANGE);
			return response;
		}
		
		int intervalDays = CommonUtils.getIntervalDays(from, to);
		if (intervalDays > 10) {
			response.setCode(ResponseCode.RESPONSE_CODE_TIME_RANGE_TOO_LONG);
			return response;
		}
		return response;
	}
	
	@RequestMapping("/query")
	public ResponseEntity query(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date from,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date to) {
		ResponseEntity response = new ResponseEntity();
		
		if (from == null || to == null) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			return response;
		}
		
		if (from.after(to)) {
			response.setCode(ResponseCode.RESPONSE_CODE_TIME_RANGE);
			return response;
		}
		
		int intervalDays = CommonUtils.getIntervalDays(from, to);
		if (intervalDays > 10) {
			response.setCode(ResponseCode.RESPONSE_CODE_TIME_RANGE_TOO_LONG);
			return response;
		}
		
		List<KitOn3FEntity> entities = kitOn3FService.query(from, to);
		if (entities != null) {
			response.setData(entities);
			response.setTotal(entities.size());
		}
		
		return response;
	}
	
	@RequestMapping("/export")
	public void export(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date from,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date to, 
			HttpServletRequest request, HttpServletResponse response) {
		List<KitOn3FEntity> entities = kitOn3FService.query(from, to);
		Exporter exporter = new KitOn3FExporter(entities);
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
			logger.info("Failed to export Kit info on 3F.", e);
		} finally {
			try {
				CommonUtils.close(bos);
				CommonUtils.close(os);
				CommonUtils.close(wb);
			} catch (Exception e) {
				logger.error("Failed to export Kit info on 3F.", e);
			}
		}
	}
	
}
