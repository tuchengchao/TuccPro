package com.tcc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tcc.web.utils.SpringContextUtil;

@SpringBootApplication
public class Application 
{
    public static void main( String[] args )
    {
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	SpringContextUtil.setApplicationContext(ctx);
    }
}
