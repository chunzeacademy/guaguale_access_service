package com.company.guaguale.service;

import java.util.List;

import com.company.guaguale.domain.AccountInfo;
import com.company.guaguale.domain.BonusRequest;
import com.xinwei.nnl.common.domain.ProcessResult;

public interface AccountTransferService {
	public ProcessResult transferService(AccountInfo srcAccountInfo,AccountInfo destAccountInfo);
	public List<AccountInfo> getWillTransferList(int preDays,int amount) throws Exception;
	
	public int settled(int days,int loopNumber);
	public String getPreAccountDay(int preDays);
	public void reSettledAccount(AccountInfo settledAccountInfo);
	public void refreshAccountRelation(String userPayPlatform, String openId);
	
}
