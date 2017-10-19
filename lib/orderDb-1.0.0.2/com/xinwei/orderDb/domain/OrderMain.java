package com.xinwei.orderDb.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderMain implements Serializable {
	public static final String Step_start="start";	
	public static final String Step_end="end";
	
	
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 分区ID. */
	private String partitionid;

	/** 订单编号. */
	private String orderid;
	
	/** 流程ID. */
	private String flowid;	

	/** 订单类型. */
	private String catetory;

	/** parentOrderID. */
	private String parentorderid;

	/** 父订单类型. */
	private String parentordercategory;

	/** 订单业务关键字. */
	private String ownerkey;

	/** 当前步骤. */
	private String currentstep;

	/** 当前步骤状态. */
	private Integer currentstatus;

	/** 最新更新时间. */
	private Date updatetime;

	/** 订单是否结束. */
	private int isfinished;

	/**
	 * Constructor.
	 */
	public OrderMain() {
	}

	/**
	 * Set the 分区ID.
	 * 
	 * @param partitionid
	 *            分区ID
	 */
	public void setPartitionid(String partitionid) {
		this.partitionid = partitionid;
	}

	/**
	 * Get the 分区ID.
	 * 
	 * @return 分区ID
	 */
	public String getPartitionid() {
		return this.partitionid;
	}

	/**
	 * Set the 订单编号.
	 * 
	 * @param orderid
	 *            订单编号
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
		this.partitionid = orderid.substring(orderid.length() - 7, orderid.length() - 4);
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
	 * Set the parentOrderID.
	 * 
	 * @param parentorderid
	 *            parentOrderID
	 */
	public void setParentorderid(String parentorderid) {
		this.parentorderid = parentorderid;
	}

	/**
	 * Get the parentOrderID.
	 * 
	 * @return parentOrderID
	 */
	public String getParentorderid() {
		return this.parentorderid;
	}

	/**
	 * Set the 父订单类型.
	 * 
	 * @param parentordercategory
	 *            父订单类型
	 */
	public void setParentordercategory(String parentordercategory) {
		this.parentordercategory = parentordercategory;
	}

	/**
	 * Get the 父订单类型.
	 * 
	 * @return 父订单类型
	 */
	public String getParentordercategory() {
		return this.parentordercategory;
	}

	/**
	 * Set the 订单业务关键字.
	 * 
	 * @param ownerkey
	 *            订单业务关键字
	 */
	public void setOwnerkey(String ownerkey) {
		this.ownerkey = ownerkey;
	}

	/**
	 * Get the 订单业务关键字.
	 * 
	 * @return 订单业务关键字
	 */
	public String getOwnerkey() {
		return this.ownerkey;
	}

	/**
	 * Set the 当前步骤.
	 * 
	 * @param currentstep
	 *            当前步骤
	 */
	public void setCurrentstep(String currentstep) {
		this.currentstep = currentstep;
	}

	/**
	 * Get the 当前步骤.
	 * 
	 * @return 当前步骤
	 */
	public String getCurrentstep() {
		return this.currentstep;
	}

	/**
	 * Set the 当前步骤状态.
	 * 
	 * @param currentstatus
	 *            当前步骤状态
	 */
	public void setCurrentstatus(Integer currentstatus) {
		this.currentstatus = currentstatus;
	}

	/**
	 * Get the 当前步骤状态.
	 * 
	 * @return 当前步骤状态
	 */
	public Integer getCurrentstatus() {
		return this.currentstatus;
	}

	/**
	 * Set the 最新更新时间.
	 * 
	 * @param updatetime
	 *            最新更新时间
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	/**
	 * Get the 最新更新时间.
	 * 
	 * @return 最新更新时间
	 */
	public Date getUpdatetime() {
		return this.updatetime;
	}

	/**
	 * Set the 订单是否结束.
	 * 
	 * @param isfinished
	 *            订单是否结束
	 */
	public void setIsfinished(int isfinished) {
		this.isfinished = isfinished;
	}

	/**
	 * Get the 订单是否结束.
	 * 
	 * @return 订单是否结束
	 */
	public int getIsfinished() {
		return this.isfinished;
	}

	@Override
	public String toString() {
		return "OrderMain [partitionid=" + partitionid + ", orderid=" + orderid + ", catetory=" + catetory
				+ ", parentorderid=" + parentorderid + ", parentordercategory=" + parentordercategory + ", ownerkey="
				+ ownerkey + ", currentstep=" + currentstep + ", currentstatus=" + currentstatus + ", updatetime="
				+ updatetime + ", isfinished=" + isfinished + "]";
	}

}
