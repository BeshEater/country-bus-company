package com.besheater.training.countrybuscompany;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Runner {

    public static void main( String[] args ) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(RootContextConfig.class);
        log.debug("My cool debug message");
    }
}