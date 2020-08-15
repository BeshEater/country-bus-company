package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Route;
import com.besheater.training.countrybuscompany.entity.RoutePart;
import com.besheater.training.countrybuscompany.entity.Town;
import com.besheater.training.countrybuscompany.entity.Transit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransitRepoImplTest extends CrudRepoTest<Transit> {

    @Override
    public void init() {
        RouteRepo routeRepo = new RouteRepoImpl(database);
        RoutePartRepo routePartRepo = new RoutePartRepoImpl(database, routeRepo);
        TownRepo townRepo = new TownRepoImpl(database);
        entityRepo = new TransitRepoImpl(database, routePartRepo, townRepo);

        entityCount = 10L;

        existingEntity1 = Transit.builder()
                .id(1L)
                .routePart(new RoutePart(1L, new Route(1L, 125), 1))
                .fromTown(new Town(1L, "Shymkent", "KAZ", null, 42.2960, 69.5999))
                .toTown(new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958))
                .position(1)
                .build();
        existingEntity2 = Transit.builder()
                .id(2L)
                .routePart(new RoutePart(2L, new Route(2L, 271), 1))
                .fromTown(new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958))
                .toTown(new Town(3L, "Karaganda", "KAZ", "Karaganda Region", 49.8028, 73.0878))
                .position(1)
                .build();
        existingEntity3 = Transit.builder()
                .id(5L)
                .routePart(new RoutePart(3L, new Route(3L, 189), 1))
                .fromTown(new Town(5L, "Shchuchinsk", "KAZ", "Akmola Region", 52.9363, 70.1826))
                .toTown(new Town(6L, "Kokshetau", "KAZ", "Akmola Region", 53.2833, 69.3833))
                .position(2)
                .build();

        updatedExistingEntity1 = Transit.builder()
                .id(1L)
                .routePart(new RoutePart(6L, new Route(4L, null), 2))
                .fromTown(new Town(1L, "Shymkent", "KAZ", null, 42.2960, 69.5999))
                .toTown(new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958))
                .position(2)
                .build();
        updatedExistingEntity2 = Transit.builder()
                .id(2L)
                .routePart(new RoutePart(2L, new Route(2L, 271), 1))
                .fromTown(new Town(8L, "Chelyabinsk", "RUS", "Chelyabinsk Oblast", 55.1547, 61.3758))
                .toTown(new Town(9L, "Yekaterinburg", "RUS", "Sverdlovsk Oblast", 55.1547, 61.3758))
                .position(1)
                .build();
        updatedExistingEntity3 = Transit.builder()
                .id(5L)
                .routePart(new RoutePart(3L, new Route(3L, 189), 1))
                .fromTown(new Town(5L, "Shchuchinsk", "KAZ", "Akmola Region", 52.9363, 70.1826))
                .toTown(new Town(4L, "Nur-Sultan", "KAZ", null, 51.1666, 71.4333))
                .position(1)
                .build();

        nonExistingEntity1 = Transit.builder()
                .id(11L)
                .routePart(new RoutePart(7L, new Route(5L, 513), 1))
                .fromTown(new Town(10L, "Nizhny Novgorod", "RUS", "Nizhny Novgorod Oblast", 56.3269, 44.0075))
                .toTown(new Town(11L, "Moscow", "RUS", "Central Federal District", 55.7558, 37.6172))
                .position(1)
                .build();
        nonExistingEntity2 = Transit.builder()
                .id(12L)
                .routePart(new RoutePart(3L, new Route(3L, 189), 1))
                .fromTown(new Town(1L, "Shymkent", "KAZ", null, 42.2960, 69.5999))
                .toTown(new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958))
                .position(3)
                .build();
        nonExistingEntity3 = Transit.builder()
                .id(19L)
                .routePart(new RoutePart(4L, new Route(3L, 189), 2))
                .fromTown(new Town(7L, "Kostanay", "KAZ", "Kostanay Region", 53.2118, 63.6325))
                .toTown(new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958))
                .position(1)
                .build();

        newEntity1 = Transit.builder()
                .id(null)
                .routePart(new RoutePart(2L, new Route(2L, 271), 1))
                .fromTown(new Town(1L, "Shymkent", "KAZ", null, 42.2960, 69.5999))
                .toTown(new Town(4L, "Nur-Sultan", "KAZ", null, 51.1666, 71.4333))
                .position(1)
                .build();
        newEntity2 = Transit.builder()
                .id(null)
                .routePart(new RoutePart(7L, new Route(5L, 513), 1))
                .fromTown(new Town(11L, "Moscow", "RUS", "Central Federal District", 55.7558, 37.6172))
                .toTown(new Town(9L, "Yekaterinburg", "RUS", "Sverdlovsk Oblast", 55.1547, 61.3758))
                .position(2)
                .build();
        newEntity3 = Transit.builder()
                .id(null)
                .routePart(new RoutePart(4L, new Route(3L, 189), 2))
                .fromTown(new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958))
                .toTown(new Town(3L, "Karaganda", "KAZ", "Karaganda Region", 49.8028, 73.0878))
                .position(3)
                .build();
    }

    @Override
    public Long getEntityId(Transit transit) {
        return transit.getId();
    }

    @Override
    public boolean entitiesEqualsWithoutId(Transit transit1, Transit transit2) {
        return transit1.getRoutePart().equals(transit2.getRoutePart()) &&
                transit1.getFromTown().equals(transit2.getFromTown()) &&
                transit1.getToTown().equals(transit2.getToTown()) &&
                transit1.getPosition().equals(transit2.getPosition());
    }

    @Test
    void constructor_nullArguments_throwsException() {
        RouteRepo routeRepo = new RouteRepoImpl(database);
        RoutePartRepo routePartRepo = new RoutePartRepoImpl(database, routeRepo);
        TownRepo townRepo = new TownRepoImpl(database);

        assertThrows(NullPointerException.class, () -> new TransitRepoImpl(null, routePartRepo, townRepo));
        assertThrows(NullPointerException.class, () -> new TransitRepoImpl(database, null, townRepo));
        assertThrows(NullPointerException.class, () -> new TransitRepoImpl(database, routePartRepo, null));
        assertThrows(NullPointerException.class, () -> new TransitRepoImpl(null, null, null));
    }
}