package com.tcc.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Md5Util {
	/**
	 * 用md5加密
	 * 
	 * @param str
	 * @param salt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String b64Md5(String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		str = str + salt;
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		Base64.Encoder encoder = Base64.getEncoder();
		// 加密后的字符串
		String newstr = encoder.encodeToString((md5.digest(str.getBytes("utf-8"))));
		return newstr;
	}

	public static String hexMd5(String str, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		str = str + salt;
		// 确定计算方法
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] bytes = md5.digest(str.getBytes("utf-8"));
		StringBuilder ret = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
			ret.append(Character.forDigit(bytes[i] & 0xf, 16));
		}
		return ret.toString();
	}

	public static void main(String[] args) {
		try {
			System.out.println(hexMd5("1234", "verfication"));
			System.out.println(b64Md5("1234", "verfication"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
