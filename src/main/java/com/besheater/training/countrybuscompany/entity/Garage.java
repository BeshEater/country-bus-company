package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class Garage implements IdEntity {

    private Long id;
    @NonNull private Town town;
    @NonNull private String name;
    @NonNull private String address;
    @NonNull private Integer capacity;

    @Override
    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garage garage = (Garage) o;
        return town.equals(garage.town) &&
                name.equals(garage.name) &&
                address.equals(garage.address) &&
                capacity.equals(garage.capacity);
    }
}