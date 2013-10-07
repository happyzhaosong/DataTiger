package com.food.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.client.logic.FoodBB;
import com.general.common.constants.GeneralConstants;
import com.general.common.dto.JsonDTO;
import com.general.common.util.GeneralWebTool;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;
import com.general.common.util.search.SearchTool;


public class FoodActionServlet extends HttpServlet {

	FoodBB foodBB = new FoodBB(); 
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuffer forwardBuf = new StringBuffer();
		JsonDTO jsonDto = null;
			
		try{
			if(SearchTool.ifSearchTooFrequently(request))
			{
				jsonDto = new JsonDTO();
				jsonDto.setSuccess(false);
				jsonDto.setMessage("�����ʵ�̫Ƶ������5���Ӻ���ʡ�");
			}else
			{
				String action = GeneralWebTool.getStringAttributeBeforeParameter(GeneralConstants.ACTION, request);
				
				if(GeneralConstants.ACTION_SEARCH.equals(action))
				{
					jsonDto = foodBB.searchFood(request);
				}else if(GeneralConstants.ACTION_SEARCH_GET_FOOD_SEARCH_KEYWORDS.equals(action))
				{
					jsonDto = foodBB.getTopFoodSearchKeyword(request);
				}else if(GeneralConstants.ACTION_SEARCH_CLICK_ITEM.equals(action))
				{
					foodBB.clickItem(request, response);
				}else if(GeneralConstants.ACTION_SEARCH_FILTER_KEYWORD.equals(action))
				{
					jsonDto = foodBB.getRelatedFoodSearchKeyword(request);		
				}
			}		
		}catch(Exception ex)
		{
			LogTool.logError(ex, this.getClass().getName());
			if(jsonDto==null) jsonDto = new JsonDTO();
			jsonDto.setSuccess(false);
			jsonDto.setMessage(ex.getMessage());
		}finally
		{
			if(jsonDto==null)
			{
				forwardBuf.append(GeneralConstants.PAGE_LOGIN);
			}
			
			if(!forwardBuf.toString().equals(GeneralConstants.PAGE_LOGIN))
			{
				try
				{
					forwardBuf.append(JsonTool.getJsonString(jsonDto));
				}catch(Exception ex)
				{
					LogTool.logError(ex, this.getClass().getName());
				}
			}			
			GeneralWebTool.writeAjaxResponse(response, forwardBuf.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

	
	
	
}
