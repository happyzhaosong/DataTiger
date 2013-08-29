package com.data.collect.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;
import com.general.common.dto.ParseTplItemDTO;

@DBTable(name="parse")
public class ParseTplDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="parse_name")
	private String name = "";
	
	@DBColumn(name="parse_desc")
	private String desc = "";
		
	@DBColumn(name="category_id")
	private int categoryId = -1;
	
	@DBColumn(name="save_to_table")
	private String saveToTable = "";
	
	private List<ParseTplItemDTO> parseTplItemList = null;

	public List<ParseTplItemDTO> getParseTplItemList() {
		if(this.parseTplItemList==null)
		{
			this.parseTplItemList = new ArrayList<ParseTplItemDTO>();
		}
		return parseTplItemList;
	}

	public void setParseTplItemList(List<ParseTplItemDTO> parseTplItemList) {
		this.parseTplItemList = parseTplItemList;
	}

	public String getSaveToTable() {
		return saveToTable;
	}

	public void setSaveToTable(String saveToTable) {
		this.saveToTable = saveToTable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
