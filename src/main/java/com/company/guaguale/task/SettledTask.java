package com.company.guaguale.task;

import com.company.guaguale.service.AccountTransferService;

public class SettledTask implements Runnable{
	private AccountTransferService accountTransferService;
	/**
	 * 结算的天
	 */
	private long settledDay;
	/**
	 * 结算的个数
	 */
	private long amount;
	
	public SettledTask(long settledDay,long amount,AccountTransferService accountTransferService)
	{
		this.settledDay = settledDay;
		this.amount = amount;
		this.accountTransferService=accountTransferService;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			accountTransferService.settled((int)this.settledDay, (int)this.amount);
		}
		catch(Exception e)
		{
			
		}
	}

}
