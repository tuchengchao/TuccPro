package com.tcc.dingtalk.utils;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.tcc.dingtalk.entity.DingTalkResBean;
import com.tcc.dingtalk.entity.DingTalkTxtMsg;
import com.tcc.dingtalk.vo.DingTalkMsgVO;

import net.sf.json.JSONObject;

public class DingTalkUtils {
	public static String ANGNTID = "171751458";
	public static String CORPID = "ding1284cea1e755803535c2f4657eb6378f";
	public static String CORPSECRET = "slZcppr-LFOahmRuAOcHJ3_liXzopf_JOTIqt8yZWUVhuTXmROXB4Jgeor1tFeJF";
	public static Timer reflashAccessTokenTimer = new Timer();
	public static String ACCESSTOKEN = null;
	/**
	 * 获取AccessToken HTTPS/GET
	 * @param corpid
	 * @param corpsecret
	 * @return
	 */
	private static void reflashAccessToken(){
		String baseUrl = "https://oapi.dingtalk.com/gettoken";
		String resTxt = HttpsCaller.httpsGetRequest(baseUrl + "?corpid=" + CORPID + "&corpsecret=" + CORPSECRET);
		JSONObject resJson = JSONObject.fromObject(resTxt);
		if(resJson.getInt("errcode") == 0){
			ACCESSTOKEN = resJson.getString("access_token");
			reflashAccessTokenTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					DingTalkUtils.reflashAccessToken();
				}
			}, Long.parseLong(resJson.getString("expires_in"))*999);//比过期时间少一点
		}
	}
	/**
	 * 获取AccessToken
	 * @return
	 */
	public static String getAccessToken(){
		if(ACCESSTOKEN == null){
			reflashAccessToken();
		}
		return ACCESSTOKEN;
	}
	/**
	 * 发送消息给个人或者群 HTTPS/POST msg中的cid需在页面通过jsAPI获取
	 * @param msg 消息主体
	 * @return
	 */
	public static DingTalkResBean sendTxtMsg(DingTalkTxtMsg msg){
		String baseUrl = "https://oapi.dingtalk.com/message/send_to_conversation?access_token" + getAccessToken();
		return new DingTalkResBean(HttpsCaller.httpsPostRequest(baseUrl, msg.toJson()));
	}
	/**
	 * 获取管理员列表 HTTPS/GET
	 * @return
	 */
	public static DingTalkResBean getAdmin(){
		String baseUrl = "https://oapi.dingtalk.com/user/get_admin";
		return new DingTalkResBean(HttpsCaller.httpsGetRequest(baseUrl + "?access_token=" + getAccessToken()));
	}
	/**
	 * 获取部门列表 HTTPS/GET
	 * @param fetchChild 是否递归获取所有子部门
	 * @param id 父部门id，默认为1（根部门）
	 * @param lang 语言 选填（默认zh_CN，未来支持en_US）	
	 * @return
	 */
	public static DingTalkResBean getDeptList(Boolean fetchChild, String id, String lang){
		if(id == null || id.equals("")) {
			id = "1";
		}
		if(lang == null || (lang.equals("zh_CN")||lang.equals("en_US"))){
			lang = "zh_CN";
		}
		String baseUrl = "https://oapi.dingtalk.com/department/list?access_token="+ getAccessToken() + "&fetch_child=" 
				+ fetchChild + "&id="+id + "&lang="+lang;
		return new DingTalkResBean(HttpsCaller.httpsGetRequest(baseUrl));
	}
	/**
	 * 获取部门用户列表（简）HTTPS/GET
	 * @param deptId
	 * @return
	 */
	public static DingTalkResBean getDeptMember(String deptId){
		String baseUrl = "https://oapi.dingtalk.com/user/simplelist?access_token=" + getAccessToken() + "&department_id=" + deptId;
		return new DingTalkResBean(HttpsCaller.httpsGetRequest(baseUrl));
	}
	/**
	 * 获取部门用户列表（详细）HTTPS/GET
	 * @param deptId
	 * @return
	 */
	public static DingTalkResBean getDeptMemberMore(String deptId){
		String baseUrl = "https://oapi.dingtalk.com/user/list?access_token=" + getAccessToken() + "&department_id=" + deptId;
		return new DingTalkResBean(HttpsCaller.httpsGetRequest(baseUrl));
	}
	/**
	 * 发送企业通知信息 HTTPS/POST
	 * @param msg
	 * @return
	 */
	public static DingTalkResBean sendEpMsg(DingTalkMsgVO msg) {
		String baseUrl = "https://oapi.dingtalk.com/message/send?access_token=" + getAccessToken();
		String data = "";
		if(msg.getMsgtype().equals("text")){
			data = msg.toEpTxtMsg().toJson();
		}
		else if(msg.getMsgtype().equals("image")){
			data = msg.toEpImageMsg().toJson();
		}
		else if(msg.getMsgtype().equals("link")){
			data = msg.toEpLinkMsg().toJson();
		}
		else if(msg.getMsgtype().equals("file")){
			data = msg.toEpFileMsg().toJson();
		}
		return new DingTalkResBean(HttpsCaller.httpsPostRequest(baseUrl, data));
	}
	/**
	 * 获取部门详细信息 HTTPS/GET
	 * @param id
	 * @return
	 */
	public static DingTalkResBean getDpetMore(String id){
		String baseUrl = "https://oapi.dingtalk.com/department/get?access_token=" + getAccessToken() + "&id=" + id;
		return new DingTalkResBean(HttpsCaller.httpsGetRequest(baseUrl));
	}
	/**
	 * 上传媒体文件到钉钉
	 * @param file
	 * @param type
	 * @return
	 */
	public static DingTalkResBean uploadMedia(File file, String type) {
		String baseUrl = "https://oapi.dingtalk.com/media/upload?access_token=" + getAccessToken() + "&type=" + type;
        return new DingTalkResBean(HttpsCaller.postFile(baseUrl, file));
    }
	public static void main(String[] args) {
//		String accessTokenR = getAccessToken();
//		System.out.println(accessTokenR);
//		String admin = getAdmin().getJson().toString();
//		System.out.println(admin);
		String deptList = getDeptList(false, "1", null).getJson().toString();
		System.out.println(deptList);
		String deptMember = getDeptMember("64318144").getJson().toString();
		System.out.println(deptMember);
//		
//		String deptMore = getDpetMore("1").getJson().toString();
//		System.out.println(deptMore);
//		
//		DingTalkMsgVO msgVO = new DingTalkMsgVO();
//		msgVO.setToparty("");
//		msgVO.setTouser("manager8333");
//		msgVO.setMsgType("text");
//		msgVO.setMsgContent("测试消息1");
//		String sendEmsg = sendEnterpriseMsg(msgVO).getJson().toString();
//		System.out.println(sendEmsg);
		
//		String baseUrl = "https://oapi.dingtalk.com/message/send?access_token=" + getAccessToken();
//		System.out.println(HttpsCaller.httpsPostRequest(baseUrl, "{\"touser\":\"manager8333\",\"toparty\":\"\",\"agentid\":\"171751458\",\"msgtype\":\"text\",\"text\":{\"content\":\"测试消息1\"}}"));
	
//		DingTalkResBean bean = uploadMedia(new File("D:\\test.jpg"), "image");
//		System.out.println(bean.getJson().toString());
	}
	
}
