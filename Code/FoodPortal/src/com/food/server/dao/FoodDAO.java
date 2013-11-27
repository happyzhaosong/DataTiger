package com.food.server.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.food.common.dto.FoodDTO;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.dto.search.SolrSearchKeywordDTO;
import com.general.common.dto.search.SolrSearchOrderDTO;
import com.general.common.dto.search.SolrSearchParamsDTO;
import com.general.common.util.StringTool;
import com.general.common.util.search.SearchTool;
import com.general.server.dao.BaseDAO;
import com.general.server.manager.DBManager;
import com.general.server.manager.SolrManager;

public class FoodDAO extends BaseDAO {

	private static String CORE_NAME = "core_xiu_hao_chi";
		
	public List<FoodDTO> searchFood(BaseSearchParamsDTO searchParamsDto) throws Exception
	{
		List<FoodDTO> ret = new ArrayList<FoodDTO>();
		
		SolrSearchParamsDTO solrSearchParamsDto = this.getFoodSolrSearchParamsDtoFromSearchParamsDto(searchParamsDto, this.getPageDto());				
		ret = ret.getClass().cast(SolrManager.getInstance().searchDataIndex(CORE_NAME, solrSearchParamsDto, FoodDTO.class));

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
	
	
	private SolrSearchParamsDTO getFoodSolrSearchParamsDtoFromSearchParamsDto(BaseSearchParamsDTO searchParamsDto, BasePageDTO pageDto)
	{
		SolrSearchParamsDTO ret = SearchTool.getSolrSearchParamsDtoFromSearchParamsDto(searchParamsDto, pageDto);
		
		List<SolrSearchKeywordDTO> sarchKeywordList = new ArrayList<SolrSearchKeywordDTO>();		
		/* solr search keyword column list*/
		if(!StringTool.isEmpty(searchParamsDto.getSearchKeyword()))	
		{
			SolrSearchKeywordDTO searchKeywordDtoBiaoTi = new SolrSearchKeywordDTO();
			searchKeywordDtoBiaoTi.setColum("BIAO_TI");
			searchKeywordDtoBiaoTi.setKeyword(searchParamsDto.getSearchKeyword());
			searchKeywordDtoBiaoTi.setPriority(10);
			sarchKeywordList.add(searchKeywordDtoBiaoTi);
			
			/*
			SolrSearchKeywordDTO searchKeywordDtoMetaKeyword = new SolrSearchKeywordDTO();
			searchKeywordDtoMetaKeyword.setColum("META_SEARCH_KEYWORD");
			searchKeywordDtoMetaKeyword.setKeyword(searchParamsDto.getSearchKeyword());
			searchKeywordDtoMetaKeyword.setPriority(1);
			sarchKeywordList.add(searchKeywordDtoMetaKeyword);
	        */
			
			SolrSearchKeywordDTO searchKeywordDtoShopName = new SolrSearchKeywordDTO();
			searchKeywordDtoShopName.setColum("SHOP_NAME");
			searchKeywordDtoShopName.setKeyword(searchParamsDto.getSearchKeyword());
			searchKeywordDtoShopName.setPriority(3);
			sarchKeywordList.add(searchKeywordDtoShopName);
			
			
			SolrSearchKeywordDTO searchKeywordDtoMall = new SolrSearchKeywordDTO();
			searchKeywordDtoMall.setColum("SHANG_PIN_LAI_YUAN");
			searchKeywordDtoMall.setKeyword(searchParamsDto.getSearchWebSite());
			searchKeywordDtoMall.setPriority(3);
			sarchKeywordList.add(searchKeywordDtoMall);
			
			ret.setSarchKeywordList(sarchKeywordList);
		}		
		return ret;
	}

}
