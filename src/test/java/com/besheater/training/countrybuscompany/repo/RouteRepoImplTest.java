package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Route;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteRepoImplTest extends CrudRepoTest<Route> {

    @Override
    public void init() {
        entityRepo = new RouteRepoImpl(database);

        entityCount = 5L;

        existingEntity1 = new Route(1L, 125);
        existingEntity2 = new Route(2L, 271);
        existingEntity3 = new Route(4L, null);

        updatedExistingEntity1 = new Route(1L, 212);
        updatedExistingEntity2 = new Route(2L, null);
        updatedExistingEntity3 = new Route(4L, 200);

        nonExistingEntity1 = new Route(6L, 315);
        nonExistingEntity2 = new Route(7L, null);
        nonExistingEntity3 = new Route(11L, 201);

        newEntity1 = new Route(null, 389);
        newEntity2 = new Route(null, 85);
        newEntity3 = new Route(null, null);
    }

    @Override
    public Long getEntityId(Route route) {
        return route.getId();
    }

    @Override
    public boolean entitiesEqualsWithoutId(Route route1, Route route2) {
        return Objects.equals(route1.getAveragePassengersPerDay(), route2.getAveragePassengersPerDay());
    }

    @Test
    void constructor_nullArguments_throwsException() {
        assertThrows(NullPointerException.class, () -> new RouteRepoImpl(null));
    }
}