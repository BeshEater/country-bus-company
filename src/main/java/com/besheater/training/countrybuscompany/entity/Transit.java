package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Transit {

    private Long id;
    private Long routePartId;
    private Long fromTownId;
    private Long toTownId;
    private Integer position;
}