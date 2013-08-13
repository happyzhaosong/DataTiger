package com.data.collect.client.logic;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.WebSiteCategoryDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.util.ClassTool;
import com.data.collect.common.util.LogTool;
import com.data.collect.common.util.StringTool;


public class BaseBB {
	
	protected String[] getDeleteIdsRetArray(HttpServletRequest request) throws Exception
	{
		String delIds = request.getParameter(Constants.DATA_DELETE_IDS);
		StringTool.checkEmpty(delIds, "You must select one record to delete.");
		String delIdArr[] = delIds.split(Constants.SEPERATOR_COMMA);
		int size = delIdArr.length;
		for(int i=0;i<size;i++)
		{
			StringTool.checkInteger(delIdArr[i], "Deleted record's id must be integer.");
		}
		return delIdArr;
	}
	
	protected String getDataIdsRetString(HttpServletRequest request) throws Exception
	{
		return request.getParameter(Constants.DATA_IDS);		
	}
	
	protected String getDeleteIdsRetString(HttpServletRequest request) throws Exception
	{
		return request.getParameter(Constants.DATA_DELETE_IDS);		
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
