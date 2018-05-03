package com.tcc.voice.iflytek.service;

import java.io.File;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizerListener;
import com.tcc.voice.iflytek.IflytekInit;

public class IflytekTtsService {
	static {
		IflytekInit.init();
	}
	
	private static class DefaultValue {
		public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
		public static final String VOICE = "小燕";
		public static final String BG_SOUND = "0";
		public static final String SPEED = "50";
		public static final String PITCH = "50";
		public static final String VOLUME = "50";
		public static final String SAVE = "D:\\Documents\\Music\\";
	}
	/**
	 * 合成语音
	 * @param content
	 * @param filename
	 */
	public void synthesize(String content, String filename) {
		// 1.创建SpeechSynthesizer对象
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// 2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		initParam(mTts, filename);
		// 3.开始合成
		mTts.startSpeaking(content, mSynListener);
	}
	/**
	 * 设置参数
	 * @param mTts
	 * @param filename
	 */
	public void initParam(SpeechSynthesizer mTts, String filename) {
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, DefaultValue.ENG_TYPE);
		mTts.setParameter(SpeechConstant.VOICE_NAME, DefaultValue.VOICE);
		mTts.setParameter(SpeechConstant.BACKGROUND_SOUND, DefaultValue.BG_SOUND);
		mTts.setParameter(SpeechConstant.SPEED, DefaultValue.SPEED);
		mTts.setParameter(SpeechConstant.PITCH, DefaultValue.PITCH);
		mTts.setParameter(SpeechConstant.VOLUME, DefaultValue.VOLUME);
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, DefaultValue.SAVE + File.separator + filename);
	}

	private SynthesizerListener mSynListener = new SynthesizerListener() {

		@Override
		public void onSpeakBegin() {
		}

		@Override
		public void onBufferProgress(int progress, int beginPos, int endPos, String info) {
		}

		@Override
		public void onSpeakPaused() {
		}

		@Override
		public void onSpeakResumed() {
		}

		@Override
		public void onSpeakProgress(int progress, int beginPos, int endPos) {
		}

		@Override
		public void onCompleted(SpeechError error) {
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, int arg3, Object obj1, Object obj2) {
		}
	};

	public static void main(String[] args) {
		new IflytekTtsService().synthesize(
				"君不见，黄河之水天上来，奔流到海不复回。君不见，高堂明镜悲白发，朝如青丝暮成雪。人生得意须尽欢，莫使金樽空对月。天生我材必有用，千金散尽还复来。烹羊宰牛且为乐，会须一饮三百杯。岑夫子，丹丘生，将进酒，杯莫停。与君歌一曲，请君为我倾耳听。(倾耳听 一作：侧耳听)钟鼓馔玉不足贵，但愿长醉不复醒。(不足贵 一作：何足贵；不复醒 一作：不愿醒/不用醒)古来圣贤皆寂寞，惟有饮者留其名。(古来 一作：自古；惟 通：唯)陈王昔时宴平乐，斗酒十千恣欢谑。主人何为言少钱，径须沽取对君酌。五花马，千金裘，呼儿将出换美酒，与尔同销万古愁。","将进酒.pcm");
	}
}
