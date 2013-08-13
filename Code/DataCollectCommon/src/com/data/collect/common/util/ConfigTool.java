package com.data.collect.common.util;

import java.util.Properties;
import com.data.collect.common.dto.DBMQCfgInfoDTO;

public class ConfigTool extends BaseTool {

	private static DBMQCfgInfoDTO dbCfgInfo = null;

	public static final String CONFIG_FILE_NAME = "DCDBMQConfig.properties";
	
	public static final String DB_IP = "db_ip";
	public static final String DB_PORT = "db_port";
	
	public static final String DB_NAME = "db_name";
	public static final String DB_USER = "db_user";
	public static final String DB_PASSWD = "db_passwd";
	
	public static final String DEFAULT_DB_IP = "localhost";
	public static final String DEFAULT_DB_PORT = "3306";
	public static final String DEFAULT_DB_NAME = "data_collect";
	public static final String DEFAULT_DB_USER = "dataCollect";
	public static final String DEFAULT_DB_PASSWD = "saz008632";	

	public static final String INFORMATION_SCHEMA_DB_NAME = "information_schema_db_name";
	public static final String INFORMATION_SCHEMA_DB_USER = "information_schema_db_user";
	public static final String INFORMATION_SCHEMA_DB_PASSWD = "information_schema_db_passwd";
	
	public static final String DEFAULT_INFORMATION_SCHEMA_DB_NAME = "information_schema";
	public static final String DEFAULT_INFORMATION_SCHEMA_DB_USER = "dataCollect";
	public static final String DEFAULT_INFORMATION_SCHEMA_DB_PASSWD = "saz008632";
	
	public static final String LOG_FILE_PATH_WINDOWS = "log_file_path_windows";
	public static final String LOG_FILE_PATH_OTHER = "log_file_path_other";	
	
	public static final String DEFAULT_CONFIG_FILE_PATH_WINDOWS = "c:/data_collect/";
	public static final String DEFAULT_CONFIG_FILE_PATH_OTHER = "/usr/local/etc/data_collect/";
	public static final String DEFAULT_LOG_FILE_PATH_WINDOWS = DEFAULT_CONFIG_FILE_PATH_WINDOWS + "logs/";
	public static final String DEFAULT_LOG_FILE_PATH_OTHER = DEFAULT_CONFIG_FILE_PATH_OTHER + "logs/";	
	
	public static final String MQ_URL = "mq_url";
	public static final String MQ_USER = "mq_user";
	public static final String MQ_PASSWD = "mq_passwd";
	public static final String MQ_SUBJECT = "mq_subject";

	public static final String DEFAULT_MQ_URL = "tcp://10.228.66.15:61616";
	public static final String DEFAULT_MQ_USER = "";
	public static final String DEFAULT_MQ_PASSWD = "";
	public static final String DEFAULT_MQ_SUBJECT = "TOOL.DEFAULT";
	
	public static final String CHROME_DRIVER_PATH = "chrome_driver_path";
	public static final String DEFAULT_CHROME_DRIVER_PATH = "E:/SelfWorkSpace/DataCollect/CommonLibRepository/tool/chromedriver2_win32_0.8/chromedriver.exe";
		
	public static final String LOG_LEVEL = "log_level";
	public static final String DEFAULT_LOG_LEVEL = "debug";
	
	
	public static DBMQCfgInfoDTO getDBMQConfig() throws Exception
	{
		if(dbCfgInfo==null)
		{
			dbCfgInfo = new DBMQCfgInfoDTO();			
			String dbCfgFilePath = ConfigTool.getConfigFilePath();	
 			
			Properties dbProps = FileTool.getPropertiesFile(dbCfgFilePath);
			if(dbProps.size()==0)
			{
				dbProps.setProperty(ConfigTool.DB_IP, DEFAULT_DB_IP);
				dbProps.setProperty(ConfigTool.DB_PORT, DEFAULT_DB_PORT);
				dbProps.setProperty(ConfigTool.DB_NAME, DEFAULT_DB_NAME);
				dbProps.setProperty(ConfigTool.DB_USER, DEFAULT_DB_USER);
				dbProps.setProperty(ConfigTool.DB_PASSWD, DEFAULT_DB_PASSWD);
				
				dbProps.setProperty(ConfigTool.INFORMATION_SCHEMA_DB_NAME, DEFAULT_INFORMATION_SCHEMA_DB_NAME);
				dbProps.setProperty(ConfigTool.INFORMATION_SCHEMA_DB_USER, DEFAULT_INFORMATION_SCHEMA_DB_USER);
				dbProps.setProperty(ConfigTool.INFORMATION_SCHEMA_DB_PASSWD, DEFAULT_INFORMATION_SCHEMA_DB_PASSWD);
				
				dbProps.setProperty(ConfigTool.MQ_URL, DEFAULT_MQ_URL);
				dbProps.setProperty(ConfigTool.MQ_USER, DEFAULT_MQ_USER);
				dbProps.setProperty(ConfigTool.MQ_PASSWD, DEFAULT_MQ_PASSWD);
				dbProps.setProperty(ConfigTool.MQ_SUBJECT, DEFAULT_MQ_SUBJECT);
				
				dbProps.setProperty(ConfigTool.CHROME_DRIVER_PATH, DEFAULT_CHROME_DRIVER_PATH);
				
				dbProps.setProperty(ConfigTool.LOG_LEVEL, DEFAULT_LOG_LEVEL);				
				FileTool.writePropertiesFile(dbCfgFilePath, dbProps);
			}			

			dbCfgInfo.setDbIp(StringTool.isEmpty(dbProps.getProperty(ConfigTool.DB_IP), DEFAULT_DB_IP));
			dbCfgInfo.setDbPort(StringTool.isEmpty(dbProps.getProperty(ConfigTool.DB_PORT), DEFAULT_DB_PORT));
			dbCfgInfo.setDbName(StringTool.isEmpty(dbProps.getProperty(ConfigTool.DB_NAME), DEFAULT_DB_NAME));
			dbCfgInfo.setDbUser(StringTool.isEmpty(dbProps.getProperty(ConfigTool.DB_USER), DEFAULT_DB_USER));
			dbCfgInfo.setDbPasswd(StringTool.isEmpty(dbProps.getProperty(ConfigTool.DB_PASSWD), DEFAULT_DB_PASSWD));
				
			dbCfgInfo.setInformationShcemaDbName(StringTool.isEmpty(dbProps.getProperty(ConfigTool.INFORMATION_SCHEMA_DB_NAME), ConfigTool.DEFAULT_INFORMATION_SCHEMA_DB_NAME));
			dbCfgInfo.setInformationShcemaDbUser(StringTool.isEmpty(dbProps.getProperty(ConfigTool.INFORMATION_SCHEMA_DB_USER), ConfigTool.DEFAULT_INFORMATION_SCHEMA_DB_USER));
			dbCfgInfo.setInformationShcemaDbPasswd(StringTool.isEmpty(dbProps.getProperty(ConfigTool.INFORMATION_SCHEMA_DB_PASSWD), ConfigTool.DEFAULT_INFORMATION_SCHEMA_DB_PASSWD));
				
			dbCfgInfo.setMqUrl(StringTool.isEmpty(dbProps.getProperty(ConfigTool.MQ_URL), DEFAULT_MQ_URL));
			dbCfgInfo.setMqUser(StringTool.isEmpty(dbProps.getProperty(ConfigTool.MQ_USER), DEFAULT_MQ_USER));
			dbCfgInfo.setMqPasswd(StringTool.isEmpty(dbProps.getProperty(ConfigTool.MQ_PASSWD), DEFAULT_MQ_PASSWD));
			dbCfgInfo.setMqSubject(StringTool.isEmpty(dbProps.getProperty(ConfigTool.MQ_SUBJECT), DEFAULT_MQ_SUBJECT));
			
			dbCfgInfo.setChromeDriverPath(StringTool.isEmpty(dbProps.getProperty(ConfigTool.CHROME_DRIVER_PATH), ConfigTool.DEFAULT_CHROME_DRIVER_PATH));
			dbCfgInfo.setLogLevel(StringTool.isEmpty(dbProps.getProperty(ConfigTool.LOG_LEVEL), ConfigTool.DEFAULT_LOG_LEVEL));
		}
		return dbCfgInfo;
	}
	
	private static String getConfigFilePath() throws Exception
	{
		String ret = "";
		if(UtilTool.ifWindowsOs())
		{
			ret = ConfigTool.DEFAULT_CONFIG_FILE_PATH_WINDOWS;
		}else
		{
			ret = ConfigTool.DEFAULT_CONFIG_FILE_PATH_OTHER;			
		}		
		FileTool.isFileExist(ret, true, true);
		ret += CONFIG_FILE_NAME;
		return ret;
	}
}
