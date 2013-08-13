package com.data.collect.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.UrlDTO;


public class URLTool extends BaseTool{
		
	private static String TYPE_PARAMETER = "TYPE_PARAMETER";
	private static String TYPE_ATTRIBUTE = "TYPE_ATTRIBUTE";
	
	public static UrlDTO getUrlContent(String address) throws Exception
	{
		UrlDTO ret = new UrlDTO();
		ret.setSuccess(true);
		try{
			URL url = new URL(address);
			URLConnection urlConn = url.openConnection();
			HttpURLConnection httpUrlConn = (HttpURLConnection)urlConn;
			
			InputStream is = httpUrlConn.getInputStream();
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader bufReader = new BufferedReader(reader);
			String urlContent = bufReader.readLine();
			ret.setUrlContent(urlContent);
			if("1".equals(urlContent))
			{
				ret.setExist(true);
			}else
			{
				ret.setExist(false);
			}
		}catch(Exception ex)
		{
			LogTool.logError( ex);
			ret.setSuccess(false);
			ret.setCode(Constants.ERROR_CODE_URL_CAN_NOT_CONNECT);
			ret.setMessage(ex.getMessage());
			throw ex;
		}finally
		{
			return ret;
		}
	}
	
	
	public static String getUrlParameterPair(HttpServletRequest request) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(URLTool.getEnumerationParamPair(request, TYPE_PARAMETER));
		buf.append(URLTool.getEnumerationParamPair(request, TYPE_ATTRIBUTE));
		return buf.toString();
	}
	
	
	private static String getEnumerationParamPair(HttpServletRequest request, String type) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		Enumeration<String> enu = null;
		if(TYPE_PARAMETER.equalsIgnoreCase(type))
		{
			enu = request.getParameterNames();
		}else if(TYPE_ATTRIBUTE.equalsIgnoreCase(type))
		{
			enu = request.getAttributeNames();
		}
		
		while(enu.hasMoreElements())
		{
			String val = "";
			String key = enu.nextElement();
			if(TYPE_PARAMETER.equalsIgnoreCase(type))
			{
				val = request.getParameter(key);
			}else if(TYPE_ATTRIBUTE.equalsIgnoreCase(type))
			{
				Object obj = request.getAttribute(key);
				if(obj!=null && obj instanceof String)
				{
					val = (String)obj;
				}
			}
			buf.append(key);
			buf.append(Constants.EQUAL_MARK);
			buf.append(val);
			buf.append(Constants.AND_MARK);			
		}
		return buf.toString();
	}
}
