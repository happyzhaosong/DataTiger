package com.data.collect.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.constants.Constants;
import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.StringTool;

@DBTable(name="site")
public class WebSiteDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="category_id")
	private int categoryId;
	
	@DBColumn(name="site_name")
	private String name = "";
	
	@DBColumn(name="site_desc")
	private String desc = "";
	
	@DBColumn(name="browser_type")
	private String browserType = "";
	
	@DBColumn(name="parse_id")
	private int parseId;
		
	@DBColumn(name="top_url")
	private String topUrl = "";
	
	@DBColumn(name="alert_text_to_reset_d_task")
	private String alertTextToResetDTask = "";
	
	@DBColumn(name="multi_country")
	private boolean multiCountry = false;

	@DBColumn(name="place_holders")
	private String placeHolders = "";

	@DBColumn(name="start_download_time")
	private int startDownloadTime = 0;//0-23 to indicate which hour to start download.
	
	@DBColumn(name="download_duration_seconds")
	private int downloadDurationSeconds;
	
	@DBColumn(name="over_write_attributes")
	private String overWriteAttributes = "";
	
	@DBColumn(name="download_task_reget_duration_d")
	private int downloadTaskRegetDuration;
	
	@DBColumn(name="need_login")
	private boolean needLogin = false;
	
	@DBColumn(name="login_check_return_url")
	private String loginCheckReturnUrl = "";
	
	@DBColumn(name="login_check_return_data")
	private String loginCheckReturnData = "";
	
	@DBColumn(name="login_check_return_data_xpath")
	private String loginCheckReturnDataXPath = "";
	
	@DBColumn(name="test_passed")
	private boolean testPassed = false;
	
	@DBColumn(name="site_status")
	private boolean siteStatus = false;
	
	@DBColumn(name="access_denied_page_element_xpath")
	private String accessDeniedPageElementXpath = "";

	@DBColumn(name="access_denied_page_element_value")
	private String accessDeniedPageElementValue = "";
	
	@DBColumn(name="need_scroll_page_url_charactor")
	private String needScrollPageUrlCharactor = "";
		
	@DBColumn(name="not_need_scroll_page_url_charactor")
	private String notNeedScrollPageUrlCharactor = "";	
	
	@DBColumn(name="set_high_level_task_url_charactor")
	private String setHighLevelTaskUrlCharactor = "";	
	
	@DBColumn(name="set_middle_level_task_url_charactor")
	private String setMiddleLevelTaskUrlCharactor = "";	
	
	@DBColumn(name="show_img_in_browser")
	private boolean showImgInBrowser = false;
	
	private String testPassedString = "";
	
	private String siteStatusString = "";

	private List<WebSiteContentPageCheckDTO> contentPageCheckDtoList = new ArrayList<WebSiteContentPageCheckDTO>();
	
	private List<WebSitePageLinkParseDTO> pageLinkParseDtoList = new ArrayList<WebSitePageLinkParseDTO>();
	
	private List<WebSiteLoginAccountDTO> siteLoginAccountDtoList = new ArrayList<WebSiteLoginAccountDTO>();
	
	private List<ParseTplItemDTO> parseItemList = new ArrayList<ParseTplItemDTO>();

	public boolean isShowImgInBrowser() {
		return showImgInBrowser;
	}

	public void setShowImgInBrowser(boolean showImgInBrowser) {
		this.showImgInBrowser = showImgInBrowser;
	}

	public String getSetHighLevelTaskUrlCharactor() {
		return setHighLevelTaskUrlCharactor;
	}

	public void setSetHighLevelTaskUrlCharactor(String setHighLevelTaskUrlCharactor) {
		this.setHighLevelTaskUrlCharactor = setHighLevelTaskUrlCharactor;
	}

	public String getSetMiddleLevelTaskUrlCharactor() {
		return setMiddleLevelTaskUrlCharactor;
	}

	public void setSetMiddleLevelTaskUrlCharactor(
			String setMiddleLevelTaskUrlCharactor) {
		this.setMiddleLevelTaskUrlCharactor = setMiddleLevelTaskUrlCharactor;
	}

	public String getNeedScrollPageUrlCharactor() {
		return needScrollPageUrlCharactor;
	}

	public void setNeedScrollPageUrlCharactor(String needScrollPageUrlCharactor) {
		this.needScrollPageUrlCharactor = needScrollPageUrlCharactor;
	}

	public String getNotNeedScrollPageUrlCharactor() {
		return notNeedScrollPageUrlCharactor;
	}

	public void setNotNeedScrollPageUrlCharactor(
			String notNeedScrollPageUrlCharactor) {
		this.notNeedScrollPageUrlCharactor = notNeedScrollPageUrlCharactor;
	}

	public String getAlertTextToResetDTask() {
		return alertTextToResetDTask;
	}

	public void setAlertTextToResetDTask(String alertTextToResetDTask) {
		this.alertTextToResetDTask = alertTextToResetDTask;
	}

	public List<WebSiteContentPageCheckDTO> getContentPageCheckDtoList() {
		return contentPageCheckDtoList;
	}

	public void setContentPageCheckDtoList(
			List<WebSiteContentPageCheckDTO> contentPageCheckDtoList) {
		this.contentPageCheckDtoList = contentPageCheckDtoList;
	}

	public String getAccessDeniedPageElementXpath() {
		return accessDeniedPageElementXpath;
	}

	public void setAccessDeniedPageElementXpath(String accessDeniedPageElementXpath) {
		this.accessDeniedPageElementXpath = accessDeniedPageElementXpath;
	}

	public String getAccessDeniedPageElementValue() {
		return accessDeniedPageElementValue;
	}

	public void setAccessDeniedPageElementValue(String accessDeniedPageElementValue) {
		this.accessDeniedPageElementValue = accessDeniedPageElementValue;
	}

	public List<ParseTplItemDTO> getParseItemList() {
		return parseItemList;
	}

	public void setParseItemList(List<ParseTplItemDTO> parseItemList) {
		this.parseItemList = parseItemList;
	}

	public String getBrowserType() {
		return StringTool.isEmpty(browserType, Constants.WEB_DRIVER_BROWSER_TYPE_HTML_UNIT);
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public boolean isMultiCountry() {
		return multiCountry;
	}

	public void setMultiCountry(boolean multiCountry) {
		this.multiCountry = multiCountry;
	}

	public String getPlaceHolders() {
		return placeHolders;
	}

	public void setPlaceHolders(String placeHolders) {
		this.placeHolders = placeHolders;
	}

	public String getTestPassedString() {
		if(this.testPassed)
		{
			testPassedString = "Passed";
		}else
		{
			testPassedString = "Not Passed";
		}
		return testPassedString;
	}

	public void setTestPassedString(String testPassedString) {
		this.testPassedString = testPassedString;
	}

	public String getSiteStatusString() {
		if(this.siteStatus)
		{
			siteStatusString = "Product";
		}else
		{
			siteStatusString = "Develop";
		}
		return siteStatusString;
	}

	public void setSiteStatusString(String siteStatusString) {
		this.siteStatusString = siteStatusString;
	}

	public List<WebSiteLoginAccountDTO> getSiteLoginAccountDtoList() {
		return siteLoginAccountDtoList;
	}

	public void setSiteLoginAccountDtoList(
			List<WebSiteLoginAccountDTO> siteLoginAccountDtoList) {
		this.siteLoginAccountDtoList = siteLoginAccountDtoList;
	}

	public List<WebSitePageLinkParseDTO> getPageLinkParseDtoList() {
		return pageLinkParseDtoList;
	}

	public void setPageLinkParseDtoList(
			List<WebSitePageLinkParseDTO> pageLinkParseDtoList) {
		this.pageLinkParseDtoList = pageLinkParseDtoList;
	}

	public boolean isSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(boolean siteStatus) {
		this.siteStatus = siteStatus;
	}

	public boolean isTestPassed() {
		return testPassed;
	}

	public void setTestPassed(boolean testPassed) {
		this.testPassed = testPassed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getParseId() {
		return parseId;
	}

	public void setParseId(int parseId) {
		this.parseId = parseId;
	}

	public String getTopUrl() {
		return topUrl;
	}

	public void setTopUrl(String topUrl) {
		this.topUrl = topUrl;
	}

	public int getStartDownloadTime() {
		return startDownloadTime;
	}

	public void setStartDownloadTime(int startDownloadTime) {
		this.startDownloadTime = startDownloadTime;
	}

	public int getDownloadDurationSeconds() {
		return downloadDurationSeconds;
	}

	public void setDownloadDurationSeconds(int downloadDurationSeconds) {
		this.downloadDurationSeconds = downloadDurationSeconds;
	}

	public String getOverWriteAttributes() {
		return overWriteAttributes;
	}

	public void setOverWriteAttributes(String overWriteAttributes) {
		this.overWriteAttributes = overWriteAttributes;
	}

	public int getDownloadTaskRegetDuration() {
		return downloadTaskRegetDuration;
	}

	public void setDownloadTaskRegetDuration(int downloadTaskRegetDuration) {
		this.downloadTaskRegetDuration = downloadTaskRegetDuration;
	}

	public boolean isNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(boolean needLogin) {
		this.needLogin = needLogin;
	}

	public String getLoginCheckReturnUrl() {
		return loginCheckReturnUrl;
	}

	public void setLoginCheckReturnUrl(String loginCheckReturnUrl) {
		this.loginCheckReturnUrl = loginCheckReturnUrl;
	}

	public String getLoginCheckReturnData() {
		return loginCheckReturnData;
	}

	public void setLoginCheckReturnData(String loginCheckReturnData) {
		this.loginCheckReturnData = loginCheckReturnData;
	}

	public String getLoginCheckReturnDataXPath() {
		return loginCheckReturnDataXPath;
	}

	public void setLoginCheckReturnDataXPath(String loginCheckReturnDataXPath) {
		this.loginCheckReturnDataXPath = loginCheckReturnDataXPath;
	}
	
}
