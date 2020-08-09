package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Route {

    private Long id;
    private Integer averagePassengersPerDay;
}