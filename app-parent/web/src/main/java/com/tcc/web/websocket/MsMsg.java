package com.tcc.web.websocket;

import com.google.gson.GsonBuilder;

public class MsMsg {
	
	private Object msg;
	private String category;
	private MsType type;
	private String operation;

	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public MsType getType() {
		return type;
	}
	public void setType(MsType type) {
		this.type = type;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	private MsMsg(){};
	/**
	 * 生成
	 * @param msg
	 * @param type
	 * @return
	 */
	public static MsMsg msg(Object msg, String category, MsType ...type){
		MsMsg _msg = new MsMsg();
		_msg.msg = msg;
		_msg.category = category;
 		if(type.length > 0){
 			_msg.type = type[0];
 		}
 		else{
 			_msg.type = MsType.TEXT;
 		}
		return _msg;
	}
	/**
	 * 生成
	 * @param msg
	 * @param category
	 * @param operation
	 * @param type
	 * @return
	 */
	public static MsMsg msg(Object msg, String category, String operation, MsType ...type){
		MsMsg _msg = new MsMsg();
		_msg.msg = msg;
		_msg.category = category;
		_msg.setOperation(operation);
 		if(type.length > 0){
 			_msg.type = type[0];
 		}
 		else{
 			_msg.type = MsType.TEXT;
 		}
		return _msg;
	}
	public String toJson(){
		return new GsonBuilder().serializeNulls().create() .toJson(this);
	}
}
