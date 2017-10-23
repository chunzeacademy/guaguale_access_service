package com.company.guaguale.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.company.guaguale.domain.AccountBalance;
import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.domain.RestResult;

public class RestTemplateTest {
private static RestTemplate template = new RestTemplate();
	String simsBaseUrl = "http://101.200.166.163:9100/guaguale/";
	//String simsBaseUrl = "http://127.0.0.1:9100/guaguale/";

	public static void main(String[] args)
	{
		RestTemplateTest restTemplateTest = new RestTemplateTest();
		int i=10;
		while(i<10)
		{
			i++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long c = System.currentTimeMillis();
			System.out.print(System.currentTimeMillis());
			restTemplateTest.plusBonus();
			System.out.print(" time:(" + (System.currentTimeMillis()-c) + ")");
			
		}
		//restTemplateTest.refreshRelation();
		restTemplateTest.queryBalance();
		
		
	}
	
	public void plusBonus()
	{
		RestResult result = new RestResult();
		BonusRequest bonusRequest = new BonusRequest();
		bonusRequest.setUserPayPaltform("test7");
		bonusRequest.setOpenid("openid1");
		bonusRequest.setFourth_deal_no("1234567890abcdefghi");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -3);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		bonusRequest.setNext_client_time(formatter.format(now.getTime()));
		bonusRequest.setSuccess_time("20171014141314");
		bonusRequest.setCurrency("yuan");
		bonusRequest.setOrderPaymentAmount("12334");
		String userPlatFormId = bonusRequest.getUserPayPaltform();
		String openId = bonusRequest.getOpenid();
		System.out.println(simsBaseUrl + userPlatFormId + "/" + openId + "/transferBonus");
		result  = template.postForObject(simsBaseUrl + userPlatFormId + "/" + openId + "/transferBonus" , bonusRequest, RestResult.class);
		System.out.println("restResultInfo = " + result);
		/**
		 * retCode 0 --成功；
		 */
	}
	
	public void queryBalance()
	{
		RestResult result = new RestResult();
		String userPlatFormId = "wechat";
		String openId = "o97mfwQwyR09ANdVDYDSizVEHg5Y";
		//String userPlatFormId = "test7";
		//String openId = "openid1";
		
		result  = template.getForObject(simsBaseUrl + userPlatFormId + "/" + openId + "/" + "balance", RestResult.class);
		//template.getForObject(url, responseType, uriVariables)
		System.out.println("query Balance restResultInfo = " + result);
		/**
		 * retCode 0 --成功； 
		 * responseInfo 保存 List<AccountBalance> .
		 * 
		 */
	}
	
	public void refreshRelation()
	{
		RestResult result = new RestResult();
		String userPlatFormId = "wechat";
		String openId = "o97mfwQwyR09ANdVDYDSizVEHg5Y";
		
		///{userPayPlatform}/{openId}/refreshRelation
		result  = template.getForObject(simsBaseUrl + userPlatFormId + "/" + openId + "/" + "refreshRelation", RestResult.class);
		//template.getForObject(url, responseType, uriVariables)
		System.out.println("query Balance restResultInfo = " + result);
		/**
		 * retCode 0 --成功； 
		 * responseInfo 保存 List<AccountBalance> .
		 * 
		 */
	}
	
}
