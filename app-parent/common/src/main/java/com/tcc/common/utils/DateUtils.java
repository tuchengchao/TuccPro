package com.tcc.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	/**
	 * 将Date格式转成字符串格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String... format){
		format = format.length == 0 ? new String[]{"yyyy-MM-dd hh:mm:ss"} : format;
		SimpleDateFormat sdf = new SimpleDateFormat(format[0]);
		String dateStr = "";
		try{
			dateStr = sdf.format(date);
		}
		catch (Exception e){
			
		}
		return dateStr;
	}
}
