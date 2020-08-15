package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Bus;
import com.besheater.training.countrybuscompany.entity.Route;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BusRepoImplTest extends CrudRepoTest<Bus> {

    private BusRepoImpl busRepo;

    @Override
    public void init() {
        entityRepo = new BusRepoImpl(database, new RouteRepoImpl(database));

        entityCount = 11L;

        existingEntity1 = new Bus(1L, new Route(1L, 125), "KZ726GK01", 65, false);
        existingEntity2 = new Bus(2L, new Route(2L, 271), "KZ146GK01", 120, true);
        existingEntity3 = new Bus(5L, null, "KZ560AK10", 75, false);

        updatedExistingEntity1 = new Bus(1L, new Route(5L, 513), "KK000PPP", 60, false);
        updatedExistingEntity2 = new Bus(2L, null, "CC111PPP", 120, true);
        updatedExistingEntity3 = new Bus(5L, new Route(3L, 189), "KZ560AK10", 90, true);

        nonExistingEntity1 = new Bus(12L, new Route(1L, 125), "TK4481", 50, false);
        nonExistingEntity2 = new Bus(13L, null, "TT111PPP", 65, false);
        nonExistingEntity3 = new Bus(17L, new Route(3L, 189), "KZ146GK01", 120, true);

        newEntity1 = new Bus(null, new Route(4L, null), "WW000TT", 60, false);
        newEntity2 = new Bus(null, null, "CC111YYY", 110, true);
        newEntity3 = new Bus(null, new Route(3L, 189), "TT148KPU", 65, false);
    }

    @Override
    public Long getEntityId(Bus bus) {
        return bus.getId();
    }

    @Override
    public boolean entitiesEqualsWithoutId(Bus bus1, Bus bus2) {
        return bus1.isDoubleDecker() == bus2.isDoubleDecker() &&
                Objects.equals(bus1.getRoute(), bus2.getRoute()) &&
                bus1.getRegistrationNumber().equals(bus2.getRegistrationNumber()) &&
                bus1.getCapacity().equals(bus2.getCapacity());
    }

    @BeforeEach
    public void initBusRepo() {
        RouteRepo routeRepo = new RouteRepoImpl(database);
        busRepo = new BusRepoImpl(database, routeRepo);
    }

    @Test
    void constructor_nullArguments_throwsException() {
        RouteRepo routeRepo = new RouteRepoImpl(database);

        assertThrows(NullPointerException.class, () -> new BusRepoImpl(null, routeRepo));
        assertThrows(NullPointerException.class, () -> new BusRepoImpl(database, null));
        assertThrows(NullPointerException.class, () -> new BusRepoImpl(null, null));
    }

    @Test
    void getBussesOnRoute_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.getBussesOnRoute(null));
    }

    @Test
    void getBussesOnRoute_existingRoute_returnsBussesOnThatRoute() {
        Route route1 = new Route(1L, 125);
        Route route2 = new Route(5L, 513);

        List<Bus> bussesOnRoute1 = Arrays.asList(new Bus(1L, route1, "KZ726GK01", 65, false));
        List<Bus> bussesOnRoute2 = Arrays.asList(
                new Bus(8L, route2, "P097VK77RUS", 100, true),
                new Bus(9L, route2, "P174VK77RUS", 120, true),
                new Bus(10L, route2, "P213VK77RUS", 70, false));
        assertIterableEquals(bussesOnRoute1, busRepo.getBussesOnRoute(route1));
        assertIterableEquals(bussesOnRoute2, busRepo.getBussesOnRoute(route2));
    }

    @Test
    void getBussesOnRoute_nonExistingRoute_returnsEmptyCollection() {
        Route nonExistingRoute = new Route(7L, null);
        assertTrue(busRepo.getBussesOnRoute(nonExistingRoute).isEmpty());
    }
}