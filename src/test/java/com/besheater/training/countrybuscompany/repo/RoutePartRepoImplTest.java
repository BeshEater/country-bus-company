package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Route;
import com.besheater.training.countrybuscompany.entity.RoutePart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RoutePartRepoImplTest extends CrudeRepoTest<RoutePart> {

    @Override
    public void init() {
        entityRepo = new RoutePartRepoImpl(database, new RouteRepoImpl(database));

        entityCount = 8L;

        existingEntity1 = new RoutePart(1L, new Route(1L, 125), 1);
        existingEntity2 = new RoutePart(4L, new Route(3L, 189), 2);
        existingEntity3 = new RoutePart(5L, new Route(4L, null), 1);

        updatedExistingEntity1 = new RoutePart(1L, new Route(2L, 271), 2);
        updatedExistingEntity2 = new RoutePart(4L, new Route(4L, null), 2);
        updatedExistingEntity3 = new RoutePart(5L, new Route(4L, null), 3);

        nonExistingEntity1 = new RoutePart(9L, new Route(5L, 513), 1);
        nonExistingEntity2 = new RoutePart(10L, new Route(2L, 271), 2);
        nonExistingEntity3 = new RoutePart(19L, new Route(4L, null), 3);

        newEntity1 = new RoutePart(null, new Route(1L, 125), 1);
        newEntity2 = new RoutePart(null, new Route(3L, 189), 2);
        newEntity3 = new RoutePart(null, new Route(4L, null), 3);
    }

    @Override
    public Long getEntityId(RoutePart routePart) {
        return routePart.getId();
    }

    @Override
    public boolean entitiesEqualsWithoutId(RoutePart routePart1, RoutePart routePart2) {
        return routePart1.getRoute().equals(routePart2.getRoute()) &&
                routePart1.getPosition().equals(routePart2.getPosition());
    }

    @Test
    void constructor_nullArguments_throwsException() {
        RouteRepo routeRepo = new RouteRepoImpl(database);

        assertThrows(NullPointerException.class, () -> new RoutePartRepoImpl(null, routeRepo));
        assertThrows(NullPointerException.class, () -> new RoutePartRepoImpl(database, null));
        assertThrows(NullPointerException.class, () -> new RoutePartRepoImpl(null, null));
    }
}