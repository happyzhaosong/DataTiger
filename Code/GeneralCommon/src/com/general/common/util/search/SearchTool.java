package com.general.common.util.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.general.common.constants.GeneralConstants;
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
			if(currAction.equalsIgnoreCase((String)lastSearchActionObj))
			{
				if(lastSearchTimeObj!=null)
				{		
					long lastSearchTime = ((Long)lastSearchTimeObj).longValue();			
					if(DateTool.getSecondsBetweenTwoTime(currTime, lastSearchTime) < GeneralConstants.SEARCH_FREQUENT_DURATION_IN_SECONDS)
					{
						ret = true;
					}
				}
			}
		}
		
		session.setAttribute(GeneralConstants.SEARCH_LAST_SEARCH_ACTION, currAction);
		session.setAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION, currTime);	
		return ret;
	}
}
