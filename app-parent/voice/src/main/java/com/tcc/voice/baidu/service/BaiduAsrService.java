package com.tcc.voice.baidu.service;

import java.io.IOException;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.util.Util;

public class BaiduAsrService {
	// 设置APPID/AK/SK
	public static final String APP_ID = "10332257";
	public static final String API_KEY = "XqPGSHTgqLfaITiNns7KOROB";
	public static final String SECRET_KEY = "9AWkBKWjGRD3ICzHaU0LuqbVKwXIweSu";


	public String recognize(String path) {
		// 初始化一个AipSpeech
		AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
		byte[] data;
		try {
			data = Util.readFileByBytes(path);
			JSONObject asrRes = client.asr(data, "pcm", 16000, null);
			if(asrRes.get("err_msg").toString().contains("success")){
				return asrRes.get("result").toString();
			}
			else{
				System.out.println(asrRes);
				return "识别失败";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		String path = "F:\\2100_convert.wav";
		System.out.println(new BaiduAsrService().recognize(path));
	}
}
