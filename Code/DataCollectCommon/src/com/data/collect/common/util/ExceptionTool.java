package com.data.collect.common.util;

public class ExceptionTool extends BaseTool {

	
	public static boolean isDataTruncationException(Exception ex)
	{
		boolean ret = false;
		if(ex.getClass().getName().equals("com.mysql.jdbc.MysqlDataTruncation"))
		{
			ret = true;
		}
		return ret;
	}
	
	public static String getExceptionStackTraceString(Exception ex)
	{
		StringBuffer retBuf = new StringBuffer();
		StackTraceElement stackArr[] = ex.getStackTrace();
		if(!ClassTool.isNullObj(stackArr))
		{
			int len = stackArr.length;
			for(int i=0;i<len;i++)
			{
				StackTraceElement stack = stackArr[i];
				retBuf.append("File name = ");
				retBuf.append(stack.getFileName());
				retBuf.append("</br>Class name = ");
				retBuf.append(stack.getClassName());
				retBuf.append("</br>Method name = ");
				retBuf.append(stack.getMethodName());
				retBuf.append("</br>Line number = ");
				retBuf.append(stack.getLineNumber());
			}
		}
		
		return retBuf.toString();
	}
}
