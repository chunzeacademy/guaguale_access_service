package com.company.guaguale.domain;

import java.io.Serializable;

public class BonusRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2674301054693879338L;

	/**
	 * 
	 */
	private String userPayPaltform;
	
	private String jjl_appid;
	//好近商户id
	private String merchantID;
	//好进交易流水号
	private String fourth_deal_no;
	//外部交易流水号
	private String third_statement_no;
	//支付类型：支付宝扫码:800101； 支付宝反扫:80010；微信扫码:800201； 微信刷卡:800208； 微信公众号支付:800207
	private String pay_type;
	//客户请求交易时间
	private String next_client_time;
	//订单支付金额，单位分
	private String orderPaymentAmount;
	//系统请求交易时间
	private String demand_time;
	//支付时间
	private String success_time;
	//交易结果、订单状态
	private String order_state;
	//openid
	private String openid;
	//币种
	private String currency;
	//是否取消
	private String cancel;
	/**
	 * 签名信息
	 */
	private String checkCrc;
	
	public String getJjl_appid() {
		return jjl_appid;
	}
	public void setJjl_appid(String jjl_appid) {
		this.jjl_appid = jjl_appid;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getFourth_deal_no() {
		return fourth_deal_no;
	}
	public void setFourth_deal_no(String fourth_deal_no) {
		this.fourth_deal_no = fourth_deal_no;
	}
	public String getThird_statement_no() {
		return third_statement_no;
	}
	public void setThird_statement_no(String third_statement_no) {
		this.third_statement_no = third_statement_no;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getNext_client_time() {
		return next_client_time;
	}
	public void setNext_client_time(String next_client_time) {
		this.next_client_time = next_client_time;
	}
	public String getOrderPaymentAmount() {
		return orderPaymentAmount;
	}
	public void setOrderPaymentAmount(String orderPaymentAmount) {
		this.orderPaymentAmount = orderPaymentAmount;
	}
	public String getDemand_time() {
		return demand_time;
	}
	public void setDemand_time(String demand_time) {
		this.demand_time = demand_time;
	}
	public String getSuccess_time() {
		return success_time;
	}
	public void setSuccess_time(String success_time) {
		this.success_time = success_time;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCancel() {
		return cancel;
	}
	public void setCancel(String cancel) {
		this.cancel = cancel;
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
	@Override
	public String toString() {
		return "BonusRequest [jjl_appid=" + jjl_appid + ", merchantID=" + merchantID + ", fourth_deal_no="
				+ fourth_deal_no + ", third_statement_no=" + third_statement_no + ", pay_type=" + pay_type
				+ ", next_client_time=" + next_client_time + ", orderPaymentAmount=" + orderPaymentAmount
				+ ", demand_time=" + demand_time + ", success_time=" + success_time + ", order_state=" + order_state
				+ ", openid=" + openid + ", currency=" + currency + ", cancel=" + cancel + "]";
	}
	
}
