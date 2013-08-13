package com.data.collect.server.dao;

import java.util.List;
import com.data.collect.common.dto.UserDTO;

public class UserDAO extends BaseDAO {

	public UserDTO authUser(String userName, String passwd) throws Exception
	{
		UserDTO ret = new UserDTO();
		this.initStringBuffer();		
		this.whereBuf.append(" user_name ");
		this.whereBuf.append(" = '");
		this.whereBuf.append(userName);
		this.whereBuf.append("' and pass_word = '");
		this.whereBuf.append(passwd);
		this.whereBuf.append("'");
		
		List<Object> objList = this.selectDtoList(UserDTO.class);	
		if(objList.size()>0)
		{
			ret = (UserDTO)objList.get(0);
			ret.setExist(true);
		}else
		{
			ret.setExist(false);
		}
		return ret;		
	}
}
