package com.tcc.dingtalk.entity.epmsg;

import java.util.HashMap;

import com.google.gson.Gson;

public class DingTalkEpLinkMsg extends DingTalkBaseEpMsg{
	private HashMap<String,String> link;
	
	public DingTalkEpLinkMsg(){
		setMsgtype("link");
		link = new HashMap<>();
		link.put("messageUrl", "");
		link.put("picUrl", "");
		link.put("title", "");
		link.put("text", "");
	}

	public void setMessageUrl(String messageUrl){
		link.put("messageUrl", messageUrl);
	}
	
	public String getMessageUrl(){
		return link.get("messageUrl");
	}
	
	public void setPicUrl(String picUrl){
		link.put("picUrl", picUrl);
	}
	
	public String getPicUrl(){
		return link.get("picUrl");
	}
	
	public void setTitle(String title){
		link.put("title", title);
	}
	
	public String getTitle(){
		return link.get("title");
	}
	
	public void setText(String text){
		link.put("text", text);
	}
	
	public String getText(){
		return link.get("text");
	}
	
	public String toJson(){
		return new Gson().toJson(this);
	}
}
