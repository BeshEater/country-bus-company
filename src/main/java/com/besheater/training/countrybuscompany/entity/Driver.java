package com.besheater.training.countrybuscompany.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

public class Driver {

    private static final Logger LOG = LogManager.getLogger();

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