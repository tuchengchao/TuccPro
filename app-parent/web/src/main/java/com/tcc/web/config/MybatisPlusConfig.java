package com.tcc.web.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.tcc.common.utils.StrUtils;

/**
 * MybatisPlus配置
 *
 */
@Configuration
@EnableTransactionManagement(order = 2)
public class MybatisPlusConfig {

    private static final Log log = LogFactory.getLog(MybatisPlusConfig.class);
    
    @Value("${mybatisPlus.basePackage}")String basePackage;

    @Value("${mybatisPlus.configLocation}")String configLocation;
	/**
	 * mybatisPlus分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor pagination = new PaginationInterceptor();
		return pagination;
	}
	
	/**
	 * mybatisPlus全局配置
	 * @param idType
	 * @param fieldStrategy
	 * @param dbColumnUnderline
	 * @param isRefresh
	 * @param isCapitalMode
	 * @param logicDeleteValue
	 * @param logicNotDeleteValue
	 * @return
	 */
	@Bean(name = "globalConfig")
  	public GlobalConfiguration globalConfig(
          @Value("${mybatisPlus.globalConfig.idType}") Integer idType, //主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
          @Value("${mybatisPlus.globalConfig.fieldStrategy}") Integer fieldStrategy, //字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
          @Value("${mybatisPlus.globalConfig.dbColumnUnderline}") Boolean dbColumnUnderline, //驼峰下划线转换
          @Value("${mybatisPlus.globalConfig.refreshMapper}") Boolean isRefresh, //刷新mapper 调试神器
          @Value("${mybatisPlus.globalConfig.capitalMode}") Boolean isCapitalMode, //数据库大写下划线转换
          @Value("${mybatisPlus.globalConfig.logicDeleteValue}") String logicDeleteValue, //逻辑删除配置
          @Value("${mybatisPlus.globalConfig.logicNotDeleteValue}") String logicNotDeleteValue //逻辑删除配置
	) {
		log.info("初始化GlobalConfiguration");
		GlobalConfiguration globalConfig = new GlobalConfiguration();
		if (!StrUtils.isBlank(idType)) {
			globalConfig.setIdType(idType); // 主键类型
		}
		if (!StrUtils.isBlank(fieldStrategy)) {
			globalConfig.setFieldStrategy(fieldStrategy); //字段策略
		}
		if (!StrUtils.isBlank(dbColumnUnderline)) {
			globalConfig.setDbColumnUnderline(dbColumnUnderline); // 驼峰下划线转换
		}
		if (!StrUtils.isBlank(isRefresh)) {
			globalConfig.setRefresh(isRefresh); //刷新mapper 调试神器
		}
		if (!StrUtils.isBlank(isCapitalMode)) {
			globalConfig.setCapitalMode(isCapitalMode); // 数据库大写下划线转换
		}
		if (!StrUtils.isBlank(logicDeleteValue)) {
			globalConfig.setLogicDeleteValue(logicDeleteValue); //逻辑删除配置
		}
		if (!StrUtils.isBlank(logicNotDeleteValue)) {
			globalConfig.setLogicNotDeleteValue(logicNotDeleteValue);//逻辑删除配置
		}
		return globalConfig;
	}
	/**
	 * sqlSessionFactory
	 * @param globalConfig
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "globalConfig")GlobalConfiguration globalConfig,
                                               @Qualifier(value = "basisDataSource")DruidDataSource dataSource,
                                               @Value("${mybatisPlus.mapperLocations}")String mapperLocations,
                                               @Value("${mybatisPlus.typeAliasesPackage}")String typeAliasesPackage,
                                               @Value("${mybatisPlus.configLocation}")String configLocation) throws Exception {
        log.info("初始化SqlSessionFactory");
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource); //数据源
        sqlSessionFactory.setGlobalConfig(globalConfig); //全局配置
        Interceptor[] interceptor = {new PaginationInterceptor()};
        sqlSessionFactory.setPlugins(interceptor); //分页插件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //自动扫描Mapping.xml文件
            sqlSessionFactory.setMapperLocations(resolver.getResources(mapperLocations));
            sqlSessionFactory.setConfigLocation(resolver.getResource(configLocation));
            sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
            return sqlSessionFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
	/**
	 * 动态包扫描
	 * @return
	 */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        log.info("初始化MapperScannerConfigurer");
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.tcc.web.mapper");
        return mapperScannerConfigurer;
    }

    /**
     * 配置事务管理
     * @param dataSource
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier(value = "basisDataSource")DruidDataSource dataSource) {
        log.info("初始化DataSourceTransactionManager");
        DataSourceTransactionManager dstm = new DataSourceTransactionManager(dataSource);
        return dstm;
    }
}
