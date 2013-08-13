package com.data.collect.common.dto;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.annotation.DBColumn;
import com.data.collect.common.annotation.DBTable;

@DBTable(name="tables")
public class DBTableDTO extends BaseDTO {
	
	@DBColumn(name="TABLE_NAME")
	private String tableName = "";
	
	@DBColumn(name="TABLE_COMMENT")
	private String tableDesc = "";
	
	private List<DBTableColumnDTO> columnList = null;
	
	public List<DBTableColumnDTO> getColumnList() {
		if(columnList == null)
		{
			columnList = new ArrayList<DBTableColumnDTO>();
		}
		return columnList;
	}

	public void setColumnList(List<DBTableColumnDTO> columnList) {
		this.columnList = columnList;
	}

	public String getTableName() {
		if(tableName == null)
		{
			tableName = "";
		}
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String toString(List<ParseTplItemDTO> parseItemList) {
		StringBuffer retBuf = new StringBuffer();
		retBuf.append("Table Name = ");
		retBuf.append(this.getTableName());
		retBuf.append("<br/><br/>");
		
		int size = this.getColumnList().size();
		for(int i=0;i<size;i++)
		{
			DBTableColumnDTO columnDto = this.getColumnList().get(i);
			retBuf.append(columnDto.toString(parseItemList));
		}
		
		return retBuf.toString();
	}
	
	
}
