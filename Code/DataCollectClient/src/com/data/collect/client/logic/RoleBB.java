package com.data.collect.client.logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.RoleDTO;
import com.data.collect.common.util.WebTool;
import com.data.collect.server.dao.RoleDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.util.ClassTool;
import com.general.common.util.JsonTool;
import com.general.common.util.LogTool;
import com.general.common.util.StringTool;


public class RoleBB extends BaseBB {
	
	private RoleDAO roleDao = new RoleDAO();
	private UserBB userBB = new UserBB();
	
	public List<RoleDTO> getRoleList(HttpServletRequest request)
	{
		List<RoleDTO> ret = new ArrayList<RoleDTO>();
		try
		{
			ret = this.roleDao.getRoleList();
		}catch(Exception ex)
		{
			LogTool.logError(ex, this.getClass().getName());
			request.setAttribute(Constants.ERROR_MESSAGE, ex.getMessage());
		}finally
		{
			return ret;
		}
	}
	
	public JsonDTO getUserRoleByUserId(HttpServletRequest request) throws Exception
	{
		int userId = userBB.getLoginUserId(request);
		StringTool.checkInteger(String.valueOf(userId) , "User id should be an integer.");
		RoleDTO dto = this.roleDao.getUserRoleByUserId(userId);
		return JsonTool.getJsonDtoByObj(Constants.JSON_USER_ROLE, dto);
	}
	
	public RoleDTO getUserRoleDtoFromSession(HttpServletRequest request) throws Exception
	{
		RoleDTO ret = null;
		Object obj = WebTool.getObjFromSession(request, Constants.SESSION_LOGIN_USER_ROLE_DTO, null);
		if(!ClassTool.isNullObj(obj) && (obj instanceof RoleDTO))
		{
			ret = (RoleDTO)obj;
		}else
		{
			throw new Exception("User role not found in session. Maybe time out.");
		}
		return ret;
	}
	
	public boolean isAdminRole(RoleDTO roleDto)
	{
		boolean ret = false;
		if(!ClassTool.isNullObj(roleDto))
		{
			if("admin".equalsIgnoreCase(roleDto.getRoleName()) || "administrator".equalsIgnoreCase(roleDto.getRoleName()) || "super admin".equalsIgnoreCase(roleDto.getRoleName()))
			{
				ret = true;
			}
		}
		return ret;
	}
	
}
