package com.general.common.dto.search;

import com.general.common.dto.BaseDTO;
import com.general.common.util.StringTool;


public class BaseSearchParamsDTO extends BaseDTO {

	private String searchKeyword = "";
	private String orderByWithDierction1 = "";
	private String orderByWithDierction2 = "";
	private String orderBy1 = "";
	private String direction1 = "";	
	private String orderBy2 = "";
	private String direction2 = "";	
	private String searchWebSite = "";
	private boolean logSearchKeyword = true;
	private long searchResultCount = 0;
	
	public long getSearchResultCount() {
		return searchResultCount;
	}

	public void setSearchResultCount(long searchResultCount) {
		this.searchResultCount = searchResultCount;
	}

	public boolean isLogSearchKeyword() {
		return logSearchKeyword;
	}

	public void setLogSearchKeyword(boolean logSearchKeyword) {
		this.logSearchKeyword = logSearchKeyword;
	}

	public String getOrderByWithDierction1() {
		return orderByWithDierction1;
	}

	public void setOrderByWithDierction1(String orderByWithDierction1) {
		this.orderByWithDierction1 = orderByWithDierction1;
	}

	public String getOrderByWithDierction2() {
		return orderByWithDierction2;
	}

	public void setOrderByWithDierction2(String orderByWithDierction2) {
		this.orderByWithDierction2 = orderByWithDierction2;
	}

	public String getDirection1() {
		return direction1;
	}

	public void setDirection1(String direction1) {
		this.direction1 = direction1;
	}

	public String getDirection2() {
		return direction2;
	}

	public void setDirection2(String direction2) {
		this.direction2 = direction2;
	}

	public String getSearchKeyword() {
		searchKeyword = StringTool.isEmpty(searchKeyword, "");
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public String getOrderBy1() {
		return orderBy1;
	}

	public void setOrderBy1(String orderBy1) {
		this.orderBy1 = orderBy1;
	}

	public String getOrderBy2() {
		return orderBy2;
	}

	public void setOrderBy2(String orderBy2) {
		this.orderBy2 = orderBy2;
	}

	public String getSearchWebSite() {
		return searchWebSite;
	}

	public void setSearchWebSite(String searchWebSite) {
		this.searchWebSite = searchWebSite;
	}
}
