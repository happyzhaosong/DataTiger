package com.food.client.logic;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.common.constants.FoodConstants;
import com.food.common.dto.FoodDTO;
import com.food.server.dao.FoodDAO;
import com.general.client.logic.BaseBB;
import com.general.client.logic.search.DataSearchLogBB;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.BasePageDTO;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.GeneralWebTool;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;


public class FoodBB extends BaseBB {
	
	private DataSearchLogBB dataSearchLogBB = new DataSearchLogBB();
	private FoodDAO foodDao = new FoodDAO();
	
	public JsonDTO searchFood(HttpServletRequest request) throws Exception
	{	
		BaseSearchParamsDTO searchParamsDto = this.getGeneralSearchParamsDTOFromRequest(request);
		
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);				
		this.foodDao.setPageDto(pageDto);
		
		List<FoodDTO> dtoList = this.foodDao.searchFood(searchParamsDto);
		//this.limitSearchResult(dtoList);
		
		if(!StringTool.isEmpty(searchParamsDto.getSearchKeyword()) && searchParamsDto.isLogSearchKeyword())
		{
			LogTool.logText("Will log search keyword is " + searchParamsDto.getSearchKeyword(), this.getClass().getName());
			if(dtoList.size()>0)
			{
				searchParamsDto.setSearchResultCount(dtoList.get(0).getTotalRecordsCountInThisSearch());
			}else
			{
				searchParamsDto.setSearchResultCount(0);
			}
			this.dataSearchLogBB.logSearchData(request, searchParamsDto, GeneralConstants.SEARCH_DATA_IN_XIU_HAO_CHI);
		}
		
		return JsonTool.getJsonDtoByObjList(FoodConstants.JSON_ROOT_FOOD_LIST, dtoList);
	}
	
	public JsonDTO getTopFoodSearchKeyword(HttpServletRequest request) throws Exception
	{
		return this.dataSearchLogBB.getTopLogSearchDataListInJson(request, GeneralConstants.SEARCH_DATA_IN_XIU_HAO_CHI);
	}
	
	/*
	 * When user input search keyword in search input text field, 
	 * used for autocomplete for list out all related search keyword for user to select
	 * */
	public JsonDTO getRelatedFoodSearchKeyword(HttpServletRequest request) throws Exception
	{	
		return this.dataSearchLogBB.getRelatedFoodSearchKeyword(request, GeneralConstants.SEARCH_DATA_IN_XIU_HAO_CHI);
	}
	
	public void clickItem(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = GeneralWebTool.getStringParameterBeforeAttribute("id", request);
		String itemUrl = GeneralWebTool.getStringParameterBeforeAttribute("itemUrl", request);
		itemUrl = StringTool.decodeStr(itemUrl);
		this.foodDao.updateClickCount(id);		
		response.sendRedirect(itemUrl);		
	}
}
