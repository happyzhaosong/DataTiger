package com.general.common.dto;

public class DBMQCfgInfoDTO extends BaseDTO {
	
	//data collect db settings
	private String dbIp = "";	
	private String dbPort = "";	
	private String dbName = "";
	private String dbUser = "";
	private String dbPasswd = "";

	//data table db settings
	private String dataTblDbIp = "";	
	private String dataTblDbPort = "";	
	private String dataTblDbName = "";
	private String dataTblDbUser = "";
	private String dataTblDbPasswd = "";
	
	/*
	//informationSchema db settings (use for mysql only)
	private String informationShcemaDbName = "";
	private String informationShcemaDbUser = "";
	private String informationShcemaDbPasswd = "";
	*/
	
	//the mq server connection strings
	private String mqUrl = "";	
	private String mqUser = "";
	private String mqPasswd = "";
	private String mqSubject = "";
	
	//webdriver chrome driver path
	private String chromeDriverPath = "";
	
	
	//solr settings
	private String solrIp = "";	
	private String solrPort = "";	
			
	public String getSolrIp() {
		return solrIp;
	}
	public void setSolrIp(String solrIp) {
		this.solrIp = solrIp;
	}
	public String getSolrPort() {
		return solrPort;
	}
	public void setSolrPort(String solrPort) {
		this.solrPort = solrPort;
	}

	 
	
	public String getDataTblDbIp() {
		return dataTblDbIp;
	}
	public void setDataTblDbIp(String dataTblDbIp) {
		this.dataTblDbIp = dataTblDbIp;
	}
	public String getDataTblDbPort() {
		return dataTblDbPort;
	}
	public void setDataTblDbPort(String dataTblDbPort) {
		this.dataTblDbPort = dataTblDbPort;
	}
	public String getDataTblDbName() {
		return dataTblDbName;
	}
	public void setDataTblDbName(String dataTblDbName) {
		this.dataTblDbName = dataTblDbName;
	}
	public String getDataTblDbUser() {
		return dataTblDbUser;
	}
	public void setDataTblDbUser(String dataTblDbUser) {
		this.dataTblDbUser = dataTblDbUser;
	}
	public String getDataTblDbPasswd() {
		return dataTblDbPasswd;
	}
	public void setDataTblDbPasswd(String dataTblDbPasswd) {
		this.dataTblDbPasswd = dataTblDbPasswd;
	}
	
	/*
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	*/
	
	public String getChromeDriverPath() {
		return chromeDriverPath;
	}
	public void setChromeDriverPath(String chromeDriverPath) {
		this.chromeDriverPath = chromeDriverPath;
	}
	public String getDbIp() {
		return dbIp;
	}
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
	public String getDbPort() {
		return dbPort;
	}
	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPasswd() {
		return dbPasswd;
	}
	public void setDbPasswd(String dbPasswd) {
		this.dbPasswd = dbPasswd;
	}
	
	public String getMqUrl() {
		return mqUrl;
	}
	public void setMqUrl(String mqUrl) {
		this.mqUrl = mqUrl;
	}
	public String getMqUser() {
		return mqUser;
	}
	public void setMqUser(String mqUser) {
		this.mqUser = mqUser;
	}
	public String getMqPasswd() {
		return mqPasswd;
	}
	public void setMqPasswd(String mqPasswd) {
		this.mqPasswd = mqPasswd;
	}
	public String getMqSubject() {
		return mqSubject;
	}
	public void setMqSubject(String mqSubject) {
		this.mqSubject = mqSubject;
	}
}
