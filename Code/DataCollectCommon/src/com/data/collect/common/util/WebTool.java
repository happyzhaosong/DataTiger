package com.data.collect.common.util;

import java.util.Hashtable;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.general.common.constants.GeneralConstants;
import com.general.common.util.GeneralWebTool;

public class WebTool extends GeneralWebTool {
			
	public static String getBaseURL(HttpServletRequest request)
	{
		return GeneralWebTool.getBaseURL(request, Constants.DATA_COLLECT_SERVLET_NAME);
	}
	
	public static String getAppJSBaseURL(HttpServletRequest request)
	{
		return request.getContextPath() + Constants.PATH_APP_JS;
	}
	
	public static String getExtJSBaseURL(HttpServletRequest request)
	{
		return request.getContextPath() + Constants.PATH_EXT_JS;
	}
	
	public static String getUrlIdParametersPair(int id)
	{
		Map<String,String> map = new Hashtable<String,String>();
		map.put(Constants.ID, String.valueOf(id));		
		return createUrlParametersPair(map);
	}
	
	public static String getUrlNameParametersPair(String name)
	{
		Map<String,String> map = new Hashtable<String,String>();
		map.put(Constants.ID_NAME, name);		
		return createUrlParametersPair(map);
	}
	
	public static String getUrlCategoryIdParametersPair(int categoryId)
	{
		Map<String,String> map = new Hashtable<String,String>();
		map.put(Constants.ID_CATEGORY_ID, String.valueOf(categoryId));		
		return createUrlParametersPair(map);
	}
	
	public static String getUrlCategoryNameParametersPair(String catName)
	{
		Map<String,String> map = new Hashtable<String,String>();
		map.put(Constants.ID_CATEGORY_NAME, catName);		
		return createUrlParametersPair(map);
	}

	public static String getActionURL(HttpServletRequest request, String action)
	{
		return GeneralWebTool.getActionURL(request, Constants.DATA_COLLECT_SERVLET_NAME, action);
	}
	
	
	public static String getDataTablePageURL(HttpServletRequest request) throws Exception
	{
		return GeneralWebTool.getPageURL(request, Constants.PAGE_DATA_TABLE, false);
	}
	
	public static String getParseTemplatePageURL(HttpServletRequest request) throws Exception
	{
		return GeneralWebTool.getPageURL(request, Constants.PAGE_PARSE_TEMPLATE, false);
	}
	
	public static String getWebSitePageURL(HttpServletRequest request) throws Exception
	{
		return GeneralWebTool.getPageURL(request, Constants.PAGE_WEB_SITE, false);
	}
	
	public static String getLoginPageURL(HttpServletRequest request, boolean addParameter) throws Exception
	{
		return GeneralWebTool.getPageURL(request, Constants.PAGE_LOGIN, addParameter);
	}
	
	public static String getLogoutPageURL(HttpServletRequest request) throws Exception
	{
		return GeneralWebTool.getActionURL(request, Constants.DATA_COLLECT_SERVLET_NAME, Constants.ACTION_LOGOUT);
	}	
	
	public static String[] getOneDayHours()
	{
		return GeneralWebTool.getNumberArray(24);
	}
	public static String[] getTotalDeepLevel()
	{
		return GeneralWebTool.getNumberArray(11);
	}
	
	public static String[] getDownloadDurationTime()
	{
		return GeneralWebTool.getNumberArray(1000);
	}
}
