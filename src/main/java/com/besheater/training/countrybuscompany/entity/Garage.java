package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class Garage {

    private Long id;
    private Long townId;
    private String name;
    private String address;
    private Integer capacity;
}