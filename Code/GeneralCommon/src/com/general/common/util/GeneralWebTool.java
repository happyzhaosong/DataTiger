package com.general.common.util;

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

import com.general.common.constants.GeneralConstants;


public class GeneralWebTool extends BaseTool {
	
	public static String getStringParameterBeforeAttribute(String key, HttpServletRequest request)
	{
		String val = GeneralWebTool.getStringParameter(key, request);
		if("".equals(val))
		{
			val = GeneralWebTool.getStringAttribute(key, request);
		}
		return val.trim();
	}
	
	public static String getStringAttributeBeforeParameter(String key, HttpServletRequest request)
	{
		String val = "";
		try
		{
			val = GeneralWebTool.getStringAttribute(key, request);
			if("".equals(val))
			{
				val = GeneralWebTool.getStringParameter(key, request);
				val=URLDecoder.decode(val,GeneralConstants.PAGE_CHAR_SET_UTF8);
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
		
	public static String getBaseURL(HttpServletRequest request, String servletName)
	{
		return request.getContextPath() + servletName;
	}
	

	public static String getActionURL(HttpServletRequest request, String servletName, String action)
	{
		StringBuffer retBuf = new StringBuffer();
		retBuf.append(GeneralWebTool.getBaseURL(request, servletName));
		retBuf.append(GeneralConstants.QUESTION_MARK);
		retBuf.append(GeneralConstants.ACTION);
		retBuf.append(GeneralConstants.EQUAL_MARK);
		retBuf.append(action);
		retBuf.append(GeneralConstants.AND_MARK);
		return retBuf.toString();
	}
	
	public static String createUrlParametersPair(Map<String,String> map)
	{
		StringBuffer buf = new StringBuffer();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while(it.hasNext())
		{
			String key = it.next();
			String val = map.get(key);
			buf.append(GeneralConstants.AND_MARK);
			buf.append(key);
			buf.append(GeneralConstants.EQUAL_MARK);
			buf.append(val);
		}
		return buf.toString();
	}


	
	public static String getPageURL(HttpServletRequest request, String pageName, boolean addParameter) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(request.getContextPath());
		buf.append(pageName);
		if(addParameter)
		{
			buf.append(GeneralConstants.QUESTION_MARK);
			buf.append(URLTool.getUrlParameterPair(request));
		}
		return buf.toString();
	}
	
	
		
	public static boolean isSessionAttributeExist(String key, HttpServletRequest request) throws Exception
	{
		Object obj = GeneralWebTool.getObjFromSession(request, key, null);
		if(obj==null)
		{
			return false;
		}else
		{
			return true;
		}
	}
		
	public static String[] getNumberArray(int number)
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
			response.setCharacterEncoding(GeneralConstants.PAGE_CHAR_SET);
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
