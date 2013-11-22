package com.general.server.dao.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.server.manager.SolrManager;

public class DataSearcherDAO {

	public static List<Map<String, String>> searchData(String coreName, BaseSearchParamsDTO searchParamsDto) throws Exception
	{
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		SolrManager.getInstance().getSolrServer(coreName);
		return ret;
	}
}
