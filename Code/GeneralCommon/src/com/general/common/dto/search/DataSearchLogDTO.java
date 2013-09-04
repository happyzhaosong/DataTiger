package com.general.common.dto.search;

import java.sql.Date;
import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;

@DBTable(name="data_search_log")
public class DataSearchLogDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="search_keyword")
	private String searchKeyword = "";
	
	@DBColumn(name="total_search_count")
	private int totalSearchCount = 1;
	
	@DBColumn(name="search_in")
	private int searchIn = 1;
	
	@DBColumn(name="last_search_date")
	private long lastSearchDate = System.currentTimeMillis();

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

	public int getTotalSearchCount() {
		return totalSearchCount;
	}

	public void setTotalSearchCount(int totalSearchCount) {
		this.totalSearchCount = totalSearchCount;
	}

	public int getSearchIn() {
		return searchIn;
	}

	public void setSearchIn(int searchIn) {
		this.searchIn = searchIn;
	}

	public long getLastSearchDate() {
		return lastSearchDate;
	}

	public void setLastSearchDate(long lastSearchDate) {
		this.lastSearchDate = lastSearchDate;
	}
}
