package com.lenovo.itac.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.BigBomEntity;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.BigBomService;

/**
 * 查询BOM中物料数量大于999的MO的列表
 * @author lizh18
 *
 */
@RestController
@RequestMapping(value="/bigbom")
public class BigBomController {
	
	private static Logger logger = LoggerFactory.getLogger(BigBomController.class);
	
	@Autowired
	private BigBomService bigBomService;

	@GetMapping(value="/query")
	public ResponseEntity getSnsWithBigBom(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		
		List<BigBomEntity> sns = bigBomService.getAll();
		response.setData(sns);
		response.setTotal(sns != null ? sns.size() : 0);
		
		logger.info("query - query sns with big bom success.");
		return response;
	}
}
