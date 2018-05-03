package com.tcc.dingtalk.entity.epmsg;

import com.google.gson.GsonBuilder;

public class DingTalkBaseEpMsg {
	private String touser;/*员工id列表（消息接收者，多个接收者用|分隔）*/
	private String toparty;/*部门id列表，多个接收者用|分隔。*/
	private String agentid;/*钉钉应用id*/
	private String msgtype;/*消息类型*/
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getToparty() {
		return toparty;
	}
	public void setToparty(String toparty) {
		this.toparty = toparty;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	public String toJson(){
		return new  GsonBuilder().serializeNulls().create() .toJson(this);
	}
	
}
