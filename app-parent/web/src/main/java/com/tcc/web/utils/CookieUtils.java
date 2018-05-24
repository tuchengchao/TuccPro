package com.tcc.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcc.common.utils.JwtUtils;
import com.tcc.web.store.Constants;

public class CookieUtils {
	/**
	 * 获取Cookie
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name){
		Cookie cookies[] = request.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				if(cookie.getName().equals(name)){
					return cookie;
				}
			}
		}
		return null;
	}
	/**
	 * 设置JWT
	 * @param response
	 * @param jsonWebToken
	 */
	public static void addJwtCookie(HttpServletResponse response, String jsonWebToken){
        addCookie(response, Constants.JWT_NAME, jsonWebToken, "User JWT Authorization", "", true, JwtUtils.expiresMicrosecond / 1000, "/", false, 1);
	}
	/**
	 * 设置Cookie
	 * @param response
	 * @param name
	 * @param value
	 * @param comment
	 * @param domain
	 * @param httpOnly
	 * @param maxAge
	 * @param path
	 * @param secure
	 * @param version
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, String comment,  String domain, Boolean httpOnly, int maxAge, String path, Boolean secure, int version ){
		Cookie cookie = new Cookie(name, value);
		cookie.setComment(comment);
		cookie.setDomain(domain);
		cookie.setHttpOnly(httpOnly);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setSecure(secure);
		cookie.setVersion(version);
        response.addCookie(cookie);
	}
	/**
	 * 移除JWT
	 * @param response
	 */
	public static void removeJwtCookie(HttpServletResponse response){
		addCookie(response, Constants.JWT_NAME, "", "", "", true, 0, "/", false, 1);
	}
}
