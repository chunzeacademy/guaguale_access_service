package com.xinwei.orderDb.domain;

import java.io.Serializable;

public class OrderRunning implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 当前步骤状态. */
	private int currentstatus;

	/** 分区ID. */
	private String partitionid;

	/** 订单编号. */
	private String orderid;

	/**
	 * Constructor.
	 */
	public OrderRunning() {
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

	@Override
	public String toString() {
		return "OrderRunning [currentstatus=" + currentstatus + ", partitionid=" + partitionid + ", orderid=" + orderid
				+ "]";
	}
}
