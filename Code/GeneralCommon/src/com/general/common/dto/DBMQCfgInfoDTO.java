package com.general.common.dto;

public class DBMQCfgInfoDTO extends BaseDTO {
	
	private String dbIp = "";	
	private String dbPort = "";	
	private String dbName = "";
	private String dbUser = "";
	private String dbPasswd = "";
	
	private String dataTblDbIp = "";	
	private String dataTblDbPort = "";	
	private String dataTblDbName = "";
	private String dataTblDbUser = "";
	private String dataTblDbPasswd = "";
	
	private String informationShcemaDbName = "";
	private String informationShcemaDbUser = "";
	private String informationShcemaDbPasswd = "";
	
	private String mqUrl = "";	
	private String mqUser = "";
	private String mqPasswd = "";
	private String mqSubject = "";
	
	private String chromeDriverPath = "";
	
	private String logLevel = "";
	
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
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
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
	public String getInformationShcemaDbName() {
		return informationShcemaDbName;
	}
	public void setInformationShcemaDbName(String informationShcemaDbName) {
		this.informationShcemaDbName = informationShcemaDbName;
	}
	public String getInformationShcemaDbUser() {
		return informationShcemaDbUser;
	}
	public void setInformationShcemaDbUser(String informationShcemaDbUser) {
		this.informationShcemaDbUser = informationShcemaDbUser;
	}
	public String getInformationShcemaDbPasswd() {
		return informationShcemaDbPasswd;
	}
	public void setInformationShcemaDbPasswd(String informationShcemaDbPasswd) {
		this.informationShcemaDbPasswd = informationShcemaDbPasswd;
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
