package com.general.server.dao.search;

import java.util.ArrayList;
import java.util.List;

import com.general.common.dto.BasePageDTO;
import com.general.common.dto.search.DataSearchLogDTO;
import com.general.common.dto.search.DataSearchLogDetailDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;
import com.general.server.dao.BaseDAO;
import com.general.server.manager.DBManager;

public class DataSearchLogDAO extends BaseDAO {

	public void logSearchData(DataSearchLogDTO logDto, DataSearchLogDetailDTO logDetailDto) throws Exception
	{
		//update data_search_log table
		if(!StringTool.isEmpty(logDto.getSearchKeyword()))
		{
			this.initStringBuffer();

			//check whether the searchKeyword exist or not
			this.whereBuf.append(" search_in = ");
			this.whereBuf.append(logDto.getSearchIn());
			this.whereBuf.append(" and search_keyword = '");	
			this.whereBuf.append(logDto.getSearchKeyword());
			this.whereBuf.append("'");
			
			BasePageDTO pageDto = new BasePageDTO();		
			pageDto.setLimit("10");
			pageDto.setStart("0");			
			this.setPageDto(pageDto);
			
			List<DataSearchLogDTO> logList = new ArrayList<DataSearchLogDTO>();
			logList = logList.getClass().cast(this.selectDtoList(DataSearchLogDTO.class, DBManager.getInstance().getDataSource()));		
			
			//if exist then update it's last search time and total search count
			if(logList.size()>0)
			{
				long lastSearchDate = logDto.getLastSearchDate();
				if(lastSearchDate==0)
				{
					lastSearchDate = System.currentTimeMillis();
				}
				
				DataSearchLogDTO existLogDto = logList.get(0);
				
				this.initStringBuffer();
				this.whereBuf.append(" id = ");
				this.whereBuf.append(existLogDto.getId());				
				
				existLogDto.setLastSearchDate(lastSearchDate);
				existLogDto.setTotalSearchCount(existLogDto.getTotalSearchCount()+1);
				existLogDto.setSearchResultCount(logDto.getSearchResultCount());
				
				LogTool.debugText("Begin update " + existLogDto.getSearchKeyword() + "'s search result count, new count is " + existLogDto.getSearchResultCount(), this.getClass().getName());				
				this.updateDto(existLogDto);
			}else
			{
				LogTool.debugText("Begin insert " + logDto.getSearchKeyword() + "'s search result count, new count is " + logDto.getSearchResultCount(), this.getClass().getName());
				this.insertDto(logDto);
			}			
		
			LogTool.debugText("Begin insert log detail dto " + logDetailDto.getSearchKeyword() + "'s search result count, new count is " + logDetailDto.getSearchResultCount(), this.getClass().getName());
			//insert data_search_log_detail table
			this.insertDto(logDetailDto);
		}		
	}
	
	
	public List<DataSearchLogDTO> getLogSearchDataList(int searchIn, String searchKeyword) throws Exception	
	{
		this.initStringBuffer();

		this.whereBuf.append(" search_result_count > 0 ");
		
		if(searchIn>0)
		{
			this.whereBuf.append(" and search_in = ");
			this.whereBuf.append(searchIn);
		}
		
		if(!StringTool.isEmpty(searchKeyword))
		{
			if(this.whereBuf.length()>0)
			{
				this.whereBuf.append(" and ");				
			}
			
			this.whereBuf.append(" search_keyword like '%");
			this.whereBuf.append(searchKeyword);
			this.whereBuf.append("%'");
		}
		
		this.orderByBuf.append(" search_result_count desc "); 
		
		List<DataSearchLogDTO> logList = new ArrayList<DataSearchLogDTO>();
		logList = logList.getClass().cast(this.selectDtoList(DataSearchLogDTO.class, DBManager.getInstance().getDataSource()));
		return logList;
	}
}
