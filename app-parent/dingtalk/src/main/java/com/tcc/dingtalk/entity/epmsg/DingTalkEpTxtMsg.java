package com.tcc.dingtalk.entity.epmsg;

import java.util.HashMap;

import com.google.gson.Gson;

public class DingTalkEpTxtMsg extends DingTalkBaseEpMsg{
	private HashMap<String,String> text;
	
	public DingTalkEpTxtMsg(){
		setMsgtype("text");
		text = new HashMap<>();
		text.put("content", "");
	}
	public HashMap<String, String> getText() {
		return text;
	}
	public void setText(HashMap<String, String> text) {
		this.text = text;
	}
	public void setContent(String content){
		text.put("content", content);
	}
	public String getContent(){
		return text.get("content");
	}
	
}
