package com.company.guaguale.domain;

public class AccountBalance extends AccountInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2054623281492373991L;
	private String balance;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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

	@Override
	public String toString() {
		return "AccountBalance [balance=" + balance + "]" + super.toString();
	}
	
}
