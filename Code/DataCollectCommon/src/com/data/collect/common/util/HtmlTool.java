package com.data.collect.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.dto.WebSitePageLinkParseDTO;
import com.general.common.constants.GeneralConstants;
import com.general.common.util.BaseTool;
import com.general.common.util.ClassTool;
import com.general.common.util.GetDeltaTimeTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;
import com.general.common.util.URLTool;


public class HtmlTool extends BaseTool {
	
	/*
	 * because web driver web element ele.getAttribute("href") too slow for a atribute retrive, so need to use regular expression to parse 
	 * out url from html page source.
	 * */
	public static List<String> parseOutUrlLinkList(String pageSrc, String currentUrl, WebSiteDTO webSiteDto, WebSitePageLinkParseDTO linkParseDto) throws Exception
	{
		List<String> retList = new ArrayList<String>();
		if(!StringTool.isEmpty(pageSrc))
		{	
			GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();
			//get all <a ...> tag html text
			String regExp = "<(\\s)*a((?!>).)*>";
			List<String> urlTagList = StringTool.runRegExpToGetStringList(pageSrc, regExp, true);
			getDltaTimeTool.getDeltaTime("parseOutUrlLinkList run regexp delta time", null, Constants.MIN_RECORD_DURATION_TIME, true);
			
			if(!ClassTool.isListEmpty(urlTagList))
			{
				if(!StringTool.isEmpty(currentUrl))
				{
					currentUrl = currentUrl.trim();
				}else
				{
					currentUrl = webSiteDto.getTopUrl().trim();
				}
				
				int size = urlTagList.size();
				for(int i=0;i<size;i++)
				{
					String urlTag = urlTagList.get(i);
					String url = HtmlTool.getUrlInHtmlTag(urlTag).trim();
					
					if(URLTool.isRegularUrl(url))
					{
						//这种情况就相当于http:// (http://stackoverflow.com/questions/4831741/can-i-change-all-my-http-links-to-just)
						if(url.startsWith("//"))
						{
							url = "http:" + url;							
						}else if(url.startsWith("/") || url.startsWith("\\"))
						{
							url = HtmlTool.getRootUrl(currentUrl) + url;
						}else if(url.startsWith("./"))
						{
							url = HtmlTool.getCurrentLevelUrlPathDirectory(currentUrl) + url.substring(1);
						}else if(url.startsWith("../"))
						{
							url = HtmlTool.getParentLevelUrlPathDirectory(currentUrl) + url.substring(2);
						}else if(url.startsWith(GeneralConstants.QUESTION_MARK))
						{
							url = HtmlTool.getUrlPathBeforeQuestionMark(currentUrl) + url;
						}else 
						{
							String urlLower = url.toLowerCase().trim();
							if(!urlLower.startsWith("http"))
							{
								String rootUrl = HtmlTool.getRootUrl(url);
								if(!URLTool.isValidDomain(rootUrl))
								{
									url = HtmlTool.getCurrentLevelUrlPathDirectory(currentUrl) + GeneralConstants.SEPERATOR_SLASH + url;		
								}
							}
						}
						
						
						HtmlTool.processUrlAndAddToList(url, retList, linkParseDto);
					}
				}				
			}
			getDltaTimeTool.getDeltaTime("parseOutUrlLinkList loop url list delta time", null, Constants.MIN_RECORD_DURATION_TIME, true);
		}
		return retList;
	}
	
	
	/*
	 * 
	 * Trim no value charactor in parsed out url.
	 * Remove no value request parameter in parsed out url.
	 * Run regexp on parsed out url if needed.
	 * Reserve useful parameters on parsed out url if needed.
	 * */
	public static void processUrlAndAddToList(String url, List<String> urlList, WebSitePageLinkParseDTO linkParseDto ) throws Exception
	{
		if(!StringTool.isEmpty(url))
		{
			List<String> urlTrimList = new ArrayList<String>();
			urlTrimList.add("%20");
			urlTrimList.add("#");			
			urlTrimList.add(".");
			urlTrimList.add("&");
			urlTrimList.add("?");
			
			url = StringTool.trimSpecialCharactor(url, urlTrimList);
			
			url = url.replaceAll("&amp;", "&");
			
			if(HtmlTool.isCorrectUrl(url, linkParseDto.getUrlCharactor(), linkParseDto.getNotUrlCharactor(), linkParseDto.getUrlMatchRegExp()))
			{
				//remove no value url parameters
				url = HtmlTool.removeNoValueUrlParameter(url);
				
				//run regexp on parsed out url
				url = HtmlTool.runRegExpOnUrl(url, linkParseDto);
				
				//run string find on parsed out url
				url = HtmlTool.reserveParamsFindOnUrl(url, linkParseDto);
								
				url = StringTool.trimSpecialCharactor(url, urlTrimList);
				
				//remove duplicated url in url list, this way can improve efficiency.
				if(!StringTool.isEmpty(url) && !StringTool.isStringEqualExistInArray(url, StringTool.stringListToStringArray(urlList)))
				{
					urlList.add(url);	
				}
			}
		}
	}
	
	/*
	 * Process parsed out url in list and return after processed url list.
	 * */
	/*
	public static List<String> decorateUrlInList(List<String> urlList, WebSitePageLinkParseDTO linkParseDto) throws Exception
	{
		List<String> ret = new ArrayList<String>();
		
		if(!ClassTool.isListEmpty(urlList))
		{
			List<String> urlTrimList = new ArrayList<String>();
			urlTrimList.add("%20");
			urlTrimList.add("#");			
			urlTrimList.add(".");
			urlTrimList.add("&");

			int size = urlList.size();
			for(int i=0;i<size;i++)
			{			
				String url = urlList.get(i);
				
				if(!StringTool.isEmpty(url))
				{
					url = StringTool.trimSpecialCharactor(url, urlTrimList);    				
					url = url.replaceAll("&amp;", "&");
					
					//remove no value url parameters
					url = HtmlTool.removeNoValueUrlParameter(url);
					
					//run regexp on parsed out url
					url = HtmlTool.runRegExpOnUrl(url, linkParseDto);
					
					//run string find on parsed out url
					url = HtmlTool.reserveParamsFindOnUrl(url, linkParseDto);
					
					if(!StringTool.isEmpty(url))
					{
						ret.add(url);
					}
				}
			}
		}
		
		return ret;
	}
	*/
	
	
	//remove no value url parameters( parameter's value is '')
	public static String removeNoValueUrlParameter(String url)
	{
		StringBuffer retBuf = new StringBuffer();
		
		//get url before question mark
		int questionMarkIdx = url.indexOf(Constants.QUESTION_MARK);
		if(questionMarkIdx!=-1)
		{
			retBuf.append(url.substring(0, questionMarkIdx+1));
		}else
		{
			url = StringTool.runRegExpToReplace(url, "#.*$", "");
			return url;
		}
		
		String urlPartArr[] = url.substring(questionMarkIdx+1).split(Constants.AND_MARK);
		
		if(!ClassTool.isNullObj(urlPartArr))
		{
			int len = urlPartArr.length;
			for(int i=0;i<len;i++)
			{
				String urlPart = urlPartArr[i];
				if(!urlPart.endsWith(Constants.EQUAL_MARK))
				{
					retBuf.append(urlPart);
					retBuf.append(Constants.AND_MARK);
				}
			}
		}
		
		if(retBuf.length()==0)
		{
			retBuf.append(url);
		}

		String ret = retBuf.toString();
		
		if(ret.endsWith(Constants.AND_MARK))
		{
			ret = ret.substring(0, ret.length()-1);
		}
		
		if(!StringTool.isEmpty(ret))
		{
			//remove # charactor in url
			ret = StringTool.runRegExpToReplace(ret, "#.*\\?", "?");
			ret = StringTool.runRegExpToReplace(ret, "#.*$", "");
		}
		return ret;
	}
	
	
	
	
	//reserve valuable parameters find on parsed out url
	public static String reserveParamsFindOnUrl(String url, WebSitePageLinkParseDTO linkParseDto) throws Exception
	{
		StringBuffer retUrlBuf = new StringBuffer();
		String reservedParamNames = linkParseDto.getRunStringFindOnUrl();
		
		//not reserve any parameters
		if(StringTool.isEmpty(reservedParamNames))
		{
			if(url.indexOf(GeneralConstants.QUESTION_MARK)>-1)
			{
				url = StringTool.getStringWithStartEndString(url, "", GeneralConstants.QUESTION_MARK);
			}
			return url;
		}else
		{
			LogTool.debugText("url before run reserve parameters = " + url);
		
			if(Constants.RESERVE_ALL_URL_PARAMETERS.equalsIgnoreCase(reservedParamNames))
			{
				return url;
			}else
			{				
				String urlArr[] = url.split(Constants.SEPERATOR_BACK_SLASH + Constants.QUESTION_MARK);
				if(!ClassTool.isNullObj(urlArr) && urlArr.length>1)
				{
					String urlPrefix = "";
					String urlSuffix = "";
					urlPrefix = urlArr[0];

					retUrlBuf.append(urlPrefix);
					retUrlBuf.append(Constants.QUESTION_MARK);
						
					urlSuffix = urlArr[1];					
					String paramsArr[] = urlSuffix.split(Constants.AND_MARK);
					if(!ClassTool.isNullObj(paramsArr))
					{
						int paramsSize = paramsArr.length;
						for(int i=0;i<paramsSize;i++)
						{
							String paramStr = paramsArr[i];
							if(HtmlTool.ifReservedParamInUrl(url, paramStr, reservedParamNames))
							{
								retUrlBuf.append(paramStr);
								retUrlBuf.append(Constants.AND_MARK);
							}
						}					
					}else
					{
						return url;
					}
				}else
				{
					return url;
				}
			}
		}
		
		String ret = retUrlBuf.toString().trim();
		if(ret.endsWith(Constants.AND_MARK))
		{
			ret = ret.substring(0, ret.length()-1);
		}		
		return ret;
	}

	
	private static boolean ifReservedParamInUrl(String url, String paramStr, String reservedParamNames)
	{
		boolean ret = false;
		if(Constants.RESERVE_ALL_URL_PARAMETERS.equalsIgnoreCase(reservedParamNames))
		{
			ret = true;
		}else
		{
			if(!StringTool.isEmpty(reservedParamNames))
			{
				Map<String, String[]> urlChaReserveParamMap = StringTool.parseStringReturnStringListMapCommonSeperator(reservedParamNames);
				String reserveParamNameArr[] = StringTool.getMatchUrlArray(url, urlChaReserveParamMap);
				
				if(!ClassTool.isStringArrayEmpty(reserveParamNameArr))
				{
					int arrSize = reserveParamNameArr.length;
					for(int i = 0; i < arrSize; i++)
					{
						String reserveParamName = reserveParamNameArr[i].toLowerCase().trim();
						if(!StringTool.isEmpty(reserveParamName))
						{
							paramStr = paramStr.toLowerCase().trim();
							if(paramStr.startsWith(reserveParamName + Constants.EQUAL_MARK))
							{
								ret = true;
								break;
							}
						}								
					}
				}
			}
		}
		return ret;
	}
	
	
	
	/*
	//run string find on parsed out url
	public static String runStringFindOnUrl(String url, WebSitePageLinkParseDTO linkParseDto) throws Exception
	{
		String retUrl = url;
		if(!StringTool.isEmpty(linkParseDto.getRunStringFindOnUrl()))
		{
			LogTool.debugText("url before run string find = " + retUrl);
			String arr[] = linkParseDto.getRunStringFindOnUrl().split(Constants.SEPERATOR_SEMICOLON);
			int arrSize = arr.length;
			for(int i = 0; i < arrSize; i++)
			{
				String strPair = arr[i];
				if(!StringTool.isEmpty(strPair))
				{
					String strPairArr[] = strPair.split(Constants.SEPERATOR_COMPLEX);
					int len = strPairArr.length;
					if(len>0)
					{
						String startStr = "";
						String endStr = "";
						if(len==1)
						{
							startStr = strPairArr[0];
						}else if(len==2)
						{
							startStr = strPairArr[0];
							endStr = strPairArr[1];	
						}
						
						
						int startIdx = 0;
						int endIdx = retUrl.length();
						if(!StringTool.isEmpty(startStr))
						{
							startIdx = retUrl.indexOf(startStr, startIdx);
							
							if(startIdx!=-1)
							{
								if(!StringTool.isEmpty(endStr))
								{
									endIdx = retUrl.indexOf(endStr, startIdx + startStr.length());
									if(endIdx!=-1)
									{
										if(startStr.startsWith("&") && endStr.startsWith("&"))
										{
											retUrl = retUrl.substring(0, startIdx) + retUrl.substring(endIdx);
										}else
										{
											retUrl = retUrl.substring(0, startIdx) + retUrl.substring(endIdx + 1);
										}
									}else
									{
										retUrl = retUrl.substring(0, startIdx);
									}
								}else
								{
									retUrl = retUrl.substring(0, startIdx);
								}
							}
						}else
						{
							//if want to remove one string in url then set startStr to empty string and endStr to not empty string.
							if(!StringTool.isEmpty(endStr))
							{
								endIdx = retUrl.indexOf(endStr, startIdx + startStr.length());
								if(endIdx!=-1)
								{
									retUrl = retUrl.substring(0, endIdx) + retUrl.substring(endIdx + endStr.length());
								}
							}
						}
					}
				}								
			}
			LogTool.debugText("url after run string find = " + retUrl);
		}

		return retUrl;
	}
	*/
	
	//run regexp on parsed out url
	public static String runRegExpOnUrl(String url, WebSitePageLinkParseDTO linkParseDto) throws Exception
	{
		if(!StringTool.isEmpty(linkParseDto.getRunRegexpOnUrl()))
		{
			LogTool.debugText("url before run regexp = " + url);
			String regExpArr[] = linkParseDto.getRunRegexpOnUrl().split(Constants.SEPERATOR_SEMICOLON);
			int regExpArrSize = regExpArr.length;
			for(int iRegExp = 0; iRegExp < regExpArrSize; iRegExp++)
			{
				String regExpStr = regExpArr[iRegExp];
				if(!StringTool.isEmpty(regExpStr))
				{
					String srcDestRegExpArr[] = regExpStr.split(Constants.SEPERATOR_COMPLEX);
					int len = srcDestRegExpArr.length;
					if(len>0)
					{
						String srcRegExp = "";
						String destRegExp = "";
						if(len==1)
						{
							srcRegExp = srcDestRegExpArr[0];
						}else if(len==2)
						{
							srcRegExp = srcDestRegExpArr[0];
							destRegExp = srcDestRegExpArr[1];	
						}
						
						if(!StringTool.isEmpty(srcRegExp))
						{
							url = StringTool.runRegExpToReplace(url, srcRegExp, destRegExp);
						}
					}
				}								
			}
			LogTool.debugText("url after run regexp = " + url);
		}
		return url;
	}
	
	
	public static boolean isCorrectUrl(String url, String urlCharactor, String notUrlCharactor, String urlMatchRegExp)
	{
		return StringTool.isCorrectString(url, urlCharactor, notUrlCharactor, urlMatchRegExp);
	}
	
	public static String getRootUrl(String currentUrl) throws Exception
	{
		String ret = "";
		if(!StringTool.isEmpty(currentUrl))
		{
			String parseUrlRootRegExp = "^((?!/).)*/";
			List<String> urlTagList = StringTool.runRegExpToGetStringList(currentUrl, parseUrlRootRegExp, true);
			if(!ClassTool.isListEmpty(urlTagList))
			{
				ret = urlTagList.get(0);
			}else
			{
				parseUrlRootRegExp = "^((?!/).)*/";
				urlTagList = StringTool.runRegExpToGetStringList(currentUrl, parseUrlRootRegExp, true);
				if(!ClassTool.isListEmpty(urlTagList))
				{
					ret = urlTagList.get(0);
				}
			}
			
			if(StringTool.isEmpty(ret))
			{
				if(currentUrl.startsWith("http"))
				{
					ret = currentUrl;
				}
			}
			
			if(ret.endsWith("/"))
			{
				ret = ret.substring(0, ret.length()-1);
			}
			
		}
		return ret;
	}
	
	public static String getCurrentLevelUrlPathDirectory(String currentUrl)
	{
		String ret = "";
		if(!StringTool.isEmpty(currentUrl))
		{
			currentUrl = currentUrl.trim();
			if(currentUrl.endsWith("/"))
			{
				if(currentUrl.length()>1)
				{
					ret = currentUrl.substring(0,currentUrl.length() - 1);
				}
			}else
			{
				int endIdx = currentUrl.lastIndexOf("/");
				
				if(endIdx==-1)
				{
					ret = currentUrl;
				}else
				{
					ret = currentUrl.substring(0,endIdx);
				}
			}
		}
		return ret;
	}
	
	
	public static String getParentLevelUrlPathDirectory(String currentUrl)
	{
		String ret = HtmlTool.getCurrentLevelUrlPathDirectory(currentUrl);
		ret = HtmlTool.getCurrentLevelUrlPathDirectory(ret);
		return ret;
	}
	
	public static String getUrlPathBeforeQuestionMark(String currentUrl)
	{
		String ret = "";
		if(!StringTool.isEmpty(currentUrl))
		{
			int endIdx = currentUrl.indexOf(GeneralConstants.QUESTION_MARK);
			if(endIdx!=-1)
			{
				ret = currentUrl.substring(0, endIdx);
			}
		}
		return ret;
	}
	
	public static String getUrlInHtmlTag(String urlHtml) throws Exception
	{
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();
		String ret = "";
		if(!StringTool.isEmpty(urlHtml))
		{
			List<String> urlTrimList = new ArrayList<String>();
			urlTrimList.add(">");
			urlTrimList.add("href");
			urlTrimList.add("HREF");
			urlTrimList.add("=");
			urlTrimList.add("\"");
			urlTrimList.add("'");
			urlTrimList.add("%20");
			urlTrimList.add("#");			
			urlTrimList.add(".");
			urlTrimList.add("&");
			
			List<String> attrNameList = new ArrayList<String>();
			attrNameList.add("href");
			attrNameList.add("HREF");
			
			ret = HtmlTool.getAttribteValueInTagHtml(urlHtml, attrNameList, urlTrimList);
			ret = ret.replaceAll("&amp;", "&");
			ret = DownloadTool.processDownloadPageUrl(ret);
			ret = ret.trim();
		}
		getDltaTimeTool.getDeltaTime("getUrlInHtmlTag delta time", null, Constants.MIN_RECORD_DURATION_TIME, true);
		return ret;
	}
	
	public static String getAttributeValueFromOuterHtml(String outerHtml, String attrName) throws Exception
	{
		String ret = "";
		if(!StringTool.isEmpty(outerHtml) && !StringTool.isEmpty(attrName))
		{
			String regExp = "<((?!>).)*" + attrName + "(\\s)*=((?!>).)*>";
			List<String> attrTagHtmlValList = StringTool.runRegExpToGetStringList(outerHtml, regExp, true);
			if(!ClassTool.isListEmpty(attrTagHtmlValList))
			{
				List<String> urlTrimList = new ArrayList<String>();
				urlTrimList.add(">");
				urlTrimList.add(attrName);
				urlTrimList.add("=");
				urlTrimList.add("\"");
				urlTrimList.add("'");			
				urlTrimList.add("#");
				urlTrimList.add("&");
				
				List<String> attrNameList = new ArrayList<String>();
				attrNameList.add(attrName);
				
				int size = attrTagHtmlValList.size();
				for(int i=0;i<size;i++)
				{
					String attrTagHtmlVal = attrTagHtmlValList.get(0);
					ret = HtmlTool.getAttribteValueInTagHtml(attrTagHtmlVal, attrNameList, urlTrimList);
				}
			}
		}
		return ret;
	}
	
	public static String getAttribteValueInTagHtml(String attrTagHtml, List<String> attrNameList, List<String> urlTrimList) throws Exception
	{	
		String ret = "";
		if(!StringTool.isEmpty(attrTagHtml))
		{
			String attrHtmlArr[] = attrTagHtml.split(" ");
			int attrHtmlArrSize = attrHtmlArr.length;
			for(int i=0;i<attrHtmlArrSize;i++)
			{
				String attrHtmlTmp = attrHtmlArr[i].trim();
				if(HtmlTool.isCorrectAttrHtml(attrHtmlTmp, attrNameList))
				{
					ret = StringTool.trimSpecialCharactor(attrHtmlTmp, urlTrimList);
					break;
				}
			}
		}
		return ret;
	}
	
	
	public static boolean isCorrectAttrHtml(String attrHtml, List<String> attrNameList) throws Exception
	{
		boolean ret = false;
		if(!ClassTool.isListEmpty(attrNameList) && !StringTool.isEmpty(attrHtml))
		{
			int size = attrNameList.size();
			for(int i=0;i<size;i++)
			{
				String attrName = attrNameList.get(i);
				if(!StringTool.isEmpty(attrName))
				{
					String regExp = "^" + attrName + "(\\s)*=";
					List<String> tmpList = StringTool.runRegExpToGetStringList(attrHtml, regExp, true);
					if(!ClassTool.isListEmpty(tmpList))
					{
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}
}
