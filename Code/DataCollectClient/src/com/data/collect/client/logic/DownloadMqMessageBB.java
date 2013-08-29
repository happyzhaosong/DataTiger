package com.data.collect.client.logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.DownloadMqMessageDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.MQMessageDTO;
import com.general.common.exception.InvalidIdException;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.StringTool;

public class DownloadMqMessageBB extends BaseBB {

	private DownloadMqMessageDAO downloadMqMessageDao = new DownloadMqMessageDAO();

	public String checkDownloadMqMessageStatusInLoop(int mqMessageid) throws Exception
	{
		String ret = "";
		ret = this.checkDownloadMqMessageStatus(mqMessageid);
		if(!Constants.MQ_MESSAGE_NOT_SEND.equals(ret) && !Constants.MQ_MESSAGE_NOT_RECEIVED.equals(ret) && !Constants.MQ_MESSAGE_RECEIVED.equals(ret) && !Constants.MQ_MESSAGE_TASK_FINISHED.equals(ret))
		{
			return ret;
		}else 
		{
			for(int i=0;i<Constants.LOOP_COUNT_10;i++)
			{
				Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND * 6);
				ret = this.checkDownloadMqMessageStatus(mqMessageid);
				if(!Constants.MQ_MESSAGE_NOT_SEND.equals(ret) && !Constants.MQ_MESSAGE_NOT_RECEIVED.equals(ret) && !Constants.MQ_MESSAGE_RECEIVED.equals(ret))
				{
					break;
				}
			}
		}
		return ret;
	}
	
	private String checkDownloadMqMessageStatus(int id) throws Exception
	{
		String ret = Constants.MQ_MESSAGE_NOT_SEND;
		if(id<0)
		{
			throw new InvalidIdException("Id can not be" + id);
		}else
		{
			MQMessageDTO mqMessageDto = this.downloadMqMessageDao.getMqMessageById(id);
			if(mqMessageDto!=null)
			{
				String receiveTime = mqMessageDto.getReceiveTime();
				String finishTime = mqMessageDto.getFinishTime();
				String failReason = mqMessageDto.getFailReason();
				
				if(!"".equals(failReason) && failReason!=null)
				{
					ret = failReason;
				}else if(!"".equals(finishTime) && finishTime!=null)
				{
					ret = Constants.MQ_MESSAGE_TASK_FINISHED;
				}else if(!"".equals(receiveTime) && receiveTime!=null)
				{
					ret = Constants.MQ_MESSAGE_RECEIVED;
				}else
				{
					ret = Constants.MQ_MESSAGE_NOT_RECEIVED;
				}
			}
		}
		return ret;
	}
	
	public JsonDTO getMQMessageListByThreadTableIdAndSiteId(HttpServletRequest request) throws Exception
	{
		
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);		
		StringTool.checkInteger(webSiteId, "Web site id must be an integer.");
		
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);					
		this.downloadMqMessageDao.setPageDto(pageDto);
		
		String threadTableId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_TABLE_ID, request);
		List<MQMessageDTO> dtoList = this.downloadMqMessageDao.getMqMessageListByThreadTableIdAndSiteId(threadTableId, webSiteId);
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_MQ_MESSAGE_LIST, dtoList);
	}
}
