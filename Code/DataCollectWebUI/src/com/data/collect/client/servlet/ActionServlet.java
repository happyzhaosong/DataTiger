package com.data.collect.client.servlet;

import java.io.IOException;



import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.collect.client.logic.DBManagerBB;
import com.data.collect.client.logic.DownloadMqMessageBB;
import com.data.collect.client.logic.DownloadTaskBB;
import com.data.collect.client.logic.DownloadThreadBB;
import com.data.collect.client.logic.MenuBB;
import com.data.collect.client.logic.ParseTplBB;
import com.data.collect.client.logic.ParseTplItemBB;
import com.data.collect.client.logic.UserBB;
import com.data.collect.client.logic.WebSiteBB;
import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.JsonDTO;
import com.data.collect.common.util.JsonTool;
import com.data.collect.common.util.LogTool;
import com.data.collect.common.util.StringTool;
import com.data.collect.common.util.WebTool;


public class ActionServlet extends HttpServlet{	

	MenuBB menuBB = new MenuBB();
	UserBB userBB = new UserBB();
	WebSiteBB siteBB = new WebSiteBB();
	ParseTplBB parseTplBB = new ParseTplBB();
	ParseTplItemBB parseTplItemBB = new ParseTplItemBB();
	DownloadTaskBB downloadTaskBB = new DownloadTaskBB();
	DownloadThreadBB downloadThreadBB = new DownloadThreadBB();
	DownloadMqMessageBB downloadMqMessageBB = new DownloadMqMessageBB();
	
	DBManagerBB  dbManagerBB = new DBManagerBB();
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
	}

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer forwardBuf = new StringBuffer();
		JsonDTO jsonDto = null;
			
		try{
			//LogTool.logText("this is just a test");
			String action = WebTool.getStringAttributeBeforeParameter(Constants.ACTION, req);
			if(StringTool.isEmpty(action))
			{
				forwardBuf.append(Constants.PAGE_LOGIN);
			}else 
			{
				if(Constants.ACTION_LOGIN.equals(action))
				{
					jsonDto = userBB.authUser(req);
				}else if(Constants.ACTION_LOGOUT.equals(action))
				{
					userBB.logout(req);
					resp.sendRedirect(WebTool.getLoginPageURL(req, false));
				}else if((Constants.ACTION_MENU + Constants.LIST).equals(action))
				{
					jsonDto = menuBB.getMenuListByUserId(req, resp);
				}else if(Constants.ACTION_WEB_DRIVER_SEARCH_BY_TYPE_LIST.equals(action))
				{
					jsonDto = downloadTaskBB.getWebDriverSearchByTypeList(req);
				}else if(action.startsWith(Constants.ACTION_WEB_SITE))
				{
					jsonDto = this.processWebSiteAction(action, req);
				}else if(action.startsWith(Constants.ACTION_PARSE_TEMPLATE))
				{
					jsonDto = this.processParseAction(action, req);
				}else if(action.startsWith(Constants.ACTION_DATA_TABLE))
				{
					jsonDto = this.processDBAction(action, req);
				}
			}
		}catch(Exception ex)
		{
			LogTool.logError( ex);
			if(jsonDto==null) jsonDto = new JsonDTO();
			jsonDto.setSuccess(false);
			jsonDto.setMessage(ex.getMessage());
		}finally
		{
			if(jsonDto==null)
			{
				forwardBuf.append(Constants.PAGE_LOGIN);
			}
			
			if(!forwardBuf.toString().equals(Constants.PAGE_LOGIN))
			{
				try
				{
					forwardBuf.append(JsonTool.getJsonString(jsonDto));
				}catch(Exception ex)
				{
					LogTool.logError( ex);
				}
			}			
			WebTool.writeAjaxResponse(resp, forwardBuf.toString());
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	
	private JsonDTO processDBAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_DATA_TABLE + Constants.LIST).equals(action))
		{
			ret = dbManagerBB.getDataTableListInJson(req);
		}else if((Constants.ACTION_DATA_TABLE + Constants.SAVE).equals(action))
		{
			ret = dbManagerBB.getDataTableListInJson(req);
		}else if((Constants.ACTION_DATA_TABLE_COLUMN + Constants.LIST).equals(action))
		{
			ret = dbManagerBB.getDataTableColumnListInJson(req);
		}else if((Constants.ACTION_DATA_TABLE_COLUMN + Constants.DATA_TYPE + Constants.LIST_ALL).equals(action))
		{
			ret = dbManagerBB.getDataTypeList(req);
		}else if((Constants.ACTION_DATA_TABLE_DATA + Constants.LIST).equals(action))
		{
			ret = dbManagerBB.getDataTableDataListInJson(req);
		}
		return ret;
	}
	
	
	private JsonDTO processWebSiteAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if(action.startsWith(Constants.ACTION_WEB_SITE_TEST))
		{
			ret = this.processWebSiteTestAction(action, req);
		}else if(action.startsWith(Constants.ACTION_WEB_SITE_CATEGORY))
		{
			ret = this.processWebSiteCategoryAction(action, req);
		}else if(action.startsWith(Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT))
		{
			ret = this.processWebSiteLoginAccountAction(action, req);
		}else if(action.startsWith(Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE))
		{
			ret = this.processWebSitePageLinkParseAction(action, req);
		}else if(action.startsWith(Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK))
		{
			ret = this.processWebSiteContentPageCheckAction(action, req);
		}else
		{
			ret = this.processWebSiteSiteAction(action, req);
		}		
		
		return ret;
	}

	private JsonDTO processWebSiteLoginAccountAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT + Constants.LIST).equals(action))
		{
			ret = siteBB.getWebSiteLoginAccountListBySiteId(req);
		}else if((Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT + Constants.SAVE).equals(action))
		{
			ret = siteBB.saveWebSiteLoginAccountItem(req);
		}else if((Constants.ACTION_WEB_SITE_LOGIN_ACCOUNT + Constants.DELETE).equals(action))
		{
			ret = siteBB.deleteWebSiteLoginAccountItem(req);
		}		
		return ret;
	}

	
	private JsonDTO processWebSitePageLinkParseAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.LIST).equals(action))
		{
			ret = siteBB.getWebSitePageLinkParseListBySiteId(req);
		}else if((Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.SAVE).equals(action))
		{
			ret = siteBB.saveWebSitePageLinkParseItem(req);
		}else if((Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.DELETE).equals(action))
		{
			ret = siteBB.deleteWebSitePageLinkParseItem(req);
		}else if((Constants.ACTION_WEB_SITE_PAGE_LINK_PARSE + Constants.COPY).equals(action))
		{
			ret = siteBB.copyWebSitePageLinkParseItem(req);
		}
		return ret;
	}
	
	private JsonDTO processWebSiteContentPageCheckAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK + Constants.LIST).equals(action))
		{
			ret = siteBB.getWebSiteContentPageCheckListBySiteId(req);
		}else if((Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK + Constants.SAVE).equals(action))
		{
			ret = siteBB.saveWebSiteContentPageCheckItem(req);
		}else if((Constants.ACTION_WEB_SITE_CONTENT_PAGE_CHECK + Constants.DELETE).equals(action))
		{
			ret = siteBB.deleteWebSiteContentPageCheckItem(req);
		}
		return ret;
	}
	
	private JsonDTO processWebSiteSiteAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE + Constants.LIST).equals(action))
		{
			ret = siteBB.getWebSiteListByCategoryId(req);
		}else if((Constants.ACTION_WEB_SITE + Constants.SAVE).equals(action))
		{
			ret = siteBB.saveWebSite(req);
		}else if((Constants.ACTION_WEB_SITE + Constants.DELETE).equals(action))
		{
			ret = siteBB.deleteWebSite(req);
		}else if((Constants.ACTION_WEB_SITE + Constants.COPY).equals(action))
		{
			ret = siteBB.copyWebSite(req);
		}
		
		return ret;
	}

	private JsonDTO processWebSiteCategoryAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_CATEGORY + Constants.LIST).equals(action))
		{
			ret = siteBB.getWebSiteCategoryList(req);
		}else if((Constants.ACTION_WEB_SITE_CATEGORY + Constants.SAVE).equals(action))
		{
			ret = siteBB.saveWebSiteCategory(req);
		}else if((Constants.ACTION_WEB_SITE_CATEGORY + Constants.DELETE).equals(action))
		{
			ret = siteBB.deleteWebSiteCategory(req);
		}
		return ret;
	}

	
	private JsonDTO processWebSiteTestAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		
		if(action.startsWith(Constants.ACTION_WEB_SITE_TEST_MQMESSAGE))
		{
			ret = this.processWebSiteTestMQMessageAction(action, req);
		}else if(action.startsWith(Constants.ACTION_WEB_SITE_TEST_THREAD))
		{
			ret = this.processWebSiteTestThreadAction(action, req);
		}else if(action.startsWith(Constants.ACTION_WEB_SITE_TEST_TASK))
		{
			ret = this.processWebSiteTestTaskAction(action, req);
		}		
		
		return ret;
	}
	
	private JsonDTO processWebSiteTestMQMessageAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_TEST_MQMESSAGE + Constants.DOWNLOAD_AND_PARSE).equals(action))
		{
			ret = this.downloadThreadBB.downloadAndParse(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_MQMESSAGE + Constants.LIST).equals(action))
		{
			ret = this.downloadMqMessageBB.getMQMessageListByThreadTableIdAndSiteId(req);
		}
		return ret;
	}
	
	private JsonDTO processWebSiteTestThreadAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.LIST).equals(action))
		{
			ret = this.downloadThreadBB.getTestDownloadThreadListByWebSiteId(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.STOP).equals(action))
		{
			ret = this.downloadThreadBB.stopWebSiteThreadList(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.DELETE).equals(action))
		{
			ret = this.downloadThreadBB.deleteDownloadThreadListByThreadTableId(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_THREAD + Constants.DELETE_ALL).equals(action))
		{
			ret = this.downloadThreadBB.deleteDownloadThreadListByUserIdWebSite(req);
		}
		
		return ret;
	}
	
	private JsonDTO processWebSiteTestTaskAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_WEB_SITE_TEST_TASK + Constants.LIST).equals(action))
		{
			ret = this.downloadTaskBB.getWebSiteDownloadTaskListByThreadTableIdAndSiteId(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_TASK + Constants.DELETE).equals(action))
		{
			ret = this.downloadTaskBB.deleteWebSiteDownloadTask(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_TASK + Constants.DELETE_ALL).equals(action))
		{
			ret = this.downloadTaskBB.deleteWebSiteALLDownloadTask(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_TASK + Constants.RESET).equals(action))
		{
			ret = this.downloadTaskBB.resetWebSiteDownloadTask(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_TASK + Constants.SET_LEVEL).equals(action))
		{
			ret = this.downloadTaskBB.setWebSiteDownloadTaskLevel(req);
		}else if((Constants.ACTION_WEB_SITE_TEST_TASK + Constants.SET_USELESS).equals(action))
		{
			ret = this.downloadTaskBB.setWebSiteDownloadTaskUseless(req);
		}		
		
		return ret;
	}

	
	private JsonDTO processParseAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if(action.startsWith(Constants.ACTION_PARSE_TEMPLATE_CATEGORY))
		{
			ret = this.processParseTplCategoryAction(action, req);
		}else if(action.startsWith(Constants.ACTION_PARSE_TEMPLATE_ITEM))
		{
			ret = this.processParseTplItemAction(action, req);
		}else
		{
			ret = this.processParseTplAction(action, req);
		}
		return ret;
	}
	
	private JsonDTO processParseTplCategoryAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.LIST).equals(action))
		{
			ret = parseTplBB.getParseTplCategoryList(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.SAVE).equals(action))
		{
			ret = parseTplBB.saveParseTplCategory(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_CATEGORY + Constants.DELETE).equals(action))
		{
			ret = parseTplBB.deleteParseTplCategory(req);
		}
		return ret;
	}
	
	private JsonDTO processParseTplItemAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_PARSE_TEMPLATE_ITEM_ACTION + Constants.SAVE).equals(action))
		{
			ret = parseTplItemBB.saveParseTplItemAction(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_ITEM_ACTION + Constants.LIST).equals(action))
		{
			ret = parseTplItemBB.getParseTplItemActionListByParseItemId(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_ITEM_ACTION + Constants.DELETE).equals(action))
		{
			ret = parseTplItemBB.deleteParseTplItemAction(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.SAVE).equals(action))
		{
			ret = parseTplItemBB.saveParseTplItem(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.LIST).equals(action))
		{
			ret = parseTplItemBB.getParseTplItemListByParseId(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.DELETE).equals(action))
		{
			ret = parseTplItemBB.deleteParseTplItem(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE_ITEM + Constants.COPY).equals(action))
		{
			ret = parseTplItemBB.copyParseTplItem(req);
		}
		return ret;
	}
	

	private JsonDTO processParseTplAction(String action, HttpServletRequest req) throws Exception 
	{
		JsonDTO ret = null;
		if((Constants.ACTION_PARSE_TEMPLATE + Constants.LIST).equals(action))
		{
			ret = parseTplBB.getParseTplListByCategoryId(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE + Constants.LIST_ALL).equals(action))
		{
			ret = parseTplBB.getAllParseTplList(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE + Constants.SAVE).equals(action))
		{
			ret = parseTplBB.saveParseTpl(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE + Constants.DELETE).equals(action))
		{
			ret = parseTplBB.deleteParseTpl(req);
		}else if((Constants.ACTION_PARSE_TEMPLATE + Constants.COPY).equals(action))
		{
			ret = parseTplBB.copyParseTpl(req);
		}
		return ret;
	}
}
