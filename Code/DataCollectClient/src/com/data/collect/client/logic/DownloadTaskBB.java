package com.data.collect.client.logic;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadTaskDTO;
import com.data.collect.common.dto.TypeDTO;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.DBManagerDAO;
import com.data.collect.server.dao.DownloadTaskDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.JsonDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.StringTool;
import com.general.server.manager.DBManager;


public class DownloadTaskBB extends BaseBB {

	private DownloadTaskDAO downloadTaskDao = new DownloadTaskDAO();
	private DBManagerDAO dbManagerDao = new DBManagerDAO();
	private RoleBB roleBB = new RoleBB();
	
	public JsonDTO getWebSiteDownloadTaskListByThreadTableIdAndSiteId(HttpServletRequest request) throws Exception
	{
		String threadTableId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_TABLE_ID, request);
		
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);		
		StringTool.checkInteger(webSiteId, "Web site id must be an integer.");
		
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);					
		this.downloadTaskDao.setPageDto(pageDto);
		
		List<DownloadTaskDTO> taskDtoList = new ArrayList<DownloadTaskDTO>();
		if(!StringTool.isEmpty(threadTableId))
		{
			taskDtoList = this.downloadTaskDao.getDownloadTaskListBySiteIdAndThreadTableId(webSiteId, threadTableId);
		}else
		{
			taskDtoList = this.downloadTaskDao.getDownloadTaskListBySiteId(webSiteId);
		}
		
		//if want to reset these download tasks apply time and task level
		String actionSub = WebTool.getStringParameterBeforeAttribute(Constants.ACTION_SUB, request);
		if(!StringTool.isEmpty(actionSub))
		{
			if(Constants.RESET_ALL.equalsIgnoreCase(actionSub))
			{
				if(!ClassTool.isListEmpty(taskDtoList))
				{
					String resetApplyTime = WebTool.getStringParameterBeforeAttribute(Constants.RESET_APPLY_TIME, request);
										
					String taskLevel = WebTool.getStringParameterBeforeAttribute(Constants.DOWNLOAD_TASK_LEVEL, request);
					if(!StringTool.isInteger(taskLevel))
					{
						taskLevel = "0";
					}
					
					String downloadTaskIdInWhereSql = "select id from download_task ";
					String whereClause = downloadTaskDao.getWhereBuf().toString().trim();
					if(whereClause.length()>0)
					{
						downloadTaskIdInWhereSql = downloadTaskIdInWhereSql + " where " + whereClause + " and task_level != ' " + taskLevel +  " '";
					}
					//reset all search condition result apply time to '' and task level to user input task level.
					String updateDonwloadTaskIds = downloadTaskDao.resetDownloadTaskApplyTimeAndLevelByWebSiteId(downloadTaskIdInWhereSql, taskLevel, resetApplyTime);	
					while(!StringTool.isEmpty(updateDonwloadTaskIds))
					{
						updateDonwloadTaskIds = downloadTaskDao.resetDownloadTaskApplyTimeAndLevelByWebSiteId(downloadTaskIdInWhereSql, taskLevel, resetApplyTime);
					}
				}
			}else if(Constants.DELETE_ALL.equalsIgnoreCase(actionSub))
			{
				String deleteSql = "delete from download_task ";
				String whereClause = downloadTaskDao.getWhereBuf().toString().trim();
				if(whereClause.length()>0)
				{
					deleteSql = deleteSql + " where " + whereClause;
				}
				downloadTaskDao.executeUpdateOrDeleteSql(deleteSql, DBManager.getInstance().getDataSource());				
			}
		}
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_TASK_LIST, taskDtoList);
	}
	
	
	public JsonDTO getWebSiteDownloadTaskListBySiteId(HttpServletRequest request) throws Exception
	{
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);			
		StringTool.checkInteger(webSiteId, "Web site id must be an integer.");			
		this.downloadTaskDao.setPageDto(pageDto);
		List<DownloadTaskDTO> dtoList = this.downloadTaskDao.getDownloadTaskListBySiteId(webSiteId);
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_TASK_LIST, dtoList);
	}
	
	
	public JsonDTO deleteWebSiteDownloadTask(HttpServletRequest request) throws Exception
	{
		//String delIdArr[] = this.getDeleteIdsRetArray(request);
		//String delIds = StringTool.getStringFromStringArray(delIdArr, Constants.SEPERATOR_COMMA);
		String delIds = this.getDeleteIdsRetString(request);
		if(!StringTool.isEmpty(delIds))
		{
			downloadTaskDao.deleteDownloadTasksByIds(delIds);
		}
		return JsonTool.getJsonDtoByMessage("");
	}
	
	public JsonDTO deleteWebSiteALLDownloadTask(HttpServletRequest request) throws Exception
	{
		String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
		StringTool.checkInteger(webSiteId, "Web site id must be an integer.");
		downloadTaskDao.deleteAllDownloadTasksByWebSiteId(webSiteId);
		return JsonTool.getJsonDtoByMessage("");
	}
	
	public JsonDTO resetWebSiteDownloadTask(HttpServletRequest request) throws Exception
	{
		if(this.roleBB.isAdminRole(roleBB.getUserRoleDtoFromSession(request)))
		{
			String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
			String resetTaskTableIds = WebTool.getStringParameterBeforeAttribute(Constants.DATA_DELETE_IDS, request);
			//update all this web site's download tasks in download_task to set each apply_time to null to rerun this task
			downloadTaskDao.resetAllDownloadTasksApplyTimeByWebSiteId(webSiteId, resetTaskTableIds);	
			return JsonTool.getJsonDtoByMessage("");
		}else
		{
			return JsonTool.getJsonDtoByMessage("Only admin user can reset download tasks apply time to ''.");
		}
	}
	
	public JsonDTO setWebSiteDownloadTaskUseless(HttpServletRequest request) throws Exception
	{
		if(this.roleBB.isAdminRole(roleBB.getUserRoleDtoFromSession(request)))
		{
			String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
			String taskTableIds = WebTool.getStringParameterBeforeAttribute(Constants.DATA_DELETE_IDS, request);
			StringTool.checkEmpty(taskTableIds, "Task table ids can not be empty.");
			String useless = WebTool.getStringParameterBeforeAttribute(Constants.DOWNLOAD_TASK_USELESS, request);
			StringTool.checkInteger(useless, "Task useless must be an integer and be 0 or 1.");
			
			String dataTableName = WebTool.getStringParameterBeforeAttribute(Constants.DATA_DB_TABLE_NAME, request);
			StringTool.checkEmpty(dataTableName, "Data table name can not be empty.");
			String dataTableIds = WebTool.getStringParameterBeforeAttribute(Constants.DATA_TABLE_ID, request);
			StringTool.checkEmpty(dataTableIds, "Data table ids can not be empty.");


			//set useless_content_page column in download_task table.
			downloadTaskDao.setDownloadTaskUselessByTaskTableId(taskTableIds, useless);	
			
			//set download_task_useless_content_page column in according data table.
			this.dbManagerDao.saveDataTableContentPageUselessOrNot(dataTableName, dataTableIds, useless);
			
			return JsonTool.getJsonDtoByMessage("");
		}else
		{
			return JsonTool.getJsonDtoByMessage("Only admin user can reset download tasks apply time to ''.");
		}
	}
	
	public JsonDTO setWebSiteDownloadTaskLevel(HttpServletRequest request) throws Exception
	{
		if(this.roleBB.isAdminRole(roleBB.getUserRoleDtoFromSession(request)))
		{
			String webSiteId = WebTool.getStringParameterBeforeAttribute(Constants.DATA_WEB_SITE_ID, request);
			String taskTableIds = WebTool.getStringParameterBeforeAttribute(Constants.DATA_DELETE_IDS, request);
			StringTool.checkEmpty(taskTableIds, "Task table ids can not be empty.");
			String taskLevel = WebTool.getStringParameterBeforeAttribute(Constants.DOWNLOAD_TASK_LEVEL, request);
			StringTool.checkInteger(taskLevel, "Task level must be an integer and between 0 - 5");

			//update all this web site's download tasks in download_task to set each task_level to taskLevel and apply_time to '' to rerun this task
			downloadTaskDao.setDownloadTaskLevelByWebSiteIdOrTaskTableId(webSiteId, taskTableIds, taskLevel);	
			return JsonTool.getJsonDtoByMessage("");
		}else
		{
			return JsonTool.getJsonDtoByMessage("Only admin user can reset download tasks apply time to ''.");
		}
	}
	
	
	public JsonDTO getWebDriverSearchByTypeList(HttpServletRequest request) throws Exception
	{
		List<TypeDTO> dtoList = new ArrayList<TypeDTO>();
		String byTypeArr[] = new String[8];
		byTypeArr[0] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_TAG_NAME;
		byTypeArr[1] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_CLASS_NAME;
		byTypeArr[2] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_CSS_SECLECTOR;
		byTypeArr[3] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_ID;
		byTypeArr[4] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_LINK_TEXT;
		byTypeArr[5] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_NAME;
		byTypeArr[6] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_PARTIAL_LINK_TEXT;
		byTypeArr[7] = Constants.WEB_DRIVER_SEARCH_BY_TYPE_XPATH;
		
		int size = byTypeArr.length;
		for(int i=0;i<size;i++)
		{
			TypeDTO dto = new TypeDTO();
			dto.setTypeName(byTypeArr[i]);
			dto.setTypeLabel(byTypeArr[i]);
			dtoList.add(dto);
		}
		
		return JsonTool.getJsonDtoByObjList(Constants.JSON_ROOT_WEB_DRIVER_SEARCH_BY_TYPE_LIST, dtoList);
	}

}
