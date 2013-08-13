package com.data.collect.common.util;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.data.collect.common.constants.Constants;


public class WebTool extends BaseTool {
	
	public static String getStringParameterBeforeAttribute(String key, HttpServletRequest request)
	{
		String val = WebTool.getStringParameter(key, request);
		if("".equals(val))
		{
			val = WebTool.getStringAttribute(key, request);
		}
		return val.trim();
	}
	
	public static String getStringAttributeBeforeParameter(String key, HttpServletRequest request)
	{
		String val = "";
		try
		{
			val = WebTool.getStringAttribute(key, request);
			if("".equals(val))
			{
				val = WebTool.getStringParameter(key, request);
				val=URLDecoder.decode(val,Constants.PAGE_CHAR_SET_UTF8);
			}
		}catch(UnsupportedEncodingException ex)
		{
			LogTool.logError(ex);
		}finally
		{			
			return val.trim();
		}
	}

	
	public static String getStringParameter(String key, HttpServletRequest request)
	{
		String val = request.getParameter(key);  
		if(val==null)
		{
			val = "";
		}
		return val;
	}
	
	public static String getStringAttribute(String key, HttpServletRequest request)
	{
		String val = null;
		Object obj = request.getAttribute(key);
		if(obj==null)
		{
			val = "";
		}else
		{
			val = (String)obj;
		}
		return val;
	}	
	
	public static void forwardRequest(String toPage, HttpServletRequest request, HttpServletResponse response)
	{
		StringBuffer forwardUrlBuf = new StringBuffer();
		forwardUrlBuf.append(toPage);					
		try{
			RequestDispatcher rd = request.getRequestDispatcher(forwardUrlBuf.toString());
			rd.include(request, response);
		}catch(Exception ex)
		{
			LogTool.logError( ex);
		}
	}
	
	public static void setSessionAttribute(String key, Object value, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
	}
		
	public static Object getObjFromSession(HttpServletRequest request, String attributeName, Class<?> objClass) throws Exception
	{
		HttpSession session = request.getSession();
		Object ret = session.getAttribute(attributeName);
		if(ret==null && objClass!=null)
		{
			ret = objClass.newInstance();
		}
		return ret;
	}
	
	public static Object getObjFromRequest(HttpServletRequest request, String attributeName, Class<?> objClass) throws Exception
	{
		Object ret = request.getAttribute(attributeName);
		if(ret==null && objClass!=null)
		{
			ret = objClass.newInstance();
		}
		return ret;
	}
		
	public static String getBaseURL(HttpServletRequest request)
	{
		return request.getContextPath() + Constants.DATA_COLLECT_SERVLET_NAME;
	}
	
	public static String getAppJSBaseURL(HttpServletRequest request)
	{
		return request.getContextPath() + Constants.PATH_APP_JS;
	}
	
	public static String getExtJSBaseURL(HttpServletRequest request)
	{
		return request.getContextPath() + Constants.PATH_EXT_JS;
	}

	public static String getActionURL(HttpServletRequest request, String action)
	{
		StringBuffer retBuf = new StringBuffer();
		retBuf.append(WebTool.getBaseURL(request));
		retBuf.append(Constants.QUESTION_MARK);
		retBuf.append(Constants.ACTION);
		retBuf.append(Constants.EQUAL_MARK);
		retBuf.append(action);
		return retBuf.toString();
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
	
	private static String createUrlParametersPair(Map<String,String> map)
	{
		StringBuffer buf = new StringBuffer();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext())
		{
			String key = it.next();
			String val = map.get(key);
			buf.append(Constants.AND_MARK);
			buf.append(key);
			buf.append(Constants.EQUAL_MARK);
			buf.append(val);
		}
		return buf.toString();
	}

	public static String getDataTablePageURL(HttpServletRequest request) throws Exception
	{
		return getPageURL(request, Constants.PAGE_DATA_TABLE, false);
	}
	
	public static String getParseTemplatePageURL(HttpServletRequest request) throws Exception
	{
		return getPageURL(request, Constants.PAGE_PARSE_TEMPLATE, false);
	}
	
	public static String getWebSitePageURL(HttpServletRequest request) throws Exception
	{
		return getPageURL(request, Constants.PAGE_WEB_SITE, false);
	}
	
	public static String getLoginPageURL(HttpServletRequest request, boolean addParameter) throws Exception
	{
		return getPageURL(request, Constants.PAGE_LOGIN, addParameter);
	}
	
	public static String getLogoutPageURL(HttpServletRequest request) throws Exception
	{
		return WebTool.getActionURL(request, Constants.ACTION_LOGOUT);
	}
	
	private static String getPageURL(HttpServletRequest request, String pageName, boolean addParameter) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(request.getContextPath());
		buf.append(pageName);
		if(addParameter)
		{
			buf.append(Constants.QUESTION_MARK);
			buf.append(URLTool.getUrlParameterPair(request));
		}
		return buf.toString();
	}
	
	
		
	public static boolean isSessionAttributeExist(String key, HttpServletRequest request) throws Exception
	{
		Object obj = WebTool.getObjFromSession(request, key, null);
		if(obj==null)
		{
			return false;
		}else
		{
			return true;
		}
	}
	
	
	public static String[] getOneDayHours()
	{
		return getNumberArray(24);
	}
	public static String[] getTotalDeepLevel()
	{
		return getNumberArray(11);
	}
	
	public static String[] getDownloadDurationTime()
	{
		return getNumberArray(1000);
	}
	
	private static String[] getNumberArray(int number)
	{
		String ret[] = {};
		if(number>0)
		{
			ret = new String[number];
			for(int i=0;i<number;i++)
			{
				ret[i] = String.valueOf(i) ;
			}
		}
		return ret;
	}
	
	public static void writeAjaxResponse(HttpServletResponse response, String dataStr)
	{
		try
		{
			//LogTool.logText(dataStr);
			response.setCharacterEncoding(Constants.PAGE_CHAR_SET);
			PrintWriter out = response.getWriter();			
			out.println(StringTool.removeReturnCharactor(dataStr));
			out.flush();
			out.close();
		}catch(Exception ex)
		{
			LogTool.logError( ex);
		}
	}
}
