package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class Route {

    private Long id;
    private Integer averagePassengersPerDay;
}