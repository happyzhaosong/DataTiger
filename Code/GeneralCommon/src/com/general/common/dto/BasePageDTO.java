package com.general.common.dto;

import java.io.Serializable;

import com.general.common.constants.GeneralConstants;
import com.general.common.util.StringTool;

public class BasePageDTO implements Serializable {

	//following parameters for page scrolling in ext js grid panel
	private String limit = GeneralConstants.DEFAULT_PAGE_SIZE;
	private String sort = "";
	private String dir = "DESC";
	private String filter = "";
	private String filterOperator = "=";
	private String filterValue = "";
	private String start = "0";
	private String page = "1";
	
	//record total record count(not only this page) for this search condition
	//always save this information in the first DTO element in return List array.
	private long totalRecordsCountInThisSearch = -1;
		
	public String getFilterValue() {
		filterValue = StringTool.isEmpty(filterValue, "");
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}

	public String getFilter() {
		if(filter==null)
		{
			filter = "";
		}
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}



	public String getFilterOperator() {
		filterOperator = StringTool.isEmpty(filterOperator, "=");
		return filterOperator;
	}

	public void setFilterOperator(String filterOperator) {
		this.filterOperator = filterOperator;
	}

	public long getTotalRecordsCountInThisSearch() {
		return totalRecordsCountInThisSearch;
	}
	
	public void setTotalRecordsCountInThisSearch(long totalRecordsCountInThisSearch) {
		this.totalRecordsCountInThisSearch = totalRecordsCountInThisSearch;
	}
	
	public String getLimit() {
		if(!StringTool.isInteger(limit))
		{
			limit = "100";
		}
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getSort() {
		if(sort==null)
		{
			sort = "";
		}
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		if(StringTool.isEmpty(dir))
		{
			dir = "DESC";
		}
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getStart() {
		if(!StringTool.isInteger(start))
		{
			start = "0";
		}
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getPage() {
		if(!StringTool.isInteger(page))
		{
			page = "1";
		}
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
}
