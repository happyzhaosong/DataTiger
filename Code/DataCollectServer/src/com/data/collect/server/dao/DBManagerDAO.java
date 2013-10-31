package com.data.collect.server.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.DBTableDTO;
import com.general.common.dto.DBTableColumnDTO;
import com.general.common.dto.ParseTplItemDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.ExceptionTool;
import com.general.common.util.GetDeltaTimeTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;
import com.general.server.dao.BaseDAO;
import com.general.server.manager.DBManager;

public class DBManagerDAO extends BaseDAO {
	
	public void saveDataTableContentPageUselessOrNot(String dataTableName, String dataTableIds, String useless) throws Exception
	{
		this.initStringBuffer();
		this.sqlBuf.append(" update ");
		this.sqlBuf.append(dataTableName);
		this.sqlBuf.append(" set ");
		this.sqlBuf.append(Constants.DATA_TABLE_COLUMN_NAME_DOWNLOAD_TASK_USELESS_CONTENT_PAGE);
		this.sqlBuf.append(Constants.EQUAL_MARK);
		this.sqlBuf.append(useless);
		
		this.whereBuf.append(" id in(");
		this.whereBuf.append(dataTableIds);
		this.whereBuf.append(")");
		
		this.updateDto(this.sqlBuf.toString());
	}
	
	
	
	//use unique index on table download_task, data_tao_bao_jie, and catch Duplicate Exception, this way can reduce check duplicate and synchronized method in java
	public String saveDataTable(DBTableDTO dto, List<ParseTplItemDTO> parseItemList) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		GetDeltaTimeTool getDltaTimeTool = new GetDeltaTimeTool();

		boolean selectPass = false;
		boolean updatePass = false;
		boolean insertPass = false;
		
		//for insert new row.
		StringBuffer insertSqlBuf = new StringBuffer();
		StringBuffer insertValueBuf = new StringBuffer();
		
		//for check repeat
		StringBuffer selectSqlBuf = new StringBuffer();
				
		//if has repeated row then use to update related row.
		StringBuffer updateSqlBuf = new StringBuffer();
		
		try
		{	
			insertSqlBuf.append("insert into ");
			insertSqlBuf.append(dto.getTableName());
			insertSqlBuf.append("(");
			
			insertValueBuf.append(" values(");
			
			selectSqlBuf.append("select id from ");
			selectSqlBuf.append(dto.getTableName());
			selectSqlBuf.append(" where ");
			
			updateSqlBuf.append("update  ");		
			updateSqlBuf.append(dto.getTableName());
			updateSqlBuf.append(" set  ");
	
			List<DBTableColumnDTO> columnList = dto.getColumnList();
			int size = columnList.size();
			for(int i=0;i<size;i++)
			{
				DBTableColumnDTO columnDto = columnList.get(i);
				
				//build insert sql
				this.buildInsertSql(insertSqlBuf, insertValueBuf, columnDto);
				if(i<size-1)
				{
					insertSqlBuf.append(Constants.SEPERATOR_COMMA);
					insertValueBuf.append(Constants.SEPERATOR_COMMA);
				}
				
				//build select sql
				this.buildSelectSql(selectSqlBuf, columnDto);
			
				//build update sql
				this.buildUpdateSql(updateSqlBuf, columnDto);	
				
			}
			
			insertSqlBuf.append(")");
			insertSqlBuf.append(insertValueBuf);
			insertSqlBuf.append(")");
			
			
			String selectSql = selectSqlBuf.toString().trim();
			//need to check repeat row
			if(selectSql.endsWith("and"))
			{
				List<Map<String,String>> repeatRowList = this.executeSelectSql(selectSql.substring(0,selectSql.length()-3), DBManager.getInstance().getDataTblDataSource());
				selectPass = true;
				long deltaTime = getDltaTimeTool.getDeltaTime("executeSelectSql", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
				if(deltaTime> Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND)
				{
					retBuf.append(" , sql = ");
					retBuf.append(selectSql.substring(0,selectSql.length()-3));
				}				

				if(!ClassTool.isNullObj(repeatRowList))
				{				
					int repeatRowListSize = repeatRowList.size();
					//if has repeat row by check repeat column value
					if(repeatRowListSize>0)
					{
						Map<String,String> rowData = repeatRowList.get(0);
						String id = rowData.get("id");
												
						updateSqlBuf.deleteCharAt(updateSqlBuf.lastIndexOf(Constants.SEPERATOR_COMMA));						
						updateSqlBuf.append(" where id = ");
						updateSqlBuf.append(id);

						this.executeUpdateOrDeleteSql(updateSqlBuf.toString(), DBManager.getInstance().getDataTblDataSource());
						updatePass = true;
						deltaTime = getDltaTimeTool.getDeltaTime("executeUpdateSql", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
						if(deltaTime> Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND)
						{
							retBuf.append(" , sql = ");
							retBuf.append(updateSqlBuf.toString());
						}						
					}else
					{
						//insert new row
						this.executeInsertSql(insertSqlBuf.toString(), DBManager.getInstance().getDataTblDataSource());
						insertPass = true;
						deltaTime = getDltaTimeTool.getDeltaTime("executeInsertSql", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
						if(deltaTime> Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND)
						{
							retBuf.append(" , sql = ");
							retBuf.append(insertSqlBuf.toString());
						}
					}
				}
			}else
			{
				//insert new row
				this.executeInsertSql(insertSqlBuf.toString(), DBManager.getInstance().getDataTblDataSource());
				insertPass = true;
				long deltaTime = getDltaTimeTool.getDeltaTime("executeInsertSql", retBuf, Constants.MIN_RECORD_DURATION_TIME, false);
				if(deltaTime> Constants.DOWNLOAD_THREAD_SLEEP_TIME_1_SECOND)
				{
					retBuf.append(" , sql = ");
					retBuf.append(insertSqlBuf.toString());
				}
			}
		}catch(Exception ex)
		{
			LogTool.logError(ex, this.getClass().getName());
			
			String exMsg = ex.getMessage();
			
			StringBuffer errMsgBuf = new StringBuffer();			
			errMsgBuf.append(exMsg);
			errMsgBuf.append("<br/><br/>");
			
			if(!selectPass)
			{
				errMsgBuf.append("********************************************Select Error****************************************************");				
				errMsgBuf.append("<br/><br/>");
				
				if(!ExceptionTool.isDataTruncationException(ex))
				{
					errMsgBuf.append(selectSqlBuf.toString());
				}
			}else if(!updatePass)
			{
				errMsgBuf.append("**********************************************Update Error**************************************************");
				errMsgBuf.append("<br/><br/>");
				
				if(!ExceptionTool.isDataTruncationException(ex))
				{
					errMsgBuf.append(updateSqlBuf.toString());
				}
				
			}else if(!insertPass)
			{
				errMsgBuf.append("******************************************Insert Error******************************************************");
				errMsgBuf.append("<br/><br/>");
				
				if(!ExceptionTool.isDataTruncationException(ex))
				{
					errMsgBuf.append(insertSqlBuf.toString());	
				}
			}
			
			if(ExceptionTool.isDataTruncationException(ex))
			{
				errMsgBuf.append(dto.toString(parseItemList));
			}
			
			throw new Exception(errMsgBuf.toString());
		}finally
		{
			return retBuf.toString();
		}
	}
	

	
	/*
	 * build update sql by check repeat column
	 * */
	private void buildUpdateSql(StringBuffer updateSqlBuf, DBTableColumnDTO columnDto) throws Exception
	{
		//update field which value is empty or not empty string
		String cValue = columnDto.getColumnValue();
		if(!StringTool.isEmpty(cValue))
		{
			updateSqlBuf.append(columnDto.getColumnName());
			updateSqlBuf.append(" = ");
			updateSqlBuf.append(this.getColumnValueByColumnType(columnDto.getColumnName(), columnDto.getColumnType(), cValue));
			updateSqlBuf.append(Constants.SEPERATOR_COMMA);
		}
	}
	
	private String getColumnValueByColumnType(String cName, String cType, String cValue) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		cType = StringTool.isEmpty(cType, Constants.DATA_TYPE_STRING);
		cValue = StringTool.isEmpty(cValue, "").trim();
		if(Constants.DATA_TYPE_STRING.equalsIgnoreCase(cType))
		{
			retBuf.append(Constants.SEPERATOR_SINGLE_QUOTES);
			retBuf.append(cValue.trim().replaceAll("'", "\\\\'"));		
			retBuf.append(Constants.SEPERATOR_SINGLE_QUOTES);
		}else if(Constants.DATA_TYPE_NUMBER.equalsIgnoreCase(cType))
		{
			if(StringTool.isNumeric(cValue))
			{
				retBuf.append(cValue.trim());	
			}else
			{
				LogTool.debugText("Not a number value, column name : " + cName + ", parsed out value : " + cValue, this.getClass().getName());
				retBuf.append(-1);
			}
		}else if(Constants.DATA_TYPE_BOOLEAN.equalsIgnoreCase(cType))
		{
			if(StringTool.isEmpty(cValue))
			{
				retBuf.append(0);
			}else
			{
				LogTool.debugText("Boolean value check , column name : " + cName + ", parsed out value : " + cValue, this.getClass().getName());
				retBuf.append(1);
			}
		}
		return retBuf.toString();
	}
	
	
	
	/*
	 * build insert sql
	 * */
	private void buildInsertSql(StringBuffer insertSqlBuf, StringBuffer insertValueBuf, DBTableColumnDTO columnDto) throws Exception
	{
		insertSqlBuf.append(columnDto.getColumnName());		
		insertValueBuf.append(this.getColumnValueByColumnType(columnDto.getColumnName(), columnDto.getColumnType(), columnDto.getColumnValue()));
	}
	
	
	/*
	 * build select sql
	 * */
	private void buildSelectSql(StringBuffer selectSqlBuf, DBTableColumnDTO columnDto) throws Exception
	{
		if(columnDto.isIfCheckRepeatColumn())
		{
			if(!StringTool.isEmpty(columnDto.getColumnValue()))
			{			
				selectSqlBuf.append(columnDto.getColumnName());
				selectSqlBuf.append(" = ");
				
				selectSqlBuf.append(this.getColumnValueByColumnType(columnDto.getColumnName(), columnDto.getColumnType(), columnDto.getColumnValue()));
	
				selectSqlBuf.append(" and ");
			}
		}
	}

	/*
	 * Return the table records list, each element in list is a list object represents one row of data.
	 * */
	public List<List<DBTableColumnDTO>> getDataTableDataList(String tableName) throws Exception {
		List<List<DBTableColumnDTO>> ret = new ArrayList<List<DBTableColumnDTO>>();
		if(!StringTool.isEmpty(tableName))
		{
			this.initStringBuffer();
	    	
	    	this.sqlBuf.append("select * from ");
	    	this.sqlBuf.append(tableName);  	
	    		    
	    	this.orderByBuf.append(" download_task_data_parse_time desc ");
	    	
	    	ret = ret.getClass().cast(this.selectDtoList(null, this.sqlBuf.toString()));
		}
		return ret;
	}
	
	
	public List<DBTableDTO> getDataTableList() throws Exception {
		return this.getDataTableListBy("", "");
	}

	public List<DBTableColumnDTO> getDataTableColumnList(String tableName) throws Exception
	{
		if(tableName==null || "".equals(tableName.trim()))
		{
			return new ArrayList<DBTableColumnDTO>();
		}else
		{
			return this.getDataTableColumnListBy(this.BY_TABLE_NAME, tableName);
		}
	}
	
	
	public String getDataTableColumnDownloadTaskIdStringSeperateBy(List<List<DBTableColumnDTO>> dataColumnList, String seperator)
	{
		StringBuffer retBuf = new StringBuffer();
		if(!ClassTool.isListEmpty(dataColumnList))
		{
			int size = dataColumnList.size();
			for(int i=0;i<size;i++)
			{
				List<DBTableColumnDTO> cList = dataColumnList.get(i);
				if(!ClassTool.isListEmpty(cList))
				{
					int cSize = cList.size();
					for(int j=0;j<cSize;j++)
					{
						DBTableColumnDTO cDto = cList.get(j);
						if("download_task_id".equalsIgnoreCase(cDto.getColumnName()))
						{
							retBuf.append(cDto.getColumnValue());
							retBuf.append(seperator);
						}
					}
				}
			}
		}
		
		String ret = retBuf.toString();
		if(ret.lastIndexOf(seperator) == (ret.length()-1))
		{
			ret = ret.substring(0, ret.length()-1);
		}
		return ret;
	}
	
	
    private List<DBTableDTO> getDataTableListBy(String byKey, String byValue) throws Exception
	{    	
    	this.initStringBuffer();   
    	
    	/*
    	this.whereBuf.append(" table_schema = '");
		this.whereBuf.append(DBManager.getInstance().getDBMQConfig().getDbName());
		this.whereBuf.append("' and table_name like '");
		this.whereBuf.append(Constants.DATA_TABLE_PREFIX);
		
		if(this.BY_TABLE_NAME.equals(byKey))
		{
			if(!StringTool.isEmpty(byValue))
			{
				this.whereBuf.append(byValue);
			}
		}
		
		this.whereBuf.append("%'");
		*/
		
    	if(this.BY_TABLE_NAME.equals(byKey))
		{
			this.whereBuf.append(" lower(table_name) like '");
			this.whereBuf.append(Constants.DATA_TABLE_PREFIX);

			if(!StringTool.isEmpty(byValue))
			{
				this.whereBuf.append(byValue);				
			}
			
			this.whereBuf.append("%'");
		}   	
    	
		List<DBTableDTO> ret = new ArrayList<DBTableDTO>();
		ret = ret.getClass().cast(this.selectDtoListFromInformationSchema(DBTableDTO.class));
		if(ret!=null)
		{
			int size = ret.size();
			for(int i=0;i<size;i++)
			{
				DBTableDTO dto = ret.get(i);
				List<DBTableColumnDTO> cList = this.getDataTableColumnListBy(this.BY_TABLE_NAME, dto.getTableName());
				dto.setColumnList(cList);
			}
		}
		return ret;
	}
    
    
    private List<DBTableColumnDTO> getDataTableColumnListBy(String byKey, String byValue) throws Exception
	{    	
    	this.initStringBuffer();
    	/*
    	this.whereBuf.append(" table_schema = '");
		this.whereBuf.append(DBManager.getInstance().getDBMQConfig().getDbName());
		this.whereBuf.append("'");
		
		if(this.BY_TABLE_NAME.equals(byKey))
		{
			this.whereBuf.append(" and table_name  = '");
			this.whereBuf.append(byValue);
			this.whereBuf.append("'");					
		}
		
		//this.orderByBuf.append(" column_name asc ");
		*/
		
		if(this.BY_TABLE_NAME.equals(byKey))
		{
			if(!StringTool.isEmpty(byValue))
			{
				this.whereBuf.append(" and table_name  = '");
				this.whereBuf.append(byValue);
				this.whereBuf.append("'");
			}
		}
		
		List<DBTableColumnDTO> ret = new ArrayList<DBTableColumnDTO>();
		return ret.getClass().cast(this.selectDtoListFromInformationSchema(DBTableColumnDTO.class));
	}	
}
