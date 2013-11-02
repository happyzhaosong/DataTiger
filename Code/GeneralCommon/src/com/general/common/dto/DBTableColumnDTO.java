package com.general.common.dto;

import java.util.List;
import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BaseDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.StringTool;
import com.general.common.dto.ParseTplItemDTO;

@DBTable(name="USER_TAB_COLUMNS ut, USER_COL_COMMENTS uc ")
public class DBTableColumnDTO extends BaseDTO implements Comparable{

	@DBColumn(name="ut.TABLE_NAME")
	private String tableName = "";
	
	@DBColumn(name="ut.COLUMN_NAME")
	private String columnName = "";
	
	@DBColumn(name="ut.DATA_TYPE")
	private String columnType = "";
	
	@DBColumn(name="ut.DATA_LENGTH")
	private String columnMaxLen = "";
	
	@DBColumn(name="uc.COMMENTS")
	private String columnDesc = "";
	
	private String columnValue = "";
	
	private boolean ifCheckRepeatColumn = false;
	
	private boolean pk = false;
	
	private boolean notNull = false;
	
	private boolean unSigned = false;
	
	private boolean autoIncrement = false;
	
	private boolean zeroFill = false;
	
	public boolean isIfCheckRepeatColumn() {
		return ifCheckRepeatColumn;
	}
	public void setIfCheckRepeatColumn(boolean ifCheckRepeatColumn) {
		this.ifCheckRepeatColumn = ifCheckRepeatColumn;
	}
	public String getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}
	public boolean isPk() {
		return pk;
	}
	public void setPk(boolean pk) {
		this.pk = pk;
	}
	public boolean isNotNull() {
		return notNull;
	}
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	public boolean isUnSigned() {
		return unSigned;
	}
	public void setUnSigned(boolean unSigned) {
		this.unSigned = unSigned;
	}
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	public boolean isZeroFill() {
		return zeroFill;
	}
	public void setZeroFill(boolean zeroFill) {
		this.zeroFill = zeroFill;
	}
	public String getColumnMaxLen() {
		return columnMaxLen;
	}
	public void setColumnMaxLen(String columnMaxLen) {
		this.columnMaxLen = columnMaxLen;
	}
	public String getColumnDesc() {
		return columnDesc;
	}
	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return StringTool.isEmpty(columnName,"");
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		if(StringTool.isEmpty(columnType))
		{
			this.columnType = GeneralConstants.DATA_TYPE_STRING;
		}
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String toString(List<ParseTplItemDTO> parseItemList) {
		StringBuffer retBuf = new StringBuffer();
		retBuf.append("Type = ");
		retBuf.append(this.getColumnType());
		retBuf.append(" <br/> ");
		retBuf.append(this.getColumnName());
		retBuf.append(" = ");		
		retBuf.append(StringTool.escapeHtmlTagInString(this.getColumnValue()));
		
		if(!ClassTool.isNullObj(parseItemList))
		{
			int size = parseItemList.size();
			for(int i=0;i<size;i++)
			{
				ParseTplItemDTO dto = parseItemList.get(i);
				if(this.getTableName().equals(dto.getSaveToTable()) && this.getColumnName().equals(dto.getSaveToColumn()))
				{
					/*
					retBuf.append(" <br/> XPath = ");
					retBuf.append(dto.getXpath());
					retBuf.append(" <br/> XPath Attribute = ");
					retBuf.append(dto.getXpathEleAttributeNames());
					*/
					retBuf.append(" <br/> Charactor = ");
					retBuf.append(dto.getCharactor());
					retBuf.append(" <br/> NotCharactor = ");
					retBuf.append(dto.getNotCharactor());
				}
			}
		}
		
		retBuf.append("<br/><br/>");
		return retBuf.toString();
	}
	@Override
	public int compareTo(Object o) {
		if(o==null || !(o instanceof DBTableColumnDTO))
		{
			return -1;
		}else
		{
			DBTableColumnDTO that = (DBTableColumnDTO)o;
			return this.getColumnName().compareTo(that.getColumnName());
		}
	}
}
