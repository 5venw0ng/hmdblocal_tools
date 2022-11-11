package com.ruoyi.common.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Value("${liquibase.context}")
    private String liquibaseContext;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        //指定changelog的位置，这里使用的一个master文件引用其他文件的方式
        liquibase.setChangeLog("classpath:/db/liquibase/changelog-master.xml");
        liquibase.setDropFirst(false);
        liquibase.setContexts(liquibaseContext);
        liquibase.setShouldRun(true);
        return liquibase;
    }

}
