package com.tcc.orderservice.config;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created on 2019-09-05
 *
 * @author fenghongyu
 */
@Configuration
@MapperScan(basePackages = "com.tcc.orderservice.dao", sqlSessionTemplateRef  = "orderSqlSessionTemplate")
public class DataSourceOrderConfig {
    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource orderDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "orderTxManager")
    public DataSourceTransactionManager orderTxManager(@Qualifier("orderDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        // DefaultVFS在获取jar上存在问题，使用springboot只能修改
        VFS.addImplClass(SpringBootVFS.class);

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/mybatis-config.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/order/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate orderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
