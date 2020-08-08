package com.besheater.training.countrybuscompany.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Transit {

    private static final Logger LOG = LogManager.getLogger();

    private Long id;
    private Long routePartId;
    private Long fromTownId;
    private Long toTownId;
    private Integer position;
}