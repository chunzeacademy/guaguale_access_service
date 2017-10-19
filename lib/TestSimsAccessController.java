/*      						
 * Copyright 2010 Beijing Xinwei, Inc. All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年6月1日	| wanchunhui 	| 	create the file                       
 */

package com.xinwei.gsm.sims;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.xinwei.gsm.sims.restful.ConfigurationInfo;
import com.xinwei.gsm.sims.restful.FreeAmountInfo;
import com.xinwei.gsm.sims.restful.PacketInfo;
import com.xinwei.gsm.sims.restful.QuerySIMInfo;
import com.xinwei.gsm.sims.restful.RechargeInfo;
import com.xinwei.gsm.sims.restful.RegisterInfo;
import com.xinwei.gsm.sims.restful.RemainSIMInfo;
import com.xinwei.gsm.sims.restful.RestResultInfo;
import com.xinwei.gsm.sims.restful.SIMAccountInfo;
import com.xinwei.gsm.sims.restful.SIMConstants;
import com.xinwei.gsm.sims.restful.SIMResourceInfo;
import com.xinwei.gsm.sims.restful.SIMThresholdInfo;
import com.xinwei.nnl.common.util.JsonUtil;

/**
 * 
 * 类简要描述
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author wangchunhui
 * 
 */

public class TestSimsAccessController
{
	private static RestTemplate template = new RestTemplate();
	
	private static DateFormat dateFormatReq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args)
	{
		
		String simsBaseUrl = "http://172.18.10.11:8098/gsm_sims/";
		// String simsBaseUrl = "http://gsmsim.cootel.com/gsm_sims/";
		String simid = "00861062802721";
		
		// testRechargeForSim(simsBaseUrl, simid);
		//
		// testTopupOneGetMore(simsBaseUrl, simid);
		//
		// testQueryInfoForSim(simsBaseUrl, simid);
		
		// testRechargeForSimNew(simsBaseUrl, simid);
		// testTopupOneGetMoreNew(simsBaseUrl, simid);
		// testQueryInfoForSimNew(simsBaseUrl, simid);
		// testQueryInfoForSimNew(simsBaseUrl, "10000000002");
		
		// testRegisterForSim(simsBaseUrl, simid);
		//
		// testRechargeForSimNew(simsBaseUrl, simid);
		//
		// testTopupOneGetMoreNew(simsBaseUrl, simid);
		
		testSynPacketForSimNew(simsBaseUrl, simid);
		
//		testSynPacketForSim(null, null);
	}
	
	private static void testRegisterForSim(String simsBaseUrl, String simid)
	{
		RegisterInfo registerInfo = new RegisterInfo();
		registerInfo.setSessionID("Session_" + System.currentTimeMillis());
		registerInfo.setSIMID(simid);
		registerInfo.setTransID(registerInfo.getSIMID() + "_" + registerInfo.getSessionID());
		registerInfo.setRegisterTime(dateFormatReq.format(new Date()));
		registerInfo.setMobileCode("30222");
		RestResultInfo restResultInfo = template.postForObject(simsBaseUrl + "registerForSim", registerInfo, RestResultInfo.class);
		System.out.println("restResultInfo = " + restResultInfo);
	}
	
	private static void testRechargeForSimNew(String simsBaseUrl, String simid)
	{
		RechargeInfo rechargeInfo = new RechargeInfo();
		rechargeInfo.setSessionID("Session_" + System.currentTimeMillis());
		rechargeInfo.setOperation(0);
		rechargeInfo.setSIMID(simid);
		rechargeInfo.setTransID(rechargeInfo.getSIMID() + "_" + rechargeInfo.getSessionID());
		rechargeInfo.setChargeTime(dateFormatReq.format(new Date()));
		List<SIMAccountInfo> sIMAccountInfo = new ArrayList<SIMAccountInfo>();
		SIMAccountInfo info = new SIMAccountInfo();
		info.setBalanceItem(101);
		info.setValue(10.65);
		info.setEndTime("2017-06-29 20:30:00");
		sIMAccountInfo.add(info);
		rechargeInfo.setSIMAccountInfo(sIMAccountInfo);
		RestResultInfo restResultInfo = template.postForObject(simsBaseUrl + "rechargeForSim", rechargeInfo, RestResultInfo.class);
		System.out.println("restResultInfo = " + restResultInfo);
	}
	
	private static void testTopupOneGetMoreNew(String simsBaseUrl, String simid)
	{
		FreeAmountInfo freeAmountInfo = new FreeAmountInfo();
		freeAmountInfo.setSessionID("Session_" + System.currentTimeMillis());
		freeAmountInfo.setSIMID(simid);
		freeAmountInfo.setTopupTime(dateFormatReq.format(new Date()));
		freeAmountInfo.setTopupAmount(1);
		freeAmountInfo.setFreeAmount(30);
		freeAmountInfo.setExpireTime("2017-07-01 10:30:00");
		freeAmountInfo.setTransID(freeAmountInfo.getSIMID() + "_" + freeAmountInfo.getSessionID());
		RestResultInfo restResultInfo = template.postForObject(simsBaseUrl + "topupOneGetMore", freeAmountInfo, RestResultInfo.class);
		System.out.println("topupOneGetMore restResultInfo = " + restResultInfo);
	}
	
	private static void testSynPacketForSimNew(String simsBaseUrl, String simid)
	{
		PacketInfo packetInfo = new PacketInfo();
		packetInfo.setSIMID(simid);
		packetInfo.setPacketID(0);
		packetInfo.setOperation(1);
		packetInfo.setSessionID("Session_" + System.currentTimeMillis());
		packetInfo.setTransID(packetInfo.getSIMID() + "_" + packetInfo.getPacketID());
		packetInfo.setQueryTime(dateFormatReq.format(new Date()));
		packetInfo.setPacketEndTime(dateFormatReq.format(new Date()));
		RestResultInfo restResultInfo1 = template.postForObject(simsBaseUrl + "synPacketForSim", packetInfo, RestResultInfo.class);
		System.out.println("synPacketForSim restResultInfo = " + restResultInfo1);
	}
	
	private static void testQueryInfoForSimNew(String simsBaseUrl, String simid)
	{
		QuerySIMInfo querySIMInfo = new QuerySIMInfo();
		querySIMInfo.setSIMID(simid);
		querySIMInfo.setSessionID("Session_" + System.currentTimeMillis());
		
		RemainSIMInfo remainSIMInfo = template.postForObject(simsBaseUrl + "queryInfoForSim", querySIMInfo, RemainSIMInfo.class);
		System.out.println("queryInfoForSim remainSIMInfo = " + remainSIMInfo);
	}
	
	private static void testRechargeForSim(String simsBaseUrl, String simid)
	{
		DateFormat dateFormatReq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simsBaseUrl = "http://gsmsim.cootel.com/gsm_sims/";
		RechargeInfo rechargeInfo = new RechargeInfo();
		rechargeInfo.setSessionID("Session_" + System.currentTimeMillis());
		rechargeInfo.setOperation(1);
		rechargeInfo.setSIMID("10000000001");
		rechargeInfo.setTransID(rechargeInfo.getSIMID() + "_" + rechargeInfo.getSessionID());
		rechargeInfo.setChargeTime(dateFormatReq.format(new Date()));
		List<SIMAccountInfo> sIMAccountInfo = new ArrayList<SIMAccountInfo>();
		SIMAccountInfo info = new SIMAccountInfo();
		info.setBalanceItem(101);
		info.setValue(20);
		info.setEndTime(dateFormatReq.format(new Date()));
		sIMAccountInfo.add(info);
		rechargeInfo.setSIMAccountInfo(sIMAccountInfo);
		RestResultInfo restResultInfo = template.postForObject(simsBaseUrl + "rechargeForSim", rechargeInfo, RestResultInfo.class);
		System.out.println("restResultInfo = " + restResultInfo);
	}
	
	private static void testTopupOneGetMore(String simsBaseUrl, String simid)
	{
		DateFormat dateFormatReq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		simsBaseUrl = "http://gsmsim.cootel.com/gsm_sims/";
		FreeAmountInfo freeAmountInfo = new FreeAmountInfo();
		freeAmountInfo.setSessionID("Session_" + System.currentTimeMillis());
		freeAmountInfo.setSIMID("10000000001");
		freeAmountInfo.setTopupTime(dateFormatReq.format(new Date()));
		freeAmountInfo.setTopupAmount(1);
		freeAmountInfo.setFreeAmount(30);
		freeAmountInfo.setExpireTime(dateFormatReq.format(new Date()));// test
																		// date
																		// please
																		// real
																		// data
		freeAmountInfo.setTransID(freeAmountInfo.getSIMID() + "_" + freeAmountInfo.getSessionID());
		RestResultInfo restResultInfo = template.postForObject(simsBaseUrl + "topupOneGetMore", freeAmountInfo, RestResultInfo.class);
		System.out.println("topupOneGetMore restResultInfo = " + restResultInfo);
	}
	
	private static void testSynPacketForSim(String simsBaseUrl, String simid)
	{
		// simsBaseUrl = "http://gsmsim.cootel.com/gsm_sims/";
		simsBaseUrl = "http://123.231.61.180:8098/gsm_sims/";
		PacketInfo packetInfo = new PacketInfo();
		packetInfo.setSIMID("100001");
		packetInfo.setPacketID(10000);
		packetInfo.setOperation(1);
		packetInfo.setSessionID("Session_" + System.currentTimeMillis());
		packetInfo.setTransID(packetInfo.getSIMID() + "_" + packetInfo.getPacketID());
		packetInfo.setQueryTime(dateFormatReq.format(new Date()));
		
		// String json = JsonUtil.toJson(packetInfo);
		// System.out.println("json = " + json);
		
		RestResultInfo restResultInfo1 = template.postForObject(simsBaseUrl + "synPacketForSim", packetInfo, RestResultInfo.class);
		System.out.println("synPacketForSim restResultInfo = " + restResultInfo1);
		//
		// List<SIMResourceInfo> dataRes = new ArrayList<SIMResourceInfo>();
		// List<SIMResourceInfo> voiceRes = new ArrayList<SIMResourceInfo>();
		// List<SIMResourceInfo> sMSRes = new ArrayList<SIMResourceInfo>();
		// SIMResourceInfo data1 = new SIMResourceInfo();
		// data1.setServiceItem(SIMConstants.DATA_TOAM);
		// data1.setResValue(1024000);
		// data1.setBeginTime(dateFormatReq.format(new Date()));
		// data1.setEndTime(dateFormatReq.format(new Date()));
		// dataRes.add(data1);
		//
		// SIMResourceInfo voice1 = new SIMResourceInfo();
		// voice1.setServiceItem(SIMConstants.VOICE_LOCAL_OFF_NET);
		// voice1.setResValue(200);
		// voice1.setBeginTime(dateFormatReq.format(new Date()));
		// voice1.setEndTime(dateFormatReq.format(new Date()));
		// voiceRes.add(voice1);
		//
		// packetInfo.setDataRes(dataRes);
		// packetInfo.setVoiceRes(voiceRes);
		// packetInfo.setSMSRes(sMSRes);
		
		// RestResultInfo restResultInfo1 = template.postForObject(simsBaseUrl +
		// "synPacketForSim", packetInfo, RestResultInfo.class);
		// System.out.println("synPacketForSim restResultInfo = " +
		// restResultInfo1);
	}
	
	private static void testQueryInfoForSim(String simsBaseUrl, String simid)
	{
		simsBaseUrl = "http://gsmsim.cootel.com/gsm_sims/";
		QuerySIMInfo querySIMInfo = new QuerySIMInfo();
		querySIMInfo.setSIMID("10000000001");
		querySIMInfo.setSessionID("Session_" + System.currentTimeMillis());
		
		RemainSIMInfo remainSIMInfo = template.postForObject(simsBaseUrl + "queryInfoForSim", querySIMInfo, RemainSIMInfo.class);
		System.out.println("queryInfoForSim remainSIMInfo = " + remainSIMInfo);
	}
	
	private static void testConfigSynToBoss(String simsBaseUrl, String simid)
	{
		ConfigurationInfo configurationInfo = new ConfigurationInfo();
		configurationInfo.setSessionID("sssssssssss");
		List<SIMThresholdInfo> sIMThresholdInfo = new ArrayList<SIMThresholdInfo>();
		SIMThresholdInfo thresholdInfo = new SIMThresholdInfo();
		thresholdInfo.setServiceItem(SIMConstants.DATA_TOAM);
		// 10MB
		thresholdInfo.setThresholdValue(10);
		thresholdInfo.setEndTime(dateFormatReq.format(new Date()));
		
		sIMThresholdInfo.add(thresholdInfo);
		configurationInfo.setSIMThresholdInfo(sIMThresholdInfo);
		
		RestResultInfo restResultInfo = template.postForObject(simsBaseUrl + "configSynToBoss", configurationInfo, RestResultInfo.class);
		System.out.println("configSynToBoss restResultInfo = " + restResultInfo);
	}
}
