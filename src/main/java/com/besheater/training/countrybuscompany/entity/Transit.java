package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class Transit {

    private Long id;
    private Long routePartId;
    private Long fromTownId;
    private Long toTownId;
    private Integer position;
}