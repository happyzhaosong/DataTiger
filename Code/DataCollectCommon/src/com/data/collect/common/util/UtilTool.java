package com.data.collect.common.util;

public class UtilTool extends BaseTool {
		
	public static boolean ifMacOs()
	{
		return UtilTool.ifConditionOs("mac");
	}
	
	public static boolean ifLinuxOs()
	{
		return UtilTool.ifConditionOs("linux");
	}
	
	public static boolean ifWindowsOs()
	{
		return UtilTool.ifConditionOs("windows");
	}
	
	private static String getOsName()
	{
		return System.getProperty("os.name");
	}
	
	private static boolean ifConditionOs(String osCharactor)
	{
		boolean ret = false;
		if(osCharactor!=null && !"".equals(osCharactor))
		{
			String osName = UtilTool.getOsName();
			if(osName.toLowerCase().indexOf(osCharactor.toLowerCase())!=-1)
			{
				ret = true;
			}
		}
		return ret;
	}	
}
