package com.tcc.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration  
public class WebSocketConfig {  
	private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    @Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
    	logger.info("初始化websocket serverEndpointExporter");
        return new ServerEndpointExporter();  
    }  
  
}  