package com.general.common.util;

import java.util.ArrayList;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.general.common.constants.GeneralConstants;
import com.general.common.exception.EmptyStringException;

public class StringTool extends BaseTool {
	
	/*
	 * parse srcStr use following 3 seperator, example:
	 * srcStr : list.tmall.com=//*[@id="J_FilterNavForm"]/div/div[1],//*[@id="select-hold"];detail.tmall.com=//*[@id="J_FilterNavForm"]/div/div[2],//*[@id="select-hold"]
	 * seperator1 : ;
	 * seperator2 : =
	 * seperator3 : ,
	 * */
	private static Map<String, String[]> parseStringReturnStringListMap(String srcStr, String seperator1, String seperator2, String seperator3)
	{
		Map<String, String[]> ret = new Hashtable<String, String[]>();
		if(!StringTool.isEmpty(srcStr))
		{
			String arr1[] = srcStr.split(seperator1);
			if(arr1!=null)
			{
				int size1 = arr1.length;
				 for(int i=0;i<size1;i++)
				 {
					 String str1 = arr1[i];
					 if(!StringTool.isEmpty(str1))
					 {
						 String arr2[] = str1.split(seperator2);
						 if(arr2!=null)
						 {
							 int size2 = arr2.length;
							 if(size2>=2)
							 {
								 String key = arr2[0];
								 String value = arr2[1];
								 
								 if(!StringTool.isEmpty(value))
								 {
									 String arr3[] = value.split(seperator3);
									 ret.put(key, arr3);
								 }
							 }
						 }
					 }
				 }
			}
		}
		return ret;
	}
	
	
	public static int getIntByBoolean(boolean val)
	{
		if(val)
		{
			return 1;
		}else
		{
			return 0 ;
		}
	}
	
	public static String[] getMatchUrlArray(String url, Map<String, String[]> urlChaArrMap)
	{
		String ret[] = null;
        
		if(urlChaArrMap!=null)
		{
			Set<String> keys = urlChaArrMap.keySet();
			Iterator it = keys.iterator();
			while(it.hasNext())
			{
				String urlCha = (String)it.next();
				if(!StringTool.isEmpty(urlCha) && url.indexOf(urlCha)!=-1)
				{
					ret = urlChaArrMap.get(urlCha);
					break;
				}
			}
		}
		
		if(ClassTool.isNullObj(ret))
		{
			ret = urlChaArrMap.get(GeneralConstants.ALL_UNMATCHED_URL);
		}
		return ret;
	}
	
	
	public static Map<String, String[]> parseStringReturnStringListMapCommonSeperator(String srcStr)
	{
		return StringTool.parseStringReturnStringListMap(srcStr, GeneralConstants.SEPERATOR_SEMICOLON, GeneralConstants.SEPERATOR_COMPLEX, GeneralConstants.SEPERATOR_COMMA);
	}
	
	public static String getStringFromObjectArray(Object objArr[], String seperator)
	{
		StringBuffer retBuf = new StringBuffer();
		if(!ClassTool.isNullObj(objArr))
		{
			int size = objArr.length;
			for(int i=0;i<size;i++)
			{
				Object obj = objArr[i];
				
				if(obj instanceof String)
				{
					retBuf.append(obj);
				}else if(obj instanceof Integer)
				{
					Integer intObj = (Integer)obj;
					retBuf.append(intObj.intValue());
				}
				
				if(i<size-1)
				{
					retBuf.append(seperator);	
				}
			}
		}
		return retBuf.toString();
	}
	
	public static boolean isInteger(String str)
	{
		try
		{
			Integer.parseInt(str);
			return true;
		}catch(NumberFormatException ex)
		{
			return false;
		}catch(Exception ex)
		{
			LogTool.logError(ex, StringTool.class.getName());
			return false;
		}
	}

	public static boolean isNumeric(String str){ 
		/*
	    Pattern pattern = Pattern.compile("[0-9]+"); 
	    return pattern.matcher(str).matches();
	    */
		
		return (StringTool.isInteger(str) || StringTool.isDouble(str) || StringTool.isLong(str));	     
	}
	
	public static boolean isLong(String str)
	{
		try
		{
			Long.parseLong(str);
			return true;
		}catch(NumberFormatException ex)
		{
			return false;
		}catch(Exception ex)
		{
			LogTool.logError(ex, StringTool.class.getName());
			return false;
		}
	}
	
	public static boolean isDouble(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException ex)
		{
			return false;
		}catch(Exception ex)
		{
			LogTool.logError(ex, StringTool.class.getName());
			return false;
		}
	}
	
	public static int checkInteger(String str, String errMsg) throws Exception
	{
		try
		{
			return Integer.parseInt(str);
		}catch(Exception ex)
		{
			LogTool.logError(ex, StringTool.class.getName());
			throw new Exception(errMsg);
		}
	}
	
	public static long checkLong(String str, String errMsg) throws Exception
	{
		try
		{
			return Long.parseLong(str);
		}catch(Exception ex)
		{
			LogTool.logError(ex, StringTool.class.getName());
			throw new Exception(errMsg);
		}
	}
	
	public static double checkDouble(String str, String errMsg) throws Exception
	{
		try
		{
			return Double.parseDouble(str);
		}catch(Exception ex)
		{
			LogTool.logError(ex, StringTool.class.getName());
			throw new Exception(errMsg);
		}
	}
	
	public static boolean isEmpty(String str)
	{
		if(str==null) str="";
		if("".equals(str.trim()))
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public static String isEmpty(String str, String defaultVal)
	{
		if(isEmpty(str))
		{
			if(defaultVal==null) defaultVal = "";
			return defaultVal.trim();
		}else
		{
			return str.trim();
		}
	}
	
	public static void checkEmpty(String str, String errMsg) throws Exception
	{
		if(str==null) str="";
		if("".equals(str.trim()))
		{
			throw new EmptyStringException(errMsg);
		}
	}
	
	public static void checkValidId(String idStr, String eleName) throws Exception
	{
		StringTool.checkEmpty(idStr, eleName + " id can not be empty.");
		StringTool.checkInteger(idStr, eleName + " id should be an integer value.");
		if(Integer.parseInt(idStr)==-1)
		{
			String errMsg = eleName + " should be saved first";
			Exception ex = new Exception(errMsg);
			LogTool.logError(ex, StringTool.class.getName());
			throw ex;
		}
	}
	
	public static String encodeStr(String str)
	{
		if(str==null || "".equals(str.trim()))
		{
			return "";
		}else
		{
			StringBuffer strBuf = new StringBuffer();
			char[] cArr = str.toCharArray();			
			int size = cArr.length;
			
			for(int i=0;i<size;i++)
			{
				char c = cArr[i];
				int cNum = (int)c;
				cNum++;
				strBuf.append((char)cNum);
			}			
			return strBuf.toString();
		}
	}
	
	public static String decodeStr(String str)
	{
		if(str==null || "".equals(str.trim()))
		{
			return "";
		}else
		{
			StringBuffer strBuf = new StringBuffer();
			char[] cArr = str.toCharArray();			
			int size = cArr.length;
			
			for(int i=0;i<size;i++)
			{
				char c = cArr[i];
				int cNum = (int)c;
				cNum--;
				strBuf.append((char)cNum);
			}			
			return strBuf.toString();
		}
	}
	

	public static String trimSpace(String str)
	{
		if(str==null)
		{
			return "";
		}else
		{
			return str.trim();
		}
	}
	

	public static boolean isStringEqualExistInArray(String data, String srcArr[])
	{
		boolean ret = false;
		if(!StringTool.isEmpty(data) && !ClassTool.isNullObj(srcArr))
		{
			int size = srcArr.length;
			for(int i=0;i<size;i++)
			{
				String tmp = srcArr[i].trim();
				if(tmp.equals(data.trim()))
				{
					ret = true;
					break;
				}
			}
		}
		return ret;
	}
	
	public static String[] stringListToStringArray(List<String> list)
	{
		String retArr[] = {};
		if(!ClassTool.isListEmpty(list))
		{
			Object objArr[] = list.toArray();
			retArr = new String[objArr.length];
			
			int size = objArr.length;
			for(int i=0;i<size;i++)
			{
				retArr[i] = (String)objArr[i];
			}
		}
		return retArr;
	}
	
	public static boolean isStringLikeExistInArray(String data, String srcArr[])
	{
		boolean ret = false;
		if(!StringTool.isEmpty(data) && !ClassTool.isNullObj(srcArr))
		{
			int size = srcArr.length;
			for(int i=0;i<size;i++)
			{
				String tmp = srcArr[i].trim();
				if(data.indexOf(tmp)!=-1)
				{
					ret = true;
					break;
				}
			}
		}
		return ret;
	}
	
	/*
	 * Translate objParam to db related column, for example:
	 * applyTime--->apply_time
	 * */

	public static String translateToDBColumn(String objParam)
	{
		StringBuffer retBuf = new StringBuffer();
		if(objParam==null) objParam = "";
		
		if(objParam.indexOf(GeneralConstants.SEPERATOR_UNDER_LINE)==-1)
		{
			int len = objParam.length();
			for(int i=0;i<len;i++)
			{
				char ch = objParam.charAt(i);
				if(Character.isLowerCase(ch))
				{
					retBuf.append(ch);
				}else
				{
					retBuf.append(GeneralConstants.SEPERATOR_UNDER_LINE);
				    retBuf.append(Character.toLowerCase(ch));
				}
			}
			return retBuf.toString();
		}else
		{
			return objParam;
		}
	}

	
	public static String trimSpecialCharactor(String str, List<String> charactorList) //throws Exception
	{
		String ret = str;
		if(!ClassTool.isListEmpty(charactorList) && !StringTool.isEmpty(str))
		{
			int size = charactorList.size();
			for(int i=0;i<size;i++)
			{
				String charactor = charactorList.get(i);
				ret = StringTool.trimSpecialCharactor(ret, charactor);
			}
		}
		return ret;
	}
	
	public static String trimSpecialCharactor(String str, String charactor) //throws Exception
	{
		String ret = "";
		if(str==null || GeneralConstants.SEPERATOR_SINGLE_QUOTES.equals(str))
		{
			return "";
		}else
		{
			str = str.trim();
			int startIdx = str.indexOf(charactor);
			if(startIdx!=0)
			{
				startIdx = 0;
			}else
			{
				startIdx += charactor.length();
			}
				
			int endIdx = str.lastIndexOf(charactor);
			if(endIdx!=(str.length()-charactor.length()))
			{
				endIdx = str.length();
			}
				
			if(startIdx < endIdx)
			{
				ret = str.substring(startIdx, endIdx);
			}
		}
		return ret;
	}
	
	public static String getStringWithStartEndString(String str, String startStr, String endStr) //throws Exception
	{
		String ret = "";
		if(!StringTool.isEmpty(str))
		{
			str = str.trim();
			int startIdx = 0;
			int endIdx = 0;
			
			if(!StringTool.isEmpty(startStr) && !StringTool.isEmpty(endStr))
			{
				startIdx = str.indexOf(startStr);
				if(startIdx!=-1)
				{
					startIdx = startIdx + startStr.length();
					endIdx = str.indexOf(endStr, startIdx + 1);
					if(endIdx!=-1)
					{
						if(startIdx < endIdx)
						{
							ret = str.substring(startIdx, endIdx);
						}
					}
				}
			}
			//get the string from beginning of the string to the endStr
			else if(StringTool.isEmpty(startStr) && !StringTool.isEmpty(endStr))
			{
				endIdx = str.indexOf(endStr, startIdx + 1);
				if(endIdx!=-1)
				{
					if(startIdx < endIdx)
					{
						ret = str.substring(startIdx, endIdx);
					}
				}
			}
			//get the string from startStr to the end of the string
			else if(!StringTool.isEmpty(startStr) && StringTool.isEmpty(endStr))
			{
				startIdx = str.indexOf(startStr);
				endIdx = str.length();
				if(startIdx!=-1)
				{
					startIdx = startIdx + startStr.length();
					if(startIdx < endIdx)
					{
						ret = str.substring(startIdx, endIdx);
					}
				}
			}
			
		}
		return ret;
	}
	
	public static int getIntegerValInStr(String value)
	{
		value = value.trim();
		if(StringTool.isInteger(value))
		{
			return Integer.parseInt(value);
		}else 
		{
			return -1;
		}
	}
	
	public static long getLongValInStr(String value)
	{
		value = value.trim();
		if(StringTool.isLong(value))
		{
			return Long.parseLong(value);
		}else 
		{
			return -1;
		}
	}
	
	public static double getDoubleValInStr(String value)
	{
		value = value.trim();
		if(StringTool.isDouble(value))
		{
			return Double.parseDouble(value);
		}else 
		{
			return -1;
		}
	}
	
	public static boolean getBooleanValInStr(String value)
	{
		if(StringTool.isEmpty(value))
		{
			return false;
		}else
		{
			value = value.trim();
			if("0".equals(value) || "false".equalsIgnoreCase(value))
			{
				return false;
			}else 
			{
				return true;
			}
		}
	}
	
	
	public static String[] splitString(String srcStr, String splitor)
	{
		List<String> ret = new ArrayList<String>();
		int startIdx = 0;
		int endIdx = srcStr.indexOf(splitor);
		while(endIdx!=-1)
		{
			String tmpStr = srcStr.substring(startIdx, endIdx);
			ret.add(tmpStr);
			startIdx = endIdx + splitor.length();
			endIdx = srcStr.indexOf(splitor, startIdx);
		}
		
		ret.add(srcStr.substring(startIdx));		
		
		int size = ret.size();
		String[] retArr = new String[size];
		for(int i=0;i<size;i++)
		{
			retArr[i] = ret.get(i);
		}
		
		return retArr;
	}
	
	
	public static String initProjectId(String projectId)
	{
		if(projectId==null || "".equals(projectId))
		{
			return GeneralConstants.VALUE_ALL;
		}else
		{
			return projectId;
		}
	}
	
	public static boolean ifMatchStringCharactor(String str, String matchCharactor)
	{
		boolean ret = false;
		if(str!=null && !"".equals(str) && matchCharactor!=null && !"".equals(matchCharactor))
		{
			String charactorArr[] = matchCharactor.split(GeneralConstants.SEPERATOR_SEMICOLON);
			int size = charactorArr.length;
			for(int i=0;i<size;i++)
			{
				String charactor = charactorArr[i];
				if(charactor!=null) charactor = charactor.trim();
				if(!"".equals(charactor))
				{
					if(str.indexOf(charactor)!=-1)
					{
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}
	
	public static boolean isCorrectString(String str, String strCharactor, String notStrCharactor, String strMatchRegExp)
	{
		boolean ret = true;
		if(StringTool.isEmpty(str))
		{
			return false;
		}
		
		if(!StringTool.isEmpty(strCharactor))
		{
			if(!StringTool.ifMatchStringCharactor(str, strCharactor))
			{
				return false;
			}
		}
		
		if(!StringTool.isEmpty(notStrCharactor))
		{
			if(StringTool.ifMatchStringCharactor(str, notStrCharactor))
			{
				return false;
			}
		}
		
		if(!StringTool.isEmpty(strMatchRegExp))
		{
			if(!StringTool.ifMatchStringCharactor(str, strMatchRegExp))
			{
				return false;
			}
		}
		return ret;
	}
	
	
	public static void removeLastIndexCharactor(StringBuffer strBuf, String charactor)
	{
		int lastIdx = strBuf.lastIndexOf(charactor);		
		if(lastIdx!=-1)
		{
			strBuf.deleteCharAt(lastIdx);
		}
	}
	
	public static String removeLastIndexCharactor(String str, String charactor)
	{
		str = str.trim();
		int lastIdx = str.lastIndexOf(charactor);		
		if(lastIdx!=-1)
		{
			return str.substring(0, lastIdx);
		}else
		{
			return str;
		}
	}
	
	public static String removeReturnCharactor(String str)
	{
		str = str.replaceAll("\n", "");
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\r\n", "");
		str = str.replaceAll("\n\r", "");
		return str;
	}
	
	public static String removeLastCharactor(String str, String charactor)
	{
		str = str.trim();
		int lastIdx = str.lastIndexOf(charactor);	
		if(lastIdx!=-1)
		{
			if(lastIdx!=(str.length()-1))
			{
				return str;
			}else
			{
				return str.substring(0, lastIdx);
			}
		}else
		{
			return str;
		}
	}
	
	
	public static List<String> replacePlaceHolderInUrl(String url, String placeHolder, String valArr[])  throws Exception
	{
		List<String> ret = new ArrayList<String>();
		if(!StringTool.isEmpty(url))
		{
			int len = valArr.length;
			if(len==0)
			{
				ret.add(url);
			}else
			{
				for(int i=0;i<len;i++)
				{
					String val = valArr[i];
					String newVal = url.replaceAll(placeHolder, val);
					ret.add(newVal);
				}
			}
		}
		return ret;
	}
	
	public static String getCountryIsoCodeArr()  throws Exception
	{
		StringBuffer cIsoCodeBuf = new StringBuffer();
		cIsoCodeBuf.append(GeneralConstants.COUNTRY_ISO_CODE0);
		cIsoCodeBuf.append(GeneralConstants.COUNTRY_ISO_CODE1);
		cIsoCodeBuf.append(GeneralConstants.COUNTRY_ISO_CODE2);
		cIsoCodeBuf.append(GeneralConstants.COUNTRY_ISO_CODE3);
		return cIsoCodeBuf.toString();
	}

   
    
    /*
     * remove html comments, and make string to lower case
     * */
    public static String removeHtmlComments(String root)
    {
    	String ret = "";
    	if(!StringTool.isEmpty(root))
    	{
    		//ret = root.toLowerCase();
    		//remove all html comments
    		ret = root.replaceAll("<!((?!>).)*>", "").trim();
    	}
    	return ret;
    }
    

    
	/**
     * Removes all invalid Unicode characters that are not suitable to be used either
     * in markup or text inside XML Documents.
     * 
     * Based on these recommendations
     * http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char
     * http://cse-mjmcl.cse.bris.ac.uk/blog/2007/02/14/1171465494443.html
     *
     * @param s The resultant String stripped of the offending characters!
     * @return
     */
    public static String removeInvalidXMLCharacters(String s)
    {
        StringBuilder out = new StringBuilder();

        int codePoint;
        int i = 0;

        while (i < s.length())
        {
            // This is the unicode code of the character.
            codePoint = s.codePointAt(i);
            if ((codePoint == 0x9) ||
                    (codePoint == 0xA) ||
                    (codePoint == 0xD) ||
                    ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                    ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                    ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)))
            {
                out.append(Character.toChars(codePoint));
            }
            i += Character.charCount(codePoint);
        }
        return out.toString();
    }
    
    
    /**
     * Remove all characters that are valid XML markups.
     * http://www.w3.org/TR/2000/REC-xml-20001006#syntax
     *
     * @param s
     * @return
     */
    public static String removeXMLMarkups(String s)
    {
        StringBuffer out = new StringBuffer();
        char[] allCharacters = s.toCharArray();
        for (char c : allCharacters)
        {
            if ((c == '\'') || (c == '<') || (c == '>') || (c == '&') || (c == '\"'))
            {
                continue;
            }
            else
            {
                out.append(c);
            }
        }
        return out.toString();
    }

    
    public static String escapeHtmlTagInString(String html)
    {
    	html = html.replaceAll("<", "&lt;");
    	html = html.replaceAll(">", "&gt;");
    	return html;
    }
    
	public static String runRegExpToReplace(String data, String srcRegExp, String destRegExpVal)
	{
		StringBuffer retBuf = new StringBuffer();
		
		Pattern p = Pattern.compile(srcRegExp,Pattern.UNICODE_CASE);
        Matcher m = p.matcher(data);

        //int startIdx = 0;

        //System.out.println("data = "+data);
        while (m.find()) {
                m.appendReplacement(retBuf, destRegExpVal);
                //String tmp = data.substring(m.start(),m.end());
                //System.out.println(tmp);
                //System.out.println(m.group()+"  "+m.start() + "   "+m.end());
                //startIdx = m.end();
        }

        m.appendTail(retBuf);
		return retBuf.toString();
	}
	
	/*
	 * return all string that match regExp in data in a list.
	 * data --- source string.
	 * regExp --- regular expression that need to match.
	 * trimStringList --- need to trimmed string list when parsed out the matched string. 
	 * */
	public static List<String> runRegExpToGetStringList(String data, String regExp, boolean caseInsensitive) //throws Exception
	{
		List<String> list = new ArrayList<String>();
		Pattern p = null;
		
		if(!caseInsensitive)
		{
			p = Pattern.compile(regExp,Pattern.UNICODE_CASE);
		}else
		{
			p = Pattern.compile(regExp,Pattern.UNICODE_CASE&Pattern.CASE_INSENSITIVE);
		}
		
		if(!ClassTool.isNullObj(p) && !StringTool.isEmpty(data))
		{
	        Matcher m = p.matcher(data);
	
	        while (m.find()) {
	        	int startIdx = m.start();
	        	int endIdx = m.end();
	        	
	        	if(startIdx < endIdx)
	        	{
		        	String tmp = data.substring(startIdx, endIdx);
		        	
		        	if(!StringTool.isEmpty(tmp))
		        	{
		        		list.add(tmp.trim());
		        	}
	        	}
	        }
		}

		return list;
	}
	
	
	/** *//**  
     * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在  
     * 包含汉字的字符串时存在隐患，现调整如下：  
     * @param src 要截取的字符串  
     * @param start_idx 开始坐标（包括该坐标)  
     * @param end_idx   截止坐标（包括该坐标）  
     * @return  
     */  
    public static String substring(String src, int start_idx, int end_idx){   
        byte[] b = src.getBytes();   
        String tgt = "";   
        for(int i=start_idx; i<=end_idx; i++){   
            tgt +=(char)b[i];   
        }   
        return tgt;   
    }
    
    
    public static String getFirstNumWordsInString(String str, int wordCount)
    {
    	if(StringTool.isEmpty(str))
    	{
    		return "";
    	}else
    	{
    		StringBuffer sb = new StringBuffer();
    		int addWordCount = 0;
    		String strArr[] = str.split(" ");
    		int size = strArr.length;
    		for(int i=0;i<size;i++)
    		{
    			String strItem = strArr[i];
    			if(!StringTool.isEmpty(strItem))
    			{
    				addWordCount++;
    				strItem = strItem.trim();
    				int letterCount = 0;
    		    	int len = strItem.length();
    		    	for(int j=0;j<len;j++)
    		    	{
    		    		char c = strItem.charAt(j);    		    		
    		    		if(StringTool.ifChineseString(String.valueOf(c)))
    		    		{
    		    			letterCount=0;
    		    			addWordCount++;    		    			
    		    		}else
    		    		{
    		    			letterCount++;
    		    		}
    		    		
    		    		if(letterCount>6)
    		    		{
    		    			letterCount=0;
    		    			addWordCount++;
    		    		}
    		    		
    		    		sb.append(c);
    		    		
    		    		if(addWordCount>wordCount)
    		    		{
    		    			return sb.toString();
    		    		}
    		    	}
    		    	sb.append(" ");
    			}
    		}
    		return sb.toString();
    	}
    }
    
    public static boolean ifChineseString(String str)
    {
    	 boolean ret = str.matches("[\u4E00-\u9FA5]"); 
    	 return ret;
    }
}
