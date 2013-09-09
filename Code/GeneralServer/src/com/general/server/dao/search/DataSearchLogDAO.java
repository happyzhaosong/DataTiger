package com.general.server.dao.search;

import java.util.ArrayList;
import java.util.List;
import com.general.common.dto.search.DataSearchLogDTO;
import com.general.common.dto.search.DataSearchLogDetailDTO;
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
				
				logDto = logList.get(0);
				logDto.setLastSearchDate(lastSearchDate);
				logDto.setTotalSearchCount(logDto.getTotalSearchCount()+1);
				
				this.updateDto(logDto);
			}else
			{
				this.insertDto(logDto);
			}			
		
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
		
		this.orderByBuf.append(" last_search_date desc, total_search_count desc "); 
		
		List<DataSearchLogDTO> logList = new ArrayList<DataSearchLogDTO>();
		logList = logList.getClass().cast(this.selectDtoList(DataSearchLogDTO.class, DBManager.getInstance().getMysqlDataSource()));
		return logList;
	}
}
