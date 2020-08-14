package com.besheater.training.countrybuscompany.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Slf4j
@Builder
public class Driver implements IdEntity {

    private Long id;
    private RoutePart routePart;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private LocalDate dateOfBirth;
    @NonNull private String address;
    private String driverLicenseNumber;
    private String phoneNumber;

    @Override
    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(routePart, driver.routePart) &&
                firstName.equals(driver.firstName) &&
                lastName.equals(driver.lastName) &&
                dateOfBirth.equals(driver.dateOfBirth) &&
                address.equals(driver.address) &&
                Objects.equals(driverLicenseNumber, driver.driverLicenseNumber) &&
                Objects.equals(phoneNumber, driver.phoneNumber);
    }
}