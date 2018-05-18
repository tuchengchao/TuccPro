package com.tcc.web.websocket;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.annotation.Resource;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.tcc.web.utils.ApplicationContextProvider;

@Component
@ServerEndpoint(value = "/msWebSocket/{uri}/{params}",  encoders = { MsEncoder.class })
public class MsWebSocket {

	private final static Logger logger = LoggerFactory.getLogger(MsWebSocket.class);
	private static final HashMap<String, Session> sessions = new HashMap<>();
	@Resource
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	
	@OnMessage
	public void onMessage(@PathParam(value = "uri") String uri, String message, Session session) {
		logger.info("recive message from uri:{} message:{}", uri , message);
	}

	@SuppressWarnings("unchecked")
	@OnOpen
	public void onOpen(@PathParam(value = "uri") String uri, Session session, EndpointConfig config) {
		logger.info("open websocket connection for {}", uri);
		sessions.put(uri, session);
		if(redisTemplate == null){
			redisTemplate =(RedisTemplate<Serializable, Serializable>)ApplicationContextProvider.getBean("redisTemplate");
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		redisTemplate.opsForValue().set(uri, map);
	}
	
	@SuppressWarnings("unchecked")
	@OnClose
	public void onClose(@PathParam(value="uri")String uri, Session session){
		logger.info("close websocket connection for {}", uri);
		sessions.remove(uri);
		if(redisTemplate == null){
			redisTemplate =(RedisTemplate<Serializable, Serializable>)ApplicationContextProvider.getBean("redisTemplate");
		}
		redisTemplate.delete(uri);
	}
	
	@OnError
	public void onError(@PathParam(value="uri")String uri,Throwable t){
		logger.info("websocket arose an error for {}:{}", uri, t.toString());
	}
	/**
	 * 发送
	 * @param uri
	 * @param message
	 * @param type
	 * @throws IOException
	 * @throws EncodeException
	 */
	public static final void send(String uri, MsMsg msg) throws IOException, EncodeException{
		Session session = sessions.get(uri);
		if(session == null){
			logger.info("send message.but {} had already leaved", uri);
		}
		else{
			logger.info("send message to {}, message: {}", uri, msg.getMsg());
			Basic basic = session.getBasicRemote();
			switch(msg.getType()){
				case TEXT:
					basic.sendText(msg.toJson());
					break;
				case OBJECT:
					basic.sendObject(msg);
					break;
				case BINARY:
				case PING:
				case PONG:
					basic.sendBinary((ByteBuffer)msg.getMsg());
					break;
			}
		}
	}
	/**
	 * 群发
	 * @param message
	 * @param type
	 * @throws IOException
	 * @throws EncodeException
	 */
	public static final void massSend(MsMsg msg) throws IOException, EncodeException{
		logger.info("send message to all user");
		for(String uri : sessions.keySet()){
			send(uri, msg);
		}
	}
	
}
