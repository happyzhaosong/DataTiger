package com.general.client.logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;


public class BaseBB {
	
	/*
	 * Parse and return order by and direction list, first element is order by, second element id direction
	 * */
	protected List<String> getOrderbyColumnAndDirection(String orderByString) throws Exception
	{
		List<String> ret = new ArrayList<String>();
		if(!StringTool.isEmpty(orderByString))
		{
			orderByString = orderByString.trim();
			String orderBy = "";
			String direction = "";
			orderByString = orderByString.toLowerCase();
			if(orderByString.endsWith(GeneralConstants.ORDER_BY_ASC_SUFFIX))
			{
				direction = GeneralConstants.ORDER_BY_ASC;
				orderBy = orderByString.substring(0, orderByString.indexOf(GeneralConstants.ORDER_BY_ASC_SUFFIX));
			}else if(orderByString.endsWith(GeneralConstants.ORDER_BY_DESC_SUFFIX))			
			{
				direction = GeneralConstants.ORDER_BY_DESC;
				orderBy = orderByString.substring(0, orderByString.indexOf(GeneralConstants.ORDER_BY_DESC_SUFFIX));				
			}else
			{
				direction = GeneralConstants.ORDER_BY_DESC;
				orderBy = orderByString;
			}
			ret.add(orderBy);
			ret.add(direction);
		}
		return ret;
	}
	
	protected BaseSearchParamsDTO getGeneralSearchParamsDTOFromRequest(HttpServletRequest request) throws Exception
	{
		BaseSearchParamsDTO searchParamsDto = (BaseSearchParamsDTO)ClassTool.extractValueFromRequest(BaseSearchParamsDTO.class, request);
		
		List<String> orderByList1 = this.getOrderbyColumnAndDirection(searchParamsDto.getOrderByWithDierction1());
		List<String> orderByList2 = this.getOrderbyColumnAndDirection(searchParamsDto.getOrderByWithDierction2());
		
		if(!ClassTool.isListEmpty(orderByList1))
		{
			searchParamsDto.setOrderBy1(orderByList1.get(0));
			searchParamsDto.setDirection1(orderByList1.get(1));
		}
		
		if(!ClassTool.isListEmpty(orderByList2))
		{
			searchParamsDto.setOrderBy2(orderByList2.get(0));
			searchParamsDto.setDirection2(orderByList2.get(1));
		}
		
		return searchParamsDto;
	}
	
	
	protected String[] getDeleteIdsRetArray(HttpServletRequest request) throws Exception
	{
		String delIds = request.getParameter(GeneralConstants.DATA_DELETE_IDS);
		StringTool.checkEmpty(delIds, "You must select one record to delete.");
		String delIdArr[] = delIds.split(GeneralConstants.SEPERATOR_COMMA);
		int size = delIdArr.length;
		for(int i=0;i<size;i++)
		{
			StringTool.checkInteger(delIdArr[i], "Deleted record's id must be integer.");
		}
		return delIdArr;
	}
	
	protected String getDataIdsRetString(HttpServletRequest request) throws Exception
	{
		return request.getParameter(GeneralConstants.DATA_IDS);		
	}
	
	protected String getDeleteIdsRetString(HttpServletRequest request) throws Exception
	{
		return request.getParameter(GeneralConstants.DATA_DELETE_IDS);		
	}

	public <T> boolean ifAnotherDtoObjExist(List<T> list, int originalId) throws Exception
	{
		boolean ret = false;
		try
		{
			if(list!=null && list.size()>0)
			{
				if(list.size()>1)
				{
					ret = true;
				}else
				{
					T dto  = list.get(0);
					Object obj = ClassTool.invokeMethod(dto, "getId", "");
					if(Integer.parseInt(obj.toString())!=originalId)
					{
						ret = true;
					}					
				}
			}
		}catch(Exception ex)
		{
			LogTool.logError( ex);
			throw ex;
		}finally
		{
			return ret;
		}
	}
}
