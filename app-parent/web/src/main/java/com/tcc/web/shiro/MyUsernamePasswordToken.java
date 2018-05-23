package com.tcc.web.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.tcc.web.enums.LoginType;

public class MyUsernamePasswordToken extends UsernamePasswordToken{

	private static final long serialVersionUID = 1070599048743758914L;

	private LoginType loginType;

	public MyUsernamePasswordToken(final String username, final String password, final LoginType loginType){
		this(username, password, false, null, loginType);
	}

	public MyUsernamePasswordToken(final String username, final String password,
            final boolean rememberMe, final String host, final LoginType loginType){
		this.setUsername(username);
		this.setPassword(password != null ? password.toCharArray() : null);
		this.setHost(host);
		this.setRememberMe(rememberMe);
		this.setLoginType(loginType);
	}
	
	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
	
}
