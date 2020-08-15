package com.besheater.training.countrybuscompany.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class Transit {

    private Long id;
    @NonNull private RoutePart routePart;
    @NonNull private Town fromTown;
    @NonNull private Town toTown;
    @NonNull private Integer position;
}