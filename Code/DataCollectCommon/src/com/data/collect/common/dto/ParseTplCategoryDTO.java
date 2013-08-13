package com.data.collect.common.dto;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;

@DBTable(name="parse_category")
public class ParseTplCategoryDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="cat_name")
	private String name = "";
	
	@DBColumn(name="cat_desc")
	private String desc = "";

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
}
