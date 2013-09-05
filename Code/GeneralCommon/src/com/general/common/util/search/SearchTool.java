package com.general.common.util.search;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.general.common.constants.GeneralConstants;
import com.general.common.util.BaseTool;
import com.general.common.util.DateTool;

public class SearchTool extends BaseTool {

	/*
	 * check whether user search duration too short
	 * */
	public static boolean ifSearchTooFrequently(HttpServletRequest request)
	{
		boolean ret = false;
		
		HttpSession session = request.getSession();
		Object lastSearchTimeObj = session.getAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION);
		long currTime = System.currentTimeMillis();
		if(lastSearchTimeObj!=null)
		{
			long lastSearchTime = ((Long)lastSearchTimeObj).longValue();			
			if(DateTool.getSecondsBetweenTwoTime(currTime, lastSearchTime) < GeneralConstants.SEARCH_FREQUENT_DURATION_IN_SECONDS)
			{
				ret = true;
			}
		}		
		session.setAttribute(GeneralConstants.SEARCH_LAST_SEARCH_TIME_SAVED_IN_SESSION, currTime);		
		return ret;
	}
}
