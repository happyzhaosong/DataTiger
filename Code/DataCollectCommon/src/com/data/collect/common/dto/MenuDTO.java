package com.data.collect.common.dto;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;

@DBTable(name="menu")
public class MenuDTO extends BaseDTO {
	
	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="menu_name")
	private String name = "";
	
	@DBColumn(name="menu_desc")
	private String desc = "";
	
	@DBColumn(name="menu_action")
	private String action = "";
	
	private String menuUrl = "";
	
	private String menuLink = "";

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
}
