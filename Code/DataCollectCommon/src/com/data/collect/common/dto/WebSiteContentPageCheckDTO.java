package com.data.collect.common.dto;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;

@DBTable(name="site_content_page_check")
public class WebSiteContentPageCheckDTO extends BaseDTO {

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="site_id")
	private int siteId = -1;
	
	@DBColumn(name="by_ele_type")
	private String byEleType = "";

	@DBColumn(name="by_ele_val")
	private String byEleVal = "";
	
	@DBColumn(name="by_tag_attribute")
	private String byTagAttribute = "";
	
	@DBColumn(name="parse_value_reg_exp")
	private String parseValueRegExp = "";
	
	@DBColumn(name="parse_value_string")
	private String parseValueString = "";
	
	@DBColumn(name="charactor")
	private String charactor = "";
	
	@DBColumn(name="not_charactor")
	private String notCharactor = "";
	
	public String getByTagAttribute() {
		return byTagAttribute;
	}

	public void setByTagAttribute(String byTagAttribute) {
		this.byTagAttribute = byTagAttribute;
	}

	public String getCharactor() {
		return charactor;
	}

	public void setCharactor(String charactor) {
		this.charactor = charactor;
	}

	public String getNotCharactor() {
		return notCharactor;
	}

	public void setNotCharactor(String notCharactor) {
		this.notCharactor = notCharactor;
	}

	public String getByEleType() {
		if(byEleType==null)
		{
			byEleType = "";
		}else
		{
			byEleType = byEleType.trim();
		}
		return byEleType;
	}

	public void setByEleType(String byEleType) {
		this.byEleType = byEleType;
	}

	public String getByEleVal() {
		if(byEleVal==null)
		{
			byEleVal = "";
		}else
		{
			byEleVal = byEleVal.trim();
		}
		return byEleVal;
	}

	public void setByEleVal(String byEleVal) {
		this.byEleVal = byEleVal;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getParseValueRegExp() {
		return parseValueRegExp;
	}

	public void setParseValueRegExp(String parseValueRegExp) {
		this.parseValueRegExp = parseValueRegExp;
	}

	public String getParseValueString() {
		return parseValueString;
	}

	public void setParseValueString(String parseValueString) {
		this.parseValueString = parseValueString;
	}
}
