package com.data.collect.web.driver.thread;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadTaskDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.exception.DeleteThreadException;
import com.data.collect.common.exception.ParsePageException;
import com.data.collect.server.dao.DownloadTaskDAO;
import com.data.collect.server.dao.WebSiteDAO;
import com.data.collect.web.driver.browser.BrowserRunner;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.MQMessageDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.DBTool;
import com.general.common.util.ExceptionTool;
import com.general.common.util.GetDeltaTimeTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;

public class ThreadRunner extends Thread {
	
	//record this thread runner's manager
	private ThreadManager manager = null;
	
	private int threadTableId = -1; 
	
	private MQMessageDTO mqDto = null;
	
	private DownloadTaskDAO downloadTaskDao = new DownloadTaskDAO();
	
	private WebSiteDAO webSiteDao = new WebSiteDAO();
	
	private BrowserRunner browserRunner = new BrowserRunner();
	
	//set running to false before interrupt this thread object.
	private boolean running = true;
	
	//record the time million seconds that this thread web driver start download page. Use this field to calculate whether this thread web driver hang or not. 
	private long lastRunTime = -1;

	//record not run task in current thread get task list, for set these task apply time to '' when this thread is stopped.
	private List<DownloadTaskDTO> notRunTaskInList = new ArrayList<DownloadTaskDTO>(); 
		
	public long getLastRunTime() {
		return lastRunTime;
	}

	public void setLastRunTime(long lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public ThreadManager getManager() {
		return manager;
	}

	public void setManager(ThreadManager manager) {
		this.manager = manager;
	}

	public synchronized boolean isRunning() {
		return running;
	}

	public synchronized void setRunning(boolean running) {
		this.running = running;
	}

	public BrowserRunner getBrowserRunner() {
		return browserRunner;
	}

	public void setBrowserRunner(BrowserRunner browserRunner) {
		this.browserRunner = browserRunner;
	}

	public int getThreadTableId() {
		return threadTableId;
	}

	public void setThreadTableId(int threadTableId) {
		this.threadTableId = threadTableId;
	}

	public MQMessageDTO getMqDto() {
		if(mqDto==null)
		{
			mqDto = new MQMessageDTO();
		}
		return mqDto;
	}

	public void setMqDto(MQMessageDTO mqDto) {
		this.mqDto = mqDto;
	}

	@Override
	public void run() {		
		LogTool.logText("Thread start, thread table id : " + this.getThreadTableId() + " , priority : " + this.getPriority());
		while(this.running)
		{
			try
			{				
				if(this.mqDto==null)
				{
					throw new DeleteThreadException("MQDto in thread runner is null.");
				}
				
				if(this.mqDto.getSiteId()<=0)
				{
					throw new DeleteThreadException("Web site id is not valid.");
				}
				
				StringBuffer totalTimeDurationBuf = new StringBuffer();
				totalTimeDurationBuf.append("Duration in task run (millisecond)<br/>");
				
				GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();
				
				String applyTaskDurationInfo = this.applyNotRunTaskList();
				getDltaTimeTool.getDeltaTime("Apply task ready, apply count = " + notRunTaskInList.size() + " , total duration time", totalTimeDurationBuf, 0, false);
				totalTimeDurationBuf.append(applyTaskDurationInfo);
				
				if(!ClassTool.isListEmpty(notRunTaskInList))
				{	
					//when download 100 pages then restart firefox browser, this way can reduce browser communication error
					this.browserRunner.resetBrowser();
					
					int size = notRunTaskInList.size();	
					if(size>0)
					{
						DownloadTaskDTO lastTaskDto = notRunTaskInList.get(0);
						int threadSleepMilliSeconds = 0;
						
						WebSiteDTO webSiteDto = webSiteDao.getWebSiteById(lastTaskDto.getSiteId());
						getDltaTimeTool.getDeltaTime("webSiteDao.getWebSiteById", totalTimeDurationBuf, Constants.MIN_RECORD_DURATION_TIME, false);
						
						for(int i=0;i<size;i++)
						{
							/*
							if(i>0 && i%10==0)
							{
								this.browserRunner.resetBrowser();
							}
							*/
							
							long startTime = System.currentTimeMillis();
							
							//for thread monitor to calculate whether this web driver hang or not.
							this.setLastRunTime(startTime);
							
							DownloadTaskDTO taskDto = notRunTaskInList.get(i);	
							
							taskDto.setRunTime(String.valueOf(startTime));
							
							String browsePageDuration = "";
							//Exception throwEx = null;
							try
							{
								//this way can reduce get web site dto time.
								if(taskDto.getSiteId()!=lastTaskDto.getSiteId())
								{
									webSiteDto = webSiteDao.getWebSiteById(lastTaskDto.getSiteId());
									getDltaTimeTool.getDeltaTime("get new WebSiteDto", totalTimeDurationBuf, Constants.MIN_RECORD_DURATION_TIME, false);									
								}								
								//2.2run each download task
								this.browserRunner.setAccessDenied(false);
								LogTool.logText("Begin download and parse page = " + taskDto.getPageUrl());
								browsePageDuration = this.browserRunner.browsePage(taskDto, webSiteDto);								
								lastTaskDto = taskDto;
								
								if(this.browserRunner.isAccessDenied())
								{
									//record access denied info and reset apply time for next time reget
									taskDto.resetApplyTime("Access denied");
									taskDto.setAccessDeniedDate(String.valueOf(System.currentTimeMillis()));
									taskDto.setAccessDeniedThreadSleepTime(threadSleepMilliSeconds);
								}
							}catch(Exception ex)
							{
								LogTool.logText("totalTimeDurationBuf exception, total time duration = " + totalTimeDurationBuf.toString());
								LogTool.logError(ex);
								
								if(!ClassTool.isNullObj(taskDto))
								{
									taskDto.setErrorMessage(ex.getMessage());									
								}
								
								if(this.browserRunner.ifBrowserUnreach(ex))
								{
									//reset browser and reset apply task for next reget.
									this.browserRunner.resetBrowser();
									if(!ClassTool.isNullObj(taskDto))
									{
										taskDto.resetApplyTime("Browser unreachable, detail message : " + ex.getMessage());
									}	
								}								
							}
							
							long endTime = System.currentTimeMillis();
							long deltaTime = endTime - startTime;
							
							taskDto.setTaskRunDeltaTime(deltaTime);		
							taskDto.setThreadTableId(this.getThreadTableId());
							taskDto.setDurationInfo(totalTimeDurationBuf.toString() + browsePageDuration);							
							this.downloadTaskDao.saveDownloadTask(taskDto);
										
							if(!this.running)
							{
								break;
							}
							
							threadSleepMilliSeconds = this.countSleepMilliSeconds(this.browserRunner.isAccessDenied(), threadSleepMilliSeconds);
							LogTool.logText("Task run delta time = " + deltaTime + " millisecond, Thread sleep " + threadSleepMilliSeconds + " millisecond.");
							this.sleep(threadSleepMilliSeconds);
						}
					}
				}else
				{
					LogTool.logText("No task applied.");
					this.sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*60);					
				}				
			}catch(Exception ex)
			{
				LogTool.logError( ex);
				try{
					if(this.browserRunner.ifBrowserUnreach(ex))
					{
						LogTool.logText("Unreach browser exception occured, so stop this running thread. See exception above.");
						ThreadManager.getInstance().updateThreadStopTimeInDB(this.getId(), System.currentTimeMillis(), ex.getMessage() + "</br> Detail : " + ExceptionTool.getExceptionStackTraceString(ex));
						ThreadManager.getInstance().deleteThreadInMemory(this.getId());
					}
				}catch(Exception e)
				{
					LogTool.logError( e);		
				}
			}
		}
	}
	 
	
	/*
	 * To avoid multiple thread apply same download tasks, so need to synchronized method
	 * return: Duration time in apply task function.
	 * */
	private String applyNotRunTaskList() throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();
		
		while(this.downloadTaskDao.ifOtherThreadApplyingTask())
		{
			this.sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*3);		
		}
		getDltaTimeTool.getDeltaTime("apply task wait", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
		
		this.downloadTaskDao.setThreadApplyingTask(true);		
		getDltaTimeTool.getDeltaTime("set applying status", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);

		
		int count = 0;

		//1.select at most 10 not applied download task from download task db
		BasePageDTO pageDto = DBTool.createPageDto("inDbTime", Constants.DB_ORDER_ASC, String.valueOf(count*10), "10", String.valueOf(count+1));
		pageDto.setLimit("100");
		pageDto.setSort("");
		this.downloadTaskDao.setPageDto(pageDto);				
		count++;
			
		//first parse content page
		notRunTaskInList = downloadTaskDao.getNotRunDownloadTaskListBySiteId(String.valueOf(mqDto.getSiteId()), 1);
		getDltaTimeTool.getDeltaTime("apply content page task", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
		
		//then parse not content page
		if(ClassTool.isListEmpty(notRunTaskInList))
		{
			notRunTaskInList = downloadTaskDao.getNotRunDownloadTaskListBySiteId(String.valueOf(mqDto.getSiteId()), 0);	
			getDltaTimeTool.getDeltaTime("apply not content page task", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
		}
						
		//2.loop for each download task 
		if(notRunTaskInList!=null)
		{
			//update all these 10 download task apply time to current time, then run download.
			this.updateAppliedDownloadTaskApplyTime(notRunTaskInList, String.valueOf(System.currentTimeMillis()));
			getDltaTimeTool.getDeltaTime("update task apply time", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
		}
		
		this.downloadTaskDao.setThreadApplyingTask(false);
		getDltaTimeTool.getDeltaTime("reset applying status", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
		return retBuf.toString();
	}

	private int countSleepMilliSeconds(boolean accessDenied, int lastMilliSeconds)
	{
		int ret = 1000;
		Random random = new Random();
		while(true)
		{
			int seed = random.nextInt(20);
			ret = seed*100;
			if(!accessDenied)
			{
				if(ret!=lastMilliSeconds)
				{
					break;
				}
			}else
			{
				ret = 3000;
			}
		}
		
		if(ret<1000)
		{
			ret = 1000;
		}
		return ret;
	}
	
	
	public void resetNotRunTaskApplyTimeInList() throws Exception
	{
		this.updateAppliedDownloadTaskApplyTime(notRunTaskInList, "");
		if(!ClassTool.isNullObj(notRunTaskInList))
		{
			int size = notRunTaskInList.size();
			if(size>0)
			{
				StringBuffer idBuf = new StringBuffer();
				for(int i=0;i<size;i++)
				{
					DownloadTaskDTO dto = notRunTaskInList.get(i);
					
					if(dto.getTaskRunDeltaTime()<=0)
					{						
						idBuf.append(dto.getId());
						
						if(i<(size-1))
						{
							idBuf.append(Constants.SEPERATOR_COMMA);
						}
						
					}
				}
				
				String resetTaskId = idBuf.toString();
				if(resetTaskId.endsWith(Constants.SEPERATOR_COMMA))
				{
					resetTaskId = resetTaskId.substring(0, resetTaskId.length()-1);
				}
				
				if(!StringTool.isEmpty(resetTaskId))
				{
					this.downloadTaskDao.updateAllDownloadTasksApplyTimeByTaskIds(resetTaskId, "", this.getThreadTableId(), String.valueOf(System.currentTimeMillis()), "Thread manager stop thread, so reset this not run download task apply time.");
				}
			}
		}

	}

	
	private void updateAppliedDownloadTaskApplyTime(List<DownloadTaskDTO> taskList, String applyTime) throws Exception
	{
		if(!ClassTool.isNullObj(taskList))
		{
			int size = taskList.size();
			if(size>0)
			{
				StringBuffer idBuf = new StringBuffer();
				for(int i=0;i<size;i++)
				{
					DownloadTaskDTO dto = taskList.get(i);
					
					dto.setApplyTime(applyTime);
						
					idBuf.append(dto.getId());
						
					if(i<(size-1))
					{
						idBuf.append(Constants.SEPERATOR_COMMA);
					}					
				}
				
				this.downloadTaskDao.updateAllDownloadTasksApplyTimeByTaskIds(idBuf.toString(), applyTime, this.getThreadTableId(), "", "");
			}
		}
	}
}
