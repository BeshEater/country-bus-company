package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Data
@AllArgsConstructor
public class Route implements IdEntity{

    private Long id;
    private Integer averagePassengersPerDay;

    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(averagePassengersPerDay, route.averagePassengersPerDay);
    }
}