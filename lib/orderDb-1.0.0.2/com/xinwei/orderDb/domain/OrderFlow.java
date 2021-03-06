package com.xinwei.orderDb.domain;

import java.io.Serializable;
import java.util.Date;

public class OrderFlow implements Serializable {
	
	
	public static final int Status_init=0;
	
	public static final int Status_Running=1;
	
	public static final int Status_Suspend=2;
	
	
	public static final int Status_stop=3;
	
	public static final int Status_end=4;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 分区ID. */
	private String partitionid;

	/** 订单编号. */
	private String orderid;

	/** 订单业务关键字. */
	private String ownerkey;

	/** 订单步骤ID. */
	private String stepid;

	/** 流程ID. */
	private String flowid;

	/** 创建时间. */
	private Date createtime;

	/** 最新更新时间. */
	private Date updatetime;

	/** 订单上下文数据关键字. */
	private String dataKey;

	/** 订单上下文数据. */
	private String contextData;

	/** 任务重做次数. */
	private String retryTimes;

	/** 当前步骤状态. */
	private int currentstatus;
	
	/**
	 * 错误和描述
	 */
	
	
	/**
	 * Constructor.
	 */
	public OrderFlow() {
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
	 * Set the 订单步骤ID.
	 * 
	 * @param stepid
	 *            订单步骤ID
	 */
	public void setStepid(String stepid) {
		this.stepid = stepid;
	}

	/**
	 * Get the 订单步骤ID.
	 * 
	 * @return 订单步骤ID
	 */
	public String getStepid() {
		return this.stepid;
	}

	/**
	 * Set the 流程ID.
	 * 
	 * @param flowid
	 *            流程ID
	 */
	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	/**
	 * Get the 流程ID.
	 * 
	 * @return 流程ID
	 */
	public String getFlowid() {
		return this.flowid;
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
	 * Set the 订单上下文数据关键字.
	 * 
	 * @param dataKey
	 *            订单上下文数据关键字
	 */
	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	/**
	 * Get the 订单上下文数据关键字.
	 * 
	 * @return 订单上下文数据关键字
	 */
	public String getDataKey() {
		return this.dataKey;
	}

	/**
	 * Set the 订单上下文数据.
	 * 
	 * @param contextData
	 *            订单上下文数据
	 */
	public void setContextData(String contextData) {
		this.contextData = contextData;
	}

	/**
	 * Get the 订单上下文数据.
	 * 
	 * @return 订单上下文数据
	 */
	public String getContextData() {
		return this.contextData;
	}

	/**
	 * Set the 任务重做次数.
	 * 
	 * @param retryTimes
	 *            任务重做次数
	 */
	public void setRetryTimes(String retryTimes) {
		this.retryTimes = retryTimes;
	}

	/**
	 * Get the 任务重做次数.
	 * 
	 * @return 任务重做次数
	 */
	public String getRetryTimes() {
		return this.retryTimes;
	}

	/**
	 * Set the 当前步骤状态.
	 * 
	 * @param currentstatus
	 *            当前步骤状态
	 */
	public void setCurrentstatus(int currentstatus) {
		this.currentstatus = currentstatus;
	}

	/**
	 * Get the 当前步骤状态.
	 * 
	 * @return 当前步骤状态
	 */
	public int getCurrentstatus() {
		return this.currentstatus;
	}

	@Override
	public String toString() {
		return "OrderFlow [partitionid=" + partitionid + ", orderid=" + orderid + ", ownerkey=" + ownerkey + ", stepid="
				+ stepid + ", flowid=" + flowid + ", createtime=" + createtime + ", updatetime=" + updatetime
				+ ", dataKey=" + dataKey + ", contextData=" + contextData + ", retryTimes=" + retryTimes
				+ ", currentstatus=" + currentstatus + "]";
	}

}