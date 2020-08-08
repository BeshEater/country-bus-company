package com.besheater.training.countrybuscompany.entity;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Data
@Log4j2
public class Driver {
    private Long id;
    private Long routePartId;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dateOfBirth;
    private String address;
    private String driverLicenseNumber;
    private String phoneNumber;
}