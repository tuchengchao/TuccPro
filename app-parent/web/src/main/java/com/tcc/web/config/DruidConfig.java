package com.tcc.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.tcc.web.websocket.MsWebSocket;

@Configuration
public class DruidConfig {
	private final static Logger logger = LoggerFactory.getLogger(MsWebSocket.class);  

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${druid.initialSize}")
    private Integer initialSize;
    @Value("${druid.minIdle}")
    private Integer minIdle;
    @Value("${druid.maxActive}")
    private Integer maxActive;
    @Value("${druid.maxWait}")
    private Integer maxWait;
    @Value("${druid.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${druid.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${druid.validationQuery}")
    private String validationQuery;
    @Value("${druid.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${druid.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${druid.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${druid.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${druid.filters}")
    private String filters;

    @Value("${druid.loginUsername}")
    private String loginUsername;
    @Value("${druid.loginPassword}")
    private String loginPassword;

    //    配置数据源
    @Bean(name = "basisDataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource initDataSource() {
    	logger.info("初始化DruidDataSource");
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName(driverClassName);
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setInitialSize(initialSize);
        dds.setMinIdle(minIdle);
        dds.setMaxActive(maxActive);
        dds.setMaxWait(maxWait);
        dds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dds.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dds.setValidationQuery(validationQuery);
        dds.setTestWhileIdle(testWhileIdle);
        dds.setTestOnBorrow(testOnBorrow);
        dds.setTestOnReturn(testOnReturn);
        dds.setPoolPreparedStatements(poolPreparedStatements);
        dds.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dds.setFilters(filters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dds;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername",loginUsername);
        servletRegistrationBean.addInitParameter("loginPassword",loginPassword);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}


