package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class Town {

    private Long id;
    private String name;
    private String countryCode;
    private String region;
    private Double latitude;
    private Double longitude;
}