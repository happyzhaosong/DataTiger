package com.general.client.logic.search;

import javax.servlet.http.HttpServletRequest;

import com.general.client.logic.BaseBB;
import com.general.common.dto.search.DataSearchLogDTO;
import com.general.common.dto.search.DataSearchLogDetailDTO;
import com.general.server.dao.search.DataSearchLogDAO;

/*In product env, because for more speed and less db operation to save search time, we can use active mq to implement this feature, that way, search
 * will be asynchronized with log, can save user search time.
 * */
public class DataSearchLogBB extends BaseBB {
	
	private DataSearchLogDAO dataSearchLogDao = new DataSearchLogDAO();
	
	public void logSearchData(HttpServletRequest request, String searchKeyword, int searchIn) throws Exception	
	{
		DataSearchLogDTO logDto = this.createDataSearchLogDTO(request, searchKeyword, searchIn);
		DataSearchLogDetailDTO logDetailDto = this.createDataSearchLogDetailDTO(request, searchKeyword, searchIn);
		this.dataSearchLogDao.logSearchData(logDto, logDetailDto);
	}
	
	
	private DataSearchLogDTO createDataSearchLogDTO(HttpServletRequest request, String searchKeyword, int searchIn)
	{
		DataSearchLogDTO ret = new DataSearchLogDTO();
		ret.setSearchKeyword(searchKeyword);
		ret.setLastSearchDate(System.currentTimeMillis());
		ret.setSearchIn(searchIn);
		return ret;
	}
	
	private DataSearchLogDetailDTO createDataSearchLogDetailDTO(HttpServletRequest request, String searchKeyword, int searchIn)
	{
		DataSearchLogDetailDTO ret = new DataSearchLogDetailDTO();
		ret.setSearchKeyword(searchKeyword);
		ret.setSearchDate(System.currentTimeMillis());
		ret.setSearcherIp(request.getRemoteAddr());
		ret.setSearchIn(searchIn);		
		return ret;		
	}

}
