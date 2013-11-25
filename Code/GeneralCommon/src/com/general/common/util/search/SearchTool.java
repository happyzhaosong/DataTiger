package com.general.common.util.search;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.dto.search.SolrSearchKeywordDTO;
import com.general.common.dto.search.SolrSearchOrderDTO;
import com.general.common.dto.search.SolrSearchParamsDTO;
import com.general.common.util.BaseTool;
import com.general.common.util.DateTool;
import com.general.common.util.GeneralWebTool;
import com.general.common.util.StringTool;

public class SearchTool extends BaseTool {

	/*
	 * check whether user search duration too short
	 * */
	public static boolean ifSearchTooFrequently(HttpServletRequest request)
	{
		boolean ret = false;
		
		HttpSession session = request.getSession();
		
		Object lastSearchActionObj = session.getAttribute(GeneralConstants.SEARCH_LAST_SEARCH_ACTION);
		Object lastSearchTimeObj = session.getAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION);
		
		String currAction = GeneralWebTool.getStringAttributeBeforeParameter(GeneralConstants.ACTION, request);
		long currTime = System.currentTimeMillis();
		
		if(StringTool.isEmpty(currAction))
		{
			ret = true;
		}else if(lastSearchActionObj!=null)
		{
			if(GeneralConstants.ACTION_SEARCH.equalsIgnoreCase(currAction) && lastSearchTimeObj!=null)
			{		
				long lastSearchTime = ((Long)lastSearchTimeObj).longValue();		
				//如果访问间隔小于 100 毫秒则认为是攻击访问
				if(DateTool.getMilliSecondsBetweenTwoTime(currTime, lastSearchTime) < GeneralConstants.SEARCH_FREQUENT_DURATION_IN_MILLISECONDS)
				{
					ret = true;
				}
			}			
		}
		
		if(GeneralConstants.ACTION_SEARCH.equalsIgnoreCase(currAction))
		{
			session.setAttribute(GeneralConstants.SEARCH_LAST_SEARCH_ACTION, currAction);
			session.setAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION, currTime);
		}
		return ret;
	}
	
	
	public static SolrSearchParamsDTO getSolrSearchParamsDtoFromSearchParamsDto(BaseSearchParamsDTO searchParamsDto, BasePageDTO pageDto)
	{
		SolrSearchParamsDTO ret = new SolrSearchParamsDTO();
		List<SolrSearchOrderDTO> sarchOrderList = new ArrayList<SolrSearchOrderDTO>();	
		
		/* solr search order column list*/
		if(!StringTool.isEmpty(searchParamsDto.getOrderBy1()))
		{
			SolrSearchOrderDTO solrSearchOrderDto1 = new SolrSearchOrderDTO();
			solrSearchOrderDto1.setOrderColumn(searchParamsDto.getOrderBy1().toUpperCase());
			solrSearchOrderDto1.setDirection(searchParamsDto.getDirection1());
			sarchOrderList.add(solrSearchOrderDto1);
		}

		if(!StringTool.isEmpty(searchParamsDto.getOrderBy2()))
		{
			SolrSearchOrderDTO solrSearchOrderDto2 = new SolrSearchOrderDTO();
			solrSearchOrderDto2.setOrderColumn(searchParamsDto.getOrderBy2().toUpperCase());
			solrSearchOrderDto2.setDirection(searchParamsDto.getDirection2());
			sarchOrderList.add(solrSearchOrderDto2);
		}

		ret.setSarchOrderList(sarchOrderList);
		ret.setStartRow((Long.parseLong(pageDto.getPage()) - 1)*Long.parseLong(pageDto.getLimit()));
		ret.setPageSize(Long.parseLong(pageDto.getLimit()));
		
		return ret;
	}
}
