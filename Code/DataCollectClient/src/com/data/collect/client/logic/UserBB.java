package com.data.collect.client.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.RoleDTO;
import com.data.collect.common.dto.UserDTO;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.RoleDAO;
import com.data.collect.server.dao.UserDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;


public class UserBB extends BaseBB {

	private UserDAO userDao = new UserDAO();
	private RoleDAO roleDao = new RoleDAO();
	
	
	/*
	 * All methods return target url.
	 * */
	public JsonDTO authUser(HttpServletRequest request) throws Exception
	{
		UserDTO uDto = null;
		JsonDTO jsonDto = new JsonDTO();
		uDto = (UserDTO)ClassTool.extractValueFromRequest(UserDTO.class, request);			
		StringTool.checkEmpty(uDto.getUserName(),Constants.ERROR_MESSAGE_USER_NAME_CAN_NOT_EMPTY);
		StringTool.checkEmpty(uDto.getPassWord(),Constants.ERROR_MESSAGE_PASSWORD_CAN_NOT_EMPTY);		
		uDto = userDao.authUser(uDto.getUserName(), uDto.getPassWord());
		if(!uDto.isExist())
		{
			throw new Exception(Constants.ERROR_MESSAGE_USER_NAME_PASSWORD_NOT_CORRECT);
		}else
		{
			uDto.setLoginTime(String.valueOf(System.currentTimeMillis()));
			//need to save user login time back to db.--- to be done
			RoleDTO userRoleDto = roleDao.getUserRoleByUserId(uDto.getId());
			this.setUserAndRoleDtoInSession(uDto, userRoleDto, request);
			jsonDto.setSuccess(true);
			jsonDto.addDataToMap(Constants.JSON_USER_ACCOUNT, uDto);
		}
		return jsonDto;
	}
	
	public void setUserAndRoleDtoInSession(UserDTO uDto, RoleDTO userRoleDto, HttpServletRequest request)
	{
		WebTool.setSessionAttribute(Constants.SESSION_LOGIN_USER_DTO, uDto, request);
		WebTool.setSessionAttribute(Constants.SESSION_LOGIN_USER_ROLE_DTO, userRoleDto, request);
	}
	
	public UserDTO getUserDtoFromSession(HttpServletRequest request) throws Exception
	{
		UserDTO ret = null;
		Object obj =  WebTool.getObjFromSession(request, Constants.SESSION_LOGIN_USER_DTO, null);
		if(!ClassTool.isNullObj(obj) && (obj instanceof UserDTO))
		{
			ret = (UserDTO)obj;
		}
		return ret;
	}
	
	public JsonDTO checkLogin(HttpServletRequest request) throws Exception
	{
		JsonDTO jsonDto = new JsonDTO();
		Object userDto = null;
		userDto = this.getUserDtoFromSession(request);	
		if(userDto==null)
		{
			//session time out, need login
			jsonDto.setSuccess(false);
			jsonDto.setExist(false);
			jsonDto.setCode(Constants.ERROR_CODE_SESSION_TIME_OUT);
			jsonDto.setMessage("You have not access web site too long, need login again.");
		}else
		{
			jsonDto.setSuccess(true);
			jsonDto.setExist(true);
			jsonDto.setCode(Constants.ERROR_CODE_NO_ERROR);
		}
		return jsonDto; 
	}
	
	public int getLoginUserId(HttpServletRequest request) throws Exception
	{
		UserDTO userDto = this.getUserDtoFromSession(request);
		if(userDto==null)
		{
			throw new Exception(Constants.ERROR_MESSAGE_USER_NOT_LOGGEDIN);
		}else
		{
			return userDto.getId();
		}
	}
	
	
	public boolean logout(HttpServletRequest request)
	{
		boolean ret = true;
		try
		{
			HttpSession session = request.getSession();
			if(session!=null)
			{
				this.setUserAndRoleDtoInSession(null, null, request);
				session.invalidate();	
			}			
		}catch(Exception ex)
		{
			LogTool.logError( ex);
			ret = false;
		}finally
		{
			return ret;
		}
	}
}
