package com.food.server.dao;

import java.util.ArrayList;
import java.util.List;

import com.food.common.dto.FoodDTO;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.util.StringTool;
import com.general.server.dao.BaseDAO;
import com.general.server.manager.DBManager;

public class FoodDAO extends BaseDAO {

	public List<FoodDTO> searchFood(BaseSearchParamsDTO searchParamsDto) throws Exception
	{
		this.initStringBuffer();

		if(!StringTool.isEmpty(searchParamsDto.getSearchWebSite()))
		{
			this.addMultipleValueClauseInWhereClause("shang_pin_lai_yuan", searchParamsDto.getSearchWebSite(), "or");
		}
		
		if(!StringTool.isEmpty(searchParamsDto.getSearchKeyword().trim()))
		{
			if(this.whereBuf.length()>0)
			{
				this.whereBuf.append(" and ");
			}
			
			this.whereBuf.append(" biao_ti like '%");	
			this.whereBuf.append(searchParamsDto.getSearchKeyword().trim());
			this.whereBuf.append("%'");
		}
		
		
		if(!StringTool.isEmpty(searchParamsDto.getOrderBy1()))
		{
			this.orderByBuf.append(searchParamsDto.getOrderBy1());
			this.orderByBuf.append(" ");
			this.orderByBuf.append(searchParamsDto.getDirection1());
		}
		
		if(!StringTool.isEmpty(searchParamsDto.getOrderBy2()))
		{
			if(this.orderByBuf.length()>0)
			{
				this.orderByBuf.append(", ");
			}
			
			this.orderByBuf.append(searchParamsDto.getOrderBy2());
			this.orderByBuf.append(" ");
			this.orderByBuf.append(searchParamsDto.getDirection2());
		}
		
		List<FoodDTO> ret = new ArrayList<FoodDTO>();
		ret = ret.getClass().cast(this.selectDtoList(FoodDTO.class, DBManager.getInstance().getMysqlDataSource()));		
		return ret;
	}
}
