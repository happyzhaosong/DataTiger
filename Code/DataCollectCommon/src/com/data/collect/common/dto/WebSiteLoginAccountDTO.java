package com.data.collect.common.dto;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;

@DBTable(name="site_auth")
public class WebSiteLoginAccountDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="site_id")
	private int siteId = -1;
	
	@DBColumn(name="login_page_url")
	private String loginPageUrl = "";
	
	@DBColumn(name="login_user")
	private String loginUser = "";
	
	@DBColumn(name="login_passwd")
	private String loginPasswd = "";
	
	@DBColumn(name="login_user_xpath")
	private String loginUserXpath = "";
	
	@DBColumn(name="login_passwd_xpath")
	private String loginPasswdXpath = "";
	
	@DBColumn(name="login_submit_xpath")
	private String loginSubmitXpath = "";

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

	public String getLoginPageUrl() {
		return loginPageUrl;
	}

	public void setLoginPageUrl(String loginPageUrl) {
		this.loginPageUrl = loginPageUrl;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public String getLoginPasswd() {
		return loginPasswd;
	}

	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

	public String getLoginUserXpath() {
		return loginUserXpath;
	}

	public void setLoginUserXpath(String loginUserXpath) {
		this.loginUserXpath = loginUserXpath;
	}

	public String getLoginPasswdXpath() {
		return loginPasswdXpath;
	}

	public void setLoginPasswdXpath(String loginPasswdXpath) {
		this.loginPasswdXpath = loginPasswdXpath;
	}

	public String getLoginSubmitXpath() {
		return loginSubmitXpath;
	}

	public void setLoginSubmitXpath(String loginSubmitXpath) {
		this.loginSubmitXpath = loginSubmitXpath;
	}
}
