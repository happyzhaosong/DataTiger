package com.general.common.util;

public class GetDeltaTimeTool extends BaseTool {
	private long startTime = -1;	
	private long endTime = -1;
	
	
	public GetDeltaTimeTool() {
		super();
		startTime = System.currentTimeMillis();
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public long getDeltaTime(String message, StringBuffer retBuf, long maxDeltaTime, boolean writeLog)
	{
		if(ClassTool.isNullObj(retBuf))
		{
			retBuf = new StringBuffer();
		}
		
		endTime = System.currentTimeMillis();
		long deltaTime = endTime - startTime;
		if(deltaTime>=maxDeltaTime)
		{
			retBuf.append(" , ");
			retBuf.append(message);
			retBuf.append(" = ");
			retBuf.append(deltaTime);
			retBuf.append(" </br> ");
			startTime = endTime;
			
			if(writeLog)
			{
				LogTool.logText(retBuf.toString(), GetDeltaTimeTool.class.getName());
			}
		}		
		return deltaTime;
	}
}
