package com.tcc.web.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tcc.web.shiro.MyShiroRealm;

@Configuration
public class ShiroConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);
	
	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		logger.info("初始化shirFilter");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 注意过滤器配置顺序 不能颠倒
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
//		filterChainDefinitionMap.put("/logout", "logout");
		// 配置不会被拦截的链接 顺序判断
		
		filterChainDefinitionMap.put("/public/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/plugins/**", "anon");
		
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/hello", "anon");
		filterChainDefinitionMap.put("/verification", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/register", "anon");
		filterChainDefinitionMap.put("/msWebSocket/**", "anon");

		filterChainDefinitionMap.put("/**", "authc");
		// 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
		shiroFilterFactoryBean.setLoginUrl("/error/401");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/logon");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * 凭证匹配器 
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}
	/**
	 * 自定义shiroRealm
	 * @return
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}
	/**
	 * 安全管理器
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		logger.info("初始化securityManager");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		return securityManager;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
