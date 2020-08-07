package com.besheater.training.countrybuscompany.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Garage {

    private static final Logger LOG = LogManager.getLogger();

    private Long id;
    private Long townId;
    private String name;
    private String address;
    private Integer capacity;
}