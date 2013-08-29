package com.general.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class FileTool extends BaseTool {

	
	public static void writeFileData(String filePath, String content, boolean append)
	{
		try
		{
			File f = new File(filePath);
			if(!f.exists())
			{
				f.createNewFile();
			}
			
			FileWriter fw = new FileWriter(f, append);
			BufferedWriter bfw = new BufferedWriter(fw);
			bfw.write(content);
			bfw.flush();
			fw.flush();
			bfw.close();
			fw.close();
		}catch(Exception ex)
		{
			LogTool.logError(ex);
			ex.printStackTrace(System.err);
		}
	}
	
	public static long getFileSize(String filePath) throws Exception
	{
		long ret = 0;
		File f = new File(filePath);
		if(f.exists())
		{
			ret = f.length();
		}
		return ret;
	}
	
	public static void renameFile(String srcFilePath, String destFilePath) throws Exception
	{
		if(FileTool.isFileExist(srcFilePath, false, false))
		{
			File srcFile = new File(srcFilePath);
			File destFile = new File(destFilePath);
			srcFile.renameTo(destFile);
		}
	}
	
	public static String getFileData(String filePath) throws Exception
	{
		StringBuffer retBuf = new StringBuffer();
		if(FileTool.isFileExist(filePath, false, false))
		{
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			InputStreamReader ir = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(ir);
			
			String line = br.readLine();
			while(line!=null)
			{
				retBuf.append(line);
				line = br.readLine();
			}
			br.close();
			ir.close();
			is.close();
			br=null;
			ir=null;
			is=null;
		}
		return retBuf.toString();
	}
	
	
	public static void writePropertiesFile(String filePath, Properties props) throws Exception
	{
		if(FileTool.isFileExist(filePath, true, false))
		{
			FileOutputStream fos = new FileOutputStream(filePath);			
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			props.store(bos, "default config values.");
			bos.flush();
			fos.flush();
			bos.close();
			fos.close();
			bos=null;
			fos=null;
		}
	}
	
	public static Properties getPropertiesFile(String filePath) throws Exception
	{
		Properties props = new Properties();
		if(FileTool.isFileExist(filePath, true, false))
		{
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			props.load(bis);
			bis.close();
			is.close();
			bis=null;
			is=null;
		}
		return props;
	}
	
	public static boolean isFileExist(String filePath, boolean createNew,  boolean isDir) throws IOException
	{
		boolean ret = true;
		File file = new File(filePath);
		if(!file.exists())
		{
			ret = false;
			if(createNew)
			{
				if(isDir)
				{
					file.mkdirs();
				}else
				{
					file.createNewFile();
				}
			}
		}
		return ret;
	}
	
}
