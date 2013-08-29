package com.general.common.dto.search;

import com.general.common.dto.BaseDTO;
import com.general.common.util.StringTool;


public class BaseSearchParamsDTO extends BaseDTO {

	private String searchKeyword = "";	
	private String orderBy1 = "";
	private String direction1 = "";	
	private String orderBy2 = "";
	private String direction2 = "";	
	private int searchWebSite = -1;
	
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

	public int getSearchWebSite() {
		return searchWebSite;
	}

	public void setSearchWebSite(int searchWebSite) {
		this.searchWebSite = searchWebSite;
	}
}
