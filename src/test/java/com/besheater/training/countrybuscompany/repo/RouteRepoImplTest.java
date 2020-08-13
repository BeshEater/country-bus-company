package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.TestDatabaseFactory;
import com.besheater.training.countrybuscompany.entity.Route;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RouteRepoImplTest {

    private EmbeddedDatabase database;
    private RouteRepoImpl routeRepo;

    private Long routeCount;

    private Route existingRoute1;
    private Route existingRoute2;
    private Route existingRoute3;

    private Route nonExistingRoute1;
    private Route nonExistingRoute2;
    private Route nonExistingRoute3;

    private List<Route> existingRoutes;
    private List<Route> nonExistingRoutes;
    private List<Route> existingAndNonExistingRoutes;

    private List<Long> existingRoutesIds;
    private List<Long> nonExistingRoutesIds;
    private List<Long> existingAndNonExistingRoutesIds;

    public RouteRepoImplTest() {
        routeCount = 5L;

        existingRoute1 = new Route(1L, 125);
        existingRoute2 = new Route(2L, 271);
        existingRoute3 = new Route(4L, null);

        nonExistingRoute1 = new Route(6L, 315);
        nonExistingRoute2 = new Route(7L, null);
        nonExistingRoute3 = new Route(11L, 201);

        existingRoutes = Arrays.asList(existingRoute1, existingRoute2, existingRoute3);
        nonExistingRoutes = Arrays.asList(nonExistingRoute1, nonExistingRoute2, nonExistingRoute3);
        existingAndNonExistingRoutes = new ArrayList<>();
        existingAndNonExistingRoutes.addAll(existingRoutes);
        existingAndNonExistingRoutes.addAll(nonExistingRoutes);

        existingRoutesIds = existingRoutes.stream().map(Route::getId)
                                                   .collect(Collectors.toList());
        nonExistingRoutesIds = nonExistingRoutes.stream().map(Route::getId)
                                                         .collect(Collectors.toList());
        existingAndNonExistingRoutesIds = existingAndNonExistingRoutes.stream()
                                                                      .map(Route::getId)
                                                                      .collect(Collectors.toList());
    }

    @BeforeEach
    public void initDataSource() {
        database = TestDatabaseFactory.get();
        routeRepo = new RouteRepoImpl(database);
    }

    @AfterEach
    public void closeDataSource() {
        database.shutdown();
    }

    @Test
    void count_returnsExpectedValue() {
        assertEquals(routeCount, routeRepo.count());
        routeRepo.deleteById(1L);
        routeRepo.deleteById(2L);
        assertEquals(routeCount - 2, routeRepo.count());
    }

    @Test
    void delete_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.delete(null));
    }

    @Test
    void delete_existingRoute_deletesRoute() {
        assertEquals(routeCount, routeRepo.count());
        assertTrue(routeRepo.existsById(existingRoute2.getId()));
        routeRepo.delete(existingRoute2);
        assertEquals(routeCount - 1, routeRepo.count());
        assertFalse(routeRepo.existsById(existingRoute2.getId()));
    }

    @Test
    void delete_nonExistingRoute_deletesNone() {
        assertEquals(routeCount, routeRepo.count());
        assertFalse(routeRepo.findById(nonExistingRoute3.getId()).isPresent());
        routeRepo.delete(nonExistingRoute3);
        assertEquals(routeCount, routeRepo.count());
        assertFalse(routeRepo.findById(nonExistingRoute3.getId()).isPresent());
    }

    @Test
    void deleteAll_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.deleteAll(null));
        List<Route> routesWithNull = Arrays.asList(existingRoute1, null, existingRoute3);
        assertThrows(NullPointerException.class, () -> routeRepo.deleteAll(routesWithNull));
    }

    @Test
    void deleteAll_existingRoutes_deletesRoutes() {
        assertEquals(routeCount, routeRepo.count());
        assertIterableEquals(existingRoutes, routeRepo.findAllById(existingRoutesIds));
        routeRepo.deleteAll(existingRoutes);
        assertEquals(routeCount - 3, routeRepo.count());
        assertTrue(routeRepo.findAllById(existingRoutesIds).isEmpty());
    }

    @Test
    void deleteAll_nonExistingRoutes_deletesNone() {
        assertEquals(routeCount, routeRepo.count());
        assertTrue(routeRepo.findAllById(nonExistingRoutesIds).isEmpty());
        routeRepo.deleteAll(nonExistingRoutes);
        assertEquals(routeCount, routeRepo.count());
        assertTrue(routeRepo.findAllById(nonExistingRoutesIds).isEmpty());
    }

    @Test
    void deleteAll_existingAndNonExistingRoutes_deletesOnlyExistingRoutes() {
        assertEquals(routeCount, routeRepo.count());
        assertFalse(routeRepo.findAllById(existingAndNonExistingRoutesIds).isEmpty());
        routeRepo.deleteAll(existingAndNonExistingRoutes);
        assertEquals(routeCount - 3, routeRepo.count());
        assertTrue(routeRepo.findAllById(existingAndNonExistingRoutesIds).isEmpty());
    }

    @Test
    void deleteById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.deleteById(null));
    }

    @Test
    void deleteById_existingId_deletesRoute() {
        assertEquals(routeCount, routeRepo.count());
        assertTrue(routeRepo.existsById(existingRoute3.getId()));
        routeRepo.deleteById(existingRoute3.getId());
        assertEquals(routeCount - 1, routeRepo.count());
        assertFalse(routeRepo.existsById(existingRoute3.getId()));
    }

    @Test
    void deleteById_nonExistingId_deletesNone() {
        assertEquals(routeCount, routeRepo.count());
        assertFalse(routeRepo.existsById(nonExistingRoute2.getId()));
        routeRepo.deleteById(nonExistingRoute2.getId());
        assertEquals(routeCount, routeRepo.count());
        assertFalse(routeRepo.existsById(nonExistingRoute2.getId()));
    }

    @Test
    void existsById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.existsById(null));
    }

    @Test
    void existsById_existingId_returnsTrue() {
        assertTrue(routeRepo.existsById(existingRoute1.getId()));
        assertTrue(routeRepo.existsById(existingRoute2.getId()));
        assertTrue(routeRepo.existsById(existingRoute3.getId()));
    }

    @Test
    void existsById_nonExistingId_returnsFalse() {
        assertFalse(routeRepo.existsById(nonExistingRoute1.getId()));
        assertFalse(routeRepo.existsById(nonExistingRoute2.getId()));
        assertFalse(routeRepo.existsById(nonExistingRoute3.getId()));
    }

    @Test
    void findAll_returnsExpectedValue() {
        Collection<Route> allRoutes = routeRepo.findAll();
        assertEquals(routeCount, allRoutes.size());
        assertTrue(allRoutes.containsAll(existingRoutes));
    }

    @Test
    void findAllById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.findAllById(null));
        List<Long> idsWithNull = Arrays.asList(1L, null, 3L, 4L);
        assertThrows(NullPointerException.class, () -> routeRepo.findAllById(idsWithNull));
    }

    @Test
    void findAllById_existingRoutes_findsAll() {
        Collection<Route> foundRoutes = routeRepo.findAllById(existingRoutesIds);
        assertIterableEquals(existingRoutes, foundRoutes);
    }

    @Test
    void findAllById_nonExistingRoutes_findsNone() {
        Collection<Route> foundRoutes = routeRepo.findAllById(nonExistingRoutesIds);
        assertTrue(foundRoutes.isEmpty());
    }

    @Test
    void findAllById_existingAndNonExistingRoutes_findsOnlyExistingRoutes() {
        Collection<Route> foundRoutes = routeRepo.findAllById(existingAndNonExistingRoutesIds);
        assertIterableEquals(existingRoutes, foundRoutes);
    }

    @Test
    void findById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.findById(null));
    }

    @Test
    void findById_existingId_returnsRoute() {
        assertEquals(existingRoute1, routeRepo.findById(existingRoute1.getId()).get());
        assertEquals(existingRoute2, routeRepo.findById(existingRoute2.getId()).get());
        assertEquals(existingRoute3, routeRepo.findById(existingRoute3.getId()).get());
    }

    @Test
    void findById_nonExistingId_returnsNone() {
        assertFalse(routeRepo.findById(nonExistingRoute1.getId()).isPresent());
        assertFalse(routeRepo.findById(nonExistingRoute2.getId()).isPresent());
        assertFalse(routeRepo.findById(nonExistingRoute3.getId()).isPresent());
    }

    @Test
    void save_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.save(null));
    }

    @Test
    void save_existingRoute_updatesExistingRoute() {
        Route updatedRoute = new Route(existingRoute3.getId(), 244);
        assertEquals(routeCount, routeRepo.count());
        assertNotEquals(existingRoute3, updatedRoute);
        Route savedRoute = routeRepo.save(updatedRoute);
        assertEquals(updatedRoute, savedRoute);
        assertEquals(updatedRoute, routeRepo.findById(updatedRoute.getId()).get());
        assertEquals(routeCount, routeRepo.count());
    }

    @Test
    void save_nonExistingRoute_savesNewRoute() {
        Route newRoute = new Route(null, 118);
        assertEquals(routeCount, routeRepo.count());
        Route savedRoute = routeRepo.save(newRoute);
        assertEquals(routeCount + 1, routeRepo.count());
        assertEquals(routeCount + 1, savedRoute.getId());
        assertTrue(savedRoute.equalsWithoutId(newRoute));
    }

    @Test
    void save_routeWithNonExistingId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> routeRepo.save(nonExistingRoute1));
        assertThrows(IllegalArgumentException.class, () -> routeRepo.save(nonExistingRoute3));
    }

    @Test
    void saveAll_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> routeRepo.saveAll(null));
        List<Route> routesWithNull = Arrays.asList(existingRoute1, null, existingRoute2);
        assertThrows(NullPointerException.class, () -> routeRepo.saveAll(routesWithNull));
    }

    @Test
    void saveAll_existingRoutes_updatesExisting() {
        Route updatedRoute1 = new Route(existingRoute1.getId(), 212);
        Route updatedRoute2 = new Route(existingRoute2.getId(), 187);
        List<Route> updatedRoutes = Arrays.asList(updatedRoute1, updatedRoute2);
        List<Long> updatedBussesIds = updatedRoutes.stream()
                                                   .map(Route::getId)
                                                   .collect(Collectors.toList());
        assertEquals(routeCount, routeRepo.count());
        Collection<Route> savedRoutes = routeRepo.saveAll(updatedRoutes);
        assertEquals(routeCount, routeRepo.count());
        assertIterableEquals(updatedRoutes, savedRoutes);
        assertIterableEquals(updatedRoutes, routeRepo.findAllById(updatedBussesIds));
    }

    @Test
    void saveAll_nonExistingRoutes_savesRoutes() {
        Route newRoute1 = new Route(null, 431);
        Route newRoute2 = new Route(null, null);
        List<Route> newRoutes = Arrays.asList(newRoute1, newRoute2);

        assertEquals(routeCount, routeRepo.count());
        List<Route> savedRoutes = new ArrayList<>(routeRepo.saveAll(newRoutes));
        assertEquals(routeCount + 2, routeRepo.count());

        assertTrue(newRoutes.get(0).equalsWithoutId(savedRoutes.get(0)));
        assertTrue(newRoutes.get(1).equalsWithoutId(savedRoutes.get(1)));
        assertTrue(newRoutes.get(0).equalsWithoutId(routeRepo.findById(savedRoutes.get(0).getId()).get()));
        assertTrue(newRoutes.get(1).equalsWithoutId(routeRepo.findById(savedRoutes.get(1).getId()).get()));
    }

    @Test
    void saveAll_existingAndNonExistingRoutes_savesAndUpdatesRoutes() {
        Route updatedRoute1 = new Route(existingRoute1.getId(), 214);
        Route updatedRoute2 = new Route(existingRoute2.getId(), 177);
        Route newRoute1 = new Route(null, 389);
        Route newRoute2 = new Route(null, null);
        List<Route> newAndUpdatedRoutes = Arrays.asList(updatedRoute1, updatedRoute2, newRoute1, newRoute2);

        assertEquals(routeCount, routeRepo.count());
        List<Route> savedRoutes = new ArrayList<>(routeRepo.saveAll(newAndUpdatedRoutes));
        assertEquals(routeCount + 2, routeRepo.count());

        assertTrue(newAndUpdatedRoutes.get(0).equalsWithoutId(savedRoutes.get(0)));
        assertTrue(newAndUpdatedRoutes.get(1).equalsWithoutId(savedRoutes.get(1)));
        assertTrue(newAndUpdatedRoutes.get(2).equalsWithoutId(savedRoutes.get(2)));
        assertTrue(newAndUpdatedRoutes.get(3).equalsWithoutId(savedRoutes.get(3)));
        assertTrue(newAndUpdatedRoutes.get(0).equalsWithoutId(routeRepo.findById(savedRoutes.get(0).getId()).get()));
        assertTrue(newAndUpdatedRoutes.get(1).equalsWithoutId(routeRepo.findById(savedRoutes.get(1).getId()).get()));
        assertTrue(newAndUpdatedRoutes.get(2).equalsWithoutId(routeRepo.findById(savedRoutes.get(2).getId()).get()));
        assertTrue(newAndUpdatedRoutes.get(3).equalsWithoutId(routeRepo.findById(savedRoutes.get(3).getId()).get()));
    }

    @Test
    void saveAll_routesWithNonExistingId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> routeRepo.saveAll(nonExistingRoutes));
        assertThrows(IllegalArgumentException.class, () -> routeRepo.saveAll(existingAndNonExistingRoutes));
    }
}