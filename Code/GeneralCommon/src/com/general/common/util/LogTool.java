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
		
	public static void debugError(Throwable ex, String className)
	{
		try{
			LogTool.initLogger(className);
			String errMsg = ExceptionTool.getExceptionStackTraceString(ex);		
			LogTool.getLogger().debug(errMsg);
			System.out.println(errMsg);
		}catch(Exception e)
		{
			e.printStackTrace();
			e.printStackTrace(System.err);
		}
	}
	
	public static void logError(Throwable ex, String className)
	{
		try
		{
			LogTool.initLogger(className);
			String errMsg = ExceptionTool.getExceptionStackTraceString(ex);
			LogTool.getLogger().error(errMsg);
			System.out.println(errMsg);
		}catch(Exception e)
		{
			LogTool.logText(e.getMessage(), LogTool.class.getName());
			e.printStackTrace();
			e.printStackTrace(System.err);
		}
	}
	
	public static void debugText(String text, String className) 
	{
		try
		{
			if(!StringTool.isEmpty(text))
			{
				LogTool.initLogger(className);
				LogTool.getLogger().debug(text);
				//System.out.println(className + "  " + text);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			ex.printStackTrace(System.err);
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
				System.out.println(className + "  " + text);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			ex.printStackTrace(System.err);
		}
	}
}
