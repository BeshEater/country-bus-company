package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class Town {

    private Long id;
    @NonNull private String name;
    @NonNull private String countryCode;
    private String region;
    @NonNull private Double latitude;
    @NonNull private Double longitude;
}