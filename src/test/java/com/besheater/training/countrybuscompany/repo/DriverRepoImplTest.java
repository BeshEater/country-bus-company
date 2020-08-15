package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Driver;
import com.besheater.training.countrybuscompany.entity.Route;
import com.besheater.training.countrybuscompany.entity.RoutePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DriverRepoImplTest extends CrudRepoTest<Driver> {

    private DriverRepoImpl driverRepo;

    @Override
    public void init() {
        RouteRepo routeRepo = new RouteRepoImpl(database);
        RoutePartRepo routePartRepo = new RoutePartRepoImpl(database, routeRepo);
        entityRepo = new DriverRepoImpl(database, routePartRepo);

        entityCount = 13L;

        existingEntity1 = Driver.builder()
                .id(1L)
                .routePart(new RoutePart(1L, new Route(1L, 125), 1))
                .firstName("Arman")
                .lastName("Aymagambetov")
                .dateOfBirth(LocalDate.of(1985, 2, 5))
                .address("Almaty Kozybaeyva 183, apt. 25")
                .driverLicenseNumber("207541")
                .phoneNumber("+77071263944")
                .build();
        existingEntity2 = Driver.builder()
                .id(5L)
                .routePart(new RoutePart(3L, new Route(3L, 189), 1))
                .firstName("Ivan")
                .lastName("Samoylov")
                .dateOfBirth(LocalDate.of(1993, 10, 21))
                .address("Shchuchinsk Abaya 67")
                .driverLicenseNumber("219122")
                .phoneNumber(null)
                .build();
        existingEntity3 = Driver.builder()
                .id(9L)
                .routePart(new RoutePart(6L, new Route(4L, null), 2))
                .firstName("Kim")
                .lastName("Ivanov")
                .dateOfBirth(LocalDate.of(1987, 9, 15))
                .address("Yekaterinburg Tolstoy 245, apt. 7")
                .driverLicenseNumber(null)
                .phoneNumber("+778913091324")
                .build();

        updatedExistingEntity1 = Driver.builder()
                .id(1L)
                .routePart(new RoutePart(2L, new Route(2L, 271), 1))
                .firstName("Arman")
                .lastName("Aymagambetov")
                .dateOfBirth(LocalDate.of(1985, 2, 5))
                .address("Shymkent Pavlova 17")
                .driverLicenseNumber("999777888")
                .phoneNumber("+77770008963")
                .build();
        updatedExistingEntity2 = Driver.builder()
                .id(5L)
                .routePart(null)
                .firstName("Ivan")
                .lastName("Samoylov")
                .dateOfBirth(LocalDate.of(1993, 10, 21))
                .address("Nur-Sultan Naberezhnaya 11, apt 89")
                .driverLicenseNumber("219122")
                .phoneNumber("+770748915678")
                .build();
        updatedExistingEntity3 = Driver.builder()
                .id(9L)
                .routePart(new RoutePart(6L, new Route(4L, null), 2))
                .firstName("Ahmet")
                .lastName("Ahmetov")
                .dateOfBirth(LocalDate.of(1990, 5, 24))
                .address("Karaganda Saryarka 40, apt. 23")
                .driverLicenseNumber(null)
                .phoneNumber(null)
                .build();

        nonExistingEntity1 = Driver.builder()
                .id(14L)
                .routePart(new RoutePart(4L, new Route(3L, 189), 2))
                .firstName("Kirill")
                .lastName("Stalevarov")
                .dateOfBirth(LocalDate.of(1995, 12, 31))
                .address("Almaty Sain 111, apt. 78")
                .driverLicenseNumber("789789")
                .phoneNumber("+77077894187")
                .build();
        nonExistingEntity2 = Driver.builder()
                .id(15L)
                .routePart(new RoutePart(7L, new Route(5L, 513), 1))
                .firstName("Ilyas")
                .lastName("Muhamedov")
                .dateOfBirth(LocalDate.of(1987, 5, 5))
                .address("Kostanay Borodina 51, apt. 12")
                .driverLicenseNumber(null)
                .phoneNumber("+77071263944")
                .build();
        nonExistingEntity3 = Driver.builder()
                .id(21L)
                .routePart(null)
                .firstName("Rinat")
                .lastName("Omaykulov")
                .dateOfBirth(LocalDate.of(1979, 1, 1))
                .address("Almaty Garaev 9")
                .driverLicenseNumber(null)
                .phoneNumber(null)
                .build();

        newEntity1 = Driver.builder()
                .id(null)
                .routePart(new RoutePart(1L, new Route(1L, 125), 1))
                .firstName("Frodo")
                .lastName("Frodorov")
                .dateOfBirth(LocalDate.of(1917, 9, 14))
                .address("Kokshetau Proletarskaya 8")
                .driverLicenseNumber("0000001")
                .phoneNumber("+777707771596")
                .build();
        newEntity2 = Driver.builder()
                .id(null)
                .routePart(null)
                .firstName("Sam")
                .lastName("Arogornov")
                .dateOfBirth(LocalDate.of(1972, 2, 29))
                .address("Shir Noname 17, apt. 1")
                .driverLicenseNumber(null)
                .phoneNumber(null)
                .build();
        newEntity3 = Driver.builder()
                .id(null)
                .routePart(new RoutePart(5L, new Route(4L, null), 1))
                .firstName("Jack")
                .lastName("White")
                .dateOfBirth(LocalDate.of(2001, 11, 29))
                .address("London Camel 9, apt. 11")
                .driverLicenseNumber("4789631")
                .phoneNumber("+4478963145487")
                .build();
    }

    @Override
    public Long getEntityId(Driver driver) {
        return driver.getId();
    }

    @Override
    public boolean entitiesEqualsWithoutId(Driver driver1, Driver driver2) {
        return Objects.equals(driver1.getRoutePart(), driver2.getRoutePart()) &&
                driver1.getFirstName().equals(driver2.getFirstName()) &&
                driver1.getLastName().equals(driver2.getLastName()) &&
                driver1.getDateOfBirth().equals(driver2.getDateOfBirth()) &&
                driver1.getAddress().equals(driver2.getAddress()) &&
                Objects.equals(driver1.getDriverLicenseNumber(), driver2.getDriverLicenseNumber()) &&
                Objects.equals(driver1.getPhoneNumber(), driver2.getPhoneNumber());
    }

    @BeforeEach
    public void initDriverRepo() {
        RouteRepo routeRepo = new RouteRepoImpl(database);
        RoutePartRepo routePartRepo = new RoutePartRepoImpl(database, routeRepo);
        driverRepo = new DriverRepoImpl(database, routePartRepo);
    }

    @Test
    void constructor_nullArguments_throwsException() {
        RoutePartRepo routePartRepo = new RoutePartRepoImpl(database, new RouteRepoImpl(database));

        assertThrows(NullPointerException.class, () -> new DriverRepoImpl(null, routePartRepo));
        assertThrows(NullPointerException.class, () -> new DriverRepoImpl(database, null));
        assertThrows(NullPointerException.class, () -> new DriverRepoImpl(null, null));
    }

    @Test
    void getDriversOnRoute_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> driverRepo.getDriversOnRoute(null));
    }

    @Test
    void getDriversOnRoute_existingRoute_returnsBussesOnThatRoute() {
        Route route1 = new Route(1L, 125);
        Route route3 = new Route(3L, 189);

        List<Driver> driversOnRoute1 = Arrays.asList(
                Driver.builder()
                        .id(1L)
                        .routePart(new RoutePart(1L, new Route(1L, 125), 1))
                        .firstName("Arman")
                        .lastName("Aymagambetov")
                        .dateOfBirth(LocalDate.of(1985, 2, 5))
                        .address("Almaty Kozybaeyva 183, apt. 25")
                        .driverLicenseNumber("207541")
                        .phoneNumber("+77071263944")
                        .build(),
                Driver.builder()
                        .id(2L)
                        .routePart(new RoutePart(1L, new Route(1L, 125), 1))
                        .firstName("Daniyar")
                        .lastName("Isatayev")
                        .dateOfBirth(LocalDate.of(1978, 8, 24))
                        .address("Shymkent Abaya 14, apt. 47")
                        .driverLicenseNumber("189122")
                        .phoneNumber("+77059274315")
                        .build());

        List<Driver> driversOnRoute3 = Arrays.asList(
                Driver.builder()
                        .id(5L)
                        .routePart(new RoutePart(3L, new Route(3L, 189), 1))
                        .firstName("Ivan")
                        .lastName("Samoylov")
                        .dateOfBirth(LocalDate.of(1993, 10, 21))
                        .address("Shchuchinsk Abaya 67")
                        .driverLicenseNumber("219122")
                        .phoneNumber(null)
                        .build(),
                Driver.builder()
                        .id(6L)
                        .routePart(new RoutePart(4L, new Route(3L, 189), 2))
                        .firstName("Aleksandr")
                        .lastName("Spiridonov")
                        .dateOfBirth(LocalDate.of(1984, 6, 6))
                        .address("Kokshetau Zheleznodorozhnay 12, apt. 2")
                        .driverLicenseNumber("200133")
                        .phoneNumber("+77779998812")
                        .build(),
                Driver.builder()
                        .id(7L)
                        .routePart(new RoutePart(4L, new Route(3L, 189), 2))
                        .firstName("Rinat")
                        .lastName("Fakhrutdinov")
                        .dateOfBirth(LocalDate.of(1993, 11, 1))
                        .address("Kostanay Kaiyrbekov 75, apt. 32")
                        .driverLicenseNumber("219991")
                        .phoneNumber("+77077664545")
                        .build());
        assertIterableEquals(driversOnRoute1, driverRepo.getDriversOnRoute(route1));
        assertIterableEquals(driversOnRoute3, driverRepo.getDriversOnRoute(route3));
    }

    @Test
    void getDriversOnRoute_nonExistingRoute_returnsEmptyCollection() {
        Route nonExistingRoute = new Route(7L, null);
        assertTrue(driverRepo.getDriversOnRoute(nonExistingRoute).isEmpty());
    }
}