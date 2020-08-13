package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Data
@AllArgsConstructor
public class Bus implements IdEntity{

    private Long id;
    private Route route;
    @NonNull private String registrationNumber;
    @NonNull private Integer capacity;
    @NonNull private boolean isDoubleDecker;

    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return isDoubleDecker == bus.isDoubleDecker &&
                Objects.equals(route, bus.route) &&
                registrationNumber.equals(bus.registrationNumber) &&
                capacity.equals(bus.capacity);
    }

}