package com.tcc.web.enums;

public enum LoginStatus {
	INVALID_URI(0, "无效的用户标识"),
	SUCCESS(1,"登录成功"),
	EXPIRE_VERFICATION(2, "验证码过期"),
	ERROR_VERFICATION(3, "验证码错误"),
	UNKOWN_ACCOUNT(4,"用户名或密码不正确"),
	ERROR_PASSWORD(5,"用户名或密码不正确"),
	LOCKED_ACCOUNT(6,"您的账户已被锁定，请30分钟后重新尝试"),
	DISABLED_ACCOUNT(7,"您的账户已被禁用，请联系管理员"),
	AUTO_FAILED(8,"无法自动登录");
	
	private int code;
	private String msg;

	private LoginStatus(int code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}

	public int getCode() {
		return code;
	}

	public LoginStatus setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public LoginStatus setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	
}
