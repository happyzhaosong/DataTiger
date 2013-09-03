package com.food.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.general.common.constants.GeneralConstants;

public class FoodActionFilter implements Filter {

    private HttpServletRequest request;
    private HttpServletResponse response;	

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		this.request = (HttpServletRequest)req;
		this.response = (HttpServletResponse)resp;			
		
		request.setCharacterEncoding(GeneralConstants.PAGE_CHAR_SET_UTF8);
		response.setCharacterEncoding(GeneralConstants.PAGE_CHAR_SET_UTF8);
		//request.setCharacterEncoding(GeneralConstants.PAGE_CHAR_SET_GB2312);
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub

	}

}
