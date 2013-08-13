package com.data.collect.server.manager;

import com.data.collect.common.dto.DBMQCfgInfoDTO;
import com.data.collect.common.util.ConfigTool;

public class BaseManager {
	
	private DBMQCfgInfoDTO dbCfgInfo = null;

	public DBMQCfgInfoDTO getDBMQConfig() throws Exception
	{
		if(this.dbCfgInfo==null)
		{
			this.dbCfgInfo = ConfigTool.getDBMQConfig();
		}
		return this.dbCfgInfo;
	}
}
