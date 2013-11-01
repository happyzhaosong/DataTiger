package com.data.collect.common.dto;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;
import com.general.common.util.StringTool;

@DBTable(name="download_task")
public class DownloadTaskDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="parsed_out_url_count")
	private int parsedOutUrlCount = -1;
	
	@DBColumn(name="parent_page_url")
	private String parentPageUrl = "";
	
	@DBColumn(name="page_url")
	private String pageUrl = "";

	@DBColumn(name="decode_page_url")
	private String decodePageUrl = "";
	
	@DBColumn(name="site_id")
	private int siteId = -1;
		
	@DBColumn(name="if_content_page")
	private int ifContentPage = 0;
	
	/*
	 * �жϸ�ҳ���Ƿ����������ҳ��ı�־��-1 --- ��û���жϣ�0 --- ��������ҳ�� 1 --- ���������ҳ
	 * */
	@DBColumn(name="if_really_content_page")
	private int ifReallyContentPage = -1;
	
	@DBColumn(name="in_db_time")
	private String inDbTime = "";
	
	@DBColumn(name="apply_time")
	private String applyTime = "";
		
	@DBColumn(name="if_site_top_url")
	private int ifSiteTopUrl = 0;
	
	@DBColumn(name="if_test")
	private int ifTest = 0;	

	@DBColumn(name="duration_info")
	private String durationInfo = "";

	@DBColumn(name="thread_table_id")
	private long threadTableId = -1;
	
	@DBColumn(name="run_time")
	private String runTime = "";
	
	@DBColumn(name="task_run_delta_time")
	private long taskRunDeltaTime = -1;
	
	@DBColumn(name="error_message")
	private String errorMessage = "";
	
	/*task level, default 0, level range(0,1,2,3,4,5), applying tasks will be ordered by level, big level will be run first */
	@DBColumn(name="task_level")
	private int taskLevel = 0;
	
	/*useless content page, default 0: this is a useful content page url, which will be applied to download and parse, 
	 *                              1: this is a useless content page url, which will not be applied to download and parse even it's applyTime is '' */
	@DBColumn(name="useless_content_page")
	private int uselessContentPage = 0;
	
	@DBColumn(name="access_denied_date")
	private String accessDeniedDate = "";
	
	@DBColumn(name="access_denied_thread_sleep_tim")
	private long accessDeniedThreadSleepTime = -1;
	
	@DBColumn(name="reset_apply_time_reason")
	private String resetApplyTimeReason = "";
	 
	@DBColumn(name="reset_apply_time_time")
	private String resetApplyTimeTime = "";
		
	public String getDecodePageUrl() {
		return decodePageUrl;
	}

	public void setDecodePageUrl(String decodePageUrl) {
		this.decodePageUrl = decodePageUrl;
	}

	public int getIfReallyContentPage() {
		return ifReallyContentPage;
	}

	public void setIfReallyContentPage(int ifReallyContentPage) {
		this.ifReallyContentPage = ifReallyContentPage;
	}

	public String getParentPageUrl() {
		return parentPageUrl;
	}

	public void setParentPageUrl(String parentPageUrl) {
		this.parentPageUrl = parentPageUrl;
	}

	public String getResetApplyTimeTime() {
		return resetApplyTimeTime;
	}

	public void setResetApplyTimeTime(String resetApplyTimeTime) {
		this.resetApplyTimeTime = resetApplyTimeTime;
	}

	public String getResetApplyTimeReason() {
		return resetApplyTimeReason;
	}

	public void setResetApplyTimeReason(String resetApplyTimeReason) {
		this.resetApplyTimeReason = resetApplyTimeReason;
	}

	public long getAccessDeniedThreadSleepTime() {
		return accessDeniedThreadSleepTime;
	}

	public void setAccessDeniedThreadSleepTime(long accessDeniedThreadSleepTime) {
		this.accessDeniedThreadSleepTime = accessDeniedThreadSleepTime;
	}

	public String getAccessDeniedDate() {
		return accessDeniedDate;
	}

	public void setAccessDeniedDate(String accessDeniedDate) {
		this.accessDeniedDate = accessDeniedDate;
	}

	public int getParsedOutUrlCount() {
		return parsedOutUrlCount;
	}

	public void setParsedOutUrlCount(int parsedOutUrlCount) {
		this.parsedOutUrlCount = parsedOutUrlCount;
	}

	public int getUselessContentPage() {
		return uselessContentPage;
	}

	public void setUselessContentPage(int uselessContentPage) {
		this.uselessContentPage = uselessContentPage;
	}

	public int getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(int taskLevel) {
		this.taskLevel = taskLevel;
	}

	public long getThreadTableId() {
		return threadTableId;
	}

	public void setThreadTableId(long threadTableId) {
		this.threadTableId = threadTableId;
	}

	public long getTaskRunDeltaTime() {
		return taskRunDeltaTime;
	}

	public void setTaskRunDeltaTime(long taskRunDeltaTime) {
		this.taskRunDeltaTime = taskRunDeltaTime;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getDurationInfo() {
		return durationInfo;
	}

	public void setDurationInfo(String durationInfo) {
		this.durationInfo = durationInfo;
	}

	public boolean isIfContentPage() {
		if(ifContentPage==1)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	public int getIfContentPage() {
		return ifContentPage;
	}

	public void setIfContentPage(int ifContentPage) {
		this.ifContentPage = ifContentPage;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public boolean isIfSiteTopUrl() {
		if(ifSiteTopUrl==1)
		{
			return true;
		}else
		{
			return false;	
		}		
	}

	public int getIfSiteTopUrl() {
		return ifSiteTopUrl;
	}
	
	public void setIfSiteTopUrl(int ifSiteTopUrl) {
		this.ifSiteTopUrl = ifSiteTopUrl;
	}

	public boolean isIfTest() {
		if(ifTest==1)
		{
			return true;
		}else
		{
			return false;	
		}
	}

	public int getIfTest() {
		return ifTest;
	}
	
	public void setIfTest(int ifTest) {
		this.ifTest = ifTest;
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

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getInDbTime() {
		return inDbTime;
	}

	public void setInDbTime(String inDbTime) {
		this.inDbTime = inDbTime;
	}	
	
	public void resetApplyTime(String reason)
	{
		this.setApplyTime("");
		this.setResetApplyTimeTime(String.valueOf(System.currentTimeMillis()));
		reason = StringTool.isEmpty(reason, "");
		this.setResetApplyTimeReason(reason);
	}
}
