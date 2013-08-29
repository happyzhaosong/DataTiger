package com.general.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.general.common.annotation.DBColumn;
import com.general.common.constants.GeneralConstants;


public class ClassTool extends BaseTool {
	
	public static String getSetMethod(String fieldName) throws Exception
	{
		return getMethodByPrefix(fieldName, GeneralConstants.CLASS_METHOD_PREFIX_SET);
	}
	
	public static String getGetMethod(String fieldName, String fieldType) throws Exception
	{
		String methodPrefix = "";
		if(GeneralConstants.CLASS_TYPE_BOOLEAN.equalsIgnoreCase(fieldType))
		{
			methodPrefix = GeneralConstants.CLASS_METHOD_PREFIX_IS;
		}else
		{
			methodPrefix = GeneralConstants.CLASS_METHOD_PREFIX_GET;
		}
		return getMethodByPrefix(fieldName, methodPrefix);
	}
	
	public static String getMethodByPrefix(String fieldName, String prefix) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(prefix);
		String firstCharactor = fieldName.substring(0,1);
		buf.append(firstCharactor.toUpperCase());
		buf.append(fieldName.substring(1));
		return buf.toString();
	}
	
	
	public static Object getFirstObjectInList(List objList, Class objClass) throws Exception
	{
		if(objList==null)
		{
			return objClass.newInstance();
		}else
		{
			int size = objList.size();
			if(size==0)
			{
				return objClass.newInstance();
			}else
			{
				return objList.get(0);
			}
		}
	}
	
	public static boolean isListEmpty(List objList)
	{
		if(objList==null)
		{
			return true;
		}else
		{
			int size = objList.size();
			if(size==0)
			{
				return true;
			}else
			{
				return false;
			}
		}
	}
	
	public static Class getClass(String type) throws Exception
	{
		Class ret = null;
		if(GeneralConstants.CLASS_TYPE_STRING.equals(type))
		{
			ret = String.class;			
		}else if(GeneralConstants.CLASS_TYPE_INT.equals(type))
		{
			ret = int.class;
		}else if(GeneralConstants.CLASS_TYPE_LONG.equals(type))
		{
			ret = long.class;
		}else if(GeneralConstants.CLASS_TYPE_DOUBLE.equals(type))
		{
			ret = double.class;
		}else if(GeneralConstants.CLASS_TYPE_BOOLEAN.equals(type))
		{
			ret = boolean.class;
		}
		return ret;
	}
	
	public static <T> List<Object> getObjList(List<T> list)
	{
		List<Object> ret = new ArrayList<Object>();
		if(list!=null)
		{
			int size = list.size();
			for(int i=0;i<size;i++)
			{
				T t = list.get(i);
				if(t!=null)
				{
					ret.add(t);
				}
			}
		}
		return ret;
	}
	
	public static <T> List<Object> getObjectFieldValueList(List<T> list, String fieldName) throws Exception
	{
		List<Object> ret = new ArrayList<Object>();
		if(list!=null)
		{
			int size = list.size();
			for(int i=0;i<size;i++)
			{
				T t = list.get(i);
				if(t!=null)
				{
					Map<String, Object> map = ClassTool.getObjectFieldValueMap((Object)t);
					Object val = map.get(fieldName);
					if(!ClassTool.isNullObj(val))
					{
						ret.add(val);
					}
				}
			}
		}
		return ret;
	}
	
	
	public static void invokeSetMethod(Object obj, String fieldName, String value) throws Exception
	{
		try
		{
			Field field = obj.getClass().getDeclaredField(fieldName);
			ClassTool.invokeSetMethod(obj, field, value);
		}catch(Exception ex)
		{	
			LogTool.logError( ex);
			StringBuffer buf = new StringBuffer();
			buf.append("FieldName:");
			buf.append(fieldName);
			buf.append("Error Message:");
			buf.append(ex.getMessage());
			ex = new Exception(buf.toString());
			LogTool.logError( ex);
			throw ex;					
		}
	}
	
	public static void invokeSetMethod(Object obj, Field field, String value) throws Exception
	{
		String fieldName = "";
		String fieldType = ""; 
		try
		{
			fieldName = field.getName();
			fieldType = field.getType().toString(); 
			
			Class param[] = new Class[1];
			param[0] = ClassTool.getClass(fieldType);
			Method method = obj.getClass().getDeclaredMethod(ClassTool.getSetMethod(fieldName), param);
			
			if(GeneralConstants.CLASS_TYPE_STRING.equals(fieldType))
			{
				method.invoke(obj, value);
			}else if(GeneralConstants.CLASS_TYPE_INT.equals(fieldType))
			{
				method.invoke(obj, StringTool.getIntegerValInStr(value));				
			}else if(GeneralConstants.CLASS_TYPE_LONG.equals(fieldType))
			{
				method.invoke(obj, StringTool.getLongValInStr(value));				
			}else if(GeneralConstants.CLASS_TYPE_DOUBLE.equals(fieldType))
			{				
				method.invoke(obj, StringTool.getDoubleValInStr(value));				
			}else if(GeneralConstants.CLASS_TYPE_BOOLEAN.equals(fieldType))
			{				
				method.invoke(obj, StringTool.getBooleanValInStr(value));				
			}
		}catch(NoSuchMethodException ex)
		{
			if(!"SerialVersionUID".equalsIgnoreCase(fieldName))
			{
				LogTool.logError( ex);
			}
		}catch(Exception ex)
		{	
			LogTool.logError( ex);
			StringBuffer buf = new StringBuffer();
			buf.append(fieldName);
			buf.append(ex.getMessage());
			ex = new Exception(buf.toString());
			LogTool.logError( ex);
			throw ex;					
		}
	}
	
	
	
	public static Object invokeGetMethod(Object obj, Field field) throws Exception
	{
		Object ret = null;
		String fieldName = "";
		String fieldType = "";
		try
		{
			fieldName = field.getName();
			if(!GeneralConstants.FIELD_NAME_SERIAL_VERSION_UID.equalsIgnoreCase(fieldName))
			{				
				fieldType = field.getGenericType().toString();
				Method method = obj.getClass().getDeclaredMethod(ClassTool.getGetMethod(fieldName, fieldType));			
				ret = method.invoke(obj);
			}
		}catch(Exception ex)
		{	
			LogTool.logError( ex);
			StringBuffer buf = new StringBuffer();
			buf.append(fieldName);
			buf.append(ex.getMessage());
			ex = new Exception(buf.toString());
			LogTool.logError( ex);
			throw ex;					
		}finally
		{
			return ret;
		}
	}
	
	
	public static Object invokeMethod(Object obj, String methodName, String methodArgs) throws Exception
	{
		Object ret = null;
		try
		{
			Method methods[] = obj.getClass().getDeclaredMethods();
			int size = methods.length;
			for(int i=0;i<size;i++)
			{
				Method method = methods[i];
				if(method.getName().equalsIgnoreCase(methodName))
				{
					if(methodArgs!=null && !"".equals(methodArgs))
					{
						ret = method.invoke(obj, methodArgs);
					}else
					{
						ret = method.invoke(obj);
					}
					break;
				}
			}
		}catch(Exception ex)
		{	
			LogTool.logError( ex);
			throw ex;					
		}finally
		{
			return ret;
		}
	}

	
	
	public static String getObjFieldStringVal(Object obj, Field field) throws Exception
	{
		String ret = "";
		String fieldName = "";
		String fieldType = "";
		try
		{
			fieldName = field.getName();
			if(!GeneralConstants.FIELD_NAME_SERIAL_VERSION_UID.equalsIgnoreCase(fieldName))
			{				
				fieldType = field.getGenericType().toString();
				Method method = obj.getClass().getDeclaredMethod(ClassTool.getGetMethod(fieldName, fieldType));
				Object fieldValueObj = method.invoke(obj);
				if(GeneralConstants.CLASS_TYPE_STRING.equals(fieldType) || 
				   GeneralConstants.CLASS_TYPE_INT.equals(fieldType) ||
				   GeneralConstants.CLASS_TYPE_LONG.equals(fieldType) ||
				   GeneralConstants.CLASS_TYPE_DOUBLE.equals(fieldType) ||
				   GeneralConstants.CLASS_TYPE_BOOLEAN.equals(fieldType)
				)
				{
					if(fieldValueObj!=null)
					{
						ret = fieldValueObj.toString();
					}
				}
			}
		}catch(Exception ex)
		{	
			LogTool.logError( ex);
			StringBuffer buf = new StringBuffer();
			buf.append(fieldName);
			buf.append(ex.getMessage());
			ex = new Exception(buf.toString());
			LogTool.logError( ex);
			throw ex;					
		}finally
		{
			return ret;
		}
	}
	
	public static Object extractValueFromRequest(Class objClass, HttpServletRequest request) throws Exception
	{
		return extractValue(objClass, request);
	}
	
	public static Object extractValueFromResultSet(Class objClass, ResultSet result) throws Exception
	{
		return extractValue(objClass, result);
	}

	private static Object extractValue(Class objClass, Object dataSrc) throws Exception
	{
		Object retObj = objClass.newInstance();
		Field[] fieldArr = objClass.getDeclaredFields();
		if(fieldArr!=null)
		{
			int len = fieldArr.length;
			for(int i=0;i<len;i++)
			{
				Field field = fieldArr[i];
				String fieldName = field.getName();
				String fieldType = field.getType().toString();
				
				String value = "";
				if(dataSrc instanceof HttpServletRequest)
				{
					value = GeneralWebTool.getStringAttributeBeforeParameter(fieldName, (HttpServletRequest)dataSrc);	
				}else if(dataSrc instanceof ResultSet)
				{
					DBColumn column = field.getAnnotation(DBColumn.class);
					if(column!=null)
					{
						String columnName = column.name();
						value = DBTool.getStringValueFromResultSet((ResultSet)dataSrc, columnName, fieldType);
					}
				}
					
				if(GeneralConstants.CLASS_TYPE_STRING.equals(fieldType) || GeneralConstants.CLASS_TYPE_INT.equals(fieldType) || GeneralConstants.CLASS_TYPE_LONG.equals(fieldType) || GeneralConstants.CLASS_TYPE_DOUBLE.equals(fieldType) || GeneralConstants.CLASS_TYPE_BOOLEAN.equals(fieldType))
				{
					value = StringTool.isEmpty(value, "");
					value = StringTool.removeReturnCharactor(value);
					ClassTool.invokeSetMethod(retObj, field, value);
				}
			}
		}
		return retObj;
	}
	
	
	public static Map<String, Object> getObjectFieldValueMap(Object obj) throws Exception
	{
		Map<String,Object> retMap = new HashMap<String,Object>();
		Field fieldArr[] = obj.getClass().getDeclaredFields();
		if(fieldArr!=null)
		{
			int size = fieldArr.length;
			for(int i=0;i<size;i++)
			{
				Field field = fieldArr[i];
				String key = field.getName();
				Object valObj = ClassTool.invokeGetMethod(obj, field);
				if(valObj!=null)
				{
					retMap.put(key, valObj);
				}				
			}
		}
		return retMap;
	}
	
	
	public static Map<String, Field> getAnnoFieldMap(Class objClass) throws Exception
	{
		Map<String, Field> ret = new TreeMap<String, Field>(new Comparator());
		Field fieldArr[] = objClass.getDeclaredFields();
		int size = fieldArr.length;
		for(int i=0;i<size;i++)
		{
			Field field = fieldArr[i];
			DBColumn cAnno = field.getAnnotation(DBColumn.class);
			if(cAnno!=null)
			{
				String columnName = cAnno.name();
				if(columnName!=null && !"".equals(columnName))
				{
					ret.put(columnName, field);
				}
			}
		}
		return ret;
	}
	
	
	public static String getDBColumnNameByObjFieldName(Class objClass, String fieldName)
	{
		String ret = "";
		if(!ClassTool.isNullObj(objClass))
		{
			Field fieldArr[] = objClass.getDeclaredFields();
			int size = fieldArr.length;
			for(int i=0;i<size;i++)
			{
				Field field = fieldArr[i];
				if(fieldName.equalsIgnoreCase(field.getName()))
				{
					DBColumn cAnno = field.getAnnotation(DBColumn.class);
					if(cAnno!=null)
					{
						String columnName = cAnno.name();
						ret = columnName;
						break;
					}
				}
			}
		}
		return ret;
	}
	
	
	public static boolean isNullObj(Object obj)
	{
		if(obj==null)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	
	public static Object getObjValueFromJSONObject(Class objClass, JSONObject jsonObj) throws Exception
	{
		Object ret = objClass.newInstance();
		Field fieldArr[] = objClass.getDeclaredFields();
		if(!ClassTool.isNullObj(fieldArr))
		{
			int len = fieldArr.length;
			for(int i=0;i<len;i++)
			{
				Field field = fieldArr[i];
				String fieldName = field.getName();
				
				if(!GeneralConstants.SERIAL_VERSION_UID.equalsIgnoreCase(fieldName))
				{
					String value = "";
					try
					{
						value = jsonObj.getString(fieldName);
						ClassTool.invokeSetMethod(ret, field, value);
					}catch(Exception ex)
					{
						LogTool.logError( ex);
					}
				}
			}
		}
		return ret;
	}
}
