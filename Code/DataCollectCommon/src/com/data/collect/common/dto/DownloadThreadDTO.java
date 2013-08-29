package com.data.collect.common.dto;

import com.data.collect.common.constants.Constants;
import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;

@DBTable(name="download_thread")
public class DownloadThreadDTO extends BaseDTO {

	private static final long serialVersionUID = -8559358147045910080L;

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	//User id who start this download thread.
	@DBColumn(name="user_id")
	private int userId = -1;
	
	//记录该线程下载的 site id， 如果该线程下载所有网站，则 site_id == -1
	@DBColumn(name="site_id")
	private int siteId = -1;
	
	@DBColumn(name="thread_id")
	private String threadId = "";

	@DBColumn(name="start_time")
	private String startTime = "";
	
	@DBColumn(name="stop_time")
	private String stopTime = "";

	@DBColumn(name="thread_type")
	private int threadType = Constants.DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE;
	
	@DBColumn(name="stop_reason")
	private String stopReason = "";

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public int getThreadType() {
		return threadType;
	}

	public void setThreadType(int threadType) {
		this.threadType = threadType;
	}
}
