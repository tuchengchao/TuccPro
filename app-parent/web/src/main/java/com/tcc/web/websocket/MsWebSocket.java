package com.tcc.web.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/msws/{key}")
public class MsWebSocket {

	private final static Logger logger = LoggerFactory.getLogger(MsWebSocket.class);
	private static final HashMap<String, Session> sessions = new HashMap<>();
	
	@OnMessage
	public void onMessage(@PathParam(value = "key") String key, String message, Session session) {
		logger.info("recive message from uri:{} message:{}", key , message);
	}

	@OnOpen
	public void onOpen(@PathParam(value = "key") String key, Session session, EndpointConfig config) {
		logger.info("open websocket connection for uri:{}", key);
		sessions.put(key, session);
	}
	
	@OnClose
	public void onClose(@PathParam(value="key")String key, Session session){
		logger.info("close websocket connection for uri", key);
		sessions.remove(key);
	}
	
	@OnError
	public void onError(@PathParam(value="key")String key){
		logger.info("websocket arose an error for uri", key);
	}
	public static final void sendText(String key, Object message, MsType... type) throws IOException, EncodeException{
		Session session = sessions.get(key);
		if(key == null){
			logger.info("send message.but {} had already leaved", key);
		}
		else{
			logger.info("send message to {}, message: {}", key, message);
			if(type.length == 0){
				type = new MsType[]{MsType.TEXT};
			}
			Basic basic = session.getBasicRemote();
			
			switch(type[0]){
				case TEXT:
					basic.sendText((String)message);
					break;
				case OBJECT:
					basic.sendObject(message);
					break;
				case BINARY:
				case PING:
				case PONG:
					basic.sendBinary((ByteBuffer)message);
					break;
			}
		}
	}
	
	
}
