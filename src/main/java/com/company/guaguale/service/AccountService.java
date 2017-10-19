package com.company.guaguale.service;

import com.company.guaguale.domain.AccountInfo;
import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.domain.PayReqeust;
import com.xinwei.nnl.common.domain.ProcessResult;

public interface AccountService {
	/**
	 * 
	 * @param bonusRequest
	 * @return
	 */
	public ProcessResult plusDailyBonus(BonusRequest bonusRequest);
	/**
	 * 根据用户支付平台和OpenID查询账户余额
	 * @param userPayPaltform
	 * @param openId
	 * @return
	 */
	public ProcessResult getBalance(String userPayPlatform,String openId);
	
	public AccountInfo getAccountInfo(String userPayPaltform,String openId,int accountType,String currency,String day,boolean autoCreate);
	public ProcessResult getBalance(AccountInfo accountInfo) ;
	public ProcessResult payDailyBonus(AccountInfo accountInfo,String requestId,String requestTime,double payAmount,String reason);
	public ProcessResult payDailyBonus(PayReqeust payReqeust);
	
	public  long createAccountId();

	
}
