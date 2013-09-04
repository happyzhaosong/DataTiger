package com.food.client.logic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.general.common.util.JsonTool;


public class FoodBB extends BaseBB {
	
	private DataSearchLogBB dataSearchLogBB = new DataSearchLogBB();
	private FoodDAO foodDao = new FoodDAO();
	
	public JsonDTO searchFood(HttpServletRequest request) throws Exception
	{	
		BaseSearchParamsDTO searchParamsDto = this.getGeneralSearchParamsDTOFromRequest(request);
		
		BasePageDTO pageDto = (BasePageDTO)ClassTool.extractValueFromRequest(BasePageDTO.class, request);					
		this.foodDao.setPageDto(pageDto);
		
		List<FoodDTO> dtoList = this.foodDao.searchFood(searchParamsDto);
		
		this.dataSearchLogBB.logSearchData(request, searchParamsDto.getSearchKeyword(), GeneralConstants.SEARCH_DATA_IN_XIU_HAO_CHI);		
		
		return JsonTool.getJsonDtoByObjList(FoodConstants.JSON_ROOT_FOOD_LIST, dtoList);
	}
}
