package com.general.common.dto.search;

import java.sql.Date;
import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;

@DBTable(name="data_search_log_detail")
public class DataSearchLogDetailDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="search_keyword")
	private String searchKeyword = "";
	
	@DBColumn(name="search_date")
	private long searchDate = System.currentTimeMillis();
	
	@DBColumn(name="searcher_ip")
	private String searcherIp = "";

	@DBColumn(name="searcher_area")
	private String searcherArea = "";
		
	@DBColumn(name="searcher_host")
	private String searcherHost = "";
	
	@DBColumn(name="search_in")
	private int searchIn = 1;

	@DBColumn(name="search_mall")
	private int searchMall = 0;
	
	@DBColumn(name="search_result_count")
	private long searchResultCount = 0;
	
	public long getSearchResultCount() {
		return searchResultCount;
	}

	public void setSearchResultCount(long searchResultCount) {
		this.searchResultCount = searchResultCount;
	}

	public int getSearchMall() {
		return searchMall;
	}

	public void setSearchMall(int searchMall) {
		this.searchMall = searchMall;
	}

	@DBColumn(name="order_by")
	private String orderBy = "";

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSearcherHost() {
		return searcherHost;
	}

	public void setSearcherHost(String searcherHost) {
		this.searcherHost = searcherHost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public long getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(long searchDate) {
		this.searchDate = searchDate;
	}

	public String getSearcherIp() {
		return searcherIp;
	}

	public void setSearcherIp(String searcherIp) {
		this.searcherIp = searcherIp;
	}

	public String getSearcherArea() {
		return searcherArea;
	}

	public void setSearcherArea(String searcherArea) {
		this.searcherArea = searcherArea;
	}

	public int getSearchIn() {
		return searchIn;
	}

	public void setSearchIn(int searchIn) {
		this.searchIn = searchIn;
	}
}
