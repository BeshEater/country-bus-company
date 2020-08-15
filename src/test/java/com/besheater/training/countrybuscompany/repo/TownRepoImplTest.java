package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Town;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TownRepoImplTest extends CrudeRepoTest<Town> {

    @Override
    public void init() {
        entityRepo = new TownRepoImpl(database);

        entityCount = 12L;

        existingEntity1 = new Town(1L, "Shymkent", "KAZ", null, 42.2960, 69.5999);
        existingEntity2 = new Town(3L, "Karaganda", "KAZ", "Karaganda Region", 49.8028, 73.0878);
        existingEntity3 = new Town(9L, "Yekaterinburg", "RUS", "Sverdlovsk Oblast", 55.1547, 61.3758);

        updatedExistingEntity1 = new Town(1L, "Shymkent", "KAZ", null, 42.3111, 70.0000);
        updatedExistingEntity2 = new Town(3L, "Karaganda", "KAZ", null, 49.8000, 73.0000);
        updatedExistingEntity3 = new Town(9L, "Sverdlovsk", "RUS", "Sverdlovsk Oblast", 55.1547, 61.3758);

        nonExistingEntity1 = new Town(13L, "New-York", "USA", "New-York", 40.7127, -74.0059);
        nonExistingEntity2 = new Town(14L, "Sydney", "AUS", null, -33.8650, 151.2094);
        nonExistingEntity3 = new Town(39L, "Madrid", "ESP", null, 40.3833, -3.7166);

        newEntity1 = new Town(null, "Aktobe", "KAZ", "Aktobe Region", 50.2908, 57.1614);
        newEntity2 = new Town(null, "Rio de Janeiro", "BRA", "Southeast", -22.9083, -43.1963);
        newEntity3 = new Town(null, "Minsk", "BLR", null,53.9100, 27.5666);
    }

    @Override
    public Long getEntityId(Town town) {
        return town.getId();
    }

    @Override
    public boolean entitiesEqualsWithoutId(Town town1, Town town2) {
        return town1.getName().equals(town2.getName()) &&
                town1.getCountryCode().equals(town2.getCountryCode()) &&
                Objects.equals(town1.getRegion(), town2.getRegion()) &&
                town1.getLatitude().equals(town2.getLatitude()) &&
                town1.getLongitude().equals(town2.getLongitude());
    }

    @Test
    void constructor_nullArguments_throwsException() {
        assertThrows(NullPointerException.class, () -> new TownRepoImpl(null));
    }
}