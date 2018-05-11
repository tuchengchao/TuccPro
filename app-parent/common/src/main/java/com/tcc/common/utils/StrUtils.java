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
	/**
	 * 生成固定长度的某范围内的随机字符串
	 * @param range
	 * @param length
	 * @return
	 */
	public static String random(String range, int length){
		StringBuffer code = new StringBuffer();
		int l = range.length();
        for(int i = 0;i < length;i ++){
            int index = (int)(Math.random() * l);
            code.append(range.charAt(index));
        }
        return code.toString();
	}
	
}
