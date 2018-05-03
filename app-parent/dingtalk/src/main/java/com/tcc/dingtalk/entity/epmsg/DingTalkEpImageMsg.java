package com.tcc.dingtalk.entity.epmsg;

import java.util.HashMap;

public class DingTalkEpImageMsg extends DingTalkBaseEpMsg{
	private HashMap<String,String> image;
	
	public DingTalkEpImageMsg(){
		setMsgtype("image");
		image = new HashMap<>();
		image.put("media_id", "");
	}
	
	public void setMedia_id(String media_id){
		image.put("media_id", media_id);
	}
	public String getMedia_id(){
		return image.get("media_id");
	}
	
}
