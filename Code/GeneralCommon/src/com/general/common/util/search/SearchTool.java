package com.general.common.util.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.general.common.constants.GeneralConstants;
import com.general.common.util.BaseTool;

public class SearchTool extends BaseTool {

	/*
	 * check whether user search duration too short
	 * */
	public static boolean ifSearchTooFrequently(HttpServletRequest request)
	{
		boolean ret = false;
		
		HttpSession session = request.getSession();		
		Object lastSearchTimeObj = session.getAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION);
		if(lastSearchTimeObj!=null)
		{
			long lastSearchTime = ((Long)lastSearchTimeObj).longValue();
			long currTime = System.currentTimeMillis();
			if((currTime - lastSearchTime) > GeneralConstants.SEARCH_FREQUENT_DURATION_IN_MILLION_SECONDS)
			{
				ret = true;
			}
		}else
		{
			session.setAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION, System.currentTimeMillis());
		}
		return ret;
	}
}
