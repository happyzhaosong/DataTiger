package com.data.collect.common.dto;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;
import com.general.common.util.StringTool;

@DBTable(name="site_url_parse")
public class WebSitePageLinkParseDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="site_id")
	private int siteId = -1;
	
	@DBColumn(name="click_ele_xpath_before_parse_url")
	private String clickEleXPathBeforeParseUrl = "";	
	
	@DBColumn(name="by_ele_type")
	private String byEleType = "";

	@DBColumn(name="by_ele_val")
	private String byEleVal = "";
	
	@DBColumn(name="url_charactor")
	private String urlCharactor = "";
	
	@DBColumn(name="not_url_charactor")
	private String notUrlCharactor = "";
	
	@DBColumn(name="url_match_reg_exp")
	private String urlMatchRegExp = "";
		
	@DBColumn(name="exec_javascript")
	private String execJavascript = "";
	
	@DBColumn(name="url_page_desc")
	private String urlPageDesc = "";
	
	@DBColumn(name="page_encoding")
	private String pageEncoding = "";	
	
	@DBColumn(name="content_page_url_charactor")
	private String contentPageUrlCharactor = "";
	
	@DBColumn(name="run_regexp_on_url")
	private String runRegexpOnUrl = "";
	
	@DBColumn(name="run_string_find_on_url")
	private String runStringFindOnUrl = "";

	public String getClickEleXPathBeforeParseUrl() {
		return clickEleXPathBeforeParseUrl;
	}

	public void setClickEleXPathBeforeParseUrl(String clickEleXPathBeforeParseUrl) {
		this.clickEleXPathBeforeParseUrl = clickEleXPathBeforeParseUrl;
	}

	public String getRunRegexpOnUrl() {
		runRegexpOnUrl = StringTool.isEmpty(runRegexpOnUrl, "");
		return runRegexpOnUrl;
	}

	public void setRunRegexpOnUrl(String runRegexpOnUrl) {
		this.runRegexpOnUrl = runRegexpOnUrl;
	}

	public String getRunStringFindOnUrl() {
		runStringFindOnUrl = StringTool.isEmpty(runStringFindOnUrl, "");
		return runStringFindOnUrl;
	}

	public void setRunStringFindOnUrl(String runStringFindOnUrl) {
		this.runStringFindOnUrl = runStringFindOnUrl;
	}

	public String getByEleType() {
		if(byEleType==null)
		{
			byEleType = "";
		}else
		{
			byEleType = byEleType.trim();
		}
		return byEleType;
	}

	public void setByEleType(String byEleType) {
		this.byEleType = byEleType;
	}

	public String getByEleVal() {
		if(byEleVal==null)
		{
			byEleVal = "";
		}else
		{
			byEleVal = byEleVal.trim();
		}
		return byEleVal;
	}

	public void setByEleVal(String byEleVal) {
		this.byEleVal = byEleVal;
	}

	public String getContentPageUrlCharactor() {
		if(contentPageUrlCharactor==null)
		{
			contentPageUrlCharactor = "";
		}else
		{
			contentPageUrlCharactor = contentPageUrlCharactor.trim();
		}
		return contentPageUrlCharactor;
	}

	public void setContentPageUrlCharactor(String contentPageUrlCharactor) {
		this.contentPageUrlCharactor = contentPageUrlCharactor;
	}

	public String getUrlMatchRegExp() {
		if(urlMatchRegExp==null)
		{
			urlMatchRegExp = "";
		}else
		{
			urlMatchRegExp = urlMatchRegExp.trim();
		}
		return urlMatchRegExp;
	}

	public void setUrlMatchRegExp(String urlMatchRegExp) {
		this.urlMatchRegExp = urlMatchRegExp;
	}

	public String getPageEncoding() {
		if(pageEncoding==null)
		{
			pageEncoding = "";
		}else
		{
			pageEncoding = pageEncoding.trim();
		}
		return pageEncoding;
	}

	public void setPageEncoding(String pageEncoding) {
		this.pageEncoding = pageEncoding;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getUrlCharactor() {
		if(urlCharactor==null)
		{
			urlCharactor = "";
		}else
		{
			urlCharactor = urlCharactor.trim();
		}
		return urlCharactor;
	}

	public void setUrlCharactor(String urlCharactor) {
		this.urlCharactor = urlCharactor;
	}

	public String getNotUrlCharactor() {
		if(notUrlCharactor==null)
		{
			notUrlCharactor = "";
		}else
		{
			notUrlCharactor = notUrlCharactor.trim();
		}
		return notUrlCharactor;
	}

	public void setNotUrlCharactor(String notUrlCharactor) {
		this.notUrlCharactor = notUrlCharactor;
	}

	public String getExecJavascript() {
		if(execJavascript==null)
		{
			execJavascript = "";
		}else
		{
			execJavascript = execJavascript.trim();
		}
		return execJavascript;
	}

	public void setExecJavascript(String execJavascript) {
		this.execJavascript = execJavascript;
	}

	public String getUrlPageDesc() {
		if(urlPageDesc==null)
		{
			urlPageDesc = "";
		}else
		{
			urlPageDesc = urlPageDesc.trim();
		}
		return urlPageDesc;
	}

	public void setUrlPageDesc(String urlPageDesc) {
		this.urlPageDesc = urlPageDesc;
	}
}
