package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Garage {

    private Long id;
    private Long townId;
    private String name;
    private String address;
    private Integer capacity;
}