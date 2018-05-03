package com.tcc.dingtalk.entity;

import java.util.HashMap;

import com.google.gson.Gson;

public class DingTalkTxtMsg {
	private String sender;
	private String cid;
	private String msgtype;
	private HashMap<String,String> text;
	/**
	 * 
	 * @param sender 发送者
	 * @param cid
	 * @param content
	 */
	public DingTalkTxtMsg(String sender, String cid, String content){
		this.sender = sender;
		this.cid = cid;
		this.msgtype = "text";
		text = new HashMap<>();
		text.put("content", "");
	}
	
	public String getSender(){
		return sender;
	}
	public void setSender(String sender){
		this.sender = sender;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public HashMap<String, String> getText() {
		return text;
	}
	public void setText(HashMap<String, String> text) {
		this.text = text;
	}
	public String getContent(){
		return text.get("content");
	}
	public void setContent(String content){
		text.put("content", content);
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}
	
}
