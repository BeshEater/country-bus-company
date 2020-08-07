package com.besheater.training.countrybuscompany.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Town {

    private static final Logger LOG = LogManager.getLogger();

    private Long id;
    private String name;
    private String countryCode;
    private String region;
    private Double latitude;
    private Double longitude;
}
