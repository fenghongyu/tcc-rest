package com.tcc.inventoryservice.config;

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
@MapperScan(basePackages = "com.tcc.inventoryservice.dao", sqlSessionTemplateRef  = "inventorySqlSessionTemplate")
public class DataSourceInventoryConfig {
    @Bean(name = "inventoryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.inventory")
    public DataSource inventoryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "inventoryTxManager")
    public DataSourceTransactionManager inventoryTxManager(@Qualifier("inventoryDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "inventorySqlSessionFactory")
    public SqlSessionFactory inventorySqlSessionFactory(@Qualifier("inventoryDataSource") DataSource dataSource) throws Exception {
        // DefaultVFS在获取jar上存在问题，使用springboot只能修改
        VFS.addImplClass(SpringBootVFS.class);

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:/mybatis/mybatis-config.xml"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/inventory/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "inventorySqlSessionTemplate")
    public SqlSessionTemplate inventorySqlSessionTemplate(@Qualifier("inventorySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
