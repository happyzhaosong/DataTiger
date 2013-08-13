package com.data.collect.server.dao;

import java.util.ArrayList;
import java.util.List;
import com.data.collect.common.dto.MenuDTO;

public class MenuDAO extends BaseDAO {
	
	public List<MenuDTO> getMenuListByUserId(int userId) throws Exception
	{
		this.initStringBuffer();	
	
		String sql = "SELECT m.id, m.menu_name, m.menu_desc, m.menu_action FROM menu m, role_menu_map r, account_role_map a ";
		
		this.whereBuf.append(" a.role_id = r.role_id AND r.menu_id = m.id AND  a.account_id = ");
		this.whereBuf.append(userId);
		
		List<MenuDTO> ret = new ArrayList<MenuDTO>();
		return ret.getClass().cast(this.selectDtoList(MenuDTO.class, sql));			
	}
}
