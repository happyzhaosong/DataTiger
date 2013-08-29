package com.general.common.dto;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.dto.BaseDTO;

@DBTable(name="parse_item_action")
public class ParseTplItemActionDTO extends BaseDTO {
	
	private static final long serialVersionUID = 3688365878348152705L;

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="parse_item_id")
	private int parseItemId = -1;
	
	@DBColumn(name="by_ele_type")
	private String byEleType = "";
	
	@DBColumn(name="by_ele_val")
	private String byEleVal = "";
	
	@DBColumn(name="by_ele_action")
	private String byEleAction = "";
	
	public String getByEleAction() {
		return byEleAction;
	}

	public void setByEleAction(String byEleAction) {
		this.byEleAction = byEleAction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParseItemId() {
		return parseItemId;
	}

	public void setParseItemId(int parseItemId) {
		this.parseItemId = parseItemId;
	}

	public String getByEleType() {
		return byEleType;
	}

	public void setByEleType(String byEleType) {
		this.byEleType = byEleType;
	}

	public String getByEleVal() {
		return byEleVal;
	}

	public void setByEleVal(String byEleVal) {
		this.byEleVal = byEleVal;
	}

}
