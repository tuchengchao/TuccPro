package com.tcc.web.websocket.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.GsonBuilder;
import com.tcc.web.websocket.bean.MsMsg;

public class MsEncoder implements Encoder.Text<MsMsg>{
	@Override
	public void destroy() {

	}

	@Override
	public void init(EndpointConfig arg0) {

	}

	@Override
	public String encode(MsMsg t) throws EncodeException {
		return new GsonBuilder().serializeNulls().create() .toJson(t);  
	}
}
