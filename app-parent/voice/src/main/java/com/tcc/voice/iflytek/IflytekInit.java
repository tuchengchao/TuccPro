package com.tcc.voice.iflytek;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

public class IflytekInit {
	private static final String appid = "5992642a";
	private static class LazyHolder {    
	       private static final IflytekInit INSTANCE = new IflytekInit();    
	} 
	private IflytekInit() {
		SpeechUtility.createUtility(SpeechConstant.APPID + "=" + appid);
	}
	public static IflytekInit init() {
	   return LazyHolder.INSTANCE; 
	}
}
