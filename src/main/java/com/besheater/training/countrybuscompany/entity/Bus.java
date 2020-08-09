package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Bus {

    private Long id;
    private Long routeId;
    private String registrationNumber;
    private Integer capacity;
    private Boolean isDoubleDecker;
}