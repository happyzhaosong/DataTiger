package com.food.client.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.JsonDTO;
import com.data.collect.common.util.JsonTool;
import com.data.collect.common.util.LogTool;
import com.data.collect.common.util.WebTool;

public class ActionServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuffer forwardBuf = new StringBuffer();
		JsonDTO jsonDto = null;
			
		try{
			String action = WebTool.getStringAttributeBeforeParameter(Constants.ACTION, req);
			if(Constants.ACTION_LOGIN.equals(action))
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
				forwardBuf.append(Constants.PAGE_LOGIN);
			}
			
			if(!forwardBuf.toString().equals(Constants.PAGE_LOGIN))
			{
				try
				{
					forwardBuf.append(JsonTool.getJsonString(jsonDto));
				}catch(Exception ex)
				{
					LogTool.logError( ex);
				}
			}			
			WebTool.writeAjaxResponse(resp, forwardBuf.toString());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
