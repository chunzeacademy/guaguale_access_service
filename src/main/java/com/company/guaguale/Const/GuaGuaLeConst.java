package com.company.guaguale.Const;

public class GuaGuaLeConst {
	public static final int RESULT_Success = 0;	
	public static final int RESULT_Error_Fail = 100001;	
	
	
	public static final int RESULT_Error_Start = 100001;	
	
	/**
	 * 账户信息返回账户不存在
	 */
	public static final int RESULT_Error_BalanceNotExit = 100;	
	
	/**
	 *rest CRC签名错误
	 */
	public static final int RESULT_Error_Crc = RESULT_Error_Start+1;	
	
	public static final int RESULT_Error_NoNeedSettled = RESULT_Error_Start+2;	
	
	
}
