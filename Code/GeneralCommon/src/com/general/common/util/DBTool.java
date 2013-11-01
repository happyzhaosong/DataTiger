package com.general.common.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.general.common.annotation.DBColumn;
import com.general.common.annotation.DBTable;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.DBTableColumnDTO;


public class DBTool extends BaseTool {
	
	public static List<DBTableColumnDTO> getResultSetColumnInList(ResultSet rs) throws SQLException
	{
		StringBuffer summaryBuf = new StringBuffer();
		List<DBTableColumnDTO> rowData = new ArrayList<DBTableColumnDTO>();
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		for(int i=1;i<=columnCount;i++)
		{
			String columnName = metaData.getColumnName(i);
			String columnValue = "";
			String columnTypeName = metaData.getColumnTypeName(i);
			
			Object valueObj = rs.getObject(i);
			if(!ClassTool.isNullObj(valueObj))
			{
				columnValue = valueObj.toString();
			}
			/*
			if("VARCHAR".equalsIgnoreCase(columnTypeName))
			{
				columnValue = rs.getString(i);
			}else if("BOOLEAN".equalsIgnoreCase(columnTypeName))
			{
				columnValue = String.valueOf(rs.getBoolean(i));
			}else if("INTEGER".equalsIgnoreCase(columnTypeName) || "INT".equalsIgnoreCase(columnTypeName))
			{
				columnValue = String.valueOf(rs.getInt(i));
			}else if("BIGINT".equalsIgnoreCase(columnTypeName))
			{
				columnValue = String.valueOf(rs.getBigDecimal(i));
			}else if("DATE".equalsIgnoreCase(columnTypeName))
			{
				columnValue = String.valueOf(rs.getDate(i));
			}else if("TIME".equalsIgnoreCase(columnTypeName))
			{
				columnValue = String.valueOf(rs.getTime(i));
			}
			*/
			
			if(!StringTool.isEmpty(columnName))
			{
				columnValue = StringTool.isEmpty(columnValue, "");				
				DBTableColumnDTO columnDto = new DBTableColumnDTO();
				columnDto.setColumnName(columnName);
				columnDto.setColumnValue(columnValue);
				columnDto.setColumnType(columnTypeName);
				rowData.add(columnDto);
				
				if(!StringTool.isEmpty(columnValue))
				{
					if(summaryBuf.length()<1000)
					{
						summaryBuf.append(columnName);
						summaryBuf.append(" : ");
						summaryBuf.append(StringTool.isEmpty(columnValue, ""));
						summaryBuf.append("||");
					}
				}
			}
		}
		
		/*
		//add summary column dto
		DBTableColumnDTO summaryColumnDto = new DBTableColumnDTO();
		summaryColumnDto.setColumnName(Constants.DATA_TABLE_SUMMARY_COLUMN_NAME);
		summaryColumnDto.setColumnValue(StringTool.isEmpty(summaryBuf.toString(), ""));
		summaryColumnDto.setColumnType("String");
		rowData.add(summaryColumnDto);		
		*/
		Object objArray[] = rowData.toArray();
		Arrays.sort(objArray);
			
		List<DBTableColumnDTO> ret = new ArrayList<DBTableColumnDTO>();
		if(!ClassTool.isNullObj(objArray))
		{
			int len = objArray.length;
			for(int i=0;i<len;i++)
			{
				ret.add((DBTableColumnDTO)objArray[i]);
			}
		}		
		return ret;
	}
	
	public static String getStringValueFromResultSet(ResultSet result, String columnName, String columnType) throws Exception
	{
		if(GeneralConstants.CLASS_TYPE_INT.equals(columnType))
		{
			return String.valueOf(result.getInt(columnName));
		}else if(GeneralConstants.CLASS_TYPE_LONG.equals(columnType))
		{
			return String.valueOf(result.getLong(columnName));
		}else if(GeneralConstants.CLASS_TYPE_BOOLEAN.equals(columnType))
		{
			return String.valueOf(result.getBoolean(columnName));
		}else if(GeneralConstants.CLASS_TYPE_DOUBLE.equals(columnType))
		{
			return String.valueOf(result.getDouble(columnName));
		}else if(GeneralConstants.CLASS_TYPE_STRING.equals(columnType))
		{
			String ret = result.getString(columnName);
			if(ret==null) 
			{
				return "";
			}else
			{
				return ret.trim();
			}
		}else
		{
			Object obj = result.getObject(columnName);
			if(obj==null)
			{
				return "";
			}else
			{
				return obj.toString().trim();
			}
		}		
	}
	
	public static String getUpdateSqlFromObjectClass(Object objInstance) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		Class objClass = objInstance.getClass();
		DBTable tAnno = (DBTable)objClass.getAnnotation(DBTable.class);
		if(tAnno==null)
		{
			throw new Exception("Must use DBTable annotation to indicate object related table name.");
		}else
		{
			retBuf.append("update ");
			retBuf.append(tAnno.name());
			retBuf.append(" set ");
			
			Map<String, Field> cfMap = ClassTool.getAnnoFieldMap(objClass);
			Set<String> keys = cfMap.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext())
			{
				String columnName = it.next();
				Field field = cfMap.get(columnName);				
				DBColumn col = field.getAnnotation(DBColumn.class);
				if(!col.autoIncreate())
				{
					retBuf.append(columnName);
					retBuf.append("=");
					String value = getInsertOrUpdateFieldValue(field,objInstance);
					retBuf.append(value);
					retBuf.append(GeneralConstants.SEPERATOR_COMMA);
				}				
			}
			StringTool.removeLastIndexCharactor(retBuf, GeneralConstants.SEPERATOR_COMMA);		
		}		
		return retBuf.toString();
	}

	public static String getDeleteSqlFromObjectClass(Class objClass) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		DBTable tAnno = (DBTable)objClass.getAnnotation(DBTable.class);
		if(tAnno==null)
		{
			throw new Exception("Must use DBTable annotation to indicate object related table name.");
		}else
		{
			retBuf.append("delete from ");
			retBuf.append(tAnno.name());
			retBuf.append(" ");
		}		
		return retBuf.toString();
	}

	public static String getSelectCountSqlFromSelectSql(String sql) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		if(sql==null) sql = "";
		sql = sql.trim().toLowerCase();
		if(sql.startsWith("select"))
		{
			String sqlArr[] = sql.split("from");
			String sqlSuffix = sqlArr[1];
			retBuf.append("select count(*) from ");
			retBuf.append(sqlSuffix);
		}else
		{
			return "";
		}		
		return retBuf.toString();
	}

	
	public static String getSelectCountSqlFromObjectClass(Class objClass) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		DBTable tAnno = (DBTable)objClass.getAnnotation(DBTable.class);
		if(tAnno==null)
		{
			throw new Exception("Must use DBTable annotation to indicate object related table name.");
		}else
		{
			retBuf.append(" select count(*) from ");
			retBuf.append(tAnno.name());
		}		
		return retBuf.toString();
	}
	
	public static String getSelectSqlFromObjectClass(Class objClass) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		DBTable tAnno = (DBTable)objClass.getAnnotation(DBTable.class);
		if(tAnno==null)
		{
			throw new Exception("Must use DBTable annotation to indicate object related table name.");
		}else
		{
			retBuf.append(" select ");
						
			Map<String, Field> cfMap = ClassTool.getAnnoFieldMap(objClass);
			Set<String> keys = cfMap.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext())
			{
				String columnName = it.next();				
				retBuf.append(columnName);
				retBuf.append(GeneralConstants.SEPERATOR_COMMA);				
			}			
			StringTool.removeLastIndexCharactor(retBuf, GeneralConstants.SEPERATOR_COMMA);			
			retBuf.append(" from ");
			retBuf.append(tAnno.name());
			retBuf.append("  ");
		}		
		return retBuf.toString();
	}	
	
	public static String getInsertSqlFromObjectClass(Object objInstance) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		Class objClass = objInstance.getClass();
		DBTable tAnno = (DBTable)objClass.getAnnotation(DBTable.class);
		if(tAnno==null)
		{
			throw new Exception("Must use DBTable annotation to indicate object related table name.");
		}else
		{
			retBuf.append(" insert into ");
			retBuf.append(tAnno.name());			
						
			StringBuffer columnBuf = new StringBuffer();
			StringBuffer valueBuf = new StringBuffer();
			columnBuf.append("(");
			valueBuf.append(" values(");
			
			Map<String, Field> cfMap = ClassTool.getAnnoFieldMap(objClass);
			Set<String> keys = cfMap.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext())
			{
				String columnName = it.next();
				Field field = cfMap.get(columnName);				
				DBColumn col = field.getAnnotation(DBColumn.class);
				if(!col.autoIncreate())
				{					
					columnBuf.append(columnName);
					columnBuf.append(GeneralConstants.SEPERATOR_COMMA);		
					
					String value = getInsertOrUpdateFieldValue(field,objInstance);
					
					valueBuf.append(value);	
					valueBuf.append(GeneralConstants.SEPERATOR_COMMA);
				}
			}
			
			StringTool.removeLastIndexCharactor(columnBuf, GeneralConstants.SEPERATOR_COMMA);
			StringTool.removeLastIndexCharactor(valueBuf, GeneralConstants.SEPERATOR_COMMA);
			
			columnBuf.append(")  ");
			valueBuf.append(")");
			
			retBuf.append(columnBuf);
			retBuf.append(valueBuf);
		}		
		return retBuf.toString();
	}
	
	private static String getInsertOrUpdateFieldValue(Field field, Object objInstance) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		String fieldType = field.getType().toString();
		if(GeneralConstants.CLASS_TYPE_STRING.equals(fieldType))
		{
			buf.append(GeneralConstants.SEPERATOR_SINGLE_QUOTES);
		}
		
		Object ret = ClassTool.invokeGetMethod(objInstance, field);
		if(GeneralConstants.CLASS_TYPE_STRING.equals(fieldType))
		{
			if(!ClassTool.isNullObj(ret))
			{
				ret = ret.toString().replaceAll("'", "\\\\'");
			}
		}
		buf.append(ret);
		
		
		if(GeneralConstants.CLASS_TYPE_STRING.equals(fieldType))
		{
			buf.append(GeneralConstants.SEPERATOR_SINGLE_QUOTES);
		}
		return buf.toString();
	}
	
	
	public static BasePageDTO createPageDto(String sort, String dir, String start, String limit, String page) throws Exception
	{
		BasePageDTO pageDto = new BasePageDTO();				
		pageDto.setSort(sort);
		pageDto.setDir(dir);
		pageDto.setStart(start);
		pageDto.setLimit(limit);
		pageDto.setPage(page);
		return pageDto;
	}
	
	
	public static String getCurrPageSql(String sql, int start, int limit)
	{
		if(!StringTool.isEmpty(sql))
		{
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append(" select * from (select rownum r, t.* from ( ");
			sqlBuf.append(sql);
			sqlBuf.append(" ) t where rownum < ");
			sqlBuf.append(start + limit); 
			sqlBuf.append(" ) where r > =");
			sqlBuf.append(start);
			return sqlBuf.toString();
		}else
		{
			return "";
		}		
	}
	
	
	public static void parseSortAndFilter(BasePageDTO pageDto) throws Exception
	{
		if(!ClassTool.isNullObj(pageDto))
		{
			String sort = pageDto.getSort();
			if(!StringTool.isEmpty(sort) && sort.startsWith("[{") && sort.endsWith("}]"))
			{
				sort = StringTool.trimSpecialCharactor(sort, "[");
				sort = StringTool.trimSpecialCharactor(sort, "]");
				JSONObject jsonObj = JSONObject.fromObject(sort);
				sort = (String)jsonObj.get("property");
				String dir = (String)jsonObj.get("direction");
				
				pageDto.setSort(sort);
				pageDto.setDir(dir);
			}			
			
			String filter = pageDto.getFilter();
			if(!StringTool.isEmpty(filter) && filter.startsWith("[{") && filter.endsWith("}]"))
			{
				filter = StringTool.trimSpecialCharactor(filter, "[");
				filter = StringTool.trimSpecialCharactor(filter, "]");
				JSONObject jsonObj = JSONObject.fromObject(filter);
				filter = (String)jsonObj.get("property");
				String filterValue = (String)jsonObj.get("value");
				
				pageDto.setFilter(filter);
				pageDto.setFilterValue(filterValue);
			}
			
			pageDto.setSort(StringTool.translateToDBColumn(pageDto.getSort()));
			pageDto.setFilter(StringTool.translateToDBColumn(pageDto.getFilter()));
		}else
		{
		}
	}
}
