package com.besheater.training.countrybuscompany.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class Transit implements IdEntity{

    private Long id;
    @NonNull private RoutePart routePart;
    @NonNull private Town fromTown;
    @NonNull private Town toTown;
    @NonNull private Integer position;

    @Override
    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transit transit = (Transit) o;
        return routePart.equals(transit.routePart) &&
                fromTown.equals(transit.fromTown) &&
                toTown.equals(transit.toTown) &&
                position.equals(transit.position);
    }
}