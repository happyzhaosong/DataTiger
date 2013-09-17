package com.general.client.logic.search;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.general.client.logic.BaseBB;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.dto.search.DataSearchLogDTO;
import com.general.common.dto.search.DataSearchLogDetailDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.StringTool;
import com.general.server.dao.search.DataSearchLogDAO;

/*In product env, because for more speed and less db operation to save search time, we can use active mq to implement this feature, that way, search
 * will be asynchronized with log, can save user search time.
 * */
public class DataSearchLogBB extends BaseBB {
	
	private DataSearchLogDAO dataSearchLogDao = new DataSearchLogDAO();
		
	public JsonDTO getLogSearchDataListInJson(HttpServletRequest request, int searchIn) throws Exception	
	{
		return JsonTool.getJsonDtoByObjList(GeneralConstants.JSON_SEARCH_KEYWORD_LIST, this.getLogSearchDataList(request, searchIn));
	}
	
	private List<DataSearchLogDTO> getLogSearchDataList(HttpServletRequest request, int searchIn) throws Exception	
	{
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);
		pageDto.setLimit("10");
		
		int start = (Integer.parseInt(pageDto.getPage()) -1)*(Integer.parseInt(pageDto.getLimit()));
		pageDto.setStart(String.valueOf(start));
		
		this.dataSearchLogDao.setPageDto(pageDto);
		return this.dataSearchLogDao.getLogSearchDataList(searchIn);
	}
	
	public void logSearchData(HttpServletRequest request, BaseSearchParamsDTO searchParamsDto, int searchIn) throws Exception	
	{
		DataSearchLogDTO logDto = this.createDataSearchLogDTO(request, searchParamsDto, searchIn);
		DataSearchLogDetailDTO logDetailDto = this.createDataSearchLogDetailDTO(request, searchParamsDto, searchIn);		
		this.dataSearchLogDao.logSearchData(logDto, logDetailDto);
	}
	
	
	private DataSearchLogDTO createDataSearchLogDTO(HttpServletRequest request, BaseSearchParamsDTO searchParamsDto, int searchIn)
	{
		DataSearchLogDTO ret = new DataSearchLogDTO();
		ret.setSearchKeyword(searchParamsDto.getSearchKeyword());
		ret.setSearchResultCount(searchParamsDto.getSearchResultCount());
		ret.setLastSearchDate(System.currentTimeMillis());
		ret.setSearchIn(searchIn);
		return ret;
	}
	
	private DataSearchLogDetailDTO createDataSearchLogDetailDTO(HttpServletRequest request, BaseSearchParamsDTO searchParamsDto, int searchIn)
	{
		DataSearchLogDetailDTO ret = new DataSearchLogDetailDTO();
		ret.setSearchKeyword(searchParamsDto.getSearchKeyword());
		ret.setSearchDate(System.currentTimeMillis());
		ret.setSearcherIp(request.getRemoteAddr());
		ret.setSearcherHost(request.getRemoteHost());
		ret.setSearchIn(searchIn);
		ret.setSearchResultCount(searchParamsDto.getSearchResultCount());
		ret.setSearchWebSite(searchParamsDto.getSearchWebSite());
		
		StringBuffer orderByBuf = new StringBuffer();
		if(!StringTool.isEmpty(searchParamsDto.getOrderBy1()))
		{
			orderByBuf.append(searchParamsDto.getOrderBy1());
			orderByBuf.append(" ");
			orderByBuf.append(searchParamsDto.getDirection1());
		}
		
		if(!StringTool.isEmpty(searchParamsDto.getOrderBy2()))
		{
			if(orderByBuf.length()!=0)
			{
				orderByBuf.append(" , ");
				orderByBuf.append(searchParamsDto.getOrderBy2());
				orderByBuf.append(" ");
				orderByBuf.append(searchParamsDto.getDirection2());
				
			}
		}
		
		ret.setOrderBy(orderByBuf.toString());		
		return ret;		
	}

}
