package com.besheater.training.countrybuscompany.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Slf4j
@Builder
public class Driver {

    private Long id;
    private RoutePart routePart;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private LocalDate dateOfBirth;
    @NonNull private String address;
    private String driverLicenseNumber;
    private String phoneNumber;
}