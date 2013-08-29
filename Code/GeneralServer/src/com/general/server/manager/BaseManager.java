package com.general.server.manager;

import com.general.common.dto.DBMQCfgInfoDTO;
import com.general.common.util.ConfigTool;

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
