package com.data.collect.server.dao;

import java.util.ArrayList;
import java.util.List;
import com.data.collect.common.dto.RoleDTO;
import com.data.collect.common.util.ClassTool;

public class RoleDAO extends BaseDAO {

	public List<RoleDTO> getRoleList() throws Exception
	{
		this.initStringBuffer();		
		List<RoleDTO> ret = new ArrayList<RoleDTO>();
		return ret.getClass().cast(this.selectDtoList(RoleDTO.class));	
	}
	
	
	public RoleDTO getUserRoleByUserId(int userId) throws Exception
	{
		this.initStringBuffer();		
		List<RoleDTO> retList = new ArrayList<RoleDTO>();		
		String sql = "SELECT id, role_name, role_desc FROM role AS r, account_role_map AS arm ";
		this.whereBuf.append(" r.id = arm.role_id AND arm.account_id = ");
		this.whereBuf.append(userId);
		retList = retList.getClass().cast(this.selectDtoList(RoleDTO.class, sql));
		
		RoleDTO ret = null;
		if(!ClassTool.isNullObj(retList))
		{
			ret = retList.get(0);
		}		
		return ret;	
	}
}
