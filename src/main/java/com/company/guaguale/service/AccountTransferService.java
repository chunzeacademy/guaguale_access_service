package com.company.guaguale.service;

import com.company.guaguale.domain.AccountInfo;
import com.company.guaguale.domain.BonusRequest;
import com.xinwei.nnl.common.domain.ProcessResult;

public interface AccountTransferService {
	public ProcessResult transferService(AccountInfo srcAccountInfo,AccountInfo destAccountInfo);
	public AccountInfo getWillTransferList(int preDays) throws Exception;
	public int settled(int days,int loopNumber);
	
}
