package com.data.collect.common.dto.search;

import com.data.collect.common.dto.BaseDTO;

public class BaseSearchParamsDTO extends BaseDTO {

	private String searchKeyword = "";
	
	private String orderBy1 = "";
	
	private String orderBy2 = "";
	
	private int searchWebSite = 1;

	public String getSearchKeyword() {
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

	public int getSearchWebSite() {
		return searchWebSite;
	}

	public void setSearchWebSite(int searchWebSite) {
		this.searchWebSite = searchWebSite;
	}
}
