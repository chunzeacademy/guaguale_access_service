package com.company.guaguale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.company.guaguale.domain.AccountInfo;

@Mapper
public interface AccountInfoMapper {
	/**
	 * 插入新的账户类型
	 * @param accountInfo
	 */
	@Insert("insert into accountInfo(userPayPaltform,openId,accountType,currency,userAccountId,accountStatus,expireTimes) VALUES (#{userPayPaltform},#{openId},#{accountType},#{currency},#{userAccountId},#{accountStatus},#{expireTimes})")
	public void insertAccountInfo(AccountInfo accountInfo);
	/**
	 * 更新状态和时间
	 * @param accountInfo
	 * @return
	 */
	@Update("update accountInfo set accountStatus=#{accountStatus},expireTimes=#{expireTimes} where userPayPaltform= #{userPayPaltform} and openId=#{openId} and accountType=#{accountType}")
	public int updateStatusTimes(AccountInfo accountInfo);
	/**
	 * 更新状态
	 * @param accountInfo
	 * @return
	 */
	@Update("update accountInfo set accountStatus=#{accountStatus} where userPayPaltform= #{userPayPaltform} and openId=#{openId} and accountType=#{accountType}")
	public int updateStatus(AccountInfo accountInfo);
	
	/**
	 * 按照账户状态查询
	 * @param accountInfo
	 * @return
	 */
	@Select("select userPayPaltform,openId,accountType,currency,userAccountId,accountStatus,expireTimes from accountInfo where userPayPaltform= #{userPayPaltform} and openId=#{openId} and accountType=#{accountType} and accountStatus=#{accountStatus}")
	public List<AccountInfo> selectByStatus(AccountInfo accountInfo);
	
	/**
	 * 按照金额查询
	 * @param accountInfo
	 * @return
	 */
	@Select("select userPayPaltform,openId,accountType,currency,userAccountId,accountStatus,expireTimes from accountInfo where userPayPaltform= #{userPayPaltform} and openId=#{openId} and accountType=#{accountType}")
	public List<AccountInfo> selectByType(AccountInfo accountInfo);
	
	@Select("select userPayPaltform,openId,accountType,currency,userAccountId,accountStatus,expireTimes from accountInfo where userPayPaltform= #{userPayPaltform} and openId=#{openId}")
	public List<AccountInfo> selectByOpenId(AccountInfo accountInfo);
	
	@Select("select userPayPaltform,openId,accountType,currency,userAccountId,accountStatus,expireTimes from accountInfo where userPayPaltform= #{userPayPaltform} and openId=#{openId} and accountType=#{accountType} and expireTimes=#{expireTimes}")
	public List<AccountInfo> selectByTimes(AccountInfo accountInfo);
	
}
