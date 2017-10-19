package com.company.guaguale.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.domain.PayReqeust;
//import com.xinwei.orderpost.domain.CommissionPresentInfo;

public class CrcUtils {
	
	public static boolean checkPayRequestCrc(PayReqeust payRequest,String password)
	{
		String crc = getPayReqCrc(payRequest,password);
		return crc.equalsIgnoreCase(payRequest.getCheckCrc());
	}
	/**
	 * 
	 * @param payRequest
	 * @param password
	 * @return
	 */
	public static String getPayReqCrc(PayReqeust payRequest,String password)
	{
		StringBuilder strb=new StringBuilder();
		// reqTransId|subsId|amt|bizType|operType|passWord
		strb.append(payRequest.getUserPayPaltform())
		.append(payRequest.getOpenId())
		.append(Math.round(payRequest.getAmount()*100))		
		.append(payRequest.getTransId())
		.append(payRequest.getPayTime());
		return getMD5Str(strb.toString());
	}
	
	public static boolean checkBonusRequestCrc(BonusRequest bonusRequest,String password)
	{
		String crc = getBonusReqest(bonusRequest,password);
		return crc.equalsIgnoreCase(bonusRequest.getCheckCrc());
	}
	/**
	 * 
	 * @param payRequest
	 * @param password
	 * @return
	 */
	public static String getBonusReqest(BonusRequest bonusRequest,String password)
	{
		StringBuilder strb=new StringBuilder();
		// reqTransId|subsId|amt|bizType|operType|passWord
		strb.append(bonusRequest.getUserPayPaltform())
		.append(bonusRequest.getOpenid())
		.append((bonusRequest.getOrderPaymentAmount()))		
		.append(bonusRequest.getFourth_deal_no())
		.append(bonusRequest.getNext_client_time());
		return getMD5Str(strb.toString());
	}
	
	public static  String getMD5Str(String str) {    
	    MessageDigest messageDigest = null;    
	  
	    try {    
	        messageDigest = MessageDigest.getInstance("MD5");    
	  
	        messageDigest.reset();    
	  
	        messageDigest.update(str.getBytes("UTF-8"));    
	    } catch (NoSuchAlgorithmException e) {    
	        System.out.println("NoSuchAlgorithmException caught!");   
	    } catch (UnsupportedEncodingException e) {    
	        e.printStackTrace();    
	    }    
	  
	    byte[] byteArray = messageDigest.digest();    
	  
	    StringBuffer md5StrBuff = new StringBuffer();    
	  
	    for (int i = 0; i < byteArray.length; i++) {                
	        if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)    
	            md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));    
	        else    
	            md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));    
	    }    
	  
	    return md5StrBuff.toString();    
	}
}
