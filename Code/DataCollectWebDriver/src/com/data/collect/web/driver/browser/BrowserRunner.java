package com.data.collect.web.driver.browser;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DBTableDTO;
import com.data.collect.common.dto.DownloadTaskDTO;
import com.data.collect.common.dto.WebSiteContentPageCheckDTO;
import com.data.collect.common.dto.WebSiteDTO;
import com.data.collect.common.dto.WebSitePageLinkParseDTO;
import com.data.collect.common.exception.ParsePageException;
import com.data.collect.common.exception.ResetDownloadTaskException;
import com.data.collect.common.util.HtmlTool;
import com.data.collect.server.dao.DBManagerDAO;
import com.data.collect.server.dao.DownloadTaskDAO;
import com.data.collect.server.dao.WebSiteDAO;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.DBTableColumnDTO;
import com.general.common.dto.ParseTplItemActionDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.ConfigTool;
import com.general.common.util.DateTool;
import com.general.common.util.GetDeltaTimeTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class BrowserRunner {
	
	private boolean accessDenied = false;	
	private WebDriver driver = null;	
	private WebSiteDAO webSiteDao = new WebSiteDAO();
	private DownloadTaskDAO downloadTaskDao = new DownloadTaskDAO();
	//private ParseTplItemDAO parseTplItemDao = new ParseTplItemDAO();
	private DBManagerDAO dbManagerDao = new DBManagerDAO();
	
	public boolean isAccessDenied() {
		return accessDenied;
	}

	public void setAccessDenied(boolean accessDenied) {
		this.accessDenied = accessDenied;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	
	
	public BrowserRunner() {

	}
	
	public void resetBrowser() throws Exception
	{
		if(!ClassTool.isNullObj(this.driver))
		{
			this.driver.quit();
			this.driver = null;
		}
	}
	
	private void initBrowserByType(String browserType) throws Exception
	{
		if(driver==null)
		{
			DesiredCapabilities dc=new DesiredCapabilities();
			//this can avoid browse page throw alert exception
	        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
	            
			if(Constants.WEB_DRIVER_BROWSER_TYPE_FIREFOX_10.equals(browserType))
			{
				FirefoxProfile fireFoxProfile = new FirefoxProfile();
	            //Disable images
	            //Disable CSS
				//fireFoxProfile.setPreference("permissions.default.stylesheet", 2);
	            //fireFoxProfile.setPreference("permissions.default.image", 2);
	            //Disable flash
	            fireFoxProfile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", "false");
	            fireFoxProfile.setPreference("webdriver.load.strategy", "fast");
	            fireFoxProfile.setPreference("dom.max_chrome_script_run_time", "6000");
	            fireFoxProfile.setPreference("dom.max_script_run_time", "6000");
	            //fireFoxProfile.setPreference("plugin.scan.plid.all", "false");
	            //Set the modified profile while creating the browser object 
	            	           
	            driver = new FirefoxDriver(null,fireFoxProfile,dc);
	            LogTool.logText("Create a firefox web driver browser");
			}else if(Constants.WEB_DRIVER_BROWSER_TYPE_HTML_UNIT.equals(browserType))
			{
	            HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver();
	            htmlUnitDriver.setJavascriptEnabled(false);
	            driver = htmlUnitDriver;
	            LogTool.logText("Create a html unit web driver browser");
			}else if(Constants.WEB_DRIVER_BROWSER_TYPE_CHROME.equals(browserType))
			{
				System.setProperty("webdriver.chrome.driver", ConfigTool.getDBMQConfig().getChromeDriverPath());
	            driver = new ChromeDriver(dc);
	            LogTool.logText("Create a chrome web driver browser");
			}
		}
	}

	/*
	 * return duration time in every step in browse page function
	 * */
	public String browsePage(DownloadTaskDTO taskDto, WebSiteDTO webSiteDto) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();
		String realPageUrl = taskDto.getPageUrl();
		Exception retEx = null;
		try
		{			
			//try only open one firefox browser and open new page in tab.
			//first need to set open new page in tab config in Firefox.
			//then open new page and do not quit webdriver when user stop one download thread.
			if(ClassTool.isNullObj(webSiteDto))
			{
				webSiteDto = webSiteDao.getWebSiteById(taskDto.getSiteId());
				getDltaTimeTool.getDeltaTime("Browse page get web site dto=null", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
			}
			
			LogTool.debugText("Start browser page");
			
			if(webSiteDto!=null)
			{
				  LogTool.debugText("Start init browser");
				  this.initBrowserByType(webSiteDto.getBrowserType());
				  LogTool.debugText("End init browser");
				  getDltaTimeTool.getDeltaTime("InitBrowserByType", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);

				  //login to web site if this web site need login
			      if(webSiteDto.isNeedLogin())
			      {
			       	
			      }
			       
			      if(!Constants.WEB_DRIVER_BROWSER_TYPE_HTML_UNIT.equals(webSiteDto.getBrowserType()))
			      {
				      //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				      driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			      }
			      
			      boolean ifBrowserUnreach = false;			      
			      //get page
			      try
			      {
			    	  driver.get(taskDto.getPageUrl());			    	  
				      getDltaTimeTool.getDeltaTime("Get web page", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
				      //this.waitForPageLoaded(driver);
				      
				      if(this.checkAccessDenied(webSiteDto))
				      {
				    	  this.setAccessDenied(true);
				    	  getDltaTimeTool.getDeltaTime("Access denied return", retBuf, 0, false);
				    	  return retBuf.toString();
				      }
				      getDltaTimeTool.getDeltaTime("Pass access denied", retBuf, 0, false);
				      
				      LogTool.debugText(retBuf.toString());	
			      }catch(Exception ex)
			      {
			    	  if(ex instanceof TimeoutException)
			    	  {
			    		  LogTool.logText("Load page time out url = " + taskDto.getPageUrl());
			    	  }else if(ex instanceof UnhandledAlertException)
			    	  {
			    		  if(!StringTool.isEmpty(webSiteDto.getAlertTextToResetDTask()))
			    		  {
			    			  if(this.ifResetAlertText(webSiteDto.getAlertTextToResetDTask()))
			    			  {
			    				  throw new ResetDownloadTaskException("UnhandledAlertException occured, need to reset this download task.");
			    			  }
			    		  }else
			    		  {
			    			  throw new ResetDownloadTaskException("UnhandledAlertException occured, need to reset this download task.");
			    		  }
			    	  }else
			    	  {
				    	  LogTool.logText("Get page exception, exception class name = " + ex.getClass().getName());
				    	  LogTool.logText("Page url = " + taskDto.getPageUrl());
				    	  LogTool.logError(ex);
				    	  
				    	  ifBrowserUnreach = ifBrowserUnreach(ex);
				    	  if(ifBrowserUnreach)
				    	  {
				    		  throw ex;
				    	  }
			    	  }
			      }
		      
    
			      Exception throwEx = null;
			      try
			      {
			    	  realPageUrl = this.getRealPageUrl(taskDto);
			    	  
				      this.scrollDownTheWholePage(webSiteDto, realPageUrl);

			    	  //first to check whether this page is content page or not
			    	  boolean isContentPage = taskDto.isIfContentPage();
			    	  
			    	  /*check whether this link is really a content page or not in run time. Because some content page url redirect to other url in run time
			    	  *for example item.taobao.com maybe redirect to detail.tmall.com some time
			    	  */
			    	  if(isContentPage)
			    	  {
			    		  isContentPage = this.checkIsContentPage(this.driver, webSiteDto);
			    	  }
			    	  			    	  
					  //if content page then parse it
				      if(isContentPage)
				      {
				    	  taskDto.setIfContentPage(true);
				    	  LogTool.debugText("Start parse content page data");  
				    	  //call parse page component to parse content page and save parsed out data into db. 
				       	  String parseContentDuration = this.parseContentPage(driver, webSiteDto, taskDto, realPageUrl);
	  			          getDltaTimeTool.getDeltaTime("parseContentPage total", retBuf, 0, false);
						  retBuf.append(parseContentDuration);
					      LogTool.debugText(retBuf.toString());
					      LogTool.debugText("End parse content page data");
				      }else
				      {
				    	  taskDto.setIfContentPage(false);
				      }
			      }catch(Exception ex)
			      {
			    	  LogTool.logError(ex);
			    	  throwEx = ex;
			    	  
			    	  ifBrowserUnreach = ifBrowserUnreach(ex);
			      }finally
			      {
			    	  if(!ifBrowserUnreach && !taskDto.isIfContentPage())
			    	  {
			    		  LogTool.debugText("Start parse url link in page");
						  String parseUrlLinkDuration = this.parseUrlLinkInPage(driver, webSiteDto, taskDto, realPageUrl);	  			      					  
						  retBuf.append(parseUrlLinkDuration);
						  getDltaTimeTool.getDeltaTime("parseUrlLinkInPage total", retBuf, 0, false);
					      LogTool.debugText(retBuf.toString());
					      LogTool.debugText("End parse url link in page");
			    	  }
			    	  if(throwEx!=null)
			    	  {
			    	  	  throw throwEx;
			    	  }
			      }
			      
			}else
			{
				taskDto.setMessage("Can not get website related info when execute task, task id: " + taskDto.getId() + ", download page url: " + taskDto.getPageUrl());
				taskDto.resetApplyTime("Can not get website related info when execute task");
				this.downloadTaskDao.saveDownloadTask(taskDto);
   		        getDltaTimeTool.getDeltaTime("Can not get website related dto save task back to db", retBuf, 0, false);
			}
		}catch(MySQLIntegrityConstraintViolationException ex)
		{
			LogTool.logText("Url exist: " + taskDto.getPageUrl());
		}catch(Exception ex)
		{
			//LogTool.logError(ex);
			retEx = ex;
			if(this.ifBrowserUnreach(ex))
			{
				//LogTool.logText("Browser unreach.");
				//LogTool.logError(ex);
				
				//because this task not finished, so reset it's apply_time to '' for next time retrieve.			
				taskDto.resetApplyTime("Browser unreach, " + ex.getMessage());
			    getDltaTimeTool.getDeltaTime("Browser unreach " + ex.getMessage() + " save task back to db", retBuf, 0, false);
			}
		}finally
		{
			if(retEx!=null)
			{
				LogTool.logText("Exception occured when download page : " + taskDto.getPageUrl());
				LogTool.logText("Exception class name : " + retEx.getClass().getName());
				LogTool.logText("Duration info : " + retBuf.toString());
				throw retEx;
			}
			return retBuf.toString();
		}
	}
	

	/*
	 * get download page real url
	 * */
	private String getRealPageUrl(DownloadTaskDTO taskDto) throws Exception
	{
		String realUrl = "";
		try
		{
  		    LogTool.debugText("Start get page real url");
  		    realUrl = this.getCurrentWebPageUrlByJS();
			LogTool.debugText("realUrl = " + realUrl);			
		}catch(Exception ex)
		{
			LogTool.debugText("Exception occured and realUrl in parseUrlLinkInPage = " + realUrl);			
			LogTool.debugError(ex);
			if(this.ifBrowserUnreach(ex))
			{
				throw ex;
			}
		}
		
		if(StringTool.isEmpty(realUrl))
		{
			realUrl = taskDto.getPageUrl();
		}
		
		return realUrl;
	}
	
	
	/*
	 *scroll down the whole page, because some part of the page will not be be visible if not scroll down.
	 * */
	private void scrollDownTheWholePage(WebSiteDTO webSiteDto, String pageUrl) throws Exception
	{
		boolean needScroll = false;
		if(!ClassTool.isNullObj(webSiteDto))
		{
			String cha = webSiteDto.getNeedScrollPageUrlCharactor();
			String notCha = webSiteDto.getNotNeedScrollPageUrlCharactor();
			
			if(!StringTool.isEmpty(cha) && StringTool.isEmpty(notCha))
			{
				if("all".equalsIgnoreCase(cha))
				{
					needScroll = true;	
				}else
				{
					if(StringTool.isStringLikeExistInArray(pageUrl, cha.split(Constants.SEPERATOR_SEMICOLON)))
					{
						needScroll = true;
					}else
					{
						needScroll = false;
					}
				}
			}else if(StringTool.isEmpty(cha) && !StringTool.isEmpty(notCha))
			{
				if("all".equalsIgnoreCase(notCha))
				{
					needScroll = false;	
				}else
				{
					if(StringTool.isStringLikeExistInArray(pageUrl, notCha.split(Constants.SEPERATOR_SEMICOLON)))
					{
						needScroll = false;
					}else
					{
						needScroll = true;
					}
				}
			}else if(!StringTool.isEmpty(cha) && !StringTool.isEmpty(notCha))
			{
				if("all".equalsIgnoreCase(cha) && !"all".equalsIgnoreCase(notCha))
				{
					needScroll = true;
				}else if(!"all".equalsIgnoreCase(cha) && "all".equalsIgnoreCase(notCha))
				{
					needScroll = false;
				}else if(!"all".equalsIgnoreCase(cha) && !"all".equalsIgnoreCase(notCha))
				{
					if(StringTool.isStringLikeExistInArray(pageUrl, cha.split(Constants.SEPERATOR_SEMICOLON)))
					{
						needScroll = true;
					}else
					{
						needScroll = false;
					}
					
					if(!StringTool.isStringLikeExistInArray(pageUrl, notCha.split(Constants.SEPERATOR_SEMICOLON)))
					{
						needScroll = true;
					}else
					{
						needScroll = false;
					}
				}
			}
		}	
		
		if(needScroll)
		{
		    JavascriptExecutor js = (JavascriptExecutor)this.driver;
		    long scrollHeightLast = -1;
		    long scrollHeight = -1;
		   
		    
		    LogTool.debugText("**************************Start scroll down, pageUrl = " + pageUrl + "*******************************");
		    
		    int scrollDownCount = 1;
		    while(true)
		    {
		    	Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND);
			    Object obj = js.executeScript("window.scrollTo(0, document.body.scrollHeight); return document.body.scrollHeight");
			    	
			    if(obj instanceof Long)
			    {
			    	scrollHeight = ((Long)obj).longValue();
			    }
			    
			    if(scrollHeightLast==scrollHeight)
			    {
			    	LogTool.debugText("**************************End scroll down, pageUrl = " + pageUrl + "*******************************");
			    	break;
			    }
			    scrollHeightLast = scrollHeight;
			    
			    LogTool.debugText("Scroll down count = " + scrollDownCount + " , scrollHeightLast = " + scrollHeightLast + " , scrollHeight = " + scrollHeight);
			    
			    scrollDownCount++;
		    }	
		}
	}
	
	
	public String getCurrentWebPageUrlByJS()
	{
		JavascriptExecutor js = (JavascriptExecutor)this.driver;
	    String url = (String)js.executeScript("return document.location.href");
	    return url;		
	}
	
	
	public boolean ifBrowserUnreach(Exception ex)
	{
		if(ex instanceof SessionNotCreatedException || ex instanceof SessionNotFoundException || ex instanceof UnreachableBrowserException || ex instanceof ResetDownloadTaskException)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	
	/*
	 * parse content page and save parsed out content into data table.
	 * */
	private String parseContentPage(WebDriver driver, WebSiteDTO webSiteDto, DownloadTaskDTO parentTaskDto, String realPageUrl) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();

		List<ParseTplItemDTO> parseItemList = webSiteDto.getParseItemList();
		
		DBTableDTO dataTableDto = new DBTableDTO();
		
		if(!ClassTool.isNullObj(parseItemList))
		{
			StringBuffer errBuf = new StringBuffer();						
			int size = parseItemList.size();
			for(int i=0;i<size;i++)
			{
				ParseTplItemDTO parseTplItemDto = parseItemList.get(i);
				try
				{
					if("".equals(dataTableDto.getTableName()))
					{
						dataTableDto.setTableName(parseTplItemDto.getSaveToTable());
					}
					
					DBTableColumnDTO columnDto = new DBTableColumnDTO();
					columnDto.setTableName(parseTplItemDto.getSaveToTable());
					columnDto.setColumnName(parseTplItemDto.getSaveToColumn());	
					columnDto.setIfCheckRepeatColumn(parseTplItemDto.isIfCheckRepeatColumn());
					columnDto.setColumnType(parseTplItemDto.getDataType());
					
					String eleData = "";
					
					if(parseTplItemDto.isPageUrlAsValue())
					{
						eleData = parentTaskDto.getPageUrl();
						//LogTool.debugText("Save url as data value");
					}else
					{
						eleData = this.parseElementValue(parseTplItemDto, realPageUrl);
						//LogTool.debugText("Parsed out data value = " + eleData);
					}
					
					eleData = StringTool.isEmpty(eleData, "");
					if(!StringTool.isEmpty(eleData) && !StringTool.isEmpty(parseTplItemDto.getSrcRegExp()))
					{
						eleData = this.processRegExpValueReplace(eleData, parseTplItemDto);
						LogTool.debugText("After run regexp on data value = " + eleData);
					}
					
					eleData = StringTool.isEmpty(eleData, "");
										
					//If data is number type and numberMultiplyBy has been set then calculate it.
					if(Constants.DATA_TYPE_NUMBER.equalsIgnoreCase(parseTplItemDto.getDataType()) &&  StringTool.isNumeric(eleData))
					{
						if(StringTool.isNumeric(parseTplItemDto.getNumberMultiplyBy()))
						{
							eleData = String.valueOf(Double.parseDouble(eleData)*Double.parseDouble(parseTplItemDto.getNumberMultiplyBy()));
						}
					}else if(Constants.DATA_TYPE_BOOLEAN.equalsIgnoreCase(parseTplItemDto.getDataType()))
					{
						//if not correct web element then set eleData to "", then will set it to false when insert it to db.
						if(!this.isCorrectWebElement(eleData, parseTplItemDto.getCharactor(), parseTplItemDto.getNotCharactor()))
						{
							eleData = "";
						}
					}
					
					columnDto.setColumnValue(eleData);
					dataTableDto.getColumnList().add(columnDto);					
				}catch(Exception ex)
				{
					LogTool.logError(ex);
					if(this.ifBrowserUnreach(ex))
					{
						throw ex;
					}
					
					errBuf.append("<br/><br/>Exception occured when parse table = ");
					errBuf.append(parseTplItemDto.getSaveToTable());
					errBuf.append("; column = ");
					errBuf.append(parseTplItemDto.getSaveToColumn());
					errBuf.append("; <br/>byType = ");
					errBuf.append(parseTplItemDto.getByEleType());
					errBuf.append("; <br/>byValue = ");
					errBuf.append(parseTplItemDto.getByEleVal());
					errBuf.append("; <br/>byTagAttribute = ");
					errBuf.append(parseTplItemDto.getByTagAttribute());
					errBuf.append("; <br/>byTagAttributeValuePlus = ");
					errBuf.append(parseTplItemDto.isByTagAttributeValuePlus());					
					errBuf.append("; <br/>parseValueRegExp = ");
					errBuf.append(parseTplItemDto.getParseValueRegExp());
					errBuf.append("; <br/>parseValueString = ");
					errBuf.append(parseTplItemDto.getParseValueString());
					errBuf.append("; <br/>SrcRegExp = ");
					errBuf.append(parseTplItemDto.getSrcRegExp());
					errBuf.append("; <br/>DestRegExp = ");
					errBuf.append(parseTplItemDto.getDestRegExp());
					errBuf.append("; <br/>Description = ");
					errBuf.append(parseTplItemDto.getColumnDesc());
					errBuf.append(";  <br/><br/>exception class name<br/><br/>");
					errBuf.append(ex.getClass().getName());
					errBuf.append(";  <br/><br/>error message<br/><br/>");
					errBuf.append(ex.getMessage());
					errBuf.append("<br/><br/>************************************************ ");
				}
			}			
			
			//each data table must has a column 'download_task_parent_page_url', for search and manipulate data in data table by data url
			DBTableColumnDTO downloadTaskParentPageUrlColumnDto = new DBTableColumnDTO();
			downloadTaskParentPageUrlColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_PARENT_PAGE_URL);			
			downloadTaskParentPageUrlColumnDto.setColumnType(Constants.DATA_TYPE_STRING);
			downloadTaskParentPageUrlColumnDto.setColumnValue(parentTaskDto.getParentPageUrl());
			dataTableDto.getColumnList().add(downloadTaskParentPageUrlColumnDto);
			
			//each data table must has a column 'download_task_page_url', for search and manipulate data in data table by data url
			DBTableColumnDTO downloadTaskPageUrlColumnDto = new DBTableColumnDTO();
			downloadTaskPageUrlColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_PAGE_URL);			
			downloadTaskPageUrlColumnDto.setColumnType(Constants.DATA_TYPE_STRING);
			downloadTaskPageUrlColumnDto.setColumnValue(parentTaskDto.getPageUrl());
			dataTableDto.getColumnList().add(downloadTaskPageUrlColumnDto);
			
			//each data table must has a column 'download_task_id'
			DBTableColumnDTO downloadTaskIdColumnDto = new DBTableColumnDTO();
			downloadTaskIdColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_ID);			
			downloadTaskIdColumnDto.setColumnType(Constants.DATA_TYPE_NUMBER);
			downloadTaskIdColumnDto.setColumnValue(String.valueOf(parentTaskDto.getId()));
			dataTableDto.getColumnList().add(downloadTaskIdColumnDto);
			
			//each data table must has a column 'download_task_level'
			DBTableColumnDTO downloadTaskLevelColumnDto = new DBTableColumnDTO();
			downloadTaskLevelColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_LEVEL);			
			downloadTaskLevelColumnDto.setColumnType(Constants.DATA_TYPE_NUMBER);
			downloadTaskLevelColumnDto.setColumnValue(String.valueOf(parentTaskDto.getTaskLevel()));
			dataTableDto.getColumnList().add(downloadTaskLevelColumnDto);
			
			//each data table must has a column 'download_task_data_parse_time'
			DBTableColumnDTO downloadTaskDataParseTimeColumnDto = new DBTableColumnDTO();
			downloadTaskDataParseTimeColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_DATA_PARSE_TIME);			
			downloadTaskDataParseTimeColumnDto.setColumnType(Constants.DATA_TYPE_STRING);
			downloadTaskDataParseTimeColumnDto.setColumnValue(DateTool.getNowString());
			dataTableDto.getColumnList().add(downloadTaskDataParseTimeColumnDto);
			
			//each data table must has a column 'download_task_data_parse_time_number'
			DBTableColumnDTO downloadTaskDataParseTimeNumberColumnDto = new DBTableColumnDTO();
			downloadTaskDataParseTimeNumberColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_DATA_PARSE_TIME_NUMBER);			
			downloadTaskDataParseTimeNumberColumnDto.setColumnType(Constants.DATA_TYPE_NUMBER);
			downloadTaskDataParseTimeNumberColumnDto.setColumnValue(String.valueOf(System.currentTimeMillis()));
			dataTableDto.getColumnList().add(downloadTaskDataParseTimeNumberColumnDto);
			
			//each data table must has a column 'download_task_useless_content_page'
			DBTableColumnDTO downloadTaskUselessContentPageColumnDto = new DBTableColumnDTO();
			downloadTaskUselessContentPageColumnDto.setColumnName(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_USELESS_CONTENT_PAGE);			
			downloadTaskUselessContentPageColumnDto.setColumnType(Constants.DATA_TYPE_NUMBER);
			downloadTaskUselessContentPageColumnDto.setColumnValue(String.valueOf(parentTaskDto.getUselessContentPage()));
			dataTableDto.getColumnList().add(downloadTaskUselessContentPageColumnDto);

			getDltaTimeTool.getDeltaTime("parse data", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
			
			String saveDataToDBDuration = this.dbManagerDao.saveDataTable(dataTableDto, parseItemList);
			getDltaTimeTool.getDeltaTime("save data to db total", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
			retBuf.append(saveDataToDBDuration);
			LogTool.debugText("Data value saved");

			if(errBuf.length()>0)
			{
				ParsePageException newEx = new ParsePageException(errBuf.toString());
				LogTool.logError(newEx);
				throw newEx;
			}
		}
		
		return retBuf.toString();

	}
	
	
	private String parseElementValue(ParseTplItemDTO parseTplItemDto, String realPageUrl) throws Exception
	{
		String eleData = "";		
		eleData = this.parseElementValueByWebDriver(parseTplItemDto, realPageUrl);
		if(StringTool.isEmpty(eleData))
		{
			//eleData = this.parseElementValueByJavaScript(parseTplItemDto);
			if(!StringTool.isEmpty(parseTplItemDto.getDefaultVal()))
			{
				eleData = parseTplItemDto.getDefaultVal();
			}
		}		
		return eleData;
	}
	
	
	private String parseElementValueByWebDriver(ParseTplItemDTO parseTplItemDto, String realPageUrl) throws Exception
	{
		String eleData = "";
		boolean useThisSetting = true;
		String useThisSettingUrlCha = parseTplItemDto.getUseThisSettingUrlCharactor();
		if(StringTool.isEmpty(useThisSettingUrlCha))
		{
			useThisSetting = true;
		}else
		{
			if(StringTool.isStringLikeExistInArray(realPageUrl, useThisSettingUrlCha.split(Constants.SEPERATOR_SEMICOLON)))
			{
				useThisSetting = true;	
			}else
			{
				useThisSetting = false;	
			}
		}
		
		if(useThisSetting)
		{
			this.parseElementValueTakeAction(parseTplItemDto.getParseItemActionList());
			
			List<WebElement> eleList = this.getElementsListBy(driver, parseTplItemDto.getByEleType(), parseTplItemDto.getByEleVal(), parseTplItemDto.getSaveToColumn());	
			
			if(!ClassTool.isListEmpty(eleList))
			{
				
				if(StringTool.isEmpty(parseTplItemDto.getByTagAttribute()))
				{
					String getDataFrom = Constants.WEB_ELEMENT_GET_DATA_FROM_INNER_HTML;
					if(parseTplItemDto.isIfDirectGetText())
					{
						getDataFrom = Constants.WEB_ELEMENT_GET_DATA_FROM_TEXT;
					}
					eleData = this.getCorrectWebElementTextInList(eleList, parseTplItemDto.getCharactor(), parseTplItemDto.getNotCharactor(), getDataFrom);
				}else
				{
					eleData = this.getCorrectWebElementAttributeInList(eleList, parseTplItemDto.getByTagAttribute(), parseTplItemDto.isByTagAttributeValuePlus(), parseTplItemDto.getCharactor(), parseTplItemDto.getNotCharactor());
				}
				
				if(!StringTool.isEmpty(eleData))
				{
					if(!StringTool.isEmpty(parseTplItemDto.getParseValueRegExp()))
					{
						String parseValueRegExpArr[] = parseTplItemDto.getParseValueRegExp().split(Constants.SEPERATOR_COMPLEX);
						eleData = this.parseEleDataByValueRegExp(eleData, parseValueRegExpArr, true);
					}else if(!StringTool.isEmpty(parseTplItemDto.getParseValueString()))
					{
						String parseValueStringArr[] = parseTplItemDto.getParseValueString().split(Constants.SEPERATOR_COMPLEX);
						eleData = this.parseEleDataByParseString(eleData, parseValueStringArr);
					}	
				}
			}	
		}
		return eleData;
	}
	
	private void parseElementValueTakeAction(List<ParseTplItemActionDTO> parseItemActionList) throws Exception
	{
		try
		{
			if(!ClassTool.isListEmpty(parseItemActionList))
			{
				LogTool.debugText("Start parseElementValueTakeAction");
				int size = parseItemActionList.size();
				for(int i=0;i<size;i++)
				{
					ParseTplItemActionDTO actionDto = parseItemActionList.get(i);
					if(!StringTool.isEmpty(actionDto.getByEleType()) && !StringTool.isEmpty(actionDto.getByEleType()))
					{
						By by = this.getSearchByCondition(actionDto.getByEleType(), actionDto.getByEleType());
						List<WebElement> eleList = this.driver.findElements(by);
						if(!ClassTool.isListEmpty(eleList))
						{
							WebElement ele = eleList.get(0);
							if(Constants.JAVA_SCRIPT_ACTION_CLICK.equalsIgnoreCase(actionDto.getByEleAction()))
							{							
								ele.click();
								LogTool.debugText(actionDto.getByEleType() + " , " + actionDto.getByEleVal() + " " + actionDto.getByEleAction());
							}
						}
					}				
				}
				LogTool.debugText("End parseElementValueTakeAction");
			}
		}catch(Exception ex)
		{
			if(this.ifBrowserUnreach(ex))
			{
				throw ex;
			}
		}
	}
	
	private String getCorrectWebElementTextInList(List<WebElement> eleList, String charactor, String notCharactor, String getWebElementDataFrom)
	{
		String eleData = "";
		
		if(!ClassTool.isNullObj(eleList))
		{
			int size = eleList.size();
			for(int i=0;i<size;i++)
			{
				WebElement webEle = eleList.get(i);
				
				if(Constants.WEB_ELEMENT_GET_DATA_FROM_TEXT.equalsIgnoreCase(getWebElementDataFrom))
				{
					eleData = webEle.getText();
				}else if(Constants.WEB_ELEMENT_GET_DATA_FROM_INNER_HTML.equalsIgnoreCase(getWebElementDataFrom))
				{
					eleData = webEle.getAttribute("innerHTML");	
				}else if(Constants.WEB_ELEMENT_GET_DATA_FROM_OUTTER_HTML.equalsIgnoreCase(getWebElementDataFrom))
				{
					eleData = webEle.getAttribute("outerHTML");	
				}								
				
				//verify wheher this eleData is correct or not
				if(!StringTool.isEmpty(eleData))
				{
					//LogTool.logText(logTxtBuf.toString());
					if(this.isCorrectWebElement(eleData, charactor, notCharactor ))
					{
						break;
					}
				}				
			}
		}
		return eleData;
	}


	private String parseEleDataByParseString(String eleData, String parseValueStringArr[]) //throws Exception
	{
		String ret = "";
		if(!ClassTool.isNullObj(parseValueStringArr))
		{
			String tmpParseOut = eleData;
			int len = parseValueStringArr.length;
			for(int i=0;i<len;i++)
			{
				String parseValueString = parseValueStringArr[i];
				String parseStringPar[] = parseValueString.split(Constants.SEPERATOR_COMPLEX1);
				if(!ClassTool.isNullObj(parseStringPar) && parseStringPar.length==2)
				{
					String startStr = parseStringPar[0];
					String endStr = parseStringPar[1];
					tmpParseOut = StringTool.getStringWithStartEndString(tmpParseOut, startStr, endStr);
					if(StringTool.isEmpty(tmpParseOut))
					{
						break;
					}
					ret = tmpParseOut;
				}
			}			
		}
		return ret;
	}
	
	private String parseEleDataByValueRegExp(String eleData, String parseValueRegExpArr[], boolean caseInsensitive) //throws Exception
	{
		String ret = "";
		if(!ClassTool.isNullObj(parseValueRegExpArr))
		{
			int len = parseValueRegExpArr.length;
			for(int i=0;i<len;i++)
			{
				String regExp = parseValueRegExpArr[i];
				List<String> retList = StringTool.runRegExpToGetStringList(eleData, regExp, caseInsensitive);
				if(!ClassTool.isListEmpty(retList))
				{
					ret = retList.get(0);
					break;
				}
			}
		}
		return ret;
	}
	
	private String getCorrectWebElementAttributeInList(List<WebElement> eleList, String attributeNames, boolean attributeValuePlus, String charactor, String notCharactor) throws Exception
	{
		String eleData = "";
		if(!ClassTool.isNullObj(eleList))
		{
			int size = eleList.size();
			for(int i=0;i<size;i++)
			{
				WebElement webEle = eleList.get(i);
				eleData = this.getWebElementAttributeValues(webEle, attributeNames, attributeValuePlus);
				
				//verify whther this eleData is correct or not
				if(!StringTool.isEmpty(eleData))
				{
					if(this.isCorrectWebElement(eleData, charactor, notCharactor))
					{
						break;
					}
				}				
			}

		}
		return eleData;
	}
	
	
	private List<WebElement> getElementsListBy(WebDriver driver, String byEleType, String byEleVal, String saveToColumn) throws Exception
	{
		List<WebElement> ret = new ArrayList<WebElement>();
		
		if(!StringTool.isEmpty(byEleType) && !StringTool.isEmpty(byEleVal))
		{
			String byValArr[] = byEleVal.split(Constants.SEPERATOR_SEMICOLON);
			if(!ClassTool.isNullObj(byValArr))
			{
				int len = byValArr.length;
				for(int i=0;i<len;i++)
				{
					String byVal = byValArr[i];
					if(!StringTool.isEmpty(byVal))
					{
						By searchBy = this.getSearchByCondition(byEleType, byVal);
						
						if(!ClassTool.isNullObj(searchBy))
						{
							StringBuffer debugTextBuf = new StringBuffer();
							debugTextBuf.append("Start driver find elements by ");
							debugTextBuf.append(byEleType);
							debugTextBuf.append(" = ");
							debugTextBuf.append(byVal);
							
							if(!StringTool.isEmpty(saveToColumn))
							{
								debugTextBuf.append(" , column name = ");
								debugTextBuf.append(saveToColumn);								
							}
							LogTool.debugText(debugTextBuf.toString());			
							
							List<WebElement> eleListTmp = driver.findElements(searchBy);
							
							if(!ClassTool.isListEmpty(eleListTmp))
							{
								ret.addAll(eleListTmp);
							}
						}
					}
				}
			}
		}		
		return ret;
	}
	

	
	
	public void waitForPageLoaded(WebDriver driver) {

	    ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
	       public Boolean apply(WebDriver driver) {
	         return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	       }
	     };

	     Wait<WebDriver> wait = new WebDriverWait(driver,30);
	     try {
	        wait.until(expectation);
	     } catch(Throwable error) {
	    	Exception ex = new Exception("Timeout waiting for Page Load Request to complete." + error.getMessage()); 
	        LogTool.logError( ex);
	     }
	 } 
	
	
	private String processRegExpValueReplace(String data, ParseTplItemDTO parseTplItemDto) throws Exception
	{
		if(StringTool.isEmpty(data.trim()))
		{
			return data.trim();
		}
		
		String srcRegExp = parseTplItemDto.getSrcRegExp();
		String destRegExp = parseTplItemDto.getDestRegExp();
		String srcRegExpArr[] = srcRegExp.split(Constants.SEPERATOR_SEMICOLON);
		String destRegExpArr[] = destRegExp.split(Constants.SEPERATOR_SEMICOLON);
		
		String newData = data;
		if(!ClassTool.isNullObj(srcRegExpArr))
		{
			int srcLen = srcRegExpArr.length;
			for(int i=0;i<srcLen;i++)
			{
				String srcRegExpTmp = srcRegExpArr[i];
				String destRegExpTmp = "";

				if(destRegExpArr.length>i)
				{
					destRegExpTmp = destRegExpArr[i];
				}
				
			    newData = StringTool.runRegExpToReplace(newData, srcRegExpTmp, destRegExpTmp);
				
			    //如果这些替换为or关系，则有一次替换成功就退出，
				if(!parseTplItemDto.isRegExpItemRelation())
				{
					//因为文本字符串有变化，则代表有一次替换成功
					if(!newData.equals(data))
					{
						break;
					}
				}
			}
		}
		return newData;
	}
	
	private String getWebElementAttributeValues(WebElement webEle, String attributeNames, boolean attributeValuePlus) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		if(!StringTool.isEmpty(attributeNames))
		{
			String attrNameArr[] = attributeNames.split(Constants.SEPERATOR_SEMICOLON);
			if(!ClassTool.isNullObj(attrNameArr))
			{
				int size = attrNameArr.length;
				for(int i=0;i<size;i++)
				{
					try
					{
						String attrName = attrNameArr[i];
						String attrValue = webEle.getAttribute(attrName);
						if(!StringTool.isEmpty(attrValue))
						{
							retBuf.append(attrValue);							
							if(!attributeValuePlus)
							{
								break;
							}							
							retBuf.append(Constants.SEPERATOR_SPACE);
						}
					}catch(Exception ex)
					{
						if(this.ifBrowserUnreach(ex))
						{
							throw ex;
						}else
						{
							LogTool.logError(ex);
						}
					}
				}
			}
		}	
		return retBuf.toString();
	}
	
	
	
	private boolean isCorrectWebElement(String eleData, String cha, String notCha)
	{
		boolean ret = false;
		boolean chaEmpty = StringTool.isEmpty(cha);
		boolean notChaEmpty = StringTool.isEmpty(notCha);
		if(chaEmpty && notChaEmpty)
		{
			ret = true;
		}else if(!chaEmpty && notChaEmpty)
		{
			if(StringTool.ifMatchStringCharactor(eleData,cha))
			{
				ret = true;
			}
		}else if(chaEmpty && !notChaEmpty)
		{
			if(!StringTool.ifMatchStringCharactor(eleData,notCha))
			{
				ret = true;
			}
		}else if(!chaEmpty && !notChaEmpty)
		{
			if(StringTool.ifMatchStringCharactor(eleData,cha) && !StringTool.ifMatchStringCharactor(eleData,notCha))
			{
				ret = true;
			}
		}
				
		return ret;
	}
	
	private void clickWebElementsBeforeParseUrl(WebDriver driver, String clickEleXPathBeforeParseUrlStr, String realPageUrl) throws Exception
	{
		if(!StringTool.isEmpty(clickEleXPathBeforeParseUrlStr))
		{
			Map<String, String[]> urlChaXPathMap = StringTool.parseStringReturnStringListMapCommonSeperator(clickEleXPathBeforeParseUrlStr);
			
			if(urlChaXPathMap!=null)
			{
				Set<String> keys = urlChaXPathMap.keySet();
				Iterator it = keys.iterator();
				while(it.hasNext())
				{
					String urlCha = (String)it.next();
					if(realPageUrl.indexOf(urlCha)!=-1)
					{
						String xpathArr[] = urlChaXPathMap.get(urlCha);
						if(xpathArr!=null)
						{
							int size = xpathArr.length;
							for(int i=0;i<size;i++)
							{
								String xpath = xpathArr[i];
								By byCondition = this.getSearchByCondition(Constants.WEB_DRIVER_SEARCH_BY_TYPE_XPATH, xpath);
							    if(byCondition!=null)
							    {
								   	List<WebElement> eleList = driver.findElements(byCondition);
								   	if(!ClassTool.isListEmpty(eleList))
								   	{
								   		int eleListSize = eleList.size();
										for(int k=0;k<eleListSize;k++)
										{
											try
											{
												WebElement ele = eleList.get(k);
												if(ele.isDisplayed() && ele.isEnabled() && ele.getSize().getWidth()>0 && ele.getSize().getHeight()>0)
												{
													ele.click();
												}
											}catch(Exception ex)
											{
												if(this.ifBrowserUnreach(ex))
												{
													throw ex;
												}else
												{
													LogTool.logError(ex);
												}
											}
								   		}
							    	}
							    }
							}
						}
					}
				}
			}
		}
	}
	
	
	/*
	 * return duration time in every step in this function
	 * */
	private String parseUrlLinkInPage(WebDriver driver, WebSiteDTO webSiteDto, DownloadTaskDTO parentTaskDto, String realPageUrl) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();

		String pageSource = "";
		try
		{
  		    LogTool.debugText("Start get page source and real url");
			pageSource = driver.getPageSource();			
			LogTool.debugText("pageSource = " + pageSource);
		}catch(Exception ex)
		{
			LogTool.debugText("Exception occured and page source in parseUrlLinkInPage = " + pageSource);
			
			LogTool.debugError(ex);
			if(this.ifBrowserUnreach(ex))
			{
				throw ex;
			}
		}
				
		List<WebSitePageLinkParseDTO> linkParseList = webSiteDto.getPageLinkParseDtoList();
		
    	if(linkParseList!=null)
    	{
    		int parsedOutUrlCount = 0;
    		int linkListSize = linkParseList.size();
    		for(int i=0;i<linkListSize;i++)
    		{
    			WebSitePageLinkParseDTO linkParseDto = linkParseList.get(i);
    			
    			this.clickWebElementsBeforeParseUrl(driver, linkParseDto.getClickEleXPathBeforeParseUrl(), realPageUrl);
    			
    			List<String> urlList = new ArrayList<String>();
    			try
    			{    			
	    			if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_TAG_NAME.equalsIgnoreCase(linkParseDto.getByEleType()))
					{
	    				if(Constants.WEB_ELEMENT_TAG_NAME_A.equalsIgnoreCase(linkParseDto.getByEleVal()))
	    				{
	    					//parsed out url link with web driver
	    					urlList = this.getUrlListByWebDriver(driver, linkParseDto);
			    			getDltaTimeTool.getDeltaTime("regexp parse new task list count " + urlList.size() + " ,<br/> HtmlTool.parseOutUrlLinkList", retBuf, 0, false);
						    LogTool.debugText(retBuf.toString());
						    
			    			if(ClassTool.isListEmpty(urlList))
			    			{
			    				//if not parsed out url with web driver then use regexp to parse url out.
			    				urlList = HtmlTool.parseOutUrlLinkList(pageSource, realPageUrl, webSiteDto, linkParseDto);			    				
				    			getDltaTimeTool.getDeltaTime("webdriver parse new task list count " + urlList.size(), retBuf, 0, false);
							    LogTool.debugText(retBuf.toString());
		
			    				if(ClassTool.isListEmpty(urlList))
			    				{
			    					LogTool.logText("Parsed out 0 url links, originalPageUrl = " + parentTaskDto.getPageUrl() + " , realPageUrl = " + realPageUrl);
			    				}
			    			}
	    				}	
					}else
					{
						if(!StringTool.isEmpty(linkParseDto.getByEleType()) && !StringTool.isEmpty(linkParseDto.getByEleVal()))
						{
							List<By> byList = this.getSearchByConditionList(linkParseDto.getByEleType(), linkParseDto.getByEleVal());						
							for(int j=0;j<byList.size();j++)
							{
					    		By byCondition = byList.get(j);
					    		if(byCondition!=null)
					    		{
						    		List<WebElement> eleList = driver.findElements(byCondition);
						    		if(!ClassTool.isListEmpty(eleList))
						    		{
							    		List<String> tmpUrlList = this.getUrlListByWebElementClick(eleList, linkParseDto, parentTaskDto, byCondition, webSiteDto, realPageUrl);
							    		getDltaTimeTool.getDeltaTime("webdriver not a tag use click parse new task list count " + urlList.size(), retBuf, 0, false);
									    LogTool.debugText(retBuf.toString());
									    
									    urlList.addAll(tmpUrlList);
						    		}
					    		}
							}
						}
					}
	    			    			
	    			parsedOutUrlCount = parsedOutUrlCount + urlList.size();
	    			
	    			String createEachTaskDuration = this.createNewTaskURL(urlList, linkParseDto, parentTaskDto, webSiteDto);
	    			getDltaTimeTool.getDeltaTime("createNewTaskURL total", retBuf, 0, false);	    				    			
	    			retBuf.append(createEachTaskDuration);
				    LogTool.debugText(retBuf.toString());
    			}catch(Exception ex)
    			{
    				parsedOutUrlCount = parsedOutUrlCount + urlList.size();
	    			
	    			String createEachTaskDuration = this.createNewTaskURL(urlList, linkParseDto, parentTaskDto, webSiteDto);
	    			getDltaTimeTool.getDeltaTime("createNewTaskURL total", retBuf, 0, false);	    				    			
	    			retBuf.append(createEachTaskDuration);
				    LogTool.debugText(retBuf.toString());
				    
    				if(this.ifBrowserUnreach(ex))
    				{
    		    		parentTaskDto.setParsedOutUrlCount(parsedOutUrlCount);    		    		
    					throw ex;
    				}
    			}    			
    		}
    		
    		parentTaskDto.setParsedOutUrlCount(parsedOutUrlCount);
    	}
    	return retBuf.toString();
	}
	
	/*
	 * byEleType: can have only one value.
	 * byEleVal: can have multiple seperated by ;
	 * */
	private List<By> getSearchByConditionList(String byEleType, String byEleVal) //throws Exception
	{
		List<By> ret = new ArrayList<By>();
		if(!StringTool.isEmpty(byEleType) && !StringTool.isEmpty(byEleVal))
		{
			String byEleValueArr[] = byEleVal.split(Constants.SEPERATOR_SEMICOLON);
			int size = byEleValueArr.length;
			for(int i=0;i<size;i++)
			{
				String byEleValueTmp = byEleValueArr[i];
			 	By byCondition = this.getSearchByCondition(byEleType, byEleValueTmp);
			 	ret.add(byCondition);
			}
		}
		return ret;
	}
	
	
	private By getSearchByCondition(String byEleType, String byEleVal) //throws Exception
	{
		By ret = null;
		if(!StringTool.isEmpty(byEleType) && !StringTool.isEmpty(byEleVal))
		{
			if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_TAG_NAME.equals(byEleType))
			{				
				ret = By.tagName(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_ID.equals(byEleType))
			{				
				ret = By.id(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_NAME.equals(byEleType))
			{				
				ret = By.name(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_CLASS_NAME.equals(byEleType))
			{				
				ret = By.className(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_CSS_SECLECTOR.equals(byEleType))
			{				
				ret = By.cssSelector(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_LINK_TEXT.equals(byEleType))
			{				
				ret = By.linkText(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_PARTIAL_LINK_TEXT.equals(byEleType))
			{				
				ret = By.partialLinkText(byEleVal);
			}else if(Constants.WEB_DRIVER_SEARCH_BY_TYPE_XPATH.equals(byEleType))
			{				
				ret = By.xpath(byEleVal);
			}
		}
		return ret;
	}
	
	
	private List<String> getUrlListByWebDriver(WebDriver driver, WebSitePageLinkParseDTO linkParseDto) throws Exception
	{
		List<String> retList = new ArrayList<String>();
		By byCondition = this.getSearchByCondition(linkParseDto.getByEleType(), linkParseDto.getByEleVal());
		if(byCondition!=null)
		{
			List<WebElement> eleList = new ArrayList<WebElement>();
			try
			{
				eleList = driver.findElements(byCondition);	
			}catch(Exception ex)
			{
				if(this.ifBrowserUnreach(ex))
				{
					throw ex;
				}
			}    		
    		
    		if(!ClassTool.isListEmpty(eleList))
    		{
    			int size = eleList.size();
    			for(int i=0;i<size;i++)
    			{
    				try
    				{
	    				WebElement ele = eleList.get(i);
	    				String url = ele.getAttribute("href");
	    				
	    				if(!StringTool.isEmpty(url))
	    				{
	    					HtmlTool.processUrlAndAddToList(url, retList, linkParseDto);
	    				}
    				}catch(Exception ex)
    				{
    					if(this.ifBrowserUnreach(ex))
    					{
    						throw ex;
    					}
    				}
    			}
    		}
		}
		return retList;
	}
	
	private List<String> getUrlListByWebElementClick(List<WebElement> eleList, WebSitePageLinkParseDTO linkParseDto, DownloadTaskDTO parentTaskDto, By byCondition, WebSiteDTO webSiteDto, String realPageUrl) throws Exception
	{
		List<String> urlList = new ArrayList<String>();

		if(eleList!=null)
		{
			int eleListSize = eleList.size();
			for(int j=0;j<eleListSize;j++)
			{
				try
				{
					WebElement ele = eleList.get(j);
					if(ele.isDisplayed() && ele.isEnabled() && ele.getSize().getWidth()>0 && ele.getSize().getHeight()>0)
					{
						ele.click();
						
						Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*5);
						
						String url = this.getCurrentWebPageUrlByJS();
			
						//Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*3);
						
						HtmlTool.processUrlAndAddToList(url, urlList, linkParseDto);
		    			
		    			if(j<(eleListSize-1))
		    			{	
			    			Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*2);
			    			try
			    			{
			    				this.driver.get(realPageUrl);
			    			}catch(Exception ex)
			    			{
				    			if(ex instanceof TimeoutException)
						    	{
				    				LogTool.logText("Load page time out url = " + parentTaskDto.getPageUrl());
						    	}else if(ex instanceof UnhandledAlertException)
						    	{
						    		if(!StringTool.isEmpty(webSiteDto.getAlertTextToResetDTask()))
						    		{
						    			if(this.ifResetAlertText(webSiteDto.getAlertTextToResetDTask()))
						    			{
						    				throw new ResetDownloadTaskException("UnhandledAlertException occured, need to reset this download task.");
						    			}
						    		}else
						    		{
						    			  throw new ResetDownloadTaskException("UnhandledAlertException occured, need to reset this download task.");
						    		}
						    	}else
						    	{
						    		throw ex;
						    	}
			    			}
			    			
			    			Thread.currentThread().sleep(Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND*5);
			    			this.scrollDownTheWholePage(webSiteDto, realPageUrl);
			    					    			
			    			eleList = this.driver.findElements(byCondition);
			    			eleListSize = eleList.size();
		    			}
					}
				}catch(Exception ex)
				{
					if(this.ifBrowserUnreach(ex))
					{
						throw ex;
					}else
					{
						LogTool.logError(ex);
					}
				}
			}
		}
		
		return urlList;
	}	
	
	private boolean ifResetAlertText(String alertText) throws Exception
	{
		boolean ret = false;
		Exception throwEx = null;
		try
		{
			Alert alertDialog = driver.switchTo().alert();
			//Get the alert text
			String realAlertText = alertDialog.getText();
			LogTool.debugText("realAlertText = " + realAlertText);
			
			if(StringTool.isStringEqualExistInArray(realAlertText, alertText.split(Constants.SEPERATOR_SEMICOLON)))
			{
				ret = true;
			}else
			{
				//Click the OK button on the alert.
				alertDialog.accept();
			}
		}catch(NoAlertPresentException ex)
		{
			LogTool.debugText("No alert found");
		}catch(Exception ex)
		{
			if(this.ifBrowserUnreach(ex))
			{
				throwEx = ex;
			}else
			{
				LogTool.logError(ex);
			}
		}finally
		{
			if(!ClassTool.isNullObj(throwEx))
			{
				throw throwEx;
			}
			return ret;
		}
	}
	
	/*
	 * check before parse content page, because some content page will redirect to other pages so need to check if config
	 * */
	private boolean checkIsContentPage(WebDriver driver, WebSiteDTO webSiteDto) throws Exception
	{
		boolean ret = true;
		Exception throwEx = null;
		try
		{
			if(!ClassTool.isNullObj(driver) && !ClassTool.isNullObj(webSiteDto))
			{
				List<WebSiteContentPageCheckDTO> list = webSiteDto.getContentPageCheckDtoList();
				if(!ClassTool.isListEmpty(list))
				{
					WebSiteContentPageCheckDTO dto = list.get(0);
					String eleData = "";
					List<WebElement> eleList = this.getElementsListBy(driver, dto.getByEleType(), dto.getByEleVal(), "");	
					
					if(!ClassTool.isListEmpty(eleList))
					{
						if(StringTool.isEmpty(dto.getByTagAttribute()))
						{
							eleData = this.getCorrectWebElementTextInList(eleList, dto.getCharactor(), dto.getNotCharactor(), Constants.WEB_ELEMENT_GET_DATA_FROM_INNER_HTML);
						}else
						{
							eleData = this.getCorrectWebElementAttributeInList(eleList, dto.getByTagAttribute(), true, dto.getCharactor(), dto.getNotCharactor());
						}
					}else
					{
						if(!StringTool.isEmpty(dto.getCharactor()))
						{
							ret = false;
						}else if(!StringTool.isEmpty(dto.getNotCharactor()))
						{
							ret = true;							
						}
						return ret;
					}
					
					if(!StringTool.isEmpty(eleData) && this.isCorrectWebElement(eleData, dto.getCharactor(), dto.getNotCharactor()))
					{
						if(!StringTool.isEmpty(dto.getParseValueRegExp()))
						{
							String parseValueRegExpArr[] = dto.getParseValueRegExp().split(Constants.SEPERATOR_COMPLEX);
							eleData = this.parseEleDataByValueRegExp(eleData, parseValueRegExpArr, true);
						}else if(!StringTool.isEmpty(dto.getParseValueString()))
						{
							String parseValueStringArr[] = dto.getParseValueString().split(Constants.SEPERATOR_COMPLEX);
							eleData = this.parseEleDataByParseString(eleData, parseValueStringArr);
						}	
					}else
					{
						eleData = "";
					}
					
					if(StringTool.isEmpty(eleData))
					{
						ret = false;
					}
				}
			}
		}catch(Exception ex)
		{
			if(this.ifBrowserUnreach(ex))
			{
				throwEx = ex;
			}else
			{
				LogTool.debugError(ex);
			}
		}finally
		{
			if(!ClassTool.isNullObj(throwEx))
			{
				throw throwEx;
			}
			return ret;
		}
	}
	
	
	private boolean checkAccessDenied(WebSiteDTO webSiteDto) throws Exception
	{
		boolean ret = false;
		if(!StringTool.isEmpty(webSiteDto.getAccessDeniedPageElementXpath()))
		{
			String xpathArr[] = webSiteDto.getAccessDeniedPageElementXpath().split(Constants.SEPERATOR_SEMICOLON);
			if(!ClassTool.isNullObj(xpathArr))
			{
				int len = xpathArr.length;
				for(int i=0;i<len;i++)
				{
					String xpath = xpathArr[i];
					if(!StringTool.isEmpty(xpath))
					{
						List<WebElement> eleList = this.getElementsListBy(driver, Constants.WEB_DRIVER_SEARCH_BY_TYPE_XPATH, xpath, "");
						
						if(!ClassTool.isListEmpty(eleList) && !StringTool.isEmpty(webSiteDto.getAccessDeniedPageElementValue()))
						{
							String valueArr[] = webSiteDto.getAccessDeniedPageElementValue().split(Constants.SEPERATOR_SEMICOLON);
							
							int size = eleList.size();
							for(int j=0;j<size;j++)
							{
								WebElement ele = eleList.get(j);
								String eleText = ele.getText();
								if(!StringTool.isEmpty(eleText))
								{
									int vLen = valueArr.length;
									for(int k=0;k<vLen;k++)
									{
										String value = valueArr[i];
										if(!StringTool.isEmpty(value))
										{
											if(eleText.indexOf(value)!=-1)
											{
												ret = true;
												return ret;
											}
										}
									}
								}
							}							
						}
					}
				}
			}
		}
		
		return ret;
	}
	
	
	/*
	 * return duration time in each step in this function
	 * */
	private String createNewTaskURL(List<String> urlList, WebSitePageLinkParseDTO linkParseDto, DownloadTaskDTO parentTaskDto, WebSiteDTO webSiteDto) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();
		
		if(!ClassTool.isListEmpty(urlList))
		{
			int size = urlList.size();
			for(int i=0;i<size;i++)
			{
				try
				{
					String url = urlList.get(i);
					this.createNewTaskURL(url, linkParseDto, parentTaskDto, webSiteDto);
					getDltaTimeTool.getDeltaTime("create single url task", retBuf, Constants.MIN_RECORD_DURATION_TIME*5, false);
				    LogTool.debugText(retBuf.toString());
				}catch(Exception ex)
				{
					LogTool.logError(ex);
				}
			}
		}
		return retBuf.toString();
	}
	
	private void createNewTaskURL(String url, WebSitePageLinkParseDTO linkParseDto, DownloadTaskDTO parentTaskDto, WebSiteDTO webSiteDto) throws Exception
	{
		boolean duplicate = false;
		int siteId = webSiteDto.getId();
		List<DownloadTaskDTO> dtList = this.downloadTaskDao.getDownloadTaskListByPageUrl(url);
		int size = dtList.size();
		for(int i=0;i<size;i++)
		{
			DownloadTaskDTO dto = dtList.get(i);
			if(dto.getSiteId()==siteId)
			{
				duplicate = true;
				break;
			}
		}
		
		if(!duplicate)
		{
			boolean ifContentPage = false;
			if(!"".equals(linkParseDto.getContentPageUrlCharactor()))
			{
				if(StringTool.ifMatchStringCharactor(url, linkParseDto.getContentPageUrlCharactor()))
				{
					ifContentPage = true;
				}
			}
					
			DownloadTaskDTO newTaskDto = new DownloadTaskDTO();
			newTaskDto.setId(-1);
			newTaskDto.setParentPageUrl(parentTaskDto.getPageUrl());
			newTaskDto.setPageUrl(url);
			newTaskDto.setSiteId(linkParseDto.getSiteId());
			newTaskDto.setIfContentPage(ifContentPage);
			newTaskDto.setInDbTime(String.valueOf(System.currentTimeMillis()));
			newTaskDto.setIfSiteTopUrl(false);
			newTaskDto.setIfTest(parentTaskDto.isIfTest());
			newTaskDto.setTaskRunDeltaTime(0);
			newTaskDto.setApplyTime("");
			
			if(!ClassTool.isNullObj(webSiteDto))
			{
				if(!this.setTaskLevel(newTaskDto, webSiteDto.getSetHighLevelTaskUrlCharactor(), Constants.TASK_LEVEL_HIGHT))
				{
					this.setTaskLevel(newTaskDto, webSiteDto.getSetMiddleLevelTaskUrlCharactor(), Constants.TASK_LEVEL_MIDDLE);
				}
			}		
			
			this.downloadTaskDao.saveDownloadTask(newTaskDto);
		}
	}
	
	
	private boolean setTaskLevel(DownloadTaskDTO taskDto, String taskLevelCharactor, int taskLevel)
	{
		boolean setTaskLevel = false;		
		String tlcArr[] = taskLevelCharactor.split(Constants.SEPERATOR_SEMICOLON);
		int size = tlcArr.length;
		for(int i=0;i<size;i++)
		{
			String tlc = tlcArr[i];
			if(taskDto.getPageUrl().indexOf(tlc)!=-1)
			{
				taskDto.setTaskLevel(taskLevel);
				setTaskLevel = true;
				break;
			}
		}
		return setTaskLevel;
	}
}
