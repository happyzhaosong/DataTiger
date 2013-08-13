package com.data.collect.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DownloadThreadDTO;
import com.data.collect.common.util.StringTool;

public class DownloadThreadDAO extends BaseDAO {
	
	protected static String BY_THREAD_ID = "BY_THREAD_ID";

	public void deleteDownloadThreadsByIds(String ids) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.deleteDto(DownloadThreadDTO.class);
	}
	
	
	public void deleteDownloadThreadsByUserId(int userId) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" user_id = ");
		this.whereBuf.append(userId);

		this.deleteDto(DownloadThreadDTO.class);
	}
	
	
	public void stopDownloadThreadsByIds(String ids, String stopReason) throws Exception
	{
		this.initStringBuffer();
		this.sqlBuf.append("update download_thread set stop_time = '");
		this.sqlBuf.append(System.currentTimeMillis());
		this.sqlBuf.append("' , stop_reason = '");
		this.sqlBuf.append(stopReason);
		this.sqlBuf.append("'");
		
		this.whereBuf.append(" id in (");
		this.whereBuf.append(StringTool.removeLastCharactor(ids, Constants.SEPERATOR_COMMA));
		this.whereBuf.append(")");
		this.updateDto(this.sqlBuf.toString());
	}
	
	public void saveDownloadThread(DownloadThreadDTO dto) throws Exception
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
	
    private List<DownloadThreadDTO> getDownloadThreadListBy(String byKey, String byValue1, String byValue2) throws Exception
 	{    	
      	this.initStringBuffer();
      	if(this.BY_THREAD_ID.equals(byKey) && byValue1!=null)
		{
			this.whereBuf.append(" thread_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue1.trim());			
		}else if((this.BY_WEB_SITE_ID.equals(byKey) || this.BY_WEB_SITE_ID_RUNNING_THREAD.equals(byKey) || this.BY_WEB_SITE_ID_TEST_THREAD.equals(byKey)) && byValue1!=null)
		{
			this.whereBuf.append(" site_id ");
			this.whereBuf.append(" = ");
			this.whereBuf.append(byValue1.trim());
			
			if(this.BY_WEB_SITE_ID_RUNNING_THREAD.equals(byKey))
			{
				this.whereBuf.append(" and (stop_time is null or stop_time='')  ");				
			}

			if(this.BY_WEB_SITE_ID_TEST_THREAD.equals(byKey))
			{
				this.whereBuf.append(" and thread_type != ");
				this.whereBuf.append(Constants.DOWNLOAD_THREAD_TYPE_ALL);
				this.whereBuf.append(" and thread_type != ");
				this.whereBuf.append(Constants.DOWNLOAD_THREAD_TYPE_WORKER);
			}
		}
    	
    	this.orderByBuf.append(" id desc ");
		List<DownloadThreadDTO> ret = new ArrayList<DownloadThreadDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadThreadDTO.class));	

 	}
    
    
	public List<DownloadThreadDTO> getDownloadThreadListByUserIdWebSiteId(int userId, String webSiteId, boolean allThreads, boolean running) throws Exception
	{
		this.initStringBuffer();
		if(!allThreads)
		{
			if(running)
			{
				this.whereBuf.append(" stop_time is null or stop_time='' and ");	
			}else
			{
				this.whereBuf.append(" stop_time is not null and stop_time!='' and ");	
			}
		}
		
		if(userId>0)
		{
			this.whereBuf.append(" user_id = ");
			this.whereBuf.append(userId);
			this.whereBuf.append(" and ");
		}
		
		if(StringTool.isInteger(webSiteId))
		{
			this.whereBuf.append(" site_id = ");
			this.whereBuf.append(webSiteId);	
		}
		
    	this.orderByBuf.append(" id desc ");
		List<DownloadThreadDTO> ret = new ArrayList<DownloadThreadDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadThreadDTO.class));
	}
	
	public List<DownloadThreadDTO> getDownloadThreadListByWebSiteId(String webSiteId) throws Exception
	{
		return this.getDownloadThreadListBy(this.BY_WEB_SITE_ID, webSiteId, ""); 
	}
	
	
	public List<DownloadThreadDTO> getRunningTestDownloadThreadListByWebSiteId(String webSiteId) throws Exception
	{
     	this.initStringBuffer();
      	
		this.whereBuf.append(" site_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(webSiteId.trim());
			
		this.whereBuf.append(" and (stop_time is null or stop_time='')  ");				
		
		this.whereBuf.append(" and thread_type != ");
		this.whereBuf.append(Constants.DOWNLOAD_THREAD_TYPE_ALL);
		this.whereBuf.append(" and thread_type != ");
		this.whereBuf.append(Constants.DOWNLOAD_THREAD_TYPE_WORKER);
		
    	this.orderByBuf.append(" id desc ");
		List<DownloadThreadDTO> ret = new ArrayList<DownloadThreadDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadThreadDTO.class));	
	}
	
	public int getRunningTestDownloadThreadCountByWebSiteId(String webSiteId) throws Exception
	{
     	this.initStringBuffer();
     	this.sqlBuf.append("select count(id) from download_thread "); 
      	
		this.whereBuf.append(" site_id ");
		this.whereBuf.append(" = ");
		this.whereBuf.append(webSiteId.trim());
			
		this.whereBuf.append(" and (stop_time is null or stop_time='')  ");				
		
		this.whereBuf.append(" and thread_type != ");
		this.whereBuf.append(Constants.DOWNLOAD_THREAD_TYPE_ALL);
		this.whereBuf.append(" and thread_type != ");
		this.whereBuf.append(Constants.DOWNLOAD_THREAD_TYPE_WORKER);		
    	
		return this.selectDtoCount();
	}
	
	
	public List<DownloadThreadDTO> getTestDownloadThreadListByWebSiteId(String webSiteId) throws Exception
	{
		return this.getDownloadThreadListBy(this.BY_WEB_SITE_ID_TEST_THREAD, webSiteId, ""); 
	}
	
	public List<DownloadThreadDTO> getDownloadThreadListByThreadTableIds(String threadTableIds) throws Exception
	{
		this.initStringBuffer();
		this.whereBuf.append(" thread_id in (");
		this.whereBuf.append(threadTableIds.trim());			
		this.whereBuf.append(") ");
		
    	this.orderByBuf.append(" id desc ");
		List<DownloadThreadDTO> ret = new ArrayList<DownloadThreadDTO>();
		return ret.getClass().cast(this.selectDtoList(DownloadThreadDTO.class));
	} 
	
}
