package com.tcc.dingtalk.entity.epmsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DingTalkEpOaMsg extends DingTalkBaseEpMsg{
	
	private HashMap<String, Object> oa;
	
	public DingTalkEpOaMsg(){
		oa = new HashMap<>();
		
		oa.put("message_url", "");//图片(非必须)
		
		HashMap<String, String> head = new HashMap<>();
		head.put("bgcolor", ""); //头部背景颜色 
		head.put("text", ""); //头部标题
		oa.put("head", head);
		
		HashMap<String,Object> body = new HashMap<>();
		List<HashMap<String, String>> formlist = new ArrayList<>(6);
		HashMap<String, String> rich = new HashMap<>();
		body.put("title", ""); //正文标题 (非必须)
		body.put("form", formlist);//正文表单(非必须，最多6个)
		rich.put("num", "");
		rich.put("unit", "");
		body.put("rich", rich);
		body.put("content", "");
		body.put("image", "");
		oa.put("body", body);
	}
	public void setMessageUrl(String messageUrl){
		oa.put("message_url", messageUrl);
	}
	public String getMessageUrl(){
		return (String)oa.get("message_url");
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getHead(){
		return (HashMap<String,String>)(oa.get("head"));
	}
	public String getHeadBgColor(){
		return getHead().get("bgcolor");
	}
	public void setHeadBgColor(String bgcolor){
		getHead().put("bgcolor", bgcolor);
	}
	public String getHeadText(){
		return getHead().get("text");
	}
	public void setHeadText(String text){
		getHead().put("text", text);
	}
	@SuppressWarnings("unchecked")
	public HashMap<String,Object> getBody(){
		return (HashMap<String,Object>)(oa.get("body"));
	}
	public String getBodyTitle(){
		return (String)getBody().get("title");
	}
	public void setBodyTitle(String title){
		getBody().put("title", title);
	}
	public HashMap<String, String> addBodyForm(String key, String value){
		if(getBodyForm().size() <= 6){
			HashMap<String, String> form = new HashMap<>();
			form.put("key", key);
			form.put("value", value);
			getBodyForm().add(form);
			return form;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> getBodyForm(){
		return (List<HashMap<String, String>>)(getBody().get("form"));
	}
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getBodyRich(){
		return (HashMap<String, String>)(getBody().get("rich"));
	}
	public String getBodyRichNum(){
		return getBodyRich().get("num");
	}
	public void setBodyRichNum(String num){
		getBodyRich().put("num", num);
	}
	public String getBodyRichUnit(){
		return getBodyRich().get("unit");
	}
	public void setBodyRichUnit(String unit){
		getBodyRich().put("unit", unit);
	}
	public String getBodyContent(){
		return (String)getBody().get("content");
	}
	public void setBodyContent(String content){
		getBody().put("content", content);
	}
	public String getBodyImage(){
		return (String)getBody().get("image");
	}
	public void setBodyImage(String image){
		getBody().put("image", image);
	}
}
