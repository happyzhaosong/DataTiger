package com.data.collect.web.driver.mq;

import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.data.collect.common.constants.Constants;
import com.data.collect.server.dao.DownloadMqMessageDAO;
import com.data.collect.web.driver.thread.ThreadManager;
import com.general.common.dto.MQMessageDTO;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;
import com.general.server.manager.MQMessageManager;

public class MQMessageListener implements MessageListener {

	private DownloadMqMessageDAO downloadMqMessageDao = new DownloadMqMessageDAO();
	
	@Override
	public void onMessage(Message message) {		
		try
		{
			//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
			if (message instanceof TextMessage) {
				LogTool.logText("On Message thread priority = " + Thread.currentThread().getPriority());				
	            TextMessage txtMsg = (TextMessage) message;
	            String jsonTxt = txtMsg.getText();
	            LogTool.logText(jsonTxt);
	            List<MQMessageDTO> mqDtoList = JsonTool.getMQDtoByJsonString(jsonTxt);
	            
	            this.processMQMessage(mqDtoList);
	        }
		}catch(Exception ex)
		{
			LogTool.logError( ex);
		}
	}

	private void processMQMessage(List<MQMessageDTO> mqDtoList) throws Exception
	{
		if(mqDtoList!=null)
		{
	        int size = mqDtoList.size();
	        for(int i=0;i<size;i++)
	        {
	        	MQMessageDTO mqDto = mqDtoList.get(i);
	        	LogTool.logText(mqDto.toString());
	        	
	        	if(!this.downloadMqMessageDao.ifMqMessageReceived(mqDto))
	        	{
		        	mqDto.setReceiveTime(String.valueOf(System.currentTimeMillis()));
		        	this.downloadMqMessageDao.saveDownloadMqMessage(mqDto);
		        	
		        	if(Constants.DOWNLOAD_THREAD_ACTION_STOP.equals(mqDto.getAction()))
		        	{
		        		ThreadManager.getInstance().stopThreadInDBAndMemory(mqDto);
		        	}else if(Constants.DOWNLOAD_THREAD_ACTION_CREATE.equals(mqDto.getAction()))
		        	{
		        		ThreadManager.getInstance().createThread(mqDto);
		        	}
	        	}
	        }
		}
	}	
	
	public void processUnReceivedMQMessage() throws Exception
	{
		List<MQMessageDTO> mqDtoList = this.downloadMqMessageDao.getUnReceivedMqMessageList();
		if(mqDtoList!=null && mqDtoList.size()>0)
		{
			LogTool.logText("Start process unreceived mq messages.");
			this.processMQMessage(mqDtoList);
			LogTool.logText("End process unreceived mq messages.");
		}else
		{
			LogTool.logText("No unreceived mq messages need to process.");
		}
	}
	
	
	public static void main(String[] args) {		
		MQMessageListener mqmListener = new MQMessageListener();
		try
		{		
			mqmListener.processUnReceivedMQMessage();
			MQMessageManager.getInstance().setMessageListener(mqmListener);
		}catch(Exception ex)
		{
			LogTool.logError(ex);
		}
	}
}
