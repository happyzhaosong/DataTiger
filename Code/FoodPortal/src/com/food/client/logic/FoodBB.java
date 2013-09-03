package com.food.client.logic;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.food.common.constants.FoodConstants;
import com.food.common.dto.FoodDTO;
import com.food.server.dao.FoodDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.dto.search.BaseSearchParamsDTO;
import com.general.common.util.JsonTool;


public class FoodBB extends BaseBB {
	
	private FoodDAO foodDao = new FoodDAO();
	
	public JsonDTO searchFood(HttpServletRequest request) throws Exception
	{	
		BaseSearchParamsDTO searchParamsDto = this.getGeneralSearchParamsDTOFromRequest(request);	
		List<FoodDTO> dtoList = this.foodDao.searchFood(searchParamsDto);
		return JsonTool.getJsonDtoByObjList(FoodConstants.JSON_ROOT_FOOD_LIST, dtoList);
	}
}
