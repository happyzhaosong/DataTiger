package com.general.common.dto.search;

import java.util.ArrayList;
import java.util.List;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BaseDTO;

public class SolrSearchParamsDTO{	
	private List<SolrSearchKeywordDTO> sarchKeywordList = new ArrayList<SolrSearchKeywordDTO>();
	private List<SolrSearchOrderDTO> sarchOrderList = new ArrayList<SolrSearchOrderDTO>();
	private long startRow = 0;
	private long pageSize = Long.parseLong(GeneralConstants.DEFAULT_PAGE_SIZE);
	public List<SolrSearchKeywordDTO> getSarchKeywordList() {
		return sarchKeywordList;
	}
	public void setSarchKeywordList(List<SolrSearchKeywordDTO> sarchKeywordList) {
		this.sarchKeywordList = sarchKeywordList;
	}
	public List<SolrSearchOrderDTO> getSarchOrderList() {
		return sarchOrderList;
	}
	public void setSarchOrderList(List<SolrSearchOrderDTO> sarchOrderList) {
		this.sarchOrderList = sarchOrderList;
	}
	public long getStartRow() {
		return startRow;
	}
	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}
	public long getPageSize() {
		return pageSize;
	}
	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
}
