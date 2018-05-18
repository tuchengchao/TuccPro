package com.tcc.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Md5Util {
	/**
	 * b64-md5
	 * 
	 * @param str
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String b64Md5(String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return b64Md5(str, salt, 1);
	}
	/**
	 * b64-md5
	 * @param str
	 * @param salt
	 * @param time
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String b64Md5(String str, String salt, int time) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		str = salt + str;
		Base64.Encoder encoder = Base64.getEncoder();
		// 加密后的字符串
		byte[] bytes = md5(str, time);
		String newstr = encoder.encodeToString(bytes);
		return newstr;
	}
	/**
	 * hex-md5
	 * @param str
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String hexMd5(String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return hexMd5(str, salt, 1);
	}
	/**
	 * hex-md5
	 * @param str
	 * @param salt
	 * @param time
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String hexMd5(String str, String salt, int time) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		str = salt + str;
		// 确定计算方法
		byte[] bytes = md5(str, time);
		StringBuilder ret = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
			ret.append(Character.forDigit(bytes[i] & 0xf, 16));
		}
		return ret.toString();
	}
	/**
	 * md5
	 * @param str
	 * @param time
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] md5(String str, int time) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		// 加密后的字符串
		byte[] bytes = str.getBytes("utf-8");
		for(int i = 0;i < time;i ++){
			bytes = md5.digest(bytes);
		}
		return bytes;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(b64Md5("king", "tcc", 2));
			System.out.println(hexMd5("123456", "tcc", 2));
			System.out.println(hexMd5("1234", "verfication"));
			System.out.println(b64Md5("1234", "verfication"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
