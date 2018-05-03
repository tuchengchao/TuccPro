package com.tcc.voice.sinovoice.service;

import com.tcc.voice.sinovoice.AccountInfo;
import com.tcc.voice.sinovoice.SinovoiceAsrHelper;
import com.tcc.voice.sinovoice.SinovoiceSysHelper;

public class SinovoiceAsrService {

	public String recognize(String path) {
		
		AccountInfo mAccountInfo;
		mAccountInfo = AccountInfo.getInstance();
		mAccountInfo.loadAccountInfo();
		SinovoiceSysHelper mHciCloudSysHelper;
		SinovoiceAsrHelper mHciCloudAsrHelper;
		mHciCloudSysHelper = SinovoiceSysHelper.getInstance();
		mHciCloudAsrHelper = SinovoiceAsrHelper.newInstance();
		mHciCloudSysHelper.init();
		mHciCloudAsrHelper.init();
		// 实时识别
		boolean nRet = mHciCloudAsrHelper.RealtimeRecog(path, "");
		String result = "fail";
		if (nRet) {
			result = mHciCloudAsrHelper.getResult();
		}
		mHciCloudAsrHelper.release();
		mHciCloudSysHelper.release();

		return result;
	}

	public static void main(String[] args) {
		SinovoiceAsrService service = new SinovoiceAsrService();
		String result = service.recognize("D:\\Documents\\Music\\test.pcm");
		System.out.println(result);
		new MyThread().start();
		new MyThread().start();
	}
}
class MyThread extends Thread{
	@Override
	public void run() {
		SinovoiceAsrService service = new SinovoiceAsrService();
		String result = service.recognize("D:\\Documents\\Music\\test.pcm");
		System.out.println(result);	
	}
}
 