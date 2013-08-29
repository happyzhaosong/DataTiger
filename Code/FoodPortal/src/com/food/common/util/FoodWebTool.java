package com.food.common.util;

import javax.servlet.http.HttpServletRequest;

import com.food.common.constants.FoodConstants;
import com.general.common.util.BaseTool;
import com.general.common.util.GeneralWebTool;

public class FoodWebTool extends BaseTool {

	public static String getActionURL(HttpServletRequest request, String action)
	{
		return GeneralWebTool.getActionURL(request, FoodConstants.FOOD_ACTION_SERVLET_NAME, action);
	}
}
