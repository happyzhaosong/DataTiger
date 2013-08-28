package com.data.collect.server.dao.search;

import java.util.ArrayList;
import java.util.List;

import com.data.collect.common.dto.search.FoodSearchParamsDTO;
import com.data.collect.common.dto.search.FoodSearchResultDTO;
import com.data.collect.server.dao.BaseDAO;
import com.data.collect.server.manager.DBManager;

public class SearchDAO extends BaseDAO {

	public List<FoodSearchResultDTO> searchFood(FoodSearchParamsDTO searchParamsDto) throws Exception
	{
		this.initStringBuffer();
		
		this.whereBuf.append(" site_name ");
		this.whereBuf.append(" = '");
		//this.whereBuf.append(byValue);
		this.whereBuf.append("'");
		
		List<FoodSearchResultDTO> ret = new ArrayList<FoodSearchResultDTO>();
		ret = ret.getClass().cast(this.selectDtoList(FoodSearchResultDTO.class, DBManager.getInstance().getMysqlDataSource()));		
		return ret;
	}
}
