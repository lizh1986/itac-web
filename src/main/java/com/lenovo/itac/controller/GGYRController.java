package com.lenovo.itac.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.lenovo.itac.entity.GGYREntity;
import com.lenovo.itac.export.Exporter;
import com.lenovo.itac.export.GGYRExporter;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.model.GGYRModel;
import com.lenovo.itac.service.GGYRService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping("/ggyr")
public class GGYRController {

	private static Logger logger = LoggerFactory.getLogger(GGYRController.class);
	
	@Autowired
	private GGYRService ggyrService;
	
	@RequestMapping(value="/query", method=RequestMethod.POST)
	public ResponseEntity query(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
		int rows = Integer.parseInt(request.getParameter("rows") != null ? request.getParameter("rows") : "20");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		
		String mos = request.getParameter("mos");
		
		logger.info("query - the ggyr query condition -  page: {}, rows: {}, sort: {}, order: {}, mos: {}"
				, page, rows, sort, order, mos);
		
		String[] arr = null;
		if (null != mos && StringUtils.isNotEmpty(mos)) {
			arr = mos.split(CommonUtils.CHARACTER_NEW_LINE);
			
			//第一页时，需要弹出找不到对应MO的提示
			if (page == 1) {
				List<String> notExistItems = ggyrService.notExists(arr);
				
				StringBuilder sb = new StringBuilder();
				if (!notExistItems.isEmpty()) {
					for (String mo : notExistItems) {
						sb.append(mo).append(CommonUtils.CHARACTER_NEW_LINE);
					}
					sb.delete(sb.length() - 2, sb.length());
				}
				
				if (sb.length() > 0) {
					response.setMsg(String.format("The MOs below could not be found.\\n%s", sb.toString()));
				}
			}
		}
		
		List<GGYREntity> entities = ggyrService.queryByMOs(page, rows, arr, sort, order);
		int total = ggyrService.getTotalCount(arr);
		
		logger.info("query ggyr info success, total is : {}", total);
  		response.setData(convert(entities));
  		response.setTotal(total);
		return response;
	}
	
	@RequestMapping(value="/export", method=RequestMethod.GET)
	public void exportGGYR(HttpServletRequest request, HttpServletResponse response) {
		String mos = request.getParameter("mos");
		if (null != mos && StringUtils.isNotEmpty(mos)) {
			String[] arr = mos.split(CommonUtils.CHARACTER_COMMA);
			List<GGYREntity> result = ggyrService.queryByMos(arr);
			
			if (CommonUtils.isNotEmpty(result)) {
				List<GGYRModel> models = convert(result);
				
				Exporter exporter = new GGYRExporter(models);
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
					logger.info("Failed to export ggyr info", e);
				} finally {
					try {
						CommonUtils.close(bos);
						CommonUtils.close(os);
						CommonUtils.close(wb);
					} catch (Exception e) {
						logger.error("Failed to export ggyr info.", e);
					}
				}
			}
		}
	}
	
	private List<GGYRModel> convert(List<GGYREntity> entities) {
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
