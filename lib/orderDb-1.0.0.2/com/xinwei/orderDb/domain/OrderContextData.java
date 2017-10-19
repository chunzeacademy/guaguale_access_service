package com.xinwei.orderDb.domain;

import java.io.Serializable;

public class OrderContextData implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 分区ID. */
	private String partitionid;

	/** 订单编号. */
	private String orderid;

	/** 订单上下文数据关键字. */
	private String dataKey;

	/** 订单步骤ID. */
	private String stepid;

	/** 流程ID. */
	private String flowid;

	/** 订单上下文数据. */
	private String contextData;

	/**
	 * Constructor.
	 */
	public OrderContextData() {
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

	@Override
	public String toString() {
		return "OrderContextData [partitionid=" + partitionid + ", orderid=" + orderid + ", dataKey=" + dataKey
				+ ", stepid=" + stepid + ", flowid=" + flowid + ", contextData=" + contextData + "]";
	}


}
