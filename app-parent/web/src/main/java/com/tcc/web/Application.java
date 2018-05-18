package com.tcc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import com.tcc.web.utils.SpringContextUtil;

@SpringBootApplication
@EnableCaching
public class Application
{
	/**
     * 自定义异常页
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

       return (container -> {
            ErrorPage error401Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error/403.html");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html");
            container.addErrorPages(error401Page, error404Page, error500Page);
       });
    }
    public static void main( String[] args )
    {
    	ApplicationContext ctx = SpringApplication.run(Application.class, args);
    	SpringContextUtil.setApplicationContext(ctx);
    }
}
