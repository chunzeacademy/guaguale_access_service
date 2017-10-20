package com.company.guaguale.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.company.guaguale.service.AccountTransferService;
import com.company.guaguale.service.impl.RedisAccountService;

@Component
@ConditionalOnProperty(name = "settled.scheduler.enable")
@EnableScheduling
public class SettledScheduler{
	private static Logger logger = LoggerFactory.getLogger(SettledScheduler.class);
	@Resource(name="accountTransferService")
	private AccountTransferService accountTransferService;
	
	@Resource(name="redisAccountService")
	private RedisAccountService  redisAccountService;
	private final int initSize = 2;
	private final int maxSize = 40;
	private final int keepAliveTime = 60;
	
	/**
	 * 每一个任务最大结算的个数
	 */
	private int maxRunNumberPerThread =  2000;
 	//创建线程池
	private ThreadPoolExecutor settledTaskPool = new ThreadPoolExecutor(initSize, maxSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(maxSize*2));
	
	@Scheduled (cron = "${settled.scheduler.cron}")
	public void settledCron()
	{
		try {
			logger.info("settledCron start ");
			managedTask();
			logger.info("settledCron end ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 获取结算超时的任务，重做；
	 */
	protected void cronMapCheck()
	{
		//十分钟
		int timeout = 15*60*1000;
		this.redisAccountService.getTimeOutSettlingAccs(timeout, 1000);
	}
	
	protected void managedTask()
	{
		boolean isSchedulerTask = false;
		long totalSize =0;
		for(int i=1;i<10;i++)
		{
			try {
				String day = accountTransferService.getPreAccountDay(i);
				long size = redisAccountService.getWillSettledSize(day);
				totalSize=totalSize+size;
				if(size>0)
				{
					//每一个线程处理的数据个数
					long perThreadAmount = size / maxSize;
					//线程个数
					long threadAmount = maxSize;
					if(perThreadAmount==0)
					{
						perThreadAmount=1;
						threadAmount = size;
					}
					else if(perThreadAmount>maxRunNumberPerThread)
					{
						//通知运维告警；
						
					}
					for(int j=0;j<threadAmount;j++)
					{
						SettledTask settledTask = new SettledTask(i,perThreadAmount,accountTransferService);
						this.settledTaskPool.execute(settledTask);
						isSchedulerTask = true;
					}
					/**
					 * 如果达到了最大线程数
					 */
					if(threadAmount==maxSize&&threadAmount>100)
					{
						break;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//如果没有调度任务，检查重做队列
		if(!isSchedulerTask)
		{
			cronMapCheck();
		}
		logger.info("settledCron size: " + totalSize);
		
	}
	
	
}
