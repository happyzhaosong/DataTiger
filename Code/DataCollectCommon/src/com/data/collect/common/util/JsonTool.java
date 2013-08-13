package com.data.collect.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.BasePageDTO;
import com.data.collect.common.dto.DBTableColumnDTO;
import com.data.collect.common.dto.JsonDTO;
import com.data.collect.common.dto.MQMessageDTO;

public class JsonTool extends BaseTool {
	
	public static String getJsonString(JsonDTO jsonDto) throws Exception
	{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(Constants.JSON_SUCCESS, jsonDto.isSuccess());
		jsonObj.put(Constants.JSON_EXIST, jsonDto.isExist());
		jsonObj.put(Constants.JSON_CODE, jsonDto.getCode());
		jsonObj.put(Constants.JSON_MESSAGE, jsonDto.getMessage());		
		
		Map<String, List<Object>> dataMap = jsonDto.getDataMap();
		if(dataMap!=null)
		{
			//jsonObj.putAll(dataMap);
			Set<String> keySet = dataMap.keySet();
			Iterator<String> keyIt = keySet.iterator();
			int itemCount = 0;
			while(keyIt.hasNext())
			{
				String key = keyIt.next();
				List<Object> valList = dataMap.get(key);
				
				if(valList!=null)
				{
					if(itemCount==0 && valList.size()>0)
					{
						Object obj = valList.get(0);
						if(obj instanceof BasePageDTO)
						{
							BasePageDTO pageDto = (BasePageDTO)obj;
							if(pageDto.getTotalRecordsCountInThisSearch()>-1)
							{
								jsonObj.put(Constants.JSON_TOTAL_RESULT_COUNT, pageDto.getTotalRecordsCountInThisSearch());		
							}
						}else if(obj instanceof List)
						{
							List rowList = (List)obj;
							if(!ClassTool.isListEmpty(rowList))
							{
								Object columnObj = rowList.get(0);
								if(columnObj instanceof BasePageDTO)
								{
									BasePageDTO pageDto = (BasePageDTO)columnObj;
									if(pageDto.getTotalRecordsCountInThisSearch()>-1)
									{
										jsonObj.put(Constants.JSON_TOTAL_RESULT_COUNT, pageDto.getTotalRecordsCountInThisSearch());		
									}
								}
							}
						}
					}				
					itemCount++;
					List<String> listObjStringList = getListObjString(valList);
					jsonObj.put(key, listObjStringList);
				}
			}	
				
		}	
		return jsonObj.toString();
	}
	
	
	private static List<String> getListObjString(List<Object> valList) throws Exception
	{
		List<String> ret = new ArrayList<String>();
		if(!ClassTool.isNullObj(valList))
		{
			int size = valList.size();
			for(int i=0;i<size;i++)
			{
				JSONObject jsonObj = new JSONObject();
				Object obj = valList.get(i);
				
				//for return table data not use DTO, use DBTableColumnDTO to represent 1 column, List to represent 1 row, List to represent resultset
				if(obj instanceof List)
				{
					List row = (List)obj;
					if(!ClassTool.isListEmpty(row))
					{
						int columnCount = row.size();
						for(int j=0;j<columnCount;j++)
						{
							DBTableColumnDTO cDto = (DBTableColumnDTO)row.get(j);
							jsonObj.put(cDto.getColumnName(), cDto.getColumnValue());
						}
						ret.add(jsonObj.toString());
					}
				}
				//for return table data use DTO, user according DTO to represent 1 row, List to represent resultset
				else
				{
					Field fieldArr[] = obj.getClass().getDeclaredFields();
					if(!ClassTool.isNullObj(fieldArr))
					{
						int len = fieldArr.length;
						for(int j=0;j<len;j++)
						{
							Field field = fieldArr[j];
							Object valObj = ClassTool.invokeGetMethod(obj, field);
							jsonObj.put(field.getName(), valObj);
						}					
						ret.add(jsonObj.toString());
					}
				}
			}
		}		
		return ret;
	}
	
	
	public static JsonDTO getJsonDtoByObj(String rootKey, Object dto)
	{
		JsonDTO jsonDto = new JsonDTO();
		if(dto!=null)
		{
			jsonDto.setSuccess(true);
			jsonDto.addDataToMap(rootKey, dto);
		}else
		{
			jsonDto.setSuccess(false);
		}
		return jsonDto;
	}
	
	public static <T> JsonDTO getJsonDtoByObjList(String rootKey, List<T> dtoList)
	{
		JsonDTO jsonDto = new JsonDTO();
		Map<String, List<Object>> dataMap = new HashMap<String, List<Object>>();		
		jsonDto.setSuccess(true);
		if(dtoList!=null && dtoList.size()>0)
		{
			jsonDto.setExist(true);
			dataMap.put(rootKey, ClassTool.getObjList(dtoList));
		}else
		{
			jsonDto.setExist(false);
		}
		jsonDto.setDataMap(dataMap);
		return jsonDto;
	}
	
	public static JsonDTO getJsonDtoByCanNotDelIds(String canNotDelIds)
	{
		String message = "Following id records can not be deleted because they has related sub table records. Id: " + canNotDelIds;	
		return JsonTool.getJsonDtoByMessage(message);
	}
	
	public static JsonDTO getJsonDtoByMessage(String message)
	{
		JsonDTO jsonDto = new JsonDTO();
		if(StringTool.isEmpty(message))
		{
			jsonDto.setSuccess(true);
			jsonDto.setMessage("Operation success.");
		}else
		{
			jsonDto.setSuccess(false);
			jsonDto.setMessage(message);
		}
		return jsonDto;
	}
	
	public static List<MQMessageDTO> getMQDtoByJsonString(String jsonStr) throws Exception
	{	
		List<MQMessageDTO> ret = new ArrayList<MQMessageDTO>();
		if(!StringTool.isEmpty(jsonStr))
		{
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			JSONArray dtoArray = (JSONArray)jsonObj.get(Constants.JSON_ROOT_ACTIVE_MQ_MESSAGE);
			if(!ClassTool.isNullObj(dtoArray))
			{
				int size = dtoArray.size();
				for(int i=0;i<size;i++)
				{
					JSONObject obj = (JSONObject)dtoArray.get(i);
					MQMessageDTO mqDto = (MQMessageDTO)(ClassTool.getObjValueFromJSONObject(MQMessageDTO.class, obj));
					ret.add(mqDto);
				}
			}			 
		}
		return ret;
	}
}
