package com.tccdemo.tcccoordinatoratomikos.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2019-09-04
 *
 * @author fenghongyu
 */
@Configuration
public class AtomikosTccConfig {

    @Bean
    public AtomikosTccSpringAdapter atomikosTccSpringAdapter() {
        return new AtomikosTccSpringAdapter();
    }

    public static class AtomikosTccSpringAdapter {
        @PostConstruct
        public void start() {
            com.atomikos.icatch.config.Configuration.init();
        }

        @PreDestroy
        public void shutdown() {
            com.atomikos.icatch.config.Configuration.shutdown(false);
        }
    }
}
