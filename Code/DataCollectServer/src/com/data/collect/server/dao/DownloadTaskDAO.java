package com.data.collect.server.dao;

import java.util.ArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadTaskDTO;
import com.general.common.dto.DBTableColumnDTO;
import com.general.common.exception.EmptyStringException;
import com.general.common.util.ClassTool;
import com.general.common.util.DBTool;
import com.general.common.util.StringTool;
import com.general.server.dao.BaseDAO;
import com.general.server.manager.DBManager;

public class DownloadTaskDAO extends BaseDAO {
	
	public boolean ifOtherThreadApplyingTask() throws Exception
	{
		boolean ret = false;
		this.initStringBuffer();
		String sql = "select applying_task,apply_time from download_thread_apply_task_sta";
		List<Object> list = this.selectDtoList(null, sql); 
		//this.executeSelectSql(sql, DBManager.getInstance().getMysqlDataSource());
		if(ClassTool.isNullObj(list) || list.size()==0)
		{
			this.initStringBuffer();
			sql = "insert into download_thread_apply_task_sta(applying_task, apply_time) values(1,'"+System.currentTimeMillis()+"')";
			this.executeInsertSql(sql, DBManager.getInstance().getDataSource());
		}else
		{
			Object obj = list.get(0);
			if(!ClassTool.isNullObj(obj))
			{
				List cList = (ArrayList)obj;
				DBTableColumnDTO columnDto = (DBTableColumnDTO)cList.get(0);
				ret = Boolean.parseBoolean(columnDto.getColumnValue());
				
				if(ret)
				{
					DBTableColumnDTO applyTimeColumnDto = (DBTableColumnDTO)cList.get(1);
					String lastApplyTime = applyTimeColumnDto.getColumnValue();
					if(StringTool.isLong(lastApplyTime))
					{
						long lastApplyTimeLong = Long.parseLong(lastApplyTime);
						if((System.currentTimeMillis() - lastApplyTimeLong)> Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND * 30)
						{
							ret = false;
						}
					}
				}
			}
		}
		return ret;
	}
	
	public void setThreadApplyingTask(boolean apply) throws Exception
	{
		this.initStringBuffer();
		String sql = "update download_thread_apply_task_sta set applying_task = " + StringTool.getIntByBoolean(apply) + ", apply_time = '"+System.currentTimeMillis()+"'";
		this.updateDto(sql);
	}
	
	public void saveDownloadTask(DownloadTaskDTO dto) throws Exception
	{
		this.initStringBuffer();
		if(dto.getId()!=-1)
		{
			this.whereBuf.append(" id = ");
			this.whereBuf.append(dto.getId());
			this.updateDto(dto);
		}else
		{
			this.insertDto(dto);
		}
	}	

	/*
	 * first get content data page and parse.
	 */
	public List<DownloadTaskDTO> getNotRunDownloadTaskListBySiteId(String siteId, int ifContentPage) throws Exception
	{
		this.initStringBuffer();
		
		this.whereBuf.append(" site_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(siteId.trim());
		this.whereBuf.append(" and useless_content_page = 0 and apply_time = ' ' ");
		
		if(ifContentPage==0 || ifContentPage==1)
		{
			this.whereBuf.append(" and if_content_page = ");
			this.whereBuf.append(ifContentPage);		
		}
		
		this.orderByBuf.append(" task_level DESC ");
			
		List<DownloadTaskDTO> ret = new ArrayList<DownloadTaskDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadTaskDTO.class));	
	}
	
	public int getNotRunDownloadTaskCountBySiteId(String siteId, int ifContentPage) throws Exception
	{
		this.initStringBuffer();
		this.sqlBuf.append("select count(id) from download_task ");
		
		this.whereBuf.append(" site_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(siteId.trim());
		this.whereBuf.append(" and useless_content_page = 0 and apply_time = ' ' ");
		
		if(ifContentPage==0 || ifContentPage==1)
		{
			this.whereBuf.append(" and if_content_page = ");
			this.whereBuf.append(ifContentPage);		
		}
		
		return this.selectDtoCount();	
	}
	
	public int getDownloadTaskCountBySiteId(String siteId) throws Exception
	{
    	this.initStringBuffer();
		this.sqlBuf.append("select count(id) from download_task ");

    	if(!StringTool.isEmpty(siteId))
    	{
			this.whereBuf.append(" site_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(siteId.trim());
    	}
		return this.selectDtoCount();	
	}
	
	
	public List<DownloadTaskDTO> getDownloadTaskListBySiteId(String siteId) throws Exception
	{
    	this.initStringBuffer();
    	
    	if(!StringTool.isEmpty(siteId))
    	{
			this.whereBuf.append(" site_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(siteId.trim());

			//remove order by can improve db performance 
			//this.orderByBuf.append(" task_level desc ");
    	}
    	
		List<DownloadTaskDTO> ret = new ArrayList<DownloadTaskDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadTaskDTO.class));	
	}
	
	public List<DownloadTaskDTO> getDownloadTaskListBySiteIdAndThreadTableId(String siteId, String threadTableIds) throws Exception
	{
		this.initStringBuffer();
		
		if(!StringTool.isEmpty(siteId))
    	{
	    	this.whereBuf.append(" site_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(siteId.trim());
    	}
		
		if(!StringTool.isEmpty(threadTableIds))
		{
			if(!StringTool.isEmpty(siteId))
			{
				this.whereBuf.append(" and ");	
			}
			
			this.whereBuf.append(" thread_table_id in (");
			this.whereBuf.append(threadTableIds);
			this.whereBuf.append(") ");
		}
		
		this.orderByBuf.append(" task_level desc ");
   	
		List<DownloadTaskDTO> ret = new ArrayList<DownloadTaskDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadTaskDTO.class));	
	}
	
	public DownloadTaskDTO getDownloadTaskByPageUrl(String url) throws Exception
	{
		if(StringTool.isEmpty(url))
		{
			throw new EmptyStringException("url can not be empty.");
		}
		
		DownloadTaskDTO ret = null;
		List<DownloadTaskDTO> taskList = this.getDownloadTaskListBy(this.BY_WEB_SITE_PAGE_URL, url, null);
		if(taskList!=null && taskList.size()>=1)
		{
			ret = taskList.get(0);
		}
		return ret; 
	}
	
	
	public List<DownloadTaskDTO> getDownloadTaskListByPageUrl(String url) throws Exception
	{
		if(StringTool.isEmpty(url))
		{
			return new ArrayList();
		}
		
		List<DownloadTaskDTO> ret = this.getDownloadTaskListBy(this.BY_WEB_SITE_PAGE_URL, url, null);
		return ret; 
	}

	
	public boolean ifDownloadUrlExist(String url, int siteId) throws Exception
	{
		boolean ret = false;
		if(!StringTool.isEmpty(url))
		{
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("SELECT id FROM download_task WHERE page_url  = '");
			sqlBuf.append(url.trim());
			sqlBuf.append("' and site_id = ");
			sqlBuf.append(siteId);
			
			List<Map<String,String>> map = this.executeSelectSql(sqlBuf.toString(), DBManager.getInstance().getDataSource());
			if(map.size()>0)
			{
				ret = true;
			}
		}
		return ret; 
	}
	
	
    private List<DownloadTaskDTO> getDownloadTaskListBy(String byKey, String byValue1, String byValue2) throws Exception
	{    	
    	this.initStringBuffer();
    	if(this.BY_WEB_SITE_PAGE_URL.equals(byKey) && !StringTool.isEmpty(byValue1))
		{
    		this.whereBuf.append(" page_url ");
			this.whereBuf.append(" = '");
			this.whereBuf.append(byValue1.trim());
			this.whereBuf.append("'");    		
		}
		List<DownloadTaskDTO> ret = new ArrayList<DownloadTaskDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadTaskDTO.class));	
	}
    
    


    
	public void deleteDownloadTasksByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(DownloadTaskDTO.class);
	}
	
	public void deleteAllNotTopUrlDownloadTasksByWebSiteId(String siteId) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" site_id = ");
		this.whereBuf.append(siteId);
		this.whereBuf.append(" and site_top_url = false ");
		this.deleteDto(DownloadTaskDTO.class);
	}
	
	public void deleteAllDownloadTasksByWebSiteId(String siteId) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" site_id = ");
		this.whereBuf.append(siteId);
		this.deleteDto(DownloadTaskDTO.class);
	}
	
	/*
	 * init all download tasks, set apply time to null to rerun the task.
	 * */
	public void resetAllDownloadTasksApplyTimeByWebSiteId(String siteId, String taskTableIds) throws Exception
	{
		this.initStringBuffer();
		
		if(!StringTool.isEmpty(siteId))
		{
			this.whereBuf.append(" site_id = ");
			this.whereBuf.append(siteId);
		}
		
		if(!StringTool.isEmpty(taskTableIds))
		{
			if(!StringTool.isEmpty(siteId))
			{
				this.whereBuf.append(" and ");
			}
			
			this.whereBuf.append(" id in (");
			this.whereBuf.append(taskTableIds);
			this.whereBuf.append(") ");
		}
		
		this.updateDto("update download_task set apply_time = ' ', if_really_content_page = -1, reset_apply_time_time = '" + System.currentTimeMillis() + "', reset_apply_time_reason = 'Reset by web site editor manually' ");
	}
	

	public void setDownloadTaskLevelByWebSiteIdOrTaskTableId(String siteId, String taskTableIds, String taskLevel) throws Exception
	{
		this.initStringBuffer();
		
		if(!StringTool.isEmpty(siteId))
		{
			this.whereBuf.append(" site_id = ");
			this.whereBuf.append(siteId);
		}
		
		if(!StringTool.isEmpty(taskTableIds))
		{
			if(!StringTool.isEmpty(siteId))
			{
				this.whereBuf.append(" and ");
			}
			
			this.whereBuf.append(" id in (");
			this.whereBuf.append(taskTableIds);
			this.whereBuf.append(") ");
		}
		
		this.updateDto("update download_task set apply_time = ' ', task_level = " + taskLevel);
	}
	
	
	public String resetDownloadTaskApplyTimeAndLevelByWebSiteId(String downloadTaskIdInWhereSql, String taskLevel, String resetApplyTime) throws Exception
	{
		String retDownloadTaskId = "";
		List<Map<String,String>> idList = this.executeSelectSql(DBTool.getCurrPageSql(downloadTaskIdInWhereSql, 0, 500) , DBManager.getInstance().getDataSource());
		if(!ClassTool.isListEmpty(idList))
		{
			StringBuffer idBuf = new StringBuffer();
			int size = idList.size();
			for(int i=0;i<size;i++)
			{
				Map<String,String> idMap = idList.get(i);
				Collection<String> idCollection = idMap.values();
				Iterator<String> it = idCollection.iterator();
				while(it.hasNext())
				{
					idBuf.append(it.next());
					idBuf.append(Constants.SEPERATOR_COMMA);
				}
 			}
			
			retDownloadTaskId = idBuf.toString();
			if(retDownloadTaskId.endsWith(Constants.SEPERATOR_COMMA))
			{
				retDownloadTaskId = retDownloadTaskId.substring(0, retDownloadTaskId.length()-1);
			}
			
			if(retDownloadTaskId.length()>0)
			{
				StringBuffer sqlBuf = new StringBuffer();
				sqlBuf.append("update download_task set ");
				if("true".equalsIgnoreCase(resetApplyTime))
				{
					sqlBuf.append(" apply_time = ' ', ");	
				}				
				sqlBuf.append(" task_level =  ");
				sqlBuf.append(taskLevel);
				sqlBuf.append(" where id in (");
				sqlBuf.append(retDownloadTaskId);
				sqlBuf.append(")");
								
				//this.executeUpdateOrDeleteSql("update download_task set apply_time = '', task_level = " + taskLevel + " where id in (" + retDownloadTaskId + ")", DBManager.getInstance().getMysqlDataSource());
				this.executeUpdateOrDeleteSql(sqlBuf.toString(), DBManager.getInstance().getDataSource());
			}
		}
		return retDownloadTaskId;
	}
	
	
	public void setDownloadTaskUselessByTaskTableId(String taskTableIds, String taskUseless) throws Exception
	{
		this.initStringBuffer();
				
		if(!StringTool.isEmpty(taskTableIds))
		{
			this.whereBuf.append(" id in (");
			this.whereBuf.append(taskTableIds);
			this.whereBuf.append(") ");
		}
		
		this.updateDto("update download_task set useless_content_page = " + taskUseless);
	}
	
		
	
	/*
	 * update all download tasks, set apply time to current time to identify that this task has been applied.
	 * */
	public void updateAllDownloadTasksApplyTimeByTaskIds(String taskIds, String applyTime, int applyThreadTableId, String resetApplyTimeTime, String resetApplyTimeReason) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(taskIds);
		this.whereBuf.append(")");
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("update download_task set thread_table_id = " + applyThreadTableId + ", apply_time = '");
		sqlBuf.append(applyTime);
		sqlBuf.append("', reset_apply_time_time = '" + resetApplyTimeTime + "', reset_apply_time_reason = '");
		sqlBuf.append(resetApplyTimeReason);
		sqlBuf.append("'");
		this.updateDto(sqlBuf.toString());
	}

}
