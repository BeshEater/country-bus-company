package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class Bus {

    private Long id;
    private Route route;
    @NonNull private String registrationNumber;
    @NonNull private Integer capacity;
    @NonNull private boolean isDoubleDecker;
}