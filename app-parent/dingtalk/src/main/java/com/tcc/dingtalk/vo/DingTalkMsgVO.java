package com.tcc.dingtalk.vo;

import com.tcc.dingtalk.entity.epmsg.DingTalkBaseEpMsg;
import com.tcc.dingtalk.entity.epmsg.DingTalkEpFileMsg;
import com.tcc.dingtalk.entity.epmsg.DingTalkEpImageMsg;
import com.tcc.dingtalk.entity.epmsg.DingTalkEpLinkMsg;
import com.tcc.dingtalk.entity.epmsg.DingTalkEpTxtMsg;
import com.tcc.dingtalk.utils.DingTalkUtils;

public class DingTalkMsgVO {
	
	private String touser;
	private String toparty;
	private String msgtype;
	private String usershow;
	private String msgcontent;
	private String fjid;
	private String media_id;
	private String title;
	private String messageUrl;
	
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
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getUsershow() {
		return usershow;
	}
	public void setUsershow(String usershow) {
		this.usershow = usershow;
	}
	public String getFjid() {
		return fjid;
	}
	public void setFjid(String fjid) {
		this.fjid = fjid;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessageUrl() {
		return messageUrl;
	}
	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}
	
	public void setEpCommon(DingTalkBaseEpMsg msg){
		msg.setAgentid(DingTalkUtils.ANGNTID);
		msg.setToparty(toparty == null ? "":toparty.replace(",", "|"));
		msg.setTouser(touser == null ? "":touser.replace(",", "|"));
	}
	public DingTalkEpTxtMsg toEpTxtMsg(){
		DingTalkEpTxtMsg msg = new DingTalkEpTxtMsg();
		setEpCommon(msg);
		msg.setContent(msgcontent);
		return msg;
	}
	public DingTalkEpImageMsg toEpImageMsg(){
		DingTalkEpImageMsg msg = new DingTalkEpImageMsg();
		setEpCommon(msg);
		msg.setMedia_id(media_id);
		return msg;
	}
	public DingTalkEpLinkMsg toEpLinkMsg(){
		DingTalkEpLinkMsg msg = new DingTalkEpLinkMsg();
		setEpCommon(msg);
		msg.setPicUrl(media_id);
		msg.setText(msgcontent);
		msg.setTitle(title);;
		msg.setMessageUrl(messageUrl);
		return msg;
	}
	
	public DingTalkEpFileMsg toEpFileMsg(){
		DingTalkEpFileMsg msg = new DingTalkEpFileMsg();
		setEpCommon(msg);
		msg.setMedia_id(media_id);
		return msg;
	}
 }
