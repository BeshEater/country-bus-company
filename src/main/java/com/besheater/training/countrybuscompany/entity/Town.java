package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
@AllArgsConstructor
public class Town implements IdEntity {

    private Long id;
    @NonNull private String name;
    @NonNull private String countryCode;
    private String region;
    @NonNull private Double latitude;
    @NonNull private Double longitude;

    @Override
    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return name.equals(town.name) &&
                countryCode.equals(town.countryCode) &&
                Objects.equals(region, town.region) &&
                latitude.equals(town.latitude) &&
                longitude.equals(town.longitude);
    }
}