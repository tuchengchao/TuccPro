package com.tcc.dingtalk.entity;

import net.sf.json.JSONObject;

public class DingTalkResBean {
	private int errcode;
	private String errmsg;
	private JSONObject json;
	
	public DingTalkResBean(String resTxt) {
		json = JSONObject.fromObject(resTxt);
		errcode = json.getInt("errcode");
		errmsg = json.getString("errmsg");
	}
	public int getErrcode() {
		return errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public JSONObject getJson() {
		return json;
	}
	public Object get(String key){
		return json.get(key);
	}
}
