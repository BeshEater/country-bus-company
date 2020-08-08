package com.besheater.training.countrybuscompany;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j2
public class Runner {

    public static void main( String[] args ) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(RootContextConfig.class);
        log.debug("My cool debug message");
    }
}