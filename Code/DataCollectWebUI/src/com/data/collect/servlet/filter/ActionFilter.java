package com.data.collect.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.collect.client.logic.UserBB;
import com.data.collect.common.constants.Constants;
import com.data.collect.common.util.WebTool;
import com.general.common.dto.JsonDTO;
import com.general.common.util.LogTool;


public class ActionFilter implements Filter{

    private HttpServletRequest request;
    private HttpServletResponse response;
    
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try
		{
			this.request = (HttpServletRequest)req;
			this.response = (HttpServletResponse)resp;			
			
			request.setCharacterEncoding(Constants.PAGE_CHAR_SET_UTF8);
			
			String action = WebTool.getStringAttributeBeforeParameter(Constants.ACTION, this.request);
			UserBB userBB = new UserBB();
			
			if(Constants.ACTION_LOGIN.equals(action) || Constants.ACTION_LOGOUT.equals(action))
			{
				chain.doFilter(req, resp);
			}else
			{
				JsonDTO jsonDto = userBB.checkLogin(this.request);
				if(jsonDto.isSuccess())
				{
					chain.doFilter(req, resp);
				}else
				{
					request.setAttribute(Constants.ERROR_MESSAGE, jsonDto.getMessage());
					response.sendRedirect(WebTool.getLoginPageURL(request, true));
				}
			}
		}catch(IOException ex)
		{
			LogTool.logError(ex, this.getClass().getName());
			throw ex;
		}catch(ServletException ex)
		{
			LogTool.logError(ex, this.getClass().getName());
			throw ex;
		}catch(Exception ex)
		{
			LogTool.logError(ex, this.getClass().getName());
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}
