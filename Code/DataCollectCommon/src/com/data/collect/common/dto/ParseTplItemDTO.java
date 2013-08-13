package com.data.collect.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;
import com.data.collect.common.constants.Constants;

@DBTable(name="parse_item")
public class ParseTplItemDTO extends BaseDTO {
	
	private static final long serialVersionUID = 3688365878348152705L;

	@DBColumn(name="id",pk=true,autoIncreate=true)
	private int id = -1;
	
	@DBColumn(name="parse_id")
	private int parseId = -1;

	@DBColumn(name="data_type")
	private String dataType = Constants.DATA_TYPE_STRING;
		
	@DBColumn(name="is_check_repeat_column")
	private boolean ifCheckRepeatColumn = false;
	
	@DBColumn(name="is_url")
	private boolean ifUrl = false;

	@DBColumn(name="is_direct_get_text")
	private boolean ifDirectGetText = true;
	
	@DBColumn(name="save_to_table")
	private String saveToTable = "";
	
	@DBColumn(name="save_to_column")
	private String saveToColumn = "";
	
	@DBColumn(name="use_this_setting_url_charactor")
	private String useThisSettingUrlCharactor = "";
	
	@DBColumn(name="column_desc")
	private String columnDesc = "";
	
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
	
	@DBColumn(name="page_url_as_value")
	private boolean pageUrlAsValue = false;

	@DBColumn(name="charactor")
	private String charactor = "";
	
	@DBColumn(name="not_charactor")
	private String notCharactor = "";
	
	@DBColumn(name="default_val")
	private String defaultVal = "";
	
	@DBColumn(name="number_format")
	private String numberFormat = "";
	
	@DBColumn(name="date_format")
	private String dateFormat = "";
	
	@DBColumn(name="src_reg_exp")
	private String srcRegExp = "";
	
	@DBColumn(name="dest_reg_exp")
	private String destRegExp = "";
	
	@DBColumn(name="reg_exp_item_relation")
	private boolean regExpItemRelation = false;
	
	@DBColumn(name="exec_javascript")
	private String execJavascript = "";

	private List<ParseTplItemActionDTO> parseItemActionList = new ArrayList<ParseTplItemActionDTO>();
		
	public String getUseThisSettingUrlCharactor() {
		return useThisSettingUrlCharactor;
	}

	public void setUseThisSettingUrlCharactor(String useThisSettingUrlCharactor) {
		this.useThisSettingUrlCharactor = useThisSettingUrlCharactor;
	}

	public boolean isIfDirectGetText() {
		return ifDirectGetText;
	}

	public void setIfDirectGetText(boolean ifDirectGetText) {
		this.ifDirectGetText = ifDirectGetText;
	}

	public List<ParseTplItemActionDTO> getParseItemActionList() {
		return parseItemActionList;
	}

	public void setParseItemActionList(
			List<ParseTplItemActionDTO> parseItemActionList) {
		this.parseItemActionList = parseItemActionList;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public boolean isRegExpItemRelation() {
		return regExpItemRelation;
	}

	public void setRegExpItemRelation(boolean regExpItemRelation) {
		this.regExpItemRelation = regExpItemRelation;
	}

	public boolean isPageUrlAsValue() {
		return pageUrlAsValue;
	}

	public void setPageUrlAsValue(boolean pageUrlAsValue) {
		this.pageUrlAsValue = pageUrlAsValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParseId() {
		return parseId;
	}

	public void setParseId(int parseId) {
		this.parseId = parseId;
	}

	public boolean isIfCheckRepeatColumn() {
		return ifCheckRepeatColumn;
	}

	public void setIfCheckRepeatColumn(boolean ifCheckRepeatColumn) {
		this.ifCheckRepeatColumn = ifCheckRepeatColumn;
	}

	public boolean isIfUrl() {
		return ifUrl;
	}

	public void setIfUrl(boolean ifUrl) {
		this.ifUrl = ifUrl;
	}

	
	public String getSaveToTable() {
		return saveToTable;
	}

	public void setSaveToTable(String saveToTable) {
		this.saveToTable = saveToTable;
	}
	
	public String getSaveToColumn() {
		return saveToColumn;
	}

	public void setSaveToColumn(String saveToColumn) {
		this.saveToColumn = saveToColumn;
	}

	public String getColumnDesc() {
		return columnDesc;
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
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

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getSrcRegExp() {
		return srcRegExp;
	}

	public void setSrcRegExp(String srcRegExp) {
		this.srcRegExp = srcRegExp;
	}

	public String getDestRegExp() {
		return destRegExp;
	}

	public void setDestRegExp(String destRegExp) {
		this.destRegExp = destRegExp;
	}

	public String getExecJavascript() {
		return execJavascript;
	}

	public void setExecJavascript(String execJavascript) {
		this.execJavascript = execJavascript;
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

	public String getByTagAttribute() {
		return byTagAttribute;
	}

	public void setByTagAttribute(String byTagAttribute) {
		this.byTagAttribute = byTagAttribute;
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
