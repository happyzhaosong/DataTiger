package com.data.collect.common.dto;

import com.data.collect.common.constants.Constants;


public class BaseDTO extends BasePageDTO{
	
	private boolean success = false;	
	private boolean exist = false;	
	private int code = Constants.ERROR_CODE_NO_ERROR;	
	private String message = "";
	private boolean selected = false;	
	/*
	private String ACTION = "";

	public String getACTION() {
		return ACTION;
	}

	public void setACTION(String aCTION) {
		ACTION = aCTION;
	}
*/
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public BaseDTO() {
		super();
		this.initErrorInfo();
	}
	
	public void initErrorInfo()
	{
		this.success = true;
		this.exist = false;
		this.code = Constants.ERROR_CODE_NO_ERROR;
		this.message = "";
	}
}
