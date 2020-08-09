package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Town {

    private Long id;
    private String name;
    private String countryCode;
    private String region;
    private Double latitude;
    private Double longitude;
}