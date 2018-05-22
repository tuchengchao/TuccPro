package com.tcc.web.utils;

import org.springframework.context.ApplicationContext;

public class SpringContextUtil {
	/**
	 * 上下文对象实例
	 */
	private static ApplicationContext applicationContext;  
	  
	/**
	 * 获取applicationContext
	 * 
	 * @return
	 */ 
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    //设置上下文  
    public static void setApplicationContext(ApplicationContext applicationContext) {  
        SpringContextUtil.applicationContext = applicationContext;  
    }  
  
    /**
	 * 通过name获取 Bean.
	 * 
	 * @param name
	 * @return
	 */
    public static Object getBean(String name){  
        return applicationContext.getBean(name);  
    }  
      
    /**
	 * 通过class获取Bean.
	 * 
	 * @param clazz
	 * @param <T>
	 * @return
	 */
    public static Object getBean(Class<?> requiredType){  
        return applicationContext.getBean(requiredType);  
    }  
}
