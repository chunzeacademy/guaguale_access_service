package com.company.guaguale.domain;

import java.io.Serializable;

public class PayReqeust implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4570078773088631729L;
	
	private String userPayPaltform;
	/**
	 * openid
	 */
	private String openId;
	
	/**
	 * 同一个账户唯一的一个交易号
	 */
	private String transId;
	
	/**
	 * 确保唯一，同一笔交易前后时间不能修改，格式为yyyy-mm-dd hh:mm:ss
	 */
	private String payTime;
	/**
	 * 保留小数点后两位
	 * 大于零，是扣钱，小于0，是加钱
	 */
	private double amount;
	/**
	 * 币种
	 */
	private String currency;
	
	private String checkCrc;
	/**
	 * 支付原因，最长128
	 */
	private String reason;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	public String getCheckCrc() {
		return checkCrc;
	}
	public void setCheckCrc(String checkCrc) {
		this.checkCrc = checkCrc;
	}
	
	
	public String getUserPayPaltform() {
		return userPayPaltform;
	}
	public void setUserPayPaltform(String userPayPaltform) {
		this.userPayPaltform = userPayPaltform;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Override
	public String toString() {
		return "PayReqeust [userPayPaltform=" + userPayPaltform + ", openId=" + openId + ", transId=" + transId
				+ ", payTime=" + payTime + ", amount=" + amount + ", currency=" + currency + ", checkCrc=" + checkCrc
				+ ", reason=" + reason + "]";
	}
	
	
	
}
