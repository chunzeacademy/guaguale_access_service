package com.company.guaguale.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.company.guaguale.domain.AccountBalance;
import com.company.guaguale.domain.AccountInfo;

@Service("redisAccountService")
public class RedisAccountService {
	
	private final String Key_prefix_SettledAccInfo = "_transSettled1.0:";
	
	/**
	 * 账户每天结转的错
	 */
	private final String Key_prefix_SettledAccLock = "_transSettledDaily1.0:";
	
	
	/**
	 * 账户的key前缀
	 */
	private final String Key_prefix_accountInfo = "_account1.0:";
	
	private final String Key_prefix_settledList = "_settledList";
	
	/**
	 * 
	 */
	private final String Key_prefix_accountRelation = "_accountRel1.0:";
	
	/**
	 * 创建用户的加锁的前缀
	 */
	private final String Key_prefix_CreateAccountLock = "_cAccountLock:";
	@Resource (name = "redisTemplate")
	protected RedisTemplate<Object, Object> redisTemplate;
	public String getAccountInfoKey(AccountInfo accountInfo)
	{
		
		return getAccountInfoKey(accountInfo.getUserPayPaltform(),accountInfo.getOpenId(),accountInfo.getAccountType());
		
	}
	
	
	
	
	protected String getAccountInfoKey(String userPayPaltform,String openId,int accountType)
	{
		StringBuilder str = new StringBuilder();
		str.append(Key_prefix_accountInfo);
		str.append(":");
		str.append(userPayPaltform);
		str.append(":");
		str.append(openId);
		str.append(":");
		str.append(accountType);
		return str.toString();			
	}
	/**
	 * 
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @return
	 */
	protected String getAccountDailyKey(String userPayPaltform,String openId,int accountType,String day)
	{
		StringBuilder str = new StringBuilder();
		str.append(Key_prefix_accountInfo);
		str.append(":");
		str.append(userPayPaltform);
		str.append(":");
		str.append(openId);
		str.append(":");
		str.append(accountType);
		str.append(":");
		
		str.append(day);
		
		return str.toString();			
	}
	
	/**
	 * 当天哪些账户有业务
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @param day
	 * @return
	 */
	protected String getDailySettledListkey(String day)
	{
		StringBuilder str = new StringBuilder();
		str.append(Key_prefix_settledList);
		str.append(":");
		str.append(day);
		
		return str.toString();			
	}
	
	public String getAccountRelationKey(String userPayPaltform,String openId)
	{
		StringBuilder str = new StringBuilder();
		str.append(Key_prefix_accountRelation);
		str.append(":");
		str.append(userPayPaltform);
		str.append(":");
		str.append(openId);
		
		return str.toString();			
	}
	/**
	 * 获取账户转账的锁
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @param day
	 * @return
	 */
	public String getSettledDailyLockKey(String userPayPaltform,String openId,int accountType,String day)
	{
		StringBuilder str = new StringBuilder();
		str.append(this.Key_prefix_SettledAccLock);
		str.append(":");
		str.append(userPayPaltform);
		str.append(":");
		str.append(openId);
		str.append(":");
		str.append(accountType);
		str.append(":");
		
		str.append(day);
		
		return str.toString();			
	}
	
	public String getSettledDailyInfoKey(String userPayPaltform,String openId,int accountType,String day)
	{
		StringBuilder str = new StringBuilder();
		str.append(this.Key_prefix_SettledAccInfo);
		str.append(":");
		str.append(userPayPaltform);
		str.append(":");
		str.append(openId);
		str.append(":");
		str.append(accountType);
		str.append(":");
		
		str.append(day);
		
		return str.toString();			
	}
	
	/**
	 * 将每天的结转信息保存到redis中
	 * @param accountInfo
	 * @return
	 */
	public boolean putAccSettledRecord(AccountBalance accountInfo)
	{
		String key = this.getSettledDailyInfoKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId(), accountInfo.getAccountType(), accountInfo.getExpireTimes());
		AccountInfo AccountInfo=null;
		try {
			this.redisTemplate.opsForValue().set(key, accountInfo, 10, TimeUnit.DAYS);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public AccountBalance getAccSettledRecord(AccountInfo accountInfo) throws Exception
	{
		String key = this.getSettledDailyInfoKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId(), accountInfo.getAccountType(), accountInfo.getExpireTimes());
		AccountBalance accountBalance=null;
		try {
			return (AccountBalance)this.redisTemplate.opsForValue().get(key);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/**
	 * 
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @return
	 */
	public AccountInfo getAccountInfo(String userPayPaltform,String openId,int accountType)
	{
		String key = getAccountInfoKey(userPayPaltform, openId, accountType);
		AccountInfo AccountInfo=null;
		try {
			AccountInfo = (AccountInfo)this.redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return AccountInfo;
	}
	public boolean putAccountInfo(AccountInfo accountInfo)
	{
		String key = getAccountInfoKey(accountInfo);
		AccountInfo AccountInfo=null;
		try {
			this.redisTemplate.opsForValue().set(key, accountInfo, 5, TimeUnit.DAYS);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	

	public AccountInfo getAccountInfoDaily(String userPayPaltform,String openId,int accountType,String day)
	{
		String key = getAccountDailyKey(userPayPaltform, openId, accountType,day);
		AccountInfo accountInfo=null;
		try {
			accountInfo = (AccountInfo)this.redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountInfo;
	}
	public boolean putAccountInfoDaily(String day,AccountInfo accountInfo)
	{
		String key =  getAccountDailyKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId(), accountInfo.getAccountType(),day);
		try {
			this.redisTemplate.opsForValue().set(key, accountInfo, 5, TimeUnit.DAYS);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取对应ID所有账户的列表
	 * @param userPayPlatForm
	 * @param openId
	 * @return
	 */
	public  Map<Object, Object> getAccountRelation(String userPayPlatForm,String openId)
	{
		String key =  getAccountRelationKey(userPayPlatForm,openId);
		
		try {
			 Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
		     return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HashMap<>();
	}
	/**
	 * 更加日期放入账户关系
	 * @param day
	 * @param accountInfo
	 * @return
	 */
	public boolean putAccountRelation(String day,AccountInfo accountInfo)
	{
		String key =  getAccountRelationKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId());
		
		try {
			HashOperations<Object, Object, Object> hashOperations = this.redisTemplate.opsForHash();
			String accountkey =  getAccountDailyKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId(), accountInfo.getAccountType(),day);
			hashOperations.put(key, accountkey, accountInfo);
			/**
			 * 
			 */
			String keyList = this.getDailySettledListkey(day);
			this.redisTemplate.opsForList().rightPush(keyList, accountInfo);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean removeAccountRelation(String day,AccountInfo accountInfo)
	{
		String key =  getAccountRelationKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId());
		
		try {
			HashOperations<Object, Object, Object> hashOperations = this.redisTemplate.opsForHash();
			String accountkey =  getAccountDailyKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId(), accountInfo.getAccountType(),day);
			hashOperations.delete(key, accountkey);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @param day
	 * @param accountInfo
	 * @return
	 */
	public AccountInfo getWillSettledAccount(String day) throws Exception
	{
		AccountInfo account=null;
		try {
			String keyList = this.getDailySettledListkey(day);
			account = (AccountInfo)this.redisTemplate.opsForList().leftPop(keyList);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
		return account;
	}
	
	
	public long getWillSettledSize(String day) throws Exception
	{
		
		try {
			String keyList = this.getDailySettledListkey(day);
			Long keySize= this.redisTemplate.opsForList().size(keyList);
			if(keySize!=null)
				return keySize.longValue();
			return 0;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception();
		}
		
	}
	/**
	 * 
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @param day
	 * @return
	 */
	protected String getCreateAccountKey(String userPayPaltform,String openId,int accountType,String day)
	{
		StringBuilder str = new StringBuilder();
		str.append(Key_prefix_CreateAccountLock);
		str.append(":");
		str.append(userPayPaltform);
		str.append(":");
		str.append(openId);
		str.append(":");
		str.append(accountType);
		str.append(":");
		
		str.append(day);
		
		return str.toString();			
	}
	
	/**
	 * 释放创建用户的锁
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @param day
	 * @param requestTime
	 */
	public void releaseCreateAccountLock(String userPayPaltform,String openId,int accountType,String day,long requestTime)  
	{
		try {
			String lockKey = this.getCreateAccountKey(userPayPaltform, openId, accountType, day);
			String requestTimeS = (String)redisTemplate.opsForValue().get(lockKey);
			long requestTimeL = Long.parseLong(requestTimeS);
			if(requestTimeL==requestTime)
			{
				redisTemplate.delete(lockKey);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 申请创建账户的锁
	 * @param userPayPaltform
	 * @param openId
	 * @param accountType
	 * @param day
	 * @return
	 */
	public long  getCreateAccountLock(String userPayPaltform,String openId,int accountType,String day){  
		try{  
            
        	String lockKey = this.getCreateAccountKey(userPayPaltform, openId, accountType, day);
            long startTime = System.currentTimeMillis();  
            boolean needWait = false;
            while (true){
            	{
	            	if(redisTemplate.opsForValue().setIfAbsent(lockKey,String.valueOf(startTime))){  
	            		startTime = System.currentTimeMillis(); 
	            		redisTemplate.opsForValue().set(lockKey,String.valueOf(startTime),30,TimeUnit.SECONDS);  
	                	return startTime;  
	                }
	            	//如果是第一次进来，最好判断一下是否老的已经过期，否则会死锁
	            	else if(!needWait)
	            	{
	            		needWait = true;
	            		String requestTimeS = (String)redisTemplate.opsForValue().get(lockKey);
	            		//获取时间
	            		if(requestTimeS!=null)
	            		{
		            		long requestTimeL=0;
							try {
								requestTimeL = Long.parseLong(requestTimeS);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		//如果没有获取到，并且已经超时
		                    if(System.currentTimeMillis() - requestTimeL > 30000){  
		                    	redisTemplate.delete(lockKey);
		                    	continue;
		                    }		
		                    else
		                    {
		                    	needWait = true;	
		                    }
	            		}
	            		//如果没有时间
	            		else
	            		{
	            			redisTemplate.delete(lockKey);
		                    continue;
	            		}
	            	}
            	}
                //如果没有获取到，并且已经超时
                if(System.currentTimeMillis() - startTime > 30000){  
                    return 0;  
                }  
                //延迟一段时间
                Thread.sleep(1000);  
            }  
        }catch (Exception e){  
              e.printStackTrace();
            return 0;  
        }  
        finally
        {
        	
        }
    }  
	
	/**
	 * 申请通用锁
	 * @param lockKey
	 * @return  0--失败
	 */
	public long  getCommonLock(String lockKey){  
		try{  
            
            long startTime = System.currentTimeMillis();  
            boolean needWait = false;
            while (true){
            	{
	            	if(redisTemplate.opsForValue().setIfAbsent(lockKey,String.valueOf(startTime))){  
	            		startTime = System.currentTimeMillis(); 
	            		redisTemplate.opsForValue().set(lockKey,String.valueOf(startTime),30,TimeUnit.SECONDS);  
	                	return startTime;  
	                }
	            	//如果是第一次进来，最好判断一下是否老的已经过期，否则会死锁
	            	else if(!needWait)
	            	{
	            		needWait = true;
	            		String requestTimeS = (String)redisTemplate.opsForValue().get(lockKey);
	            		//获取时间
	            		if(requestTimeS!=null)
	            		{
		            		long requestTimeL=0;
							try {
								requestTimeL = Long.parseLong(requestTimeS);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            		//如果没有获取到，并且已经超时
		                    if(System.currentTimeMillis() - requestTimeL > 30000){  
		                    	redisTemplate.delete(lockKey);
		                    	continue;
		                    }		
		                    else
		                    {
		                    	needWait = true;	
		                    }
	            		}
	            		//如果没有时间
	            		else
	            		{
	            			redisTemplate.delete(lockKey);
		                    continue;
	            		}
	            	}
            	}
                //如果没有获取到，并且已经超时
                if(System.currentTimeMillis() - startTime > 30000){  
                    return 0;  
                }  
                //延迟一段时间
                Thread.sleep(1000);  
            }  
        }catch (Exception e){  
              e.printStackTrace();
            return 0;  
        }  
        finally
        {
        	
        }
    }

	public void releaseCommonLock(String lockKey,long requestTime)  
	{
		try {
			String requestTimeS = (String)redisTemplate.opsForValue().get(lockKey);
			long requestTimeL = Long.parseLong(requestTimeS);
			if(requestTimeL==requestTime)
			{
				redisTemplate.delete(lockKey);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 内存中结转数据
	 * @param accountInfo
	 */
	public void settledAccount(AccountInfo accountInfo)  
	{
		String key = getAccountDailyKey(accountInfo.getUserPayPaltform(), accountInfo.getOpenId(), accountInfo.getAccountType(),accountInfo.getExpireTimes());
		AccountInfo queryAccountInfo=null;
		try {
			queryAccountInfo = (AccountInfo)this.redisTemplate.opsForValue().get(key);
			if(queryAccountInfo !=null)
			{
				redisTemplate.delete(key);
			}
			
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		removeAccountRelation(accountInfo.getExpireTimes(),accountInfo);	
		return ;
	}
	
}
