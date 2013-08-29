package com.general.common.dto;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class JsonDTO extends BaseDTO {

	private static final long serialVersionUID = 8735964695406782484L;
	private Map<String, List<Object>> dataMap = null;

	public Map<String, List<Object>> getDataMap() {
		if(dataMap==null)
		{
			dataMap = new Hashtable<String, List<Object>>();
		}
		return dataMap;
	}

	public void setDataMap(Map<String, List<Object>> dataMap) {
		this.dataMap = dataMap;
	}
	
	public void addDataToMap(String key, Object obj)
	{
		if(key!=null && !"".equals(key.trim()) && obj!=null)
		{
			List<Object> list = new ArrayList<Object>();
			list.add(obj);
			this.getDataMap().put(key, list);
		}
	}
}
