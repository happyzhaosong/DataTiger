package com.data.collect.client.logic;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadTaskDTO;
import com.data.collect.common.dto.DownloadThreadDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.util.DownloadTool;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.DownloadMqMessageDAO;
import com.data.collect.server.dao.DownloadTaskDAO;
import com.data.collect.server.dao.DownloadThreadDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.MQMessageDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.StringTool;
import com.general.server.manager.MQMessageManager;

public class DownloadThreadBB extends BaseBB {
	private DownloadTaskDAO downloadTaskDao = new DownloadTaskDAO();
	private DownloadThreadDAO downloadThreadDao = new DownloadThreadDAO();
	private DownloadMqMessageDAO downloadMqMessageDao = new DownloadMqMessageDAO();
	
	private DownloadMqMessageBB downloadMqMessageBB = new DownloadMqMessageBB();
	private RoleBB roleBB = new RoleBB();
	private UserBB userBB = new UserBB();
	
	public JsonDTO deleteDownloadThreadListByThreadTableId(HttpServletRequest request) throws Exception
	{
		String delThreadTableIds = this.getDeleteIdsRetString(request);
		
		//if not admin, then filter the thread table id out which started by this user.
		//if admin, then can stop any test threads started by any user.
		if(!this.roleBB.isAdminRole(roleBB.getUserRoleDtoFromSession(request)))
		{
			List<String>  filterOutIdsList = this.filterOutUserStartedThreadIds(delThreadTableIds, this.userBB.getLoginUserId(request));
			delThreadTableIds = filterOutIdsList.get(0);
		}

		if(!StringTool.isEmpty(delThreadTableIds))
		{
			//then delete download thread info
			downloadThreadDao.deleteDownloadThreadsByIds(delThreadTableIds);
		}
		return JsonTool.getJsonDtoByMessage("");	
	}
	
	
	public JsonDTO deleteDownloadThreadListByUserIdWebSite(HttpServletRequest request) throws Exception
	{
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
		StringTool.checkInteger(webSiteId, "Web site id must be an integer.");
		
		//get all thread table id that this user started.
		List<DownloadThreadDTO> threadDtoList = this.downloadThreadDao.getDownloadThreadListByUserIdWebSiteId(this.userBB.getLoginUserId(request), webSiteId, false, false);
		List<Object> idList = ClassTool.getObjectFieldValueList(threadDtoList, "id");
		
		if(!ClassTool.isListEmpty(idList))
		{
			String userStartedThreadTableIds = StringTool.getStringFromObjectArray(idList.toArray(), Constants.SEPERATOR_COMMA);
			
			if(!StringTool.isEmpty(userStartedThreadTableIds))
			{
				//then delete download thread info
				downloadThreadDao.deleteDownloadThreadsByIds(userStartedThreadTableIds);
			}
		}
	
		return JsonTool.getJsonDtoByMessage("");	
	}
	
	public JsonDTO getTestDownloadThreadListByWebSiteId(HttpServletRequest request) throws Exception
	{
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
		//String threadType = WebTool.getStringParameterBeforeAttribute(Constants.DATA_THREAD_TYPE, request);
		StringTool.checkInteger(webSiteId, "Web site id must be an integer.");
		//int userId = userBB.getLoginUserId(request);
		List<DownloadThreadDTO> dtoList = this.downloadThreadDao.getTestDownloadThreadListByWebSiteId(webSiteId);
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_THREAD_LIST, dtoList);
	}
	
	public List<String> filterOutUserStartedThreadIds(String delThreadTableIds, int userId) throws Exception
	{
		StringBuffer threadTableIdsBuf = new StringBuffer();
		StringBuffer threadIdsBuf = new StringBuffer();
		List<DownloadThreadDTO>  downloadThreadList = this.downloadThreadDao.getDownloadThreadListByThreadTableIds(delThreadTableIds);
		if(!ClassTool.isNullObj(downloadThreadList))
		{
			int size = downloadThreadList.size();
			for(int i=0;i<size;i++)
			{
				DownloadThreadDTO dto = downloadThreadList.get(i);
				if(dto.getUserId()==userId)
				{
					threadTableIdsBuf.append(dto.getId());
					threadIdsBuf.append(dto.getThreadId());
					if(i<(size-1))
					{
						threadTableIdsBuf.append(Constants.SEPERATOR_COMMA);
					}
				}
			}
		}
		List<String> ret = new ArrayList<String>();
		ret.add(threadTableIdsBuf.toString());
		ret.add(threadIdsBuf.toString());
		return ret;
	}
	
	public JsonDTO stopWebSiteThreadList(HttpServletRequest request) throws Exception
	{		
		JsonDTO retJson = new JsonDTO();
		String delThreadTableIds = WebTool.getStringParameterBeforeAttribute(Constants.DATA_DELETE_IDS, request);		
		String delThreadIds = WebTool.getStringParameterBeforeAttribute(Constants.DATA_THREAD_IDS, request);
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
		StringTool.checkInteger(webSiteId, "The web site id is not a valid integer.");
		
		//if not admin, then filter the thread table id out which started by this user.
		//if admin, then can stop any test threads started by any user.
		if(!this.roleBB.isAdminRole(roleBB.getUserRoleDtoFromSession(request)))
		{
			List<String>  filterOutIdsList = this.filterOutUserStartedThreadIds(delThreadTableIds, this.userBB.getLoginUserId(request));
			delThreadTableIds = filterOutIdsList.get(0);
			delThreadIds = filterOutIdsList.get(1);
		}
		
		if(!StringTool.isEmpty(delThreadTableIds) && !StringTool.isEmpty(delThreadIds))
		{
			MQMessageDTO mqDto = new MQMessageDTO();
			mqDto.setAction(Constants.DOWNLOAD_THREAD_ACTION_STOP);
			mqDto.setDownloadThreadType(Constants.DOWNLOAD_THREAD_TYPE_TESTER);
			mqDto.setThreadTableIds(delThreadTableIds);
			mqDto.setThreadIds(delThreadIds);
			mqDto.setUserId(userBB.getLoginUserId(request));
			mqDto.setSiteId(Integer.parseInt(webSiteId));
			mqDto.setSendTime(String.valueOf(System.currentTimeMillis()));
			
			this.downloadMqMessageDao.saveDownloadMqMessage(mqDto);
			
			MQMessageManager.getInstance().sendDownloadThreadMessage(mqDto);			
			String ret = downloadMqMessageBB.checkDownloadMqMessageStatusInLoop(mqDto.getId());
			retJson = DownloadTool.createMqJsonDtoByActionStatus(ret, " Stop web site thread list "); 
		}
		return retJson;
	}
	
	
	public JsonDTO downloadAndParse(HttpServletRequest request) throws Exception
	{
		JsonDTO retJson = null;
		
		//get user download site id.
		String siteId = WebTool.getStringParameterBeforeAttribute(Constants.ID, request);			
		StringTool.checkValidId(siteId, " Web site ");
		
		//get user input start thread count
		String createThreadCount = WebTool.getStringParameterBeforeAttribute("CREATE_THREAD_COUNT", request);	
		StringTool.checkInteger(createThreadCount, "The thread count you input must be an integer and between 1 - 10");
		
		//check whether there has not applied download tasks or not, if all download task has been applied then alert a message to user.
		int notRunTaskCount = this.downloadTaskDao.getNotRunDownloadTaskCountBySiteId(siteId, -1);
		if(notRunTaskCount==0)
		{
			int allTaskCount = this.downloadTaskDao.getDownloadTaskCountBySiteId(siteId);
			
			if(allTaskCount>0)
			{
				retJson = JsonTool.getJsonDtoByMessage("All download tasks of this website has been applied, you should reset them to download again.");
				retJson.setSuccess(false);
				return retJson;
			}/*else
			{
				if(!"1".equals(createThreadCount))
				{
					retJson = JsonTool.getJsonDtoByMessage("This web site's not run and all download task are all 0, in this case, you can only start 1 thread to init download tasks.");
					retJson.setSuccess(false);
					return retJson;
				}
			}*/
		}/*else if(notRunTaskCount<1000)
		{
			if(Integer.parseInt(createThreadCount)>1)
			{
				retJson = JsonTool.getJsonDtoByMessage("When this web site's not run download task count < 1000 you can only add 1 thread to 1 time.");
				retJson.setSuccess(false);
				return retJson;
			}
		}
		*/
		//check whether there has a thread to download this web site task or not.
		int siteRuningThreadCount = this.downloadThreadDao.getRunningTestDownloadThreadCountByWebSiteId(siteId);
		
		//if already has a thread to download this web site then show the thread list to user
		if((siteRuningThreadCount + Integer.parseInt(createThreadCount))>15)
		{
			retJson = JsonTool.getJsonDtoByMessage("You can not start more than 15 thread to download 1 website.");
			retJson.setSuccess(false);
		}else
		{
			//if not more than 10 thread to download this web site			
			//save this download task into download_task db
			WebSiteDTO webSiteDto = (WebSiteDTO)ClassTool.extractValueFromRequest(WebSiteDTO.class, request);
			List<String> urlList = DownloadTool.createDownloadTaskUrlList(webSiteDto);
			int size = urlList.size();
			for(int i=0;i<size;i++)
			{
				String url = urlList.get(i);								
				DownloadTaskDTO dtDto = this.downloadTaskDao.getDownloadTaskByPageUrl(url);
				if(dtDto==null)
				{
					dtDto = new DownloadTaskDTO();
					dtDto.setId(-1);
					dtDto.setPageUrl(url);
					dtDto.setSiteId(Integer.parseInt(siteId));
					dtDto.setIfContentPage(false);
					dtDto.setInDbTime(String.valueOf(System.currentTimeMillis()));
					dtDto.setApplyTime("");
					dtDto.setIfSiteTopUrl(true);
					dtDto.setIfTest(true);
					dtDto.setParsedOutUrlCount(-1);
					
					downloadTaskDao.saveDownloadTask(dtDto);
				}
			}
			
			//send a message to activemq to start a Constants.DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE thread.
			MQMessageDTO mqDto = new MQMessageDTO();
			mqDto.setAction(Constants.DOWNLOAD_THREAD_ACTION_CREATE);
			mqDto.setDownloadThreadType(Constants.DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE);
			mqDto.setSiteId(Integer.parseInt(siteId));
			mqDto.setUserId(userBB.getLoginUserId(request));
			mqDto.setSendTime(String.valueOf(System.currentTimeMillis()));			
			
			mqDto.setCreateThreadCount(Integer.parseInt(createThreadCount));
			
			this.downloadMqMessageDao.saveDownloadMqMessage(mqDto);
			
			MQMessageManager.getInstance().sendDownloadThreadMessage(mqDto);
			
			String ret = downloadMqMessageBB.checkDownloadMqMessageStatusInLoop(mqDto.getId());
			retJson = DownloadTool.createMqJsonDtoByActionStatus(ret, " Create download and parse thread ");
		}
		return retJson;
	}

}
