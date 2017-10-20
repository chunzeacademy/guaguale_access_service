package com.company.guaguale.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.company.guaguale.Const.GuaGuaLeConst;
import com.company.guaguale.domain.AccountBalance;
import com.company.guaguale.domain.AccountInfo;
import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.domain.PayReqeust;
import com.company.guaguale.mapper.AccountInfoMapper;
import com.company.guaguale.service.AccountService;
import com.xinwei.coobill.bankproxy.domain.BankProxyRequest;
import com.xinwei.coobill.bankproxy.domain.BankProxyResponse;
import com.xinwei.coobill.bankproxy.facade.BankProxyInterface;
import com.xinwei.nnl.common.domain.ProcessResult;
import com.xinwei.orderpost.common.OrderPostUtil;
import com.xinwei.orderpost.domain.CommissionPresentInfo;
import com.xinwei.orderpost.facade.CommissionPresentService;
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Resource(name="balTransferService")
	private CommissionPresentService commissionPresentService;
	
	@Resource(name="balanceService")
	private BankProxyInterface bankProxyInterface;
	
	
	
	@Resource(name="redisAccountService")
	private RedisAccountService  redisAccountService;
	
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	
	private long newAccountId=0;
	
	
	@Value("${accountDaily.expireHour:1}")  
	private int expireHour;
	
	@Value("${accountDaily.expireMinutes:00}")  
	private int expireMinutes;
	
	
	/**
	 * 余额管理需要的传输加密字符串
	 */
	@Value("${balTransfer.accesskey}")  
	private String transferAccessKey;
	
	@Value("${accountId.prefix}")  
	private String accountStartprefix;
	
	@Value("${accountRestRequest.transferKey}")  
	private String accountRestTransferKey;
	
	/**
	 * 创建账户ID
	 * @return
	 */
	@Override
	public synchronized long createAccountId()
	{
		if(newAccountId==0)
		{
			Calendar startTime = Calendar.getInstance();
			startTime.set(Calendar.YEAR, 2017);
			startTime.set(Calendar.MONTH, 9);
			startTime.set(Calendar.DAY_OF_MONTH,13);
			startTime.set(Calendar.HOUR_OF_DAY,23);
			startTime.set(Calendar.MINUTE,59);
			startTime.set(Calendar.SECOND,59);
			startTime.set(Calendar.MILLISECOND,0);
			long currentTime = System.currentTimeMillis() - startTime.getTimeInMillis();
			//currentTime = currentTime;
			this.newAccountId = currentTime;
		}
		newAccountId++;
		String str = String.valueOf(newAccountId) + this.accountStartprefix;
		return Long.parseLong(str);
			
	}
	
	
	
	protected AccountInfo createAccountDaily(String userPayPaltform,String openId,String currency,int accountType,String day)
	{
		AccountInfo accountInfo = null;
		long requestTime = 0; 
				
		try {
			requestTime=redisAccountService.getCreateAccountLock(userPayPaltform,openId,accountType,day);
			//获取已经结算的账户,因为有锁，必须在查询一次
			AccountInfo queryAccountInfo = new AccountInfo();
			queryAccountInfo.setUserPayPaltform(userPayPaltform);
			queryAccountInfo.setOpenId(openId);
			queryAccountInfo.setAccountType(accountType);
			queryAccountInfo.setExpireTimes(day);
			
			List<AccountInfo>accountInfos = accountInfoMapper.selectByTimes(queryAccountInfo);
			if(accountInfos.size()>0)
			{
				accountInfo = accountInfos.get(0);
				return accountInfo;
			}
			//查找已经结算的账户
			queryAccountInfo.setAccountStatus(queryAccountInfo.Status_HaveSettled);
			List<AccountInfo> settledAccounts = accountInfoMapper.selectByStatus(queryAccountInfo);
			if(settledAccounts!=null&& settledAccounts.size()>0)
			{
				accountInfo = settledAccounts.get(0);
				accountInfo.setAccountStatus(queryAccountInfo.Status_ready);
				accountInfo.setExpireTimes(day);
				accountInfoMapper.updateStatusTimes(accountInfo);
			}
			//没有已经结算的账户
			else
			{
				accountInfo = new AccountInfo();
				accountInfo.setUserPayPaltform(userPayPaltform);
				accountInfo.setOpenId(openId);
				accountInfo.setAccountType(accountType);
				accountInfo.setExpireTimes(day);
				accountInfo.setAccountStatus(queryAccountInfo.Status_ready);
				accountInfo.setCurrency(currency);
				accountInfo.setUserAccountId(createAccountId());
				accountInfoMapper.insertAccountInfo(accountInfo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			accountInfo = null;
		}
		finally
		{
			try {
				redisAccountService.releaseCreateAccountLock(userPayPaltform, openId, accountType, day, requestTime);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return accountInfo;
	}
	/**
	 * 获取每天的账户信息
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @param day
	 * @return
	 */
	@Override
	public AccountInfo getAccountInfo(String userPayPaltform,String openId,int accountType,String currency,String day,boolean autoCreate)
	{
		//从内存中查询账户信息
		AccountInfo accountInfo = (AccountInfo)redisAccountService.getAccountInfoDaily(userPayPaltform,openId,accountType,day);
		//如果内存中不存在，从数据库中查询
		if(accountInfo==null)
		{
			try {
				AccountInfo queryAccountInfo = new AccountInfo();
				queryAccountInfo.setUserPayPaltform(userPayPaltform);
				queryAccountInfo.setOpenId(openId);
				queryAccountInfo.setAccountType(accountType);
				queryAccountInfo.setExpireTimes(day);
				List<AccountInfo> accountInfos = accountInfoMapper.selectByTimes(queryAccountInfo);
				if(accountInfos.size()>0)
				{
					accountInfo = accountInfos.get(0);
				}
				else if(autoCreate)
				{
					accountInfo = createAccountDaily(userPayPaltform,openId,currency,accountType,day);
				}
				if(accountInfo!=null)
				{
					//更新账户余额到内存
					redisAccountService.putAccountInfoDaily(day, accountInfo);
					//更新账户关系到余额
					redisAccountService.putAccountRelation(day, accountInfo);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return accountInfo;
		
	}
	/**
	 * 获取账户天数标识，需要获取每天一个账户
	 * @param payTime
	 * @return
	 */
	protected String getAccountDay(String paySuccessTime)
	{
		Date payTime = null;
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			payTime = formatter.parse(paySuccessTime);
			
		}
		catch(Exception e)
		{
			payTime = Calendar.getInstance().getTime();
		}
		Calendar payCalendar = Calendar.getInstance();
		payCalendar.setTime(payTime);
		return payCalendar.get(payCalendar.YEAR) + "-" + (payCalendar.get(payCalendar.MONTH)+1)+ "-" + payCalendar.get(payCalendar.DAY_OF_MONTH);
  	}
	/**
	 *  获取当天的账户时间
	 * @return
	 */
	protected String getNowAccountDay()
	{
	
		Calendar payCalendar = Calendar.getInstance();
		
		return payCalendar.get(payCalendar.YEAR) + "-" + (payCalendar.get(payCalendar.MONTH)+1)+ "-" + payCalendar.get(payCalendar.DAY_OF_MONTH);
  	}
	/**
	 * 将请求时间统一转化为日期
	 * @param requestime
	 * @return
	 */
	protected Calendar getCalendar(String requestime)
	{
		Date payTime = null;
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			payTime = formatter.parse(requestime);
			
		}
		catch(Exception e)
		{
			payTime = Calendar.getInstance().getTime();
		}
		Calendar payCalendar = Calendar.getInstance();
		payCalendar.setTime(payTime);
		return payCalendar;
  	}
	/**
	 * 
	 * @param requestId
	 * @param createTime 格式yyyy-mm-dd hh:mm:ss
	 * @return
	 */
	protected String createTransId(String requestId,String requestTime)
	{
		//String str = bonusRequest.getFourth_deal_no();
		String str = requestId;
		String retStr= str.substring(0,2);
	//	Calendar requestTime = getCalendar(bonusRequest.getNext_client_time());
		Calendar requestTimeD = getCalendar(requestTime);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		
		retStr = retStr + formatter.format(requestTimeD.getTime()).substring(2) +  str.substring(2);
		return retStr;
	}
	/**
	 * 判断日账户是否过期
	 * @param accountInfo
	 * @return
	 */
	protected boolean isAccountExpired(AccountInfo accountInfo)
	{
		if(accountInfo.getAccountStatus()!=AccountInfo.Status_ready)
			return true;
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date accountDay = null;
		try {
			accountDay = formatter.parse(accountInfo.getExpireTimes());
			Calendar accountCalendar = Calendar.getInstance();
			accountCalendar.setTime(accountDay);
			if(accountCalendar.get(Calendar.DAY_OF_MONTH) != now.get(Calendar.DAY_OF_MONTH))
			{
				//如果1点以后,设置
				if(now.get(Calendar.HOUR_OF_DAY)>this.expireHour)
				{
					return true;
				}
				if(now.get(Calendar.HOUR_OF_DAY)==this.expireHour && now.get(Calendar.MINUTE)>=this.expireMinutes)
				{
					return true;
				}
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2017-10-13
		
		return false;
	}
	
	/**
	 * 
	 * @param accountInfo
	 * @param requestId
	 * @param requestTime  -- 格式 yyyy-mm-dd hh:mm:ss
	 * @param payAmount
	 * @return
	 */
	@Override
	public ProcessResult payDailyBonus(AccountInfo accountInfo,String requestId,String requestTime,double payAmount,String reason) {
		CommissionPresentInfo commissionPresentInfo=new CommissionPresentInfo();
		commissionPresentInfo.setBizType(10000);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		commissionPresentInfo.setExpireTime("20991201121212");
		commissionPresentInfo.setOperType(0);
		
		commissionPresentInfo.setAmt(payAmount);
		commissionPresentInfo.setReason("plus bonus");
		commissionPresentInfo.setSubsId(accountInfo.getUserAccountId());
		/* 2-14  orderid*/
		
		commissionPresentInfo.setReqTransId(createTransId(requestId,requestTime));
		//签名信息
		String signInfo  = OrderPostUtil.getPresentSignInfo(commissionPresentInfo, this.transferAccessKey);
		commissionPresentInfo.setSignInfo(signInfo);
		
		
		commissionPresentInfo.setReason(reason);
		List<CommissionPresentInfo> commissionPresentInfoList =new ArrayList();
		commissionPresentInfoList.add(commissionPresentInfo);
		ProcessResult processResult =commissionPresentService.presentCommission(commissionPresentInfoList);
		System.out.println(processResult.toString());
		try {
			List<CommissionPresentInfo> results = (List<CommissionPresentInfo>)processResult.getResponseInfo();
			if(results!=null)
			{
				processResult.setRetCode(results.get(0).getResult());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return processResult;
	}
	
	@Override
	public ProcessResult plusDailyBonus(BonusRequest bonusRequest) {
		// TODO Auto-generated method stub AccountInfo.AccountType_buyer_daily
		String day = getAccountDay(bonusRequest.getSuccess_time());
		AccountInfo accountInfo = getAccountInfo(bonusRequest.getUserPayPaltform(),bonusRequest.getOpenid(),AccountInfo.AccountType_buyer_daily,bonusRequest.getCurrency(),day,true);
		if(accountInfo==null)
		{
			
		}
		else
		{
			//如果账户已经结转，请保存到今天的账户上
			if(isAccountExpired(accountInfo))
			{
				day = getNowAccountDay();
				accountInfo = getAccountInfo(bonusRequest.getUserPayPaltform(),bonusRequest.getOpenid(),AccountInfo.AccountType_buyer_daily,bonusRequest.getCurrency(),day,true);
			}
				
		}
		BigDecimal order_payment_amount = new BigDecimal(bonusRequest.getOrderPaymentAmount()).divide(new BigDecimal(100));
		
		return this.payDailyBonus(accountInfo, bonusRequest.getFourth_deal_no(),bonusRequest.getNext_client_time(), order_payment_amount.doubleValue(),"");
		/*
		CommissionPresentInfo commissionPresentInfo=new CommissionPresentInfo();
		commissionPresentInfo.setBizType(10000);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		commissionPresentInfo.setExpireTime("20991201121212");
		commissionPresentInfo.setOperType(0);
		BigDecimal order_payment_amount = new BigDecimal(bonusRequest.getOrderPaymentAmount()).divide(new BigDecimal(100));
		
		commissionPresentInfo.setAmt(order_payment_amount.doubleValue());
		commissionPresentInfo.setReason("plus bonus");
		commissionPresentInfo.setSubsId(accountInfo.getUserAccountId());
				
		commissionPresentInfo.setReqTransId(createTransId(bonusRequest.getFourth_deal_no(),bonusRequest.getNext_client_time()));
		//签名信息
		String signInfo  = OrderPostUtil.getPresentSignInfo(commissionPresentInfo, this.transferAccessKey);
		commissionPresentInfo.setSignInfo(signInfo);
		
		
		
		List<CommissionPresentInfo> commissionPresentInfoList =new ArrayList();
		commissionPresentInfoList.add(commissionPresentInfo);
		ProcessResult processResult =commissionPresentService.presentCommission(commissionPresentInfoList);
		System.out.println(processResult.toString());
		return processResult;
		*/
	}

	
	
	
	@Override
	public ProcessResult getBalance(String userPayPlatform, String openId) {
		// TODO Auto-generated method stub
		ProcessResult processResult = new ProcessResult();
		Map<String,AccountBalance> retAccMap = new HashMap<String,AccountBalance>();
		List<AccountBalance> lists = new ArrayList<AccountBalance>();
		Map<Object,Object> accountMap=this.redisAccountService.getAccountRelation(userPayPlatform, openId);
		//针对账户关系进行循环
		for (Map.Entry<Object, Object> entry : accountMap.entrySet()) {
			AccountInfo accountInfo = (AccountInfo)entry.getValue();
			String  accountKey = (String)entry.getKey();
			BankProxyRequest bankProxyRequest = new BankProxyRequest();
			bankProxyRequest.setUid(accountInfo.getUserAccountId());
			//该账户是否已经查询过并取得余额
			if(retAccMap.containsKey(String.valueOf(accountInfo.getUserAccountId())))
			{
				continue;
			}
			//查询余额
			BankProxyResponse bankProxyResponse = this.bankProxyInterface.queryBankBalance(bankProxyRequest);
			//返回成功
			if(bankProxyResponse.getReturnCode()==GuaGuaLeConst.RESULT_Success)
			{
				
				AccountBalance accountBalance = new AccountBalance();
				accountBalance.setAccountInfo(accountInfo);
				accountBalance.setBalance(bankProxyResponse.getBak1());
				//是否该账户已经查询过余额
				if(retAccMap.containsKey(String.valueOf(accountBalance.getUserAccountId())))
				{
					AccountBalance havedAddBalance = retAccMap.get(String.valueOf(accountBalance.getUserAccountId()));
					//已经查过过的余额时间更新
					if(havedAddBalance!=null&&havedAddBalance.getExpireTimes().compareTo(accountBalance.getExpireTimes())>0)
					{
						continue;
					}
					//新的账户余额更新
					else
					{
						try {
							//更新余额信息
							havedAddBalance.setAccountInfo(accountBalance);
							continue;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				lists.add(accountBalance);
				retAccMap.put(String.valueOf(accountBalance.getUserAccountId()), accountBalance);
			}
        }
		processResult.setRetCode(GuaGuaLeConst.RESULT_Success);
		processResult.setResponseInfo(lists);
		return processResult;
	}

	
	@Override
	public ProcessResult getBalance(AccountInfo accountInfo) {
		// TODO Auto-generated method stub
		ProcessResult processResult = new ProcessResult();
		
		{
			BankProxyRequest bankProxyRequest = new BankProxyRequest();
			bankProxyRequest.setUid(accountInfo.getUserAccountId());
			BankProxyResponse bankProxyResponse = this.bankProxyInterface.queryBankBalance(bankProxyRequest);
			if(bankProxyResponse.getReturnCode()==GuaGuaLeConst.RESULT_Success)
			{
				
				AccountBalance accountBalance = new AccountBalance();
				accountBalance.setAccountInfo(accountInfo);
				accountBalance.setBalance(bankProxyResponse.getBak1());
				processResult.setResponseInfo(accountBalance);
				processResult.setRetCode(GuaGuaLeConst.RESULT_Success);
			}
			else
			{
				processResult.setRetCode(bankProxyResponse.getReturnCode());
			}
        }
		
		
		return processResult;
	}



	@Override
	public ProcessResult payDailyBonus(PayReqeust payRequest) {
		// TODO Auto-generated method stub
		if(!CrcUtils.checkPayRequestCrc(payRequest, accountRestTransferKey))
		{
			ProcessResult processResult = new ProcessResult();		
			processResult.setRetCode(GuaGuaLeConst.RESULT_Error_Crc);
			return processResult;
		}
		
		AccountInfo accountInfo = getAccountInfo(payRequest.getUserPayPaltform(), payRequest.getOpenId(), AccountInfo.AccountType_buyer_openId_total, payRequest.getCurrency(), AccountInfo.TotalAccount_expireTime, true);
		ProcessResult processResult = payDailyBonus(accountInfo, payRequest.getTransId(), payRequest.getPayTime(), payRequest.getAmount()*-1, payRequest.getReason());
		return processResult;
	}

}
