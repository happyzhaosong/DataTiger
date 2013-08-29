package com.data.collect.client.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.data.collect.common.constants.Constants;
import com.data.collect.common.dto.MenuDTO;
import com.data.collect.server.dao.MenuDAO;
import com.general.client.logic.BaseBB;
import com.general.common.dto.JsonDTO;
import com.general.common.util.ClassTool;

public class MenuBB extends BaseBB{
	
	private MenuDAO menuDao = new MenuDAO();
	private UserBB userBB = new UserBB();

	
	public JsonDTO getMenuListByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		JsonDTO jsonDto = new JsonDTO();		
		Map<String, List<Object>> dataMap = new HashMap<String, List<Object>>();
		
		int userId = this.userBB.getLoginUserId(request);		
		List<MenuDTO> menuList = menuDao.getMenuListByUserId(userId);
		jsonDto.setSuccess(true);
		if(menuList==null || menuList.size()==0)
		{
			jsonDto.setExist(false);
		}else
		{
			jsonDto.setExist(true);				
			dataMap.put(Constants.JSON_MENU_LIST, ClassTool.getObjList(menuList));
		}
		jsonDto.setDataMap(dataMap);
		return jsonDto;
	}
	
}
