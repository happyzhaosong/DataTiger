package com.data.collect.common.util;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadTaskDTO;
import com.data.collect.common.dto.JsonDTO;
import com.data.collect.common.dto.WebSiteDTO;

public class DownloadTool {
	
	public static JsonDTO createMqJsonDtoByActionStatus(String mqActionStatus, String action) throws Exception
	{
		JsonDTO retJson = new JsonDTO();
		if(Constants.MQ_MESSAGE_TASK_FINISHED.equals(mqActionStatus))
		{
			retJson.setSuccess(true);
			retJson.setMessage(action + " operation success.");
		}else
		{
			retJson.setSuccess(false);			
			if(Constants.MQ_MESSAGE_NOT_SEND.equals(mqActionStatus))
			{
				retJson.setMessage(action + " operation mq message not sent.");
			}else if(Constants.MQ_MESSAGE_NOT_RECEIVED.equals(mqActionStatus))
			{
				retJson.setMessage(action + " operation mq message not received.");
			}else if(Constants.MQ_MESSAGE_RECEIVED.equals(mqActionStatus))
			{
				retJson.setMessage(action + " operation not complete in time, you can refresh to see its status.");
			}else
			{
				retJson.setMessage(action + " operation failed, detail fail reason is " + mqActionStatus);	
			}			
		}
		return retJson;
	}	
	

	
	public static List<String> createDownloadTaskUrlList(WebSiteDTO webSiteDto)  throws Exception
	{
		String topUrl = webSiteDto.getTopUrl();
		StringTool.checkEmpty(topUrl, "Download web site top url can not be empty");
		
		String topUrlArr[] = StringTool.splitString(topUrl, Constants.SEPERATOR_SEMICOLON);
		
		List<String> cRet = new ArrayList<String>();
		if(webSiteDto.isMultiCountry())
		{
			int len = topUrlArr.length;
			for(int i=0;i<len;i++)
			{
				String url = topUrlArr[i];
				List<String> tmpList = StringTool.replacePlaceHolderInUrl(url, Constants.PLACE_HOLDER_COUNTRY, StringTool.getCountryIsoCodeArr().split(Constants.SEPERATOR_SPACE));
				cRet.addAll(tmpList);
			}
		}

		
		List<String> ret = new ArrayList<String>();
		if(!StringTool.isEmpty(webSiteDto.getPlaceHolders()))
		{
			if(cRet.size()>0)
			{
				for(int i=0;i<cRet.size();i++)
				{
					String url = cRet.get(i);
					List<String> tmpList = StringTool.replacePlaceHolderInUrl(url, Constants.PLACE_HOLDER_VALUE, webSiteDto.getPlaceHolders().split(Constants.SEPERATOR_SEMICOLON));
					ret.addAll(tmpList);					
				}
			}else
			{
				int len = topUrlArr.length;
				for(int i=0;i<len;i++)
				{
					String url = topUrlArr[i];
					List<String> tmpList = StringTool.replacePlaceHolderInUrl(url, Constants.PLACE_HOLDER_VALUE, webSiteDto.getPlaceHolders().split(Constants.SEPERATOR_SEMICOLON));
					ret.addAll(tmpList);					
				}
			}
		}else
		{
			if(cRet.size()>0)
			{
				ret.addAll(cRet);
			}else
			{
				int len = topUrlArr.length;
				for(int i=0;i<len;i++)
				{
					String url = topUrlArr[i];
					ret.add(url);					
				}
			}
		}


		return ret;
	}
	
	
	public static String processDownloadPageUrl(String url)
	{
		if(!StringTool.isEmpty(url))
		{
			if(url.endsWith("/#"))
			{
				url = url.substring(0, url.length()-1);
			}
		}
		return url;
	}

}