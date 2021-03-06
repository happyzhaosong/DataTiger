package com.general.common.util;

import java.text.Format;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.general.common.constants.GeneralConstants;

public class DateTool extends BaseTool {
	
	public static String workingDayStr[] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

	public static String getThisWorkingdayInStr(String dateFormatStr)
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(Calendar.SATURDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}else if(Calendar.SUNDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -2);
		}
		Date now = calendar.getTime();		
		Format fomartter = new SimpleDateFormat(dateFormatStr);
		return fomartter.format(now);
	}
	
	public static String getLastWorkingdayInStr(String dateFormatStr)
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(Calendar.SATURDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -2);
		}else if(Calendar.SUNDAY == dayOfWeek || Calendar.MONDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -3);
		}else
		{
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		Date now = calendar.getTime();		
		Format fomartter = new SimpleDateFormat(dateFormatStr);
		return fomartter.format(now);
	}
	
	public static String getThisWorkingWeekdayInStr()
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(Calendar.SATURDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}else if(Calendar.SUNDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -2);
		}
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);	
		return DateTool.workingDayStr[dayOfWeek-1];
	}
	
	public static String getLastWorkingWeekdayInStr()
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if(Calendar.SATURDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -2);
		}else if(Calendar.SUNDAY == dayOfWeek || Calendar.MONDAY == dayOfWeek)
		{
			calendar.add(Calendar.DAY_OF_YEAR, -3);
		}else
		{
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);	
		return DateTool.workingDayStr[dayOfWeek-1];
	}

	
	public static String getTodayInStr(String dateFormatStr)
	{		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();		
		Format fomartter = new SimpleDateFormat(dateFormatStr);
		return fomartter.format(now);
	}
	
	public static String getYesterdayInStr(String dateFormatStr)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = calendar.getTime();		
		Format fomartter = new SimpleDateFormat(dateFormatStr);
		return fomartter.format(yesterday);
	}
	
	public static Hashtable<String,String> getLastWeekInStr(String dateFormatStr)
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_YEAR, (0-dayOfWeek));
		Date endDate = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		Date startDate = calendar.getTime();
		 
		Format fomartter = new SimpleDateFormat(dateFormatStr);
		
		String startDateStr = fomartter.format(startDate);
		String endDateStr = fomartter.format(endDate);
		
		Hashtable ret = new Hashtable();
		ret.put(GeneralConstants.DATE_START_TIME, startDateStr);
		ret.put(GeneralConstants.DATE_END_TIME, endDateStr);
		return ret;
	}
	
	public static String getDateStringByDateFormat(Date date, String dateFormatStr)
	{
		Format fomartter = new SimpleDateFormat(dateFormatStr);		
		String ret = fomartter.format(date);
		return ret;
	}
	
	public static String getNowString()
	{
		long currTime = System.currentTimeMillis();
		Date date = new Date();
		date.setTime(currTime);
		return DateTool.getDateStringByDateFormat(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static boolean checkDate()
	{
		boolean ret = false;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		if(year>=2012 && month>=10)
		{
			ret = true;
		}
		return ret;
	}
	
	public static long getMilliSecondsBetweenTwoTime(long currSystemTimeMilliSeconds, long lastSystemTimeMilliSeconds)
	{
		long ret = 0;
		
		Date currDate = new Date(currSystemTimeMilliSeconds);
		Date lastDate = new Date(lastSystemTimeMilliSeconds);
		
		Calendar currCal = Calendar.getInstance();
		currCal.setTimeInMillis(currSystemTimeMilliSeconds);

		Calendar lastCal = Calendar.getInstance();
		lastCal.setTimeInMillis(lastSystemTimeMilliSeconds);

		
		int deltaYear = currCal.get(Calendar.YEAR) - lastCal.get(Calendar.YEAR); 
		int deltaMonth = currCal.get(Calendar.MONTH) - lastCal.get(Calendar.MONTH);
		int deltaDate = currCal.get(Calendar.DAY_OF_MONTH) - lastCal.get(Calendar.DAY_OF_MONTH);
		int deltaHour = currCal.get(Calendar.HOUR_OF_DAY) - lastCal.get(Calendar.HOUR_OF_DAY);
		int deltaMinute = currCal.get(Calendar.MINUTE) - lastCal.get(Calendar.MINUTE);
		int deltaSecond = currCal.get(Calendar.SECOND) - lastCal.get(Calendar.SECOND);
		int deltaMilliSecond = currCal.get(Calendar.MILLISECOND) - lastCal.get(Calendar.MILLISECOND);
		
		ret = deltaYear * 365 * 24 * 60 * 60 + deltaMonth * 31 * 24 * 60 *60 + deltaDate * 24 * 60 *60 + deltaHour * 60 *60 + deltaMinute * 60 + deltaSecond;
		
		ret = ret*1000 + deltaMilliSecond;
		
		System.out.println("ret miliseconds = " + ret);
		return ret;
	}
}
