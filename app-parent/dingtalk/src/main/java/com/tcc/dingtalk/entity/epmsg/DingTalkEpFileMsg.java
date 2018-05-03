package com.tcc.dingtalk.entity.epmsg;

import java.util.HashMap;

public class DingTalkEpFileMsg extends DingTalkBaseEpMsg{
	private HashMap<String,String> file;

	public DingTalkEpFileMsg(){
		setMsgtype("file");
		file = new HashMap<>();
		file.put("media_id", "");
	}
	
	public void setMedia_id(String media_id){
		file.put("media_id", media_id);
	}
	public String getMedia_id(){
		return file.get("media_id");
	}
	
}
