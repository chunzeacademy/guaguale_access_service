package com.xinwei.orderDb.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderChilds implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 订单类型. */
	private String catetory;

	/** 订单编号. */
	private String orderid;

	/** 子订单类型. */
	private String childcategory;

	/** 子订单ID. */
	private String childorderid;

	/** 创建时间. */
	private Date createtime;

	/**
	 * Constructor.
	 */
	public OrderChilds() {
	}

	/**
	 * Set the 订单类型.
	 * 
	 * @param catetory
	 *            订单类型
	 */
	public void setCatetory(String catetory) {
		this.catetory = catetory;
	}

	/**
	 * Get the 订单类型.
	 * 
	 * @return 订单类型
	 */
	public String getCatetory() {
		return this.catetory;
	}

	/**
	 * Set the 订单编号.
	 * 
	 * @param orderid
	 *            订单编号
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * Get the 订单编号.
	 * 
	 * @return 订单编号
	 */
	public String getOrderid() {
		return this.orderid;
	}

	/**
	 * Set the 子订单类型.
	 * 
	 * @param childcategory
	 *            子订单类型
	 */
	public void setChildcategory(String childcategory) {
		this.childcategory = childcategory;
	}

	/**
	 * Get the 子订单类型.
	 * 
	 * @return 子订单类型
	 */
	public String getChildcategory() {
		return this.childcategory;
	}

	/**
	 * Set the 子订单ID.
	 * 
	 * @param childorderid
	 *            子订单ID
	 */
	public void setChildorderid(String childorderid) {
		this.childorderid = childorderid;
	}

	/**
	 * Get the 子订单ID.
	 * 
	 * @return 子订单ID
	 */
	public String getChildorderid() {
		return this.childorderid;
	}

	/**
	 * Set the 创建时间.
	 * 
	 * @param createtime
	 *            创建时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * Get the 创建时间.
	 * 
	 * @return 创建时间
	 */
	public Date getCreatetime() {
		return this.createtime;
	}

	@Override
	public String toString() {
		return "OrderChilds [catetory=" + catetory + ", orderid=" + orderid + ", childcategory=" + childcategory
				+ ", childorderid=" + childorderid + ", createtime=" + createtime + "]";
	}


}
