package com.company.guaguale.controller.rest;

import java.security.PrivateKey;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.guaguale.Const.GuaGuaLeConst;
import com.company.guaguale.domain.AccountInfo;
import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.domain.PayReqeust;
import com.company.guaguale.domain.RestResult;
import com.company.guaguale.service.AccountService;
import com.company.guaguale.service.impl.CrcUtils;
import com.xinwei.nnl.common.domain.ProcessResult;

@RestController
@RequestMapping("/guaguale")
public class GuaGualeAccessController {
	  @Resource(name="accountService")
	  private AccountService accountService;
	/**
	 * 申请订单ID
	 * @param countryCode
	 * @param jsonString
	 * @return  String userPayPlatform,String openId
	 */
	@RequestMapping(method = RequestMethod.GET,value = "/{userPayPlatform}/{openId}/balance")
	public  RestResult getBalance(@PathVariable String userPayPlatform,@PathVariable String openId,@RequestBody String jsonString) {
		RestResult restResult =new RestResult();
		restResult.setRetCode(GuaGuaLeConst.RESULT_Error_Fail);
		try {
			ProcessResult processResult=accountService.getBalance(userPayPlatform, openId);
			restResult = getRestResult(processResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restResult;
	}
	
	
	
	
	protected RestResult getRestResult(ProcessResult processResult)
	{
		RestResult restResult = new RestResult();
		restResult.setResponseInfo(processResult.getResponseInfo());
		restResult.setRetCode(processResult.getRetCode());
		restResult.setRetMsg(processResult.getRetMsg());
		return restResult;
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/{userPayPlatform}/{openId}/transferBonus")
	public  RestResult transferBonus(@PathVariable String countryCode,@RequestBody BonusRequest bonusRequest) {
		RestResult restResult =new RestResult();
		restResult.setRetCode(GuaGuaLeConst.RESULT_Error_Fail);
		try {
			ProcessResult processResult = accountService.plusDailyBonus(bonusRequest);
			restResult = getRestResult(processResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restResult;
	}
	
	/**
	 * 用于从账户中扣钱；
	 * @param userPayPlatform
	 * @param openId
	 * @param payReqeust
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value = "/{userPayPlatform}/{openId}/payRequest")
	public  RestResult payReqeust(@PathVariable String userPayPlatform,@PathVariable String openId,@RequestBody PayReqeust payReqeust) {
		RestResult restResult =new RestResult();
		restResult.setRetCode(GuaGuaLeConst.RESULT_Error_Fail);
		try {
			
			ProcessResult processResult = accountService.payDailyBonus(payReqeust);
			restResult = getRestResult(processResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restResult;
	}
}
