package com.company.guaguale.domain;

import java.io.Serializable;

public class AccountInfo implements Serializable{
	/**
	 * 卖家
	 
	//卖家确认的金额
	public static final int AccountType_Seller_total=0;
	//
	public static final int AccountType_Seller_openId_total=1;
	
	//买家日收入金额
	public static final int AccountType_Seller_daily=2;
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6974017385555764553L;
	/**
	 * 买家
	 */
	//买家确认的总金额
	public static final int AccountType_buyer_total=100;
	//买家按照openId分类的汇总
	public static final int AccountType_buyer_openId_total=101;
	//买家日金额
	public static final int AccountType_buyer_daily=102;
	
	public static final String TotalAccount_expireTime="2099-01-01";
	
	public static final int Status_ready =0;
	//需要结算
	public static final int Status_needSettle =1;
	/**
	 * 
	 */
	public static final int Status_HaveSettled =2;
	/**
	 * 账户类型
	 */
	private String userPayPaltform;
	/**
	 * openid
	 */
	private String openId;
	/**
	 * 账户有效期,例如：2017-10-13的账户，填写2017-10-13
	 */
	private String expireTimes;
	/**
	 * 账户类型
	 */
	private int accountType;
	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 账户ID
	 */
	private long   userAccountId;
	
	/**
	 * 账户状态
	 */
	private int   accountStatus;
		
	public String getUserPayPaltform() {
		return userPayPaltform;
	}

	public void setUserPayPaltform(String userPayPaltform) {
		this.userPayPaltform = userPayPaltform;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	
	public long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(long userAccountId) {
		this.userAccountId = userAccountId;
	}

	
	
	public String getExpireTimes() {
		return expireTimes;
	}

	public void setExpireTimes(String expireTimes) {
		this.expireTimes = expireTimes;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	
	public AccountInfo getCopy()  {

		AccountInfo copyAccInfo = new AccountInfo();
		copyAccInfo.setAccountStatus(this.getAccountStatus());
		copyAccInfo.setAccountType(this.getAccountType());
		copyAccInfo.setCurrency(this.getCurrency());
		copyAccInfo.setExpireTimes(this.getExpireTimes());
		copyAccInfo.setOpenId(this.getOpenId());
		copyAccInfo.setUserAccountId(this.getUserAccountId());
		copyAccInfo.setUserPayPaltform(this.getUserPayPaltform());
		return copyAccInfo;

	}
	

	@Override
	public String toString() {
		return "AccountInfo [userPayPaltform=" + userPayPaltform + ", openid=" + openId + ", expireTimes=" + expireTimes
				+ ", accountType=" + accountType + ", currency=" + currency + ", userAccountId=" + userAccountId
				+ ", accountStatus=" + accountStatus + "]";
	}

	
}
