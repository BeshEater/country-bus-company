package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@Log4j2
public class Bus {

    private Long id;
    private Long routeId;
    private String registrationNumber;
    private Integer capacity;
    private Boolean isDoubleDecker;
}