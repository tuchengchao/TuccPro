package com.tcc.common.utils;

public class StrUtils {
	/**
	 * 判断字符串是否为null或者空
	 * @param str
	 * @return
	 */
	public static boolean isBlank(Object str){
		if(str == null || str.equals("")){
			return true;
		}
		return false;
	}
	
	/**
	 * 将null转换成""
	 * @param str
	 * @return
	 */
	public static String null2blank(String str){
		if(isBlank(str) || str.equals("null")){
			return "";
		}
		return str;
	}
	
}
