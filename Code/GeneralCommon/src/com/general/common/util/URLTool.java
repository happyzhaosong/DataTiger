package com.general.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.UrlDTO;


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
			LogTool.logError(ex, URLTool.class.getName());
			ret.setSuccess(false);
			ret.setCode(GeneralConstants.ERROR_CODE_URL_CAN_NOT_CONNECT);
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
			buf.append(GeneralConstants.EQUAL_MARK);
			buf.append(val);
			buf.append(GeneralConstants.AND_MARK);			
		}
		return buf.toString();
	}
	
	
	public static boolean isValidDomain(String addStr)
	{
		boolean ret = true;
		
		if("".equals(addStr) || addStr==null)
		{
			ret = false;
		}else if(addStr.startsWith("-")||addStr.endsWith("-"))
		{
			ret = false;
		}else if(addStr.indexOf(".")==-1)
		{
			ret = false;
		}else			
		{		
			String domainEle[] = addStr.split("\\.");
			int size = domainEle.length;
			for(int i=0;i<size;i++)
			{
					String domainEleStr = domainEle[i];
					if("".equals(domainEleStr.trim()))
					{
						return false;
					}
			}
			
			
			
			char[] domainChar = addStr.toCharArray();			
			size = domainChar.length;	
			for(int i=0;i<size;i++)
			{
				char eleChar = domainChar[i];
				String charStr = String.valueOf(eleChar);
				
				if(!".".equals(charStr) && !"-".equals(charStr) && !charStr.matches("[a-zA-Z]") && !charStr.matches("[0-9]"))
				{
					ret = false;
					break;
				}				
			}
		}		
		return ret;
	}
	
	
	public static boolean isInnerIP(String ipAddress)
	{    
        boolean isInnerIp = false;    
        
    	long ipNum = getIpNum(ipAddress);    
	
	    long aBegin = getIpNum("10.0.0.0");    
	    long aEnd = getIpNum("10.255.255.255");    
	    long bBegin = getIpNum("172.16.0.0");    
	    long bEnd = getIpNum("172.31.255.255");    
	    long cBegin = getIpNum("192.168.0.0");    
	    long cEnd = getIpNum("192.168.255.255");
	    long dBegin = getIpNum("224.0.0.0");    
	    long dEnd = getIpNum("239.255.255.255");  
	    isInnerIp = isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd) || isInner(ipNum,cBegin,cEnd)|| isInner(ipNum,dBegin,dEnd) || ipAddress.equals("127.0.0.1") || ipAddress.equals("255.255.255.255") || ipAddress.equals("224.0.0.1") || ipAddress.equals("0.0.0.0");
    	
        return isInnerIp;               
	}   

	
	public static boolean isBroadcastMulticastLoopbackIp(String ipAddress)
	{
		boolean ret = false;
		if(ipAddress==null)
		{
			ret = true;
		}else
		{
			// remove / when ip have this format 10.228.12.22/24
			String[] arrs = ipAddress.split("/");
			ipAddress = arrs[0];
			if("".equals(ipAddress.trim()) || "0.0.0.0".equals(ipAddress.trim()) || "127.0.0.1".equals(ipAddress.trim()) || "224.0.0.1".equals(ipAddress.trim()) || "255.255.255.255".equals(ipAddress.trim()))
			{
				ret = true;
			}
			
			if(!"".equals(ipAddress.trim()) && !ret)
			{
				
				 long ipNum = getIpNum(ipAddress);  
				 long aBegin = getIpNum("127.0.0.1");    
			     long aEnd = getIpNum("127.255.255.255");    
			     long bBegin = getIpNum("224.0.0.0");    
			     long bEnd = getIpNum("239.255.255.255");   
			     
			     if(isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd))
			     {
			    	 ret = true;
			     }
			     
			     if(!ret)
			     {
			    	 String ipArr[] = ipAddress.split("\\.");
			    	 int len = ipArr.length;
			    	 for(int i=0;i<len;i++)
			    	 {
			    		 String ip = ipArr[i];
			    		 /*if("255".equals(ip.trim()))
			    		 {
			    			 ret = true;
			    			 break;
			    		 }
			    		 */
			    	 }
			     }

			}
		}			
		return ret;
	}

	private static long getIpNum(String ipAddress) 
	{    
	    String [] ip = ipAddress.split("\\.");    
	    long a = Integer.parseInt(ip[0]);    
	    long b = Integer.parseInt(ip[1]);    
	    long c = Integer.parseInt(ip[2]);    
	    long d = Integer.parseInt(ip[3]);    
	   
	    long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;    
	    return ipNum;    
	}   


	private static boolean isInner(long userIp,long begin,long end)
	{    
	     return (userIp>=begin) && (userIp<=end);    
	}   

	public static boolean isIp(String addStr)
	{
		boolean ret = true;
		if("".equals(addStr) || addStr==null)
		{
			return false;
		}
		
		String ipEle[] = addStr.split("\\.");
		if(ipEle.length!=4)
		{
			return false;
		}else
		{
			for(int i=0;i<4;i++)
			{
				String ipEleStr = ipEle[i];
				if(!StringTool.isInteger(ipEleStr))
				{
					return false;
				}
			}
		}		
		return ret;
	}
	
	public static boolean isValidIpOrDomain(String addStr)
	{
		boolean ret = false;
		if(URLTool.isIp(addStr))
		{   if(URLTool.isValidIp(addStr))
			{
				ret = true;
			}
		}else if(URLTool.isValidDomain(addStr))
		{
			ret = true;
		}	
		return ret;
	}
	
	public static boolean isValidIp(String addStr)
	{
		boolean ret = true;
		if("".equals(addStr) || addStr==null || "0.0.0.0".equals(addStr))
		{
			return false;
		}
		
		String ipEle[] = addStr.split("\\.");
		if(ipEle.length!=4)
		{
			return false;
		}else
		{
			for(int i=0;i<4;i++)
			{
				String ipEleStr = ipEle[i];
				if(!StringTool.isInteger(ipEleStr))
				{
					return false;
				}else
				{
					if(Integer.parseInt(ipEleStr)<0 || Integer.parseInt(ipEleStr)>=256)
					{
						return false;
					}else
					{
						if(i==0 && Integer.parseInt(ipEleStr)==127)
						{
							return false;
						}
					}					
				}
			}
		}
		
		return ret;
	}

	public static boolean isValidHostName(String hostName)
	{
		boolean ret = true;
		if(hostName==null) 
		{
			return false;
		}		
		hostName = hostName.trim();
		if("".equals(hostName) || hostName.indexOf(" ")!=-1)
		{
			ret = false;
		}else
		{
			Pattern p = Pattern.compile("[a-zA-Z0-9\\.\\-\\_]+");  
			Matcher m = p.matcher(hostName);  
			ret = m.matches();
			
			if(ret)
			{
				String tmp = hostName;
				if(hostName.length()>15)
				{
					tmp = hostName.substring(0, 15);					
				}
				
				p = Pattern.compile("((.)*[a-zA-Z\\-\\_]+(.)*)+");  
				m = p.matcher(tmp);  
				ret = m.matches();
			}
		}
		return ret;
	}
	
	
	public static boolean isRegularUrl(String url)
	{
		boolean ret = true;
		if(StringTool.isEmpty(url))
		{
			ret = false;
		}else
		{
			String urlLower = url.toLowerCase().trim();			
			if(urlLower.startsWith("#") || "none".equals(urlLower) || urlLower.indexOf("javascript")>-1)
			{
				ret = false;
			}
		}
		return ret;
	}
	
}
