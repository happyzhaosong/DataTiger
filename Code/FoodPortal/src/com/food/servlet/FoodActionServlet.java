package com.food.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.general.common.constants.GeneralConstants;
import com.general.common.dto.JsonDTO;
import com.general.common.util.GeneralWebTool;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;


public class FoodActionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer forwardBuf = new StringBuffer();
		JsonDTO jsonDto = null;
			
		try{
			String action = GeneralWebTool.getStringAttributeBeforeParameter(GeneralConstants.ACTION, req);
			if(GeneralConstants.ACTION_SEARCH.equals(action))
			{
				//jsonDto = userBB.authUser(req);
			}
		}catch(Exception ex)
		{
			LogTool.logError( ex);
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
					LogTool.logError( ex);
				}
			}			
			GeneralWebTool.writeAjaxResponse(resp, forwardBuf.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}