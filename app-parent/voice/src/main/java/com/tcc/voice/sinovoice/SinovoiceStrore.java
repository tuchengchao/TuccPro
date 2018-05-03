package com.tcc.voice.sinovoice;

public class SinovoiceStrore {
	public static String sPath = "";
	static {
		sPath = new SinovoiceStrore().getClass().getResource("/").getPath();
		sPath = sPath.substring(1, sPath.indexOf("WEB-INF")) + "WEB-INF";
		sPath = sPath.replace("/", "\\").replace("%20", " ");
	}
	public static void main(String[] args) {
	}
}	
