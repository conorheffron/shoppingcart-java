package com.siriusxm.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TypicalJobConfiguration {


    @Value("${spring.datasource.url.short}")
    private String springdatasourceurl;

    @Value("${spring.datasource.username}")
    private String springdatasourceusername;

    @Value("${spring.datasource.password}")
    private String springdatasourcepassword;

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(springdatasourceurl)
                .username(springdatasourceusername)
                .password(springdatasourcepassword)
                .build();
    }
}

