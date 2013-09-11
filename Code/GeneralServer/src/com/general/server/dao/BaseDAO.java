package com.general.server.dao;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BasePageDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.DBTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;
import com.general.server.manager.DBManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;


public class BaseDAO {
	
	protected static String BY_TABLE_NAME = "BY_TABLE_NAME";
	protected static String BY_NAME = "BY_NAME";	
	protected static String BY_ID = "BY_ID";	
	
	protected static String BY_WEB_SITE_ID = "BY_WEB_SITE_ID";	
	protected static String BY_WEB_SITE_ID_RUNNING_THREAD = "BY_WEB_SITE_ID_RUNNING_THREAD";	
	//protected static String BY_WEB_SITE_ID_RUNNING_TEST_THREAD = "BY_WEB_SITE_ID_RUNNING_TEST_THREAD";	
	protected static String BY_WEB_SITE_ID_TEST_THREAD = "BY_WEB_SITE_ID_TEST_THREAD";	
	protected static String BY_WEB_SITE_ID_NOT_RUN = "BY_WEB_SITE_ID_NOT_RUN";	
	protected static String BY_WEB_SITE_PAGE_URL = "BY_WEB_SITE_PAGE_URL";
	protected static String BY_THREAD_TABLE_ID = "BY_THREAD_TABLE_ID";
	protected static String BY_THREAD_TABLE_ID_AND_SITE_ID = "BY_THREAD_TABLE_ID_AND_SITE_ID";
	
	protected static String BY_UNRECEIVED_MQ_MESSAGE = "BY_UNRECEIVED_MQ_MESSAGE";	
	
	protected static String BY_PARSE_TPL_ID = "BY_PARSE_TPL_ID";	
	protected static String BY_CATEGORY_ID = "BY_CATEGORY_ID";	
	protected static String BY_CATEGORY_ID_AND_NAME = "BY_CATEGORY_ID_AND_NAME";	
	//protected static String DB_TYPE_TEST = "DB_TYPE_TEST";	
	//protected static String DB_TYPE_PRODUCT = "DB_TYPE_PRODUCT";
	
	protected StringBuffer sqlBuf = null;	
	protected StringBuffer whereBuf = null;	
	protected StringBuffer orderByBuf = null; 
	
	private BasePageDTO pageDto = null;
	
	public StringBuffer getWhereBuf() {
		return whereBuf;
	}

	public BasePageDTO getPageDto() {
		return pageDto;
	}

	public void setPageDto(BasePageDTO pageDto) {
		this.pageDto = pageDto;
	}
	
	private void insertDto(Object dto, String idFieldName) throws Exception
	{
		Connection conn = null;
		Exception ex = null;
		String sql = "";
		try{		
			conn = DBManager.getInstance().getMysqlDataSource().getConnection();
			Statement stmt = conn.createStatement();			
			sql = DBTool.getInsertSqlFromObjectClass(dto);
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs!=null)
			{
				if(rs.next())
				{
					int id = rs.getInt(1);
					ClassTool.invokeSetMethod(dto, idFieldName, String.valueOf(id));
					rs.close();
				}					
			}
		}catch(MySQLIntegrityConstraintViolationException e)
		{
			
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(sql);
			ex = e;
		}finally
		{
			DBManager.getInstance().closeDBConn(conn);
			if(ex!=null) throw ex;
		}
	}
	
	
	protected void insertDto(Object dto) throws Exception
	{
		this.insertDto(dto, "id");
	}
	
	
	protected void updateDto(Object dto) throws Exception
	{
		this.updateOrDeleteDto(DBTool.getUpdateSqlFromObjectClass(dto));
	}
	
	protected void updateDto(String sql) throws Exception
	{
		this.updateOrDeleteDto(sql);
	}
	
	
	protected void deleteDto(Class objClass) throws Exception
	{
		this.updateOrDeleteDto(DBTool.getDeleteSqlFromObjectClass(objClass));
	}
	
	
	private void updateOrDeleteDto(String sql) throws Exception
	{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(sql);
		this.appendWhereBuffer(sqlBuf);
		this.executeUpdateOrDeleteSql(sqlBuf.toString(), DBManager.getInstance().getMysqlDataSource());
	}
	
	protected List<Object> selectDtoList(Class objClass) throws Exception
	{
		return this.selectDtoList(objClass, DBTool.getSelectSqlFromObjectClass(objClass), DBManager.getInstance().getMysqlDataSource());
	}
	
	protected List<Object> selectDtoList(Class objClass, DataSource ds) throws Exception
	{
		return this.selectDtoList(objClass, DBTool.getSelectSqlFromObjectClass(objClass), ds);
	}
	
	protected int selectDtoCount() throws Exception
	{
		int ret = 0;
		this.appendWhereBuffer(this.sqlBuf);
		
		Connection conn = null;
		Exception ex = null;	
		try{		
			conn = DBManager.getInstance().getMysqlDataSource().getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(this.sqlBuf.toString());
			if(rs.next())
			{
				ret = rs.getInt(1);
			}
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(this.sqlBuf.toString());
			ex = e;
		}finally
		{
			DBManager.getInstance().closeDBConn(conn);
			if(ex!=null) throw ex;
			return ret;
		}		
	}
	
	protected List<Object> selectDtoList(Class objClass, String sql) throws Exception
	{
		return this.selectDtoList(objClass, sql, DBManager.getInstance().getMysqlDataSource());
	}

	protected List<Object> selectDtoListFromInformationSchema(Class objClass) throws Exception
	{
		return this.selectDtoList(objClass, DBTool.getSelectSqlFromObjectClass(objClass), DBManager.getInstance().getMysqlInforSchemaDataSource());
	}
	
	/*
	 * execute insert sql
	 */
	protected void executeInsertSql(String sql, DataSource ds) throws Exception
	{
		Connection conn = null;
		Exception ex = null;

		try{		
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();			
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(sql);
			ex = e;
		}finally
		{
			DBManager.getInstance().closeDBConn(conn);
			if(ex!=null) throw ex;
		}
	}
	
	/*
	 * execute update sql
	 */
	public void executeUpdateOrDeleteSql(String sql, DataSource ds) throws Exception
	{
		Connection conn = null;
		Exception ex = null;	
		try{		
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(sql);
			ex = e;
		}finally
		{
			DBManager.getInstance().closeDBConn(conn);
			if(ex!=null) throw ex;
		}
	}
	
	/*
	 * execute select sql and return hashtable, key==column name, value==column value
	 */
	protected List<Map<String,String>> executeSelectSql(String sql, DataSource ds) throws Exception
	{
		List<Map<String,String>> ret = new ArrayList<Map<String,String>>();

		Connection conn = null;
		Exception ex = null;
		StringBuffer sqlBuf = new StringBuffer();

		try{	
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();			 
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs!=null)
			{
				ResultSetMetaData rsMd = rs.getMetaData();
				int columnCount = rsMd.getColumnCount();
				
				while(rs.next())
				{
					Map<String,String> rowDataMap = new Hashtable<String,String>();
					
					for(int i=1;i<columnCount+1;i++)
					{
						String columnName = rsMd.getColumnName(i);
						String columnTypeName = rsMd.getColumnTypeName(i);
						String columnValue = "";
						
						if(columnTypeName.toLowerCase().indexOf("int")!=-1)
						{
							columnValue = String.valueOf(rs.getInt(columnName));
						}else if(columnTypeName.toLowerCase().indexOf("varchar")!=-1 || columnTypeName.toLowerCase().indexOf("text")!=-1)
						{
							columnValue = String.valueOf(rs.getString(columnName));
						}else if(columnTypeName.toLowerCase().indexOf("float")!=-1)
						{
							columnValue = String.valueOf(rs.getFloat(columnName));
						}else if(columnTypeName.toLowerCase().indexOf("double")!=-1)
						{
							columnValue = String.valueOf(rs.getDouble(columnName));
						}else if(columnTypeName.toLowerCase().indexOf("date")!=-1)
						{
							columnValue = String.valueOf(rs.getDate(columnName));
						}else if(columnTypeName.toLowerCase().indexOf("boolean")!=-1)
						{
							columnValue = String.valueOf(rs.getBoolean(columnName));
						}else if(columnTypeName.toLowerCase().indexOf("decimal")!=-1)
						{
							columnValue = String.valueOf(rs.getBigDecimal(columnName));
						}
						
						rowDataMap.put(columnName, columnValue);
						ret.add(rowDataMap);
					}
				}
			}
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(sqlBuf.toString());
			ex = e;
		}finally
		{
			DBManager.getInstance().closeDBConn(conn);
			if(ex!=null) 
			{
				throw ex;			
			}				
			return ret;
		}
	}
	
	private List<Object> selectDtoList(Class objClass, String originalSql, DataSource ds) throws Exception
	{
		List<Object> ret = new ArrayList<Object>();
		Connection conn = null;
		Exception ex = null;
		StringBuffer sqlBuf = new StringBuffer();

		try{	
			sqlBuf.append(originalSql);
			
			this.appendWhereBuffer(sqlBuf);
			this.appendOrderByBuffer(sqlBuf, objClass);
			this.appendLimitBuffer(sqlBuf);			
			
			String sql = sqlBuf.toString();
			
			LogTool.debugText("sql = " + sql);
			
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();			 
			ResultSet rs = stmt.executeQuery(sql);
			if(rs!=null)
			{
				while(rs.next())
				{			
					if(ClassTool.isNullObj(objClass) || "list".equalsIgnoreCase(objClass.getName()))
					{
						ret.add(DBTool.getResultSetColumnInList(rs));
					}else
					{
						ret.add(objClass.cast(ClassTool.extractValueFromResultSet(objClass, rs)));
					}
				}					
			}
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(sqlBuf.toString());
			ex = e;
		}finally
		{
			try
			{
				if(ex!=null) 
				{
					throw ex;			
				}else
				{
					if(!ClassTool.isListEmpty(ret))
					{
						if(ret.get(0)!=null)
						{
							String selectCountSql = "";
							if(!StringTool.isEmpty(originalSql))
							{
								selectCountSql = DBTool.getSelectCountSqlFromSelectSql(originalSql);
							}else
							{
								selectCountSql = DBTool.getSelectCountSqlFromObjectClass(objClass);	
							}
							if(!StringTool.isEmpty(selectCountSql))
							{
								long totalRecordsCountInThisSearch = this.selectDtoListCount(selectCountSql, conn);
								if(ClassTool.isNullObj(objClass) || "list".equalsIgnoreCase(objClass.getName()))
								{
									if(ret.get(0) instanceof List)
									{
										List tmpList = (List)ret.get(0);
										if(!ClassTool.isListEmpty(tmpList))
										{
											BasePageDTO.class.cast(tmpList.get(0)).setTotalRecordsCountInThisSearch(totalRecordsCountInThisSearch);
										}
									}
								}else
								{
									BasePageDTO.class.cast(ret.get(0)).setTotalRecordsCountInThisSearch(totalRecordsCountInThisSearch);
								}
							}
						}
					}
				}
			}catch(Exception e)
			{
				LogTool.logError( e);
				ex = e;
			}finally
			{
				DBManager.getInstance().closeDBConn(conn);
				
				if(ex!=null) 
				{
					throw ex;			
				}				
				return ret;
			}
		}
	}
	

	private long selectDtoListCount(String originalSql, Connection conn) throws Exception
	{
		long ret = 0;
		Exception ex = null;
		StringBuffer sqlBuf = new StringBuffer();

		try{	
			sqlBuf.append(originalSql);
			String whereClause = this.whereBuf.toString().trim();
			if(whereClause.length()>0)
			{
				sqlBuf.append(" where ");
				sqlBuf.append(whereClause);
			}
			
			String sql = sqlBuf.toString();

			Statement stmt = conn.createStatement();			 
			ResultSet rs = stmt.executeQuery(sql);
			if(rs!=null)
			{
				if(rs.next())
				{				
					ret = rs.getLong(1);
				}					
			}
		}catch(Exception e)
		{
			LogTool.logError( e);
			LogTool.logText(sqlBuf.toString());
			ex = e;
		}finally
		{
			if(ex!=null) throw ex;
			return ret;
		}
	}


	
	protected void initStringBuffer()
	{
		this.sqlBuf = new StringBuffer();
		this.whereBuf = new StringBuffer();
		this.orderByBuf = new StringBuffer();
	}
	
	
	private void appendWhereBuffer(StringBuffer sqlBuf) throws Exception
	{
		if(ClassTool.isNullObj(this.whereBuf))
		{
			this.whereBuf = new StringBuffer();
		}
		
		this.whereBuf.trimToSize();
		if(this.whereBuf.length()>0 || this.getPageDto()!=null)
		{	
			if(this.getPageDto()!=null)
			{
				DBTool.parseSortAndFilter(this.getPageDto());

				if(!StringTool.isEmpty(this.getPageDto().getFilter()))
				{
					this.getPageDto().setFilterOperator(StringTool.isEmpty(this.getPageDto().getFilterOperator(), GeneralConstants.EQUAL_MARK));

					if(this.whereBuf.length()>0)
					{
						whereBuf.append(" and ");
					}
					
					whereBuf.append(this.getPageDto().getFilter());
									
					whereBuf.append(" ");

					if("like".equalsIgnoreCase(this.getPageDto().getFilterOperator()))
					{
						if(StringTool.isEmpty(this.getPageDto().getFilterValue().trim()))
						{
							this.getPageDto().setFilterOperator(GeneralConstants.EQUAL_MARK);
						}
					}
					
					whereBuf.append(this.getPageDto().getFilterOperator());
					
					if("like".equalsIgnoreCase(this.getPageDto().getFilterOperator()) || "not like".equalsIgnoreCase(this.getPageDto().getFilterOperator()))
					{
						whereBuf.append(" '%");
						whereBuf.append(this.getPageDto().getFilterValue().trim());
						whereBuf.append("%' and ");
						whereBuf.append(this.getPageDto().getFilter());
						whereBuf.append(" != '' ");
					}else
					{
						whereBuf.append(" '");
						whereBuf.append(this.getPageDto().getFilterValue().trim());
						whereBuf.append("'");
						
						if(StringTool.isEmpty(this.getPageDto().getFilterValue().trim()))
						{
							if(GeneralConstants.EQUAL_MARK.equals(this.getPageDto().getFilterOperator()))
							{
								whereBuf.append(" or ");
								whereBuf.append(this.getPageDto().getFilter());
								whereBuf.append(" IS NULL ");
							}else if(GeneralConstants.NOT_EQUAL_MARK.equals(this.getPageDto().getFilterOperator()))
							{
								whereBuf.append(" and ");
								whereBuf.append(this.getPageDto().getFilter());
								whereBuf.append(" IS NOT NULL ");
							}
						}
					}
				}
			}
			
			if(this.whereBuf.length()>0)
			{
				sqlBuf.append(" where ");
				sqlBuf.append(this.whereBuf);
			}
		}
	}
	
	private void appendOrderByBuffer(StringBuffer sqlBuf, Class objClass) throws Exception
	{
		if(ClassTool.isNullObj(this.orderByBuf))
		{
			this.orderByBuf = new StringBuffer();
		}
		
		this.orderByBuf.trimToSize();
		if(this.orderByBuf.length()>0 || this.getPageDto()!=null)
		{
			
			if(this.orderByBuf.length()>0)
			{
				sqlBuf.append(" order by ");
				sqlBuf.append(this.orderByBuf);
			}
			
			if(this.getPageDto()!=null)
			{
				DBTool.parseSortAndFilter(this.getPageDto());

				if(!StringTool.isEmpty(this.getPageDto().getSort()))
				{
					if(this.orderByBuf.length()==0)
					{
						sqlBuf.append(" order by ");
					}else if(this.orderByBuf.length()>0)
					{
						sqlBuf.append(", ");
					}
										
					String sort = ClassTool.getDBColumnNameByObjFieldName(objClass, this.getPageDto().getSort());
					if(StringTool.isEmpty(sort))
					{
						sort = StringTool.translateToDBColumn(this.getPageDto().getSort());
					}					
					sqlBuf.append(sort);
					sqlBuf.append(" ");
					sqlBuf.append(this.getPageDto().getDir());
				}
			}
		}
	}
	
	private void appendLimitBuffer(StringBuffer sqlBuf)
	{
		if(this.getPageDto()!=null)
		{
			if(ClassTool.isNullObj(sqlBuf))
			{
				sqlBuf = new StringBuffer();
			}			
			
			sqlBuf.append(" limit ");
			sqlBuf.append(this.getPageDto().getStart());
			sqlBuf.append(", ");
			sqlBuf.append(this.getPageDto().getLimit());
		}
	}
}
