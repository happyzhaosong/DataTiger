package com.data.collect.common.dto;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;
import com.data.collect.common.constants.Constants;

@DBTable(name="download_mq_message")
public class MQMessageDTO extends BaseDTO {
	
	private static final long serialVersionUID = 1588900741334333545L;

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="download_thread_type")
	private int downloadThreadType = Constants.DOWNLOAD_THREAD_TYPE_GET_WEB_PAGE;
	
	@DBColumn(name="site_id")
	private int siteId = -1;
	
	@DBColumn(name="user_id")
	private int userId = -1;
	
	@DBColumn(name="create_thread_count")
	private int createThreadCount = 1;
	
	@DBColumn(name="action")
	private String action = Constants.DOWNLOAD_THREAD_ACTION_START;
		
	@DBColumn(name="thread_table_ids")
	private String threadTableIds = "";
	
	@DBColumn(name="thread_ids")
	private String threadIds = "";
	
	@DBColumn(name="send_time")
	private String sendTime = "";
	
	@DBColumn(name="receive_time")
	private String receiveTime = "";
	
	@DBColumn(name="finish_time")
	private String finishTime = "";
	
	@DBColumn(name="fail_reason")
	private String failReason = "";	

	public int getCreateThreadCount() {
		return createThreadCount;
	}

	public void setCreateThreadCount(int createThreadCount) {
		this.createThreadCount = createThreadCount;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
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

	public String getThreadIds() {
		return threadIds;
	}

	public void setThreadIds(String threadIds) {
		this.threadIds = threadIds;
	}

	public String getThreadTableIds() {
		return threadTableIds;
	}

	public void setThreadTableIds(String threadTableIds) {
		this.threadTableIds = threadTableIds;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getDownloadThreadType() {
		return downloadThreadType;
	}

	public void setDownloadThreadType(int downloadThreadType) {
		this.downloadThreadType = downloadThreadType;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("id = ");
		buf.append(this.getId());
		buf.append(", siteId  = ");
		buf.append(this.getSiteId());
		buf.append(", userId  = ");
		buf.append(this.getUserId());
		buf.append(", action  = ");
		buf.append(this.getAction());
		buf.append(", downloadThreadType  = ");
		buf.append(this.getDownloadThreadType());
		buf.append(", threadTableIds  = ");
		buf.append(this.getThreadTableIds());
		buf.append(", threadIds  = ");
		buf.append(this.getThreadIds());
		buf.append(", sendTime  = ");
		buf.append(this.getSendTime());
		buf.append(", receiveTime  = ");
		buf.append(this.getReceiveTime());
		buf.append(", finishTime  = ");
		buf.append(this.getFinishTime());
		buf.append(", failReason = ");
		buf.append(this.getFailReason());
		
		return buf.toString();
	}
	
	
}
