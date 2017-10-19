package com.company.guaguale.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.company.guaguale.domain.AccountBalance;
import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.domain.RestResult;

public class RestTemplateTest {
private static RestTemplate template = new RestTemplate();
	String simsBaseUrl = "http://192.168.1.1:8098/test/";

	public static void main(String[] args)
	{
		RestTemplateTest restTemplateTest = new RestTemplateTest();
		restTemplateTest.plusBonus(null);
		restTemplateTest.queryBalance();
		
		
	}
	
	protected void plusBonus(BonusRequest bonusRequest)
	{
		RestResult result = new RestResult();
		String userPlatFormId = "";
		String openId = "";
		result  = template.postForObject(simsBaseUrl + userPlatFormId + "/" + openId + "/transferBonus" , bonusRequest, RestResult.class);
		System.out.println("restResultInfo = " + result);
		/**
		 * retCode 0 --成功；
		 */
	}
	
	protected void queryBalance()
	{
		RestResult result = new RestResult();
		String userPlatFormId = "";
		String openId = "";
		result  = template.postForObject(simsBaseUrl + userPlatFormId + "/" + openId + "/" + "balance", "", RestResult.class);
		System.out.println("restResultInfo = " + result);
		/**
		 * retCode 0 --成功； 
		 * responseInfo 保存 List<AccountBalance> .
		 * 
		 */
	}
}
