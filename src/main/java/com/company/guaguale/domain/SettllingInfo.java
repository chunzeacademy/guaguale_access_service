package com.company.guaguale.domain;

public class SettllingInfo extends AccountInfo {
	
	private long settledTime = System.currentTimeMillis();

	public long getSettledTime() {
		return settledTime;
	}

	public void setSettledTime(long settledTime) {
		this.settledTime = settledTime;
	}
	
	public void setAccountInfo(AccountInfo source)
	{
		this.setAccountStatus(source.getAccountStatus());
		this.setAccountType(source.getAccountType());
		this.setCurrency(source.getCurrency());
		this.setExpireTimes(source.getExpireTimes());
		this.setOpenId(source.getOpenId());
		this.setUserAccountId(source.getUserAccountId());
		this.setUserPayPaltform(source.getUserPayPaltform());
	}
	public AccountInfo  getAccountInfo()
	{
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountStatus(this.getAccountStatus());
		accountInfo.setAccountType(this.getAccountType());
		accountInfo.setCurrency(this.getCurrency());
		accountInfo.setExpireTimes(this.getExpireTimes());
		accountInfo.setOpenId(this.getOpenId());
		accountInfo.setUserAccountId(this.getUserAccountId());
		accountInfo.setUserPayPaltform(this.getUserPayPaltform());
		return accountInfo;
	}
	
}
