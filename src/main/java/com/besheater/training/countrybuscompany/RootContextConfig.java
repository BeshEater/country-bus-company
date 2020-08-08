package com.besheater.training.countrybuscompany;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.besheater.training.countrybuscompany")
public class RootContextConfig {
    private static final Logger LOG = LogManager.getLogger();

    @Bean
    public DataSource dataSource() {
        return null;
    }
}