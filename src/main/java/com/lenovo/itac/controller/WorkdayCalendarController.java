package com.lenovo.itac.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lenovo.itac.entity.OffDay;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.service.OffDayService;
import com.lenovo.itac.util.CommonUtils;

@RestController
@RequestMapping("/workday")
public class WorkdayCalendarController {
	
	@Autowired
	private OffDayService offDayService;

	@RequestMapping("/set")
	public ResponseEntity setOffDay(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		
		try {
			OffDay offDay = new OffDay();
			offDay.setCreated(new Date());
			offDay.setOffDay(CommonUtils.parse(date));
			if (Integer.parseInt(type) == 0) {
				offDayService.delete(CommonUtils.parse(date));
			} else {
				offDayService.insert(offDay);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			response.setCode("0001");
		}
		
		return response;
	}
	
	@RequestMapping("/query")
	public ResponseEntity queryOffDays(HttpServletRequest request) throws ParseException {
		ResponseEntity response = new ResponseEntity();
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		List<String> offDays = offDayService.queryOffDays(
				CommonUtils.parse(start), CommonUtils.parse(end));
		response.setData(offDays);
		
//		try {
//			List<String> offDays = offDayService.queryOffDays(
//					CommonUtils.parse(start), CommonUtils.parse(end));
//			response.setData(offDays);
//		} catch (Exception e) {
//			response.setCode("400");
//		}
		
		return response;
	}
}
