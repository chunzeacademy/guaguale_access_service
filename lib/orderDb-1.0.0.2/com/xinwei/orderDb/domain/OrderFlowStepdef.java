package com.xinwei.orderDb.domain;

import java.io.Serializable;

public class OrderFlowStepdef implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 订单类型. */
	private String catetory;

	/** 订单步骤ID. */
	private String stepid;

	/** 步骤名称. */
	private String stepName;

	/** 步骤执行任务. */
	private String taskIn;

	/** 任务执行后失败的步骤定义. */
	private String taskOutError;

	/** 任务执行成功后的步骤定义. */
	private String taskOutSucc;

	/** 失败后如果没有配置错误码，调用该请求. */
	private String taskOutDefault;

	/** 任务运行类型. */
	private String runType;

	/** 任务运行参数. */
	private String runInfo;

	/** 任务重做次数. */
	private String retryTimes;

	/**
	 * Constructor.
	 */
	public OrderFlowStepdef() {
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
	 * Set the 步骤名称.
	 * 
	 * @param stepName
	 *            步骤名称
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * Get the 步骤名称.
	 * 
	 * @return 步骤名称
	 */
	public String getStepName() {
		return this.stepName;
	}

	/**
	 * Set the 步骤执行任务.
	 * 
	 * @param taskIn
	 *            步骤执行任务
	 */
	public void setTaskIn(String taskIn) {
		this.taskIn = taskIn;
	}

	/**
	 * Get the 步骤执行任务.
	 * 
	 * @return 步骤执行任务
	 */
	public String getTaskIn() {
		return this.taskIn;
	}

	/**
	 * Set the 任务执行后失败的步骤定义.
	 * 
	 * @param taskOutError
	 *            任务执行后失败的步骤定义
	 */
	public void setTaskOutError(String taskOutError) {
		this.taskOutError = taskOutError;
	}

	/**
	 * Get the 任务执行后失败的步骤定义.
	 * 
	 * @return 任务执行后失败的步骤定义
	 */
	public String getTaskOutError() {
		return this.taskOutError;
	}

	/**
	 * Set the 任务执行成功后的步骤定义.
	 * 
	 * @param taskOutSucc
	 *            任务执行成功后的步骤定义
	 */
	public void setTaskOutSucc(String taskOutSucc) {
		this.taskOutSucc = taskOutSucc;
	}

	/**
	 * Get the 任务执行成功后的步骤定义.
	 * 
	 * @return 任务执行成功后的步骤定义
	 */
	public String getTaskOutSucc() {
		return this.taskOutSucc;
	}

	/**
	 * Set the 失败后如果没有配置错误码，调用该请求.
	 * 
	 * @param taskOutDefault
	 *            失败后如果没有配置错误码，调用该请求
	 */
	public void setTaskOutDefault(String taskOutDefault) {
		this.taskOutDefault = taskOutDefault;
	}

	/**
	 * Get the 失败后如果没有配置错误码，调用该请求.
	 * 
	 * @return 失败后如果没有配置错误码，调用该请求
	 */
	public String getTaskOutDefault() {
		return this.taskOutDefault;
	}

	/**
	 * Set the 任务运行类型.
	 * 
	 * @param runType
	 *            任务运行类型
	 */
	public void setRunType(String runType) {
		this.runType = runType;
	}

	/**
	 * Get the 任务运行类型.
	 * 
	 * @return 任务运行类型
	 */
	public String getRunType() {
		return this.runType;
	}

	/**
	 * Set the 任务运行参数.
	 * 
	 * @param runInfo
	 *            任务运行参数
	 */
	public void setRunInfo(String runInfo) {
		this.runInfo = runInfo;
	}

	/**
	 * Get the 任务运行参数.
	 * 
	 * @return 任务运行参数
	 */
	public String getRunInfo() {
		return this.runInfo;
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

	@Override
	public String toString() {
		return "OrderFlowStepdef [catetory=" + catetory + ", stepid=" + stepid + ", stepName=" + stepName + ", taskIn="
				+ taskIn + ", taskOutError=" + taskOutError + ", taskOutSucc=" + taskOutSucc + ", taskOutDefault="
				+ taskOutDefault + ", runType=" + runType + ", runInfo=" + runInfo + ", retryTimes=" + retryTimes + "]";
	}

}