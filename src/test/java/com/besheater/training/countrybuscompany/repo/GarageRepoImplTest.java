package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Garage;
import com.besheater.training.countrybuscompany.entity.Town;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GarageRepoImplTest extends CrudeRepoTest<Garage> {

    @Override
    public void init() {
        entityRepo = new GarageRepoImpl(database, new TownRepoImpl(database));

        entityCount = 4L;

        existingEntity1 = new Garage(1L,
                new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958),
                "GARAGE 55", "Proletarskaya 207", 7);
        existingEntity2 = new Garage(2L,
                new Town(3L, "Karaganda", "KAZ", "Karaganda Region", 49.8028, 73.0878),
                "Angar K1", "Promishlenaya 61", 15);
        existingEntity3 = new Garage(4L,
                new Town(12L, "Yaroslavl", "RUS", "Yaroslavl Oblast", 57.6166, 39.8506),
                "Transit area", "Voldaeva 81", 18);

        updatedExistingEntity1 = new Garage(1L,
                new Town(7L, "Kostanay", "KAZ", "Kostanay Region", 53.2118, 63.6325),
                "Storage facility K784-A", "Karbisheva 103", 15);
        updatedExistingEntity2 = new Garage(2L,
                new Town(3L, "Karaganda", "KAZ", "Karaganda Region", 49.8028, 73.0878),
                "Angar K1", "Promishlenaya 61", 20);
        updatedExistingEntity3 = new Garage(4L,
                new Town(4L, "Nur-Sultan", "KAZ", null, 51.1666, 71.4333),
                "Transit area", "Sarieva 12", 18);

        nonExistingEntity1 = new Garage(5L,
                new Town(11L, "Moscow", "RUS", "Central Federal District", 55.7558, 37.6172),
                "Arch TK-78", "Krasniy Kuznetc 187", 30);
        nonExistingEntity2 = new Garage(6L,
                new Town(1L, "Shymkent", "KAZ", null, 42.2960, 69.5999),
                "Open area #417", "Proletarskaya 14", 8);
        nonExistingEntity3 = new Garage(23L,
                new Town(2L, "Almaty", "KAZ", null, 43.2775, 76.8958),
                "GARAGE 59", "Dlinnaya ulitsa 7 - 45", 25);

        newEntity1 = new Garage(null,
                new Town(5L, "Shchuchinsk", "KAZ", "Akmola Region", 52.9363, 70.1826),
                "TTK 74-78", "Zheleznodorozhnaya 17", 16);
        newEntity2 = new Garage(null,
                new Town(6L, "Kokshetau", "KAZ", "Akmola Region", 53.2833, 69.3833),
                "MP-TTA 74/9", "Proletarskaya 207", 7);
        newEntity3 = new Garage(null,
                new Town(12L, "Yaroslavl", "RUS", "Yaroslavl Oblast", 57.6166, 39.8506),
                "Hangar 78/96 ", "Lenina 14", 6);
    }

    @Test
    void constructor_nullArguments_throwsException() {
        TownRepo townRepo = new TownRepoImpl(database);

        assertThrows(NullPointerException.class, () -> new GarageRepoImpl(null, townRepo));
        assertThrows(NullPointerException.class, () -> new GarageRepoImpl(database, null));
        assertThrows(NullPointerException.class, () -> new GarageRepoImpl(null, null));
    }
}