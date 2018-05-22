package com.tcc.web.bean;

import com.tcc.web.enums.LoginStatus;

public class LoginMsgBean extends JsonBean {

	private int code;
	private String msg;

	public LoginMsgBean(LoginStatus status) {
		this.code = status.getCode();
		this.msg = status.getMsg();
	}
	public static LoginMsgBean create(LoginStatus status){
		return new LoginMsgBean(status);
	}
	public int getCode() {
		return code;
	}

	public LoginMsgBean setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public LoginMsgBean setMsg(String msg) {
		this.msg = msg;
		return this;
	}

}
