package com.general.common.dto.search;

import com.general.common.dto.BaseDTO;

/*
 * Record solr search keywords parameters.
 * */
public class SolrSearchKeywordDTO extends BaseDTO {

	private String searchColum = "*";
	
	private String searchKeyword = "*";

	public String getSearchColum() {
		return searchColum;
	}

	public void setSearchColum(String searchColum) {
		this.searchColum = searchColum;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
}
