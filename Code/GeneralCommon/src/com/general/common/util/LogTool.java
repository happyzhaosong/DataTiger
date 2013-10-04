package com.general.common.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogTool extends BaseTool {
		
	private static Logger logger = null;
	private static String className = null;
	
	private static Logger getLogger() throws Exception
	{
		if(LogTool.logger==null)
		{
			if(LogTool.className==null)
			{
				LogTool.className = LogTool.class.getName();
			}
			logger = Logger.getLogger(LogTool.className);
			PropertyConfigurator.configure(ConfigTool.getLog4jConfigeFilePath());
		}
		return logger;
	}
	
	private static void initLogger(String className)
	{
		if(!StringTool.isEmpty(className))
		{
			LogTool.logger = null;
			LogTool.className = className;
		}
	}
		
	public static void debugError(Throwable ex, String className) throws Exception
	{
		LogTool.initLogger(className);
		LogTool.getLogger().debug(ExceptionTool.getExceptionStackTraceString(ex));
	}
	
	public static void logError(Throwable ex, String className)
	{
		try
		{
			LogTool.initLogger(className);
			LogTool.getLogger().error(ExceptionTool.getExceptionStackTraceString(ex));
		}catch(Exception e)
		{
			LogTool.logText(e.getMessage(), LogTool.class.getName());
			e.printStackTrace();
			e.printStackTrace(System.err);
		}
	}
	
	public static void debugText(String text, String className) throws Exception
	{
		if(!StringTool.isEmpty(text))
		{
			LogTool.initLogger(className);
			LogTool.getLogger().debug(text);
		}		
	}
	
	public static void logText(String text, String className)
	{
		try
		{
			if(!StringTool.isEmpty(text))
			{
				LogTool.initLogger(className);
				LogTool.getLogger().info(text);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			ex.printStackTrace(System.err);
		}
	}
}
