package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Route;
import org.junit.jupiter.api.BeforeEach;

class RouteRepoImplTest extends CrudeRepoTest<Route> {

    public RouteRepoImplTest() {
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

        init();
    }

    @BeforeEach
    public void initEntityRepo() {
        entityRepo = new RouteRepoImpl(database);
    }
}