package com.company.guaguale.domain;

import java.io.Serializable;

public class RestResult implements Serializable {
private static final long serialVersionUID = -1019182308145750001L;
	
	// 返回错误码：0为成功，其它为失败默认值设置成-1
	private int retCode=0;
	
	// 返回错误信息
	private String retMsg;
	
	// 返回结果信息
	private Object responseInfo;
	
	public RestResult()
	{
		super();
	}
	
	public int getRetCode()
	{
		return retCode;
	}
	
	public void setRetCode(int retCode)
	{
		this.retCode = retCode;
	}
	
	public String getRetMsg()
	{
		return retMsg;
	}
	
	public void setRetMsg(String retMsg)
	{
		this.retMsg = retMsg;
	}
	
	public Object getResponseInfo()
	{
		return responseInfo;
	}
	
	public void setResponseInfo(Object responseInfo)
	{
		this.responseInfo = responseInfo;
	}
	
	@Override
	public String toString()
	{
		return "ProcessResult [retCode=" + retCode + ", retMsg=" + retMsg
				+ ", responseInfo=" + responseInfo + "]";
	}
}
