package com.general.common.dto.search;

import com.general.common.dto.BaseDTO;

/*
 * Record solr search keywords parameters.
 * */
public class SolrSearchKeywordDTO{

	private String colum = "*";
	
	private String keyword = "*";
	
	private int priority = 1;

	public String getColum() {
		return colum;
	}

	public void setColum(String colum) {
		this.colum = colum;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
