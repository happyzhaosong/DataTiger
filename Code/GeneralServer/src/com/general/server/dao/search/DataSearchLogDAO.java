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
			logList = logList.getClass().cast(this.selectDtoList(DataSearchLogDTO.class, DBManager.getInstance().getMysqlDataSource()));		
			
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
				existLogDto.setTotalSearchCount(logDto.getTotalSearchCount()+1);
				existLogDto.setSearchResultCount(logDto.getSearchResultCount());
				
				LogTool.debugText("Begin update " + existLogDto.getSearchKeyword() + "'s search result count, new count is " + existLogDto.getSearchResultCount());				
				this.updateDto(existLogDto);
			}else
			{
				LogTool.debugText("Begin insert " + logDto.getSearchKeyword() + "'s search result count, new count is " + logDto.getSearchResultCount());
				this.insertDto(logDto);
			}			
		
			LogTool.debugText("Begin insert log detail dto " + logDetailDto.getSearchKeyword() + "'s search result count, new count is " + logDetailDto.getSearchResultCount());
			//insert data_search_log_detail table
			this.insertDto(logDetailDto);
		}		
	}
	
	
	public List<DataSearchLogDTO> getLogSearchDataList(int searchIn) throws Exception	
	{
		this.initStringBuffer();

		//check whether the searchKeyword exist or not
		if(searchIn>0)
		{
			this.whereBuf.append(" search_in = ");
			this.whereBuf.append(searchIn);
		}
		
		this.orderByBuf.append(" search_result_count desc "); 
		
		List<DataSearchLogDTO> logList = new ArrayList<DataSearchLogDTO>();
		logList = logList.getClass().cast(this.selectDtoList(DataSearchLogDTO.class, DBManager.getInstance().getMysqlDataSource()));
		return logList;
	}
}
