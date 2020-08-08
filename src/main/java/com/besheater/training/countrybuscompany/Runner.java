package com.besheater.training.countrybuscompany;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner {
    private static final Logger LOG = LogManager.getLogger();

    public static void main( String[] args ) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(RootContextConfig.class);
        LOG.debug("My cool debug message");
    }
}