package com.general.client.logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.general.common.constants.GeneralConstants;
import com.general.common.util.ClassTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;


public class BaseBB {
	
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
