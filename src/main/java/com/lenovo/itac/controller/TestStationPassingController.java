package com.lenovo.itac.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.lenovo.itac.entity.LoginUserInfo;
import com.lenovo.itac.entity.TestStationPassingRecord;
import com.lenovo.itac.http.response.ResponseCode;
import com.lenovo.itac.http.response.ResponseEntity;
import com.lenovo.itac.model.NextWorkStepOfSN;
import com.lenovo.itac.service.TestStationPassingService;
import com.lenovo.itac.service.api.ItacApiService;
import com.lenovo.itac.util.CommonUtils;

/**
 * 临时的一个小功能：让用户输入SN列表，将列表中的SN自动过测试工位，并保存自动过站的记录
 * @author lizh18
 *
 */
@RestController
@RequestMapping("/teststation")
public class TestStationPassingController {

	private static Logger logger = LoggerFactory.getLogger(TestStationPassingController.class);
	
	@Autowired
	private TestStationPassingService testStationPassingService;
	
	@RequestMapping("/pass")
	public ResponseEntity pass(HttpServletRequest request) {
		ResponseEntity response = new ResponseEntity();
		String sns = request.getParameter("sns");
		logger.info("query - the query condition is: {}", sns);
		if (StringUtils.isEmpty(sns)) {
			response.setCode(ResponseCode.RESPONSE_CODE_PARAM_IS_NULL);
			return response;
		}
		
		List<NextWorkStepOfSN> list = Lists.newArrayList();
		
		String currentUser = ((LoginUserInfo)request.getSession().getAttribute("user")).getLoginName();
		
		String[] array = sns.split(CommonUtils.CHARACTER_NEW_LINE);
		for (String sn : array) {
			String erpGroup = ItacApiService.getNextWorkStep(currentUser, sn);
			// 如果某 SN 不在 测试的工位， 则记录其当前工位并放入返回列表中，如果在测试工位，则需要进一步处理（过站）
			if (!ItacApiService.isTestErpGroup(erpGroup)) {
				NextWorkStepOfSN item = new NextWorkStepOfSN(sn, erpGroup);
				list.add(item);
			} else {
				while (!erpGroup.equalsIgnoreCase(ItacApiService.CAP_ERP_GROUP)) {
					int result = ItacApiService.uploadState(currentUser, erpGroup, sn);
					// 如果过站失败，则记录当前工位并放入返回列表
					if (result != 0) {
						NextWorkStepOfSN item = new NextWorkStepOfSN(sn, erpGroup);
						item.setDesc("Failed to Pass Station.");
						list.add(item);
						break;
					}
					
					try {
						TestStationPassingRecord record = new TestStationPassingRecord();
						record.setNextWorkStep(erpGroup);
						record.setOperator(currentUser);
						record.setSerialNumber(sn);
						record.setTime(new Date());
						testStationPassingService.insert(record);
					} catch (Exception e) {
						logger.error("Failed to save test station passing record, serialNumber is {}, nextWorkStep = {}", sn, erpGroup);
						logger.error("Failed to save test station passing record", e);
					}
					
					erpGroup = ItacApiService.getNextWorkStep(currentUser, sn);
				}
				
				ItacApiService.setNextWorkStep(currentUser, sn);
				NextWorkStepOfSN item = new NextWorkStepOfSN(sn, erpGroup);
				list.add(item);
			}
		}
		
		response.setData(list);
		response.setTotal(list.size());
		return response;
	}
}
