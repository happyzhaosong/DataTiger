package com.data.collect.web.driver.thread;

import java.util.List;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.util.ClassTool;
import com.data.collect.common.util.LogTool;

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
		
		LogTool.logText("Thread monitor start, priority : " + this.getPriority());
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
							//means that tRunner web driver hang for 5 minutes, need to reset
							if(deltaTime>(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*300))
							{
								LogTool.logText("Thread web driver hang, so reset it. Thread id : " + tRunner.getId() + " , Thread table id : " + tRunner.getThreadTableId());
								tRunner.getBrowserRunner().resetBrowser();
								LogTool.logText("Reset hang thread web driver done.");
							}
						}
					}
				}			
				
				this.sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*30);
			}catch(Exception ex)
			{
				LogTool.logError(ex);
			}
		}
	}
	
}
