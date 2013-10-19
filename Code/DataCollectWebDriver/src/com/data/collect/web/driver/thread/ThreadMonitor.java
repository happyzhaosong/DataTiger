package com.data.collect.web.driver.thread;

import java.util.List;

import com.data.collect.common.constants.Constants;
import com.general.common.util.ClassTool;
import com.general.common.util.LogTool;

/*
 * Monitor each thread status, if one thread web driver hang, then 
 * restart that webdriver to continue read web pages.
 * */
public class ThreadMonitor extends Thread {

	//record this thread monitor's manager
	private ThreadManager manager = null;
	
	public ThreadManager getManager() {
		return manager;
	}

	public void setManager(ThreadManager manager) {
		this.manager = manager;
	}

	@Override
	public void run() {
		
		LogTool.logText("Thread monitor start, priority : " + this.getPriority(), this.getClass().getName());
		while(true)
		{
			try
			{
				List<ThreadRunner> tRunnerList = this.manager.getThreadRunnerList();
			
				if(!ClassTool.isListEmpty(tRunnerList))
				{
					int size = tRunnerList.size();
					for(int i=0;i<size;i++)
					{
						ThreadRunner tRunner = tRunnerList.get(i);
						if(tRunner.getLastRunTime()!=-1)
						{
							long deltaTime = System.currentTimeMillis() - tRunner.getLastRunTime();
							//means that tRunner web driver hang for 60 minutes, need to reset, Because some pages need a lot of time to parse url and save those url to db.
							if(deltaTime>(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*300))
							{
								StringBuffer logTxtBuf = new StringBuffer();
								logTxtBuf.append("Thread web driver hang, so reset it. Thread id : ");
								logTxtBuf.append(tRunner.getId());
								logTxtBuf.append(" , Thread table id : ");
								logTxtBuf.append(tRunner.getThreadTableId());
								
								if(tRunner.getBrowserRunner()!=null && tRunner.getBrowserRunner().getDriver()!=null)
								{
									logTxtBuf.append(" , Current Url : ");
									logTxtBuf.append(tRunner.getBrowserRunner().getDriver().getCurrentUrl());
								}else
								{
									logTxtBuf.append(" , This browser has been reseted and not started again. Maybe the parse task not finished in the program.");
								}
								
								LogTool.logText(logTxtBuf.toString() , this.getClass().getName());
								
								tRunner.getBrowserRunner().resetBrowser();
								
								LogTool.logText("Reset hang thread web driver done.", this.getClass().getName());
							}
						}
					}
				}			
				
				this.sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*30);
			}catch(Exception ex)
			{
				LogTool.logError(ex, this.getClass().getName());
			}
		}
	}
	
}
