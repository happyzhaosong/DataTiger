package com.data.collect.web.driver.thread;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadThreadDTO;
import com.data.collect.common.dto.MQMessageDTO;
import com.data.collect.common.util.ClassTool;
import com.data.collect.common.util.LogTool;
import com.data.collect.server.dao.DownloadMqMessageDAO;
import com.data.collect.server.dao.DownloadThreadDAO;

public class ThreadManager {
	
	private List<ThreadRunner> threadRunnerList = null;
	
	private static ThreadManager instance = null;
	private DownloadThreadDAO downloadThreadDao = new DownloadThreadDAO();
	private DownloadMqMessageDAO downloadMqMessageDao = new DownloadMqMessageDAO();
	private ThreadMonitor threadMonitor = null;
	
	public static ThreadManager getInstance() throws Exception
	{
		if(instance==null)
		{
			instance = new ThreadManager();	
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}	
		return instance;
	}

	private ThreadManager() {

	}

	public List<ThreadRunner> getThreadRunnerList() {
		if(this.threadRunnerList==null)
		{
			this.threadRunnerList = new ArrayList<ThreadRunner>();
		}
		return threadRunnerList;
	}

	public void setThreadRunnerList(List<ThreadRunner> threadRunnerList) {
		this.threadRunnerList = threadRunnerList;
	}
	
	public void createThread(MQMessageDTO mqDto) throws Exception
	{
		LogTool.logText("ThreadManager.createThread thread priority = " + Thread.currentThread().getPriority());
		
		if(ClassTool.isNullObj(this.threadMonitor))
		{
			this.threadMonitor = new ThreadMonitor();
			this.threadMonitor.setManager(this);
			this.threadMonitor.setPriority(Thread.MAX_PRIORITY - 1);
			this.threadMonitor.start();
		}
		
		int createThreadCount = mqDto.getCreateThreadCount();
		
		for(int i=0;i<createThreadCount;i++)
		{
			ThreadRunner tRunner = new ThreadRunner();
			tRunner.setMqDto(mqDto);			
				
			DownloadThreadDTO threadDto = new DownloadThreadDTO();
			threadDto.setId(-1);
			threadDto.setThreadId(String.valueOf(tRunner.getId()));
			threadDto.setStartTime(String.valueOf(System.currentTimeMillis()));
			threadDto.setThreadType(mqDto.getDownloadThreadType());
			threadDto.setUserId(mqDto.getUserId());
			threadDto.setSiteId(mqDto.getSiteId());
				
			this.downloadThreadDao.saveDownloadThread(threadDto);
			tRunner.setThreadTableId(threadDto.getId());
			
			mqDto.setThreadTableIds(String.valueOf(threadDto.getId()));
			mqDto.setThreadIds(String.valueOf(tRunner.getId()));
			mqDto.setFinishTime(String.valueOf(System.currentTimeMillis()));
			mqDto.setFailReason("");
			this.downloadMqMessageDao.saveDownloadMqMessage(mqDto);
			
			this.getThreadRunnerList().add(tRunner);
			
			tRunner.setManager(this);
			tRunner.setPriority(Thread.MAX_PRIORITY-2);
			tRunner.start();			
			
			Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*90);
		}
	}
		
	public void stopThreadInDBAndMemory(MQMessageDTO mqDto) throws Exception
	{
		LogTool.logText("ThreadManager.stopThreadInDBAndMemory thread priority = " + Thread.currentThread().getPriority());

		String threadTableIds = mqDto.getThreadTableIds();
		String threadIds = mqDto.getThreadIds();	
		//delete thread in memory first, this way if in memory thread delete failed, then db info will not be deleted also.
		this.deleteThreadObjectInMemoryByThreadIds(threadIds);
		//stop thread in db, set stop_time in table download_thread
		this.downloadThreadDao.stopDownloadThreadsByIds(threadTableIds, Constants.DOWNLOAD_THREAD_ACTION_STOP);
		//delete all thread task map info for these threads 
		//this.downloadThreadTaskMapDao.deleteDownloadThreadTaskMapByThreadTableIds(threadTableIds);		
		
		mqDto.setFinishTime(String.valueOf(System.currentTimeMillis()));
		mqDto.setFailReason("");
		this.downloadMqMessageDao.saveDownloadMqMessage(mqDto);
	}
	
	/*
	public void stopThreadInDBAndMemory(String threadTableIds, String threadIds, String stopReason) throws Exception
	{
		//delete thread in memory first, this way if in memory thread delete failed, then db info will not be deleted also.
		this.deleteThreadObjectInMemoryByThreadIds(threadIds);
		//stop thread in db, set stop_time in table download_thread
		this.downloadThreadDao.stopDownloadThreadsByIds(threadTableIds,  stopReason);
	}
	*/
	
	public void deleteThreadInMemory(long threadId) throws Exception
	{
		this.deleteThreadObjectInMemoryByThreadIds(String.valueOf(threadId));
	}
	
	public void updateThreadStopTimeInDB(long threadId, long stopTime, String stopReason) throws Exception
	{
		List<DownloadThreadDTO> list = this.downloadThreadDao.getDownloadThreadListByThreadTableIds(String.valueOf(threadId));
		if(list!=null && list.size()>0)
		{
			DownloadThreadDTO dto = list.get(0);
			dto.setStopTime(String.valueOf(stopTime));
			dto.setStopReason(stopReason);
			this.downloadThreadDao.saveDownloadThread(dto);
		}		
	}
	
	private void deleteThreadObjectInMemoryByThreadIds(String ids) throws Exception
	{
		String idArr[] = ids.split(Constants.SEPERATOR_COMMA);
		int len = idArr.length;
		for(int i=0;i<len;i++)
		{
			this.deleteThreadObjectInMemoryByThreadId(idArr[i]);
		}
	}
	
	private void deleteThreadObjectInMemoryByThreadId(String id) throws Exception
	{
		if(this.threadRunnerList!=null)
		{
			int size = this.threadRunnerList.size();
			for(int i=0;i<size;i++)
			{
				ThreadRunner threadRunner = this.threadRunnerList.get(i);
				if(threadRunner.getId()==Long.parseLong(id))
				{
					threadRunner.setRunning(false);

					try
					{
						threadRunner.interrupt();
					}catch(Exception ex)
					{
						LogTool.logError(ex);
						if(ex instanceof InterruptedException)
						{
							while(!threadRunner.isInterrupted())
							{
								LogTool.logText("Thread not interrupted.");
							}
						}else
						{
							throw ex;
						}
					}finally
					{
						if(!ClassTool.isNullObj(threadRunner.getBrowserRunner()))
						{
							if(!ClassTool.isNullObj(threadRunner.getBrowserRunner().getDriver()))
							{
								threadRunner.getBrowserRunner().getDriver().quit();
								threadRunner.getBrowserRunner().setDriver(null);
							}
						}
						   
						threadRunner.resetNotRunTaskApplyTimeInList();
						this.threadRunnerList.remove(threadRunner);
						threadRunner = null;
						size = this.threadRunnerList.size();
					}
				}
			}
		}
	}
}
