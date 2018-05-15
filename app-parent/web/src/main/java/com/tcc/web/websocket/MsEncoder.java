package com.tcc.web.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.GsonBuilder;

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
