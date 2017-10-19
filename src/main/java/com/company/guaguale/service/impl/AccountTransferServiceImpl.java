package com.company.guaguale.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.guaguale.Const.GuaGuaLeConst;
import com.company.guaguale.domain.AccountBalance;
import com.company.guaguale.domain.AccountInfo;
import com.company.guaguale.mapper.AccountInfoMapper;
import com.company.guaguale.mapper.OrderFlowMapper;
import com.company.guaguale.service.AccountService;
import com.company.guaguale.service.AccountTransferService;
import com.xinwei.nnl.common.domain.ProcessResult;
@Service("accountTransferService")
public class AccountTransferServiceImpl implements AccountTransferService {

	@Autowired
	private OrderFlowMapper orderFlowMapper;
	
	@Resource(name="redisAccountService")
	private RedisAccountService  redisAccountService;
	
	@Resource(name="accountService")
	private AccountService accountService;
	
	@Autowired
	private AccountInfoMapper accountInfoMapper;
	
	
	
	/**
	 * 判断是否是当天的账户
	 * @param accountInfo
	 * @return
	 */
	protected boolean isCurrentAccount(AccountInfo accountInfo)
	{
		
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date accountDay = null;
		try {
			accountDay = formatter.parse(accountInfo.getExpireTimes());
			Calendar accountCalendar = Calendar.getInstance();
			accountCalendar.setTime(accountDay);
			if(accountCalendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
					accountCalendar.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
					accountCalendar.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH)
					)
			{
				return true;
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2017-10-13
		
		return false;
	}
	
	protected String getPreAccountDay(int preDays)
	{
		Calendar payCalendar = Calendar.getInstance();
		payCalendar.add(Calendar.DATE, preDays * -1);
		return payCalendar.get(payCalendar.YEAR) + "-" + (payCalendar.get(payCalendar.MONTH)+1)+ "-" + payCalendar.get(payCalendar.DAY_OF_MONTH);
  	}
	/**
	 * 获取需要结转的账户信息
	 * @param preDays
	 * @return
	 * @throws Exception
	 */
	@Override
	public AccountInfo getWillTransferList(int preDays) throws Exception
	{
		String day = getPreAccountDay(preDays);
		AccountInfo accountInfo= this.redisAccountService.getWillSettledAccount(day);
		return accountInfo;
	}
	
	
	/**
	 * 
	 * @param days 结转几天前的
	 * @return
	 */
	@Override
	public int settled(int days,int loopNumber) 
	{
		
		int preDays = 1;
		int index=0;
		AccountInfo accountInfo = null;
		try {
			do {
				index++;
				accountInfo = getWillTransferList(preDays);
				if(accountInfo==null)
				{
					preDays++;
				}
				else
				{
					break;
				}
				
			}while(preDays<=days&&index<loopNumber);
			if(accountInfo!=null)
			{
				AccountInfo destAccountInfo = getSumAccInfoByOpen(accountInfo);
				this.transferService(accountInfo, destAccountInfo);
			}
			else
			{
				return GuaGuaLeConst.RESULT_Error_NoNeedSettled;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return GuaGuaLeConst.RESULT_Error_Fail;
		}
		return GuaGuaLeConst.RESULT_Success;
	}
	
	/**
	 * 获取按照Openid汇总的账户信息；
	 * @param accountInfo
	 * @return
	 */
	protected AccountInfo getSumAccInfoByOpen(AccountInfo accountInfo)
	{
		AccountInfo totalAccOpenid= accountInfo.getCopy();
		totalAccOpenid.setAccountType(AccountInfo.AccountType_buyer_openId_total);
		totalAccOpenid.setExpireTimes(AccountInfo.TotalAccount_expireTime);
		return totalAccOpenid;
	}
	
	
	
	/**
	 * 获取需要结转的账户列表
	 * @param userPayPlatform
	 * @param openId
	 * @return
	 */
	protected ProcessResult getWillTransferList(String userPayPlatform, String openId)
	{
		ProcessResult balanceResult = accountService.getBalance(userPayPlatform, openId);
		List<AccountInfo> accountLists = new ArrayList<AccountInfo>();
		if(balanceResult.getRetCode()==GuaGuaLeConst.RESULT_Success)
		{
			List<AccountBalance> lists = (List<AccountBalance>)balanceResult.getResponseInfo();
			for(AccountBalance accountBalance:lists)
			{
				
				//判断是否需要结转
				boolean isCurrentAccount = this.isCurrentAccount(accountBalance);
				if(isCurrentAccount)
				{
					continue;
				}
				
				DecimalFormat    df   = new DecimalFormat("######0.00");
				double balance;
				try {
					balance = df.parse(accountBalance.getBalance()).doubleValue();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					continue;
				}
				/**
				 * 账户余额为0 并且账户已经结转；
				 */
				if(balance==0 && accountBalance.getAccountStatus()==accountBalance.Status_HaveSettled)
				{
					//if(accountBalance.getAccountStatus()!=accountBalance.Status_ready)
				}
				
			}
		}
		return null;
	}
	
	
	
	@Override
	public ProcessResult transferService(AccountInfo srcAccountInfo, AccountInfo destAccountInfo) {
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(GuaGuaLeConst.RESULT_Error_Fail);
		long requestTime=0;
		//获取单个账户的转账锁
		String lockKey = this.redisAccountService.getSettledDailyLockKey(srcAccountInfo.getUserPayPaltform(),
				srcAccountInfo.getOpenId(), srcAccountInfo.getAccountType(), srcAccountInfo.getExpireTimes());

		try {
			requestTime = this.redisAccountService.getCommonLock(lockKey);
			if(requestTime>0)
			{
				processResult  =this.accountService.getBalance(srcAccountInfo);
				if(processResult.getRetCode()==GuaGuaLeConst.RESULT_Success)
				{
					DecimalFormat    df   = new DecimalFormat("######0.00");
					double balance;
					try {
						//获取余额
						AccountBalance accountBalance =(AccountBalance)processResult.getResponseInfo();
						balance = df.parse(accountBalance.getBalance()).doubleValue();
						//如果余额大于零
						if(balance<=0.00)
						{
							
						}
						else
						{
							//保存结转信息
							this.redisAccountService.putAccSettledRecord(accountBalance);
							String requestId = "S:" + accountBalance.getUserAccountId() + String.valueOf(balance);
							String requestDailyTime = accountBalance.getExpireTimes() + " 00:00:01";
							//减去原来的信息
							processResult = this.accountService.payDailyBonus(srcAccountInfo, requestId, requestDailyTime, -1 * balance,accountBalance.toString());
							//如果账户成功扣减
							if(processResult.getRetCode()==GuaGuaLeConst.RESULT_Success)
							{
								requestDailyTime = accountBalance.getExpireTimes() + " 00:00:02" ;
								AccountInfo checkDestAccountInfo = this.accountService.getAccountInfo(destAccountInfo.getUserPayPaltform(), destAccountInfo.getOpenId(), destAccountInfo.getAccountType(), destAccountInfo.getCurrency(), destAccountInfo.getExpireTimes(), true);
								if(checkDestAccountInfo==null)
								{
									processResult.setRetCode(GuaGuaLeConst.RESULT_Error_Fail);
								}
								processResult = this.accountService.payDailyBonus(checkDestAccountInfo, requestId, requestDailyTime,balance,accountBalance.toString());
								//如果目标账户成功增加余额
								if(processResult.getRetCode()==GuaGuaLeConst.RESULT_Success)
								{
									//修改账户状态
									this.redisAccountService.settledAccount(srcAccountInfo);
									srcAccountInfo.setAccountStatus(srcAccountInfo.Status_HaveSettled);
									srcAccountInfo.setExpireTimes("1970-01-01");
									accountInfoMapper.updateStatusTimes(srcAccountInfo);
								}
								
							}
						}
					}
					catch(Exception e)
					{
						
					}
				}
				
			}
			// TODO Auto-generated method stub
				}
		catch(Exception e)
		{
			
		}
		finally {
			// TODO: handle finally clause
			if(requestTime>0)
			{
				redisAccountService.releaseCommonLock(lockKey, requestTime);
			}
		}
		return processResult;
	}
	
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


	
}
