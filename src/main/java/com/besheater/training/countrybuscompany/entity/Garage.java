package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class Garage {

    private Long id;
    @NonNull private Town town;
    @NonNull private String name;
    @NonNull private String address;
    @NonNull private Integer capacity;
}