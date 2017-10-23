package com.company.guaguale.service.impl;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.guaguale.Const.GuaGuaLeConst;
import com.company.guaguale.domain.BonusRequest;
import com.company.guaguale.service.AccountService;
import com.company.guaguale.service.AccountTransferService;
import com.company.guaguale.task.SettledTask;
import com.xinwei.nnl.common.domain.ProcessResult;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {
	
	@Resource(name="redisAccountService")
	private RedisAccountService  redisAccountService;
	
	
    @Resource(name="accountService")
    private AccountService accountService;
    @Resource(name="accountTransferService")
    private AccountTransferService accountTransferService;
    
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPlusDailyBonus() {
		BonusRequest bonusRequest = new BonusRequest();
		bonusRequest.setUserPayPaltform("test6");
		bonusRequest.setOpenid("openid");
		bonusRequest.setFourth_deal_no("1234567890abcdefghi");
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, -3);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		bonusRequest.setNext_client_time(formatter.format(now.getTime()));
		bonusRequest.setSuccess_time("20171014141314");
		bonusRequest.setCurrency("yuan");
		bonusRequest.setOrderPaymentAmount("12334");
		accountService.plusDailyBonus(bonusRequest);
		ProcessResult processResult = accountService.getBalance(bonusRequest.getUserPayPaltform(), bonusRequest.getOpenid());
		System.out.println("******************");
		System.out.println(processResult);		

		
		
		bonusRequest.setUserPayPaltform("test6");
		bonusRequest.setOpenid("openid1");
		bonusRequest.setFourth_deal_no("1234567890abcdefghi");
		bonusRequest.setNext_client_time(formatter.format(now.getTime()));
		bonusRequest.setSuccess_time("20171014141314");
		bonusRequest.setCurrency("yuan");
		bonusRequest.setOrderPaymentAmount("12312");
		accountService.plusDailyBonus(bonusRequest);
		processResult = accountService.getBalance(bonusRequest.getUserPayPaltform(), bonusRequest.getOpenid());
		System.out.println("******************");
		System.out.println(processResult);		

		
		//fail("Not yet implemented");
	}
	@Test
	public void testSettled() {
		int result = GuaGuaLeConst.RESULT_Success;
		do {
			result = accountTransferService.settled(10,1000);
			
		}while(result==GuaGuaLeConst.RESULT_Success);
		BonusRequest bonusRequest = new BonusRequest();
		bonusRequest.setUserPayPaltform("test6");
		bonusRequest.setOpenid("openid");
		bonusRequest.setFourth_deal_no("1234567890abcdefghi");
		bonusRequest.setNext_client_time("20171013121314");
		bonusRequest.setSuccess_time("20171013141314");
		bonusRequest.setCurrency("yuan");
		bonusRequest.setOrderPaymentAmount("12334");
		ProcessResult processResult = accountService.getBalance(bonusRequest.getUserPayPaltform(), bonusRequest.getOpenid());
		System.out.println("******************");
		System.out.println(processResult);		

	}
	@Test
	public void testSchedulerSettled()
	{
		for(int i=1;i<10;i++)
		{
			String day = accountTransferService.getPreAccountDay(i);
			try {
				long size = redisAccountService.getWillSettledSize(day);
				System.out.println(day + ":" + size);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
