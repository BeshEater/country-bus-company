package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class RoutePart {

    private Long id;
    private Long routeId;
    private Integer position;
}