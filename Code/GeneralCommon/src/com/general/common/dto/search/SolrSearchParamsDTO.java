package com.general.common.dto.search;

import java.util.ArrayList;
import java.util.List;
import com.general.common.constants.GeneralConstants;

public class SolrSearchParamsDTO{	
	private List<SolrSearchKeywordDTO> sarchKeywordList = new ArrayList<SolrSearchKeywordDTO>();
	private List<SolrSearchOrderDTO> sarchOrderList = new ArrayList<SolrSearchOrderDTO>();
	private int startRow = 0;
	private int pageSize = Integer.parseInt(GeneralConstants.DEFAULT_PAGE_SIZE);
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
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
