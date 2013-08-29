package com.data.collect.common.dto;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;


@DBTable(name="account")
public class UserDTO extends BaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -729902769659053902L;

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="user_name")
	private String userName = "";
	
	@DBColumn(name="pass_word")
	private String passWord = "";
	
	@DBColumn(name="qq")
	private String qq = "";
	
	@DBColumn(name="email")
	private String email = "";
	
	@DBColumn(name="mobile")
	private String mobile = "";
	
	@DBColumn(name="mobile")
	private String loginTime = "-1";

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
