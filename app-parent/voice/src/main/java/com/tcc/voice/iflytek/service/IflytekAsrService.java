package com.tcc.voice.iflytek.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.tcc.voice.iflytek.IflytekInit;

public class IflytekAsrService{
	static {
		IflytekInit.init();
	}
	
	private String result = "";
	private boolean mIsEndOfSpeech = false;
	private boolean isEndOfRecognize = false;
	private static class DefaultValue{
		public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;//引擎类型 { "cloud", "local","mixed" }
		public static final String SPEECH_TIMEOUT = "60000";//语音输入超时时间 [0, 60000]
		public static final String NET_TIMEOUT = "20000"; //网络连接超时时间 [0, 30000]
		public static final String LANGUAGE = "zh_cn";//语言 { "zh_cn", "en_us" }
		public static final String ACCENT = "mandarin";//语言区域（方言） { null, "mandarin", "cantonese", "lmz", "henanese" }
		public static final String DOMAIN = "iat";//应用领域  { "iat", "video", "poi", "music" }
		public static final String VAD_BOS = "5000"; //前端点超时 [1000, 10000]
		public static final String VAD_EOS = "1800"; //后端点超时 [0, 10000]
		public static final String RATE = "16000";//识别采样率 {8000, 16000}
		public static final String NBEST = "1";//句子级多候选    听写：[1, 5]
		public static final String WBEST = "1";//词级多候选     听写：[1, 5]
		public static final String PTT = "1";//标点符号
		public static final String RESULT_TYPE = "plain";//识别结果类型 { "json", "xml", "plain" }
	}
	/**
	 * 获取语音识别结果
	 * @return
	 */
	private String getResult(){
		while(!isEndOfRecognize){
		//等待识别结束  
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * 识别语音
	 * @param path 文件路径
	 * @throws SQLException
	 * @throws IOException
	 */
	public String recognize(String path) throws SQLException, IOException{
		InputStream fis = new FileInputStream(path);
		isEndOfRecognize = false;
		result = "";
		//1.创建SpeechRecognizer对象
		SpeechRecognizer recognizer= SpeechRecognizer.createRecognizer( );
		initParam(recognizer);
		//3.开始听写
		recognizer.startListening(mRecoListener);
		final byte[] buffer = new byte[64*1024];
		if (0 == fis.available()) {
			recognizer.cancel();
		} else {
			int lenRead = buffer.length;
			while( buffer.length==lenRead && !mIsEndOfSpeech ){
				lenRead = fis.read( buffer );
				recognizer.writeAudio( buffer, 0, lenRead );
			}//end of while
			fis.close();
		}
		recognizer.stopListening();
		return getResult();
	}
	/**
	 * 设置参数
	 * @param recognizer
	 */
	private void initParam(SpeechRecognizer recognizer){
		recognizer.setParameter( SpeechConstant.ENGINE_TYPE, DefaultValue.ENG_TYPE );
		recognizer.setParameter( SpeechConstant.SAMPLE_RATE, DefaultValue.RATE );
		recognizer.setParameter( SpeechConstant.NET_TIMEOUT, DefaultValue.NET_TIMEOUT );
		recognizer.setParameter( SpeechConstant.KEY_SPEECH_TIMEOUT, DefaultValue.SPEECH_TIMEOUT );
		recognizer.setParameter( SpeechConstant.LANGUAGE, DefaultValue.LANGUAGE );
		recognizer.setParameter( SpeechConstant.ACCENT, DefaultValue.ACCENT );
		recognizer.setParameter( SpeechConstant.DOMAIN, DefaultValue.DOMAIN );
		recognizer.setParameter( SpeechConstant.VAD_BOS, DefaultValue.VAD_BOS );
		recognizer.setParameter( SpeechConstant.VAD_EOS, DefaultValue.VAD_EOS );
		recognizer.setParameter( SpeechConstant.ASR_NBEST, DefaultValue.NBEST );
		recognizer.setParameter( SpeechConstant.ASR_WBEST, DefaultValue.WBEST );
		recognizer.setParameter( SpeechConstant.ASR_PTT, DefaultValue.PTT );
		recognizer.setParameter( SpeechConstant.RESULT_TYPE, DefaultValue.RESULT_TYPE );
		recognizer.setParameter( SpeechConstant.ASR_AUDIO_PATH, null );
		recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
	}
	//听写监听器
	private RecognizerListener mRecoListener = new RecognizerListener(){
		
		//开始识别
		@Override
		public void onBeginOfSpeech() {
		}

		//结束识别
		@Override
		public void onEndOfSpeech() {
			mIsEndOfSpeech = true;
		}

		/**
		 * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加
		 */
		@Override
		public void onResult(RecognizerResult results, boolean islast) {
			result += results.getResultString();
			isEndOfRecognize = islast;
		}
		
		//音量变化
		@Override
		public void onVolumeChanged(int volume) {
			if (volume == 0)
				volume = 1;
			else if (volume >= 6)
				volume = 6;
		}
		
		//异常
		@Override
		public void onError(SpeechError error) {
			if (null != error){
				isEndOfRecognize = true;
				result = error.getErrorDescription(false);
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int agr2, String msg) {
		}
	};
	
	
	public static void main(String[] args) {
		try {
			SpeechUtility.createUtility( SpeechConstant.APPID + "=5992642a");
			IflytekAsrService service = new IflytekAsrService();
			String result = service.recognize("F:\\20180122_convert.wav");
			System.out.println(result);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}