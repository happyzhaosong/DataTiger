package com.data.collect.common.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class LogTool extends BaseTool {

	private static String logFilePath = null;	
	private static String logFile = null;
	private static PrintWriter logFilePrintWriter = null;
	private static BufferedWriter logFileBufWriter = null;
	
	public static void debugError(Throwable ex) throws Exception
	{
		if("debug".equalsIgnoreCase(ConfigTool.getDBMQConfig().getLogLevel()))
		{
			logError(ex);
		}
	}
	
	public static void logError(Throwable ex)
	{
		try
		{
			LogTool.logText(DateTool.getNowString());
			ex.printStackTrace(LogTool.getLogWriter());
			LogTool.getLogWriter().flush();			
			ex.printStackTrace();
		}catch(Exception e)
		{
			LogTool.logText(e.getMessage());
			e.printStackTrace();
			e.printStackTrace(System.err);
		}
	}
	
	public static void debugText(String text) throws Exception
	{
		if("debug".equalsIgnoreCase(ConfigTool.getDBMQConfig().getLogLevel()))
		{
			if(!StringTool.isEmpty(text))
			{
				logText("***debug***" + text);
			}
		}
	}
	
	public static void logText(String text)
	{
		try
		{
			if(!StringTool.isEmpty(text))
			{
				StringBuffer buf = new StringBuffer();
				buf.append("\r\n*****log text start*****\r\n");
				buf.append(" Thread id : ");
				buf.append(Thread.currentThread().getId());
				buf.append(" , Run Time : ");
				buf.append(DateTool.getNowString());
				buf.append(" ");
				buf.append(text);
				buf.append("\r\n*****log text end*****\r\n");
				
				LogTool.getLogWriter().write(buf.toString());
				LogTool.getLogWriter().flush();
				System.out.println(buf.toString());
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			ex.printStackTrace(System.err);
		}
	}
		
	private synchronized static PrintWriter getLogWriter() throws Exception
	{
		String logFile= LogTool.getLogFile();
		if(logFileBufWriter==null)
		{
			
			//File file = new File(logFile, logFile);
			logFileBufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
		}
		
		if(logFilePrintWriter==null)
		{
			if(logFileBufWriter!=null)
			{
				logFilePrintWriter = new PrintWriter(logFileBufWriter);
			}else
			{
				logFilePrintWriter = new PrintWriter(logFile);
			}
		}
		
		return logFilePrintWriter;
	}
	
	private synchronized static String getLogFile()
	{
		try
		{
			if(logFile==null)
			{
				String logFilePath = LogTool.getLogFilePath();
				logFile = logFilePath + "data_collect";
				FileTool.isFileExist(logFile, true, false);			
			}
			
			long fileSize = FileTool.getFileSize(logFile);

			if(fileSize>10000000)
			{
				if(!ClassTool.isNullObj(LogTool.logFilePrintWriter))
				{
					LogTool.logFilePrintWriter.write("fileSize > 10000000");
				//}

				/*
				if(!ClassTool.isNullObj(logFileBufWriter))
				{
					LogTool.logFilePrintWriter.write("logFileBufWriter.flush()");
					logFileBufWriter.flush();
				}
				*/
				//if(!ClassTool.isNullObj(logFilePrintWriter))
				//{
					LogTool.logFilePrintWriter.write("logFilePrintWriter.flush()");
					logFilePrintWriter.flush();
				//}
				/*
				if(!ClassTool.isNullObj(logFileBufWriter))
				{
					LogTool.logFilePrintWriter.write("logFileBufWriter.close()");
					logFileBufWriter.close();
				}
				*/
				//if(!ClassTool.isNullObj(logFilePrintWriter))
				//{
					LogTool.logFilePrintWriter.write("logFilePrintWriter.close()");
					logFilePrintWriter.close();
				}
				logFileBufWriter = null;				
				logFilePrintWriter = null;
				
				String backupLogFile = logFile + "_" + System.currentTimeMillis();
				FileTool.renameFile(logFile, backupLogFile);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
			ex.printStackTrace(System.err);
		}finally
		{
			return logFile;
		}
	}
	
	private static String getLogFilePath() throws IOException
	{
		if(logFilePath==null)
		{
			if(UtilTool.ifWindowsOs())
			{
				logFilePath = ConfigTool.DEFAULT_LOG_FILE_PATH_WINDOWS;
			}else
			{
				logFilePath = ConfigTool.DEFAULT_LOG_FILE_PATH_OTHER;
			}
			
			FileTool.isFileExist(logFilePath, true, true);
		}
		return logFilePath;
	}
}
