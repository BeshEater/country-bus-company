package com.besheater.training.countrybuscompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
public class RoutePart implements IdEntity{

    private Long id;
    @NonNull private Route route;
    @NonNull private Integer position;

    @Override
    public boolean equalsWithoutId(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutePart routePart = (RoutePart) o;
        return route.equals(routePart.route) &&
                position.equals(routePart.position);
    }
}