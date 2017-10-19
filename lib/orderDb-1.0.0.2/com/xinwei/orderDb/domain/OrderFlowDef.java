package com.xinwei.orderDb.domain;

import java.io.Serializable;

public class OrderFlowDef implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 订单类型. */
	private String catetory;

	/** 分类名称. */
	private String categoryName;

	/** 版本. */
	private String version;

	/** 订单ID生成规则. */
	private String orderidCategory;

	/** 部署id. */
	private String deployId;

	/** 总共的步骤信息. */
	private String steps;

	/** 订单结束状态. */
	private String finishedstep;

	/**
	 * Constructor.
	 */
	public OrderFlowDef() {
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
	 * Set the 分类名称.
	 * 
	 * @param categoryName
	 *            分类名称
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * Get the 分类名称.
	 * 
	 * @return 分类名称
	 */
	public String getCategoryName() {
		return this.categoryName;
	}

	/**
	 * Set the 版本.
	 * 
	 * @param version
	 *            版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Get the 版本.
	 * 
	 * @return 版本
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * Set the 订单ID生成规则.
	 * 
	 * @param orderidCategory
	 *            订单ID生成规则
	 */
	public void setOrderidCategory(String orderidCategory) {
		this.orderidCategory = orderidCategory;
	}

	/**
	 * Get the 订单ID生成规则.
	 * 
	 * @return 订单ID生成规则
	 */
	public String getOrderidCategory() {
		return this.orderidCategory;
	}

	/**
	 * Set the 部署id.
	 * 
	 * @param deployId
	 *            部署id
	 */
	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	/**
	 * Get the 部署id.
	 * 
	 * @return 部署id
	 */
	public String getDeployId() {
		return this.deployId;
	}

	/**
	 * Set the 总共的步骤信息.
	 * 
	 * @param steps
	 *            总共的步骤信息
	 */
	public void setSteps(String steps) {
		this.steps = steps;
	}

	/**
	 * Get the 总共的步骤信息.
	 * 
	 * @return 总共的步骤信息
	 */
	public String getSteps() {
		return this.steps;
	}

	/**
	 * Set the 订单结束状态.
	 * 
	 * @param finishedstep
	 *            订单结束状态
	 */
	public void setFinishedstep(String finishedstep) {
		this.finishedstep = finishedstep;
	}

	/**
	 * Get the 订单结束状态.
	 * 
	 * @return 订单结束状态
	 */
	public String getFinishedstep() {
		return this.finishedstep;
	}

	@Override
	public String toString() {
		return "OrderFlowDef [catetory=" + catetory + ", categoryName=" + categoryName + ", version=" + version
				+ ", orderidCategory=" + orderidCategory + ", deployId=" + deployId + ", steps=" + steps
				+ ", finishedstep=" + finishedstep + "]";
	}


}