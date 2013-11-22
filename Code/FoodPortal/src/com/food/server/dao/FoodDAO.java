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

	private static String CORE_NAME = "core_xiu_hao_chi";
		
	public List<FoodDTO> searchFood(BaseSearchParamsDTO searchParamsDto) throws Exception
	{
		/*
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
			if(this.whereBuf.length()>0)
			{
				this.whereBuf.append(" and ");
			}
			
			this.whereBuf.append(searchParamsDto.getOrderBy1());
			this.whereBuf.append(" != -1 ");
			
			this.orderByBuf.append(searchParamsDto.getOrderBy1());
			this.orderByBuf.append(" ");
			this.orderByBuf.append(searchParamsDto.getDirection1());
		}
		
		if(!StringTool.isEmpty(searchParamsDto.getOrderBy2()))
		{
			if(this.whereBuf.length()>0)
			{
				this.whereBuf.append(" and ");
			}
			
			this.whereBuf.append(searchParamsDto.getOrderBy2());
			this.whereBuf.append(" != -1 ");
			
			if(this.orderByBuf.length()>0)
			{
				this.orderByBuf.append(", ");
			}
			
			this.orderByBuf.append(searchParamsDto.getOrderBy2());
			this.orderByBuf.append(" ");
			this.orderByBuf.append(searchParamsDto.getDirection2());
		}
		*/
		List<FoodDTO> ret = new ArrayList<FoodDTO>();
		
		
		ret = ret.getClass().cast(this.selectDtoList(FoodDTO.class, DBManager.getInstance().getDataSource()));		
		return ret;
	}
	
	public void updateClickCount(String id) throws Exception
	{
		if(StringTool.isInteger(id))
		{
			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append(" update ");
			sqlBuf.append(GeneralConstants.TABLE_DATA_XIU_HAO_CHI);
			sqlBuf.append(" set click_count = click_count + 1 where id = ");
			sqlBuf.append(id);		
			this.executeUpdateOrDeleteSql(sqlBuf.toString(), DBManager.getInstance().getDataSource());
		}
	}

}
