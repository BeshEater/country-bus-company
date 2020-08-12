package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.TestDatabaseFactory;
import com.besheater.training.countrybuscompany.entity.Bus;
import com.besheater.training.countrybuscompany.entity.Route;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BusRepoImplTest {

    private EmbeddedDatabase database;
    private BusRepoImpl busRepo;
    
    private Long busCount;

    private Bus existingBus1;
    private Bus existingBus2;
    private Bus existingBus3;

    private Bus nonExistingBus1;
    private Bus nonExistingBus2;
    private Bus nonExistingBus3;

    private List<Bus> existingBusses;
    private List<Bus> nonExistingBusses;
    private List<Bus> existingAndNonExistingBusses;

    private List<Long> existingBussesIds;
    private List<Long> nonExistingBussesIds;
    private List<Long> existingAndNonExistingBussesIds;

    public BusRepoImplTest() {
        busCount = 11L;
        
        existingBus1 = new Bus(1L, new Route(1L, 125), "KZ726GK01", 65, false);
        existingBus2 = new Bus(2L, new Route(2L, 271), "KZ146GK01", 120, true);
        existingBus3 = new Bus(5L, null, "KZ560AK10", 75, false);

        nonExistingBus1 = new Bus(12L, new Route(1L, 125), "TK4481", 50, false);
        nonExistingBus2 = new Bus(13L, null, "TT111PPP", 65, false);
        nonExistingBus3 = new Bus(17L, new Route(3L, 189), "KZ146GK01", 120, true);

        existingBusses = Arrays.asList(existingBus1, existingBus2, existingBus3);
        nonExistingBusses = Arrays.asList(nonExistingBus1, nonExistingBus2, nonExistingBus3);
        existingAndNonExistingBusses = new ArrayList<>();
        existingAndNonExistingBusses.addAll(existingBusses);
        existingAndNonExistingBusses.addAll(nonExistingBusses);

        existingBussesIds = existingBusses.stream().map(Bus::getId)
                                                   .collect(Collectors.toList());
        nonExistingBussesIds = nonExistingBusses.stream().map(Bus::getId)
                                                         .collect(Collectors.toList());
        existingAndNonExistingBussesIds = existingAndNonExistingBusses.stream()
                                                                      .map(Bus::getId)
                                                                      .collect(Collectors.toList());
    }

    @BeforeEach
    public void initDataSource() {
        database = TestDatabaseFactory.get();
        busRepo = new BusRepoImpl(database, new RouteRepoImpl(database));
    }

    @AfterEach
    public void closeDataSource() {
        database.shutdown();
    }

    @Test
    void count_returnsExpectedValue() {
        assertEquals(busCount, busRepo.count());
        busRepo.deleteById(1L);
        busRepo.deleteById(2L);
        assertEquals(busCount - 2, busRepo.count());
    }

    @Test
    void delete_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.delete(null));
    }

    @Test
    void delete_existingBus_deletesBus() {
        assertEquals(busCount, busRepo.count());
        assertTrue(busRepo.findById(existingBus2.getId()).isPresent());
        busRepo.delete(existingBus2);
        assertEquals(busCount - 1, busRepo.count());
        assertFalse(busRepo.findById(existingBus2.getId()).isPresent());
    }

    @Test
    void delete_nonExistingBus_deletesNone() {
        assertEquals(busCount, busRepo.count());
        assertFalse(busRepo.findById(nonExistingBus1.getId()).isPresent());
        busRepo.delete(nonExistingBus1);
        assertEquals(busCount, busRepo.count());
        assertFalse(busRepo.findById(nonExistingBus1.getId()).isPresent());
    }

    @Test
    void deleteAll_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.deleteAll(null));
        List<Bus> busesWithNull = Arrays.asList(existingBus1, null, existingBus3);
        assertThrows(NullPointerException.class, () -> busRepo.deleteAll(busesWithNull));
    }

    @Test
    void deleteAll_existingBusses_deletesBusses() {
        assertEquals(busCount, busRepo.count());
        assertIterableEquals(existingBusses, busRepo.findAllById(existingBussesIds));
        busRepo.deleteAll(existingBusses);
        assertEquals(busCount - 3, busRepo.count());
        assertTrue(busRepo.findAllById(existingBussesIds).isEmpty());
    }

    @Test
    void deleteAll_nonExistingBusses_deletesNone() {
        assertEquals(busCount, busRepo.count());
        assertTrue(busRepo.findAllById(nonExistingBussesIds).isEmpty());
        busRepo.deleteAll(nonExistingBusses);
        assertEquals(busCount, busRepo.count());
        assertTrue(busRepo.findAllById(nonExistingBussesIds).isEmpty());
    }

    @Test
    void deleteAll_existingAndNonExistingBusses_deletesOnlyExistingBusses() {
        assertEquals(busCount, busRepo.count());
        assertFalse(busRepo.findAllById(existingAndNonExistingBussesIds).isEmpty());
        busRepo.deleteAll(existingAndNonExistingBusses);
        assertEquals(busCount - 3, busRepo.count());
        assertTrue(busRepo.findAllById(existingAndNonExistingBussesIds).isEmpty());
    }

    @Test
    void deleteById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.deleteById(null));
    }

    @Test
    void deleteById_existingId_deletesBus() {
        assertEquals(busCount, busRepo.count());
        assertTrue(busRepo.existsById(existingBus3.getId()));
        busRepo.deleteById(existingBus3.getId());
        assertEquals(busCount - 1, busRepo.count());
        assertFalse(busRepo.existsById(existingBus3.getId()));
    }

    @Test
    void deleteById_nonExistingId_deletesNone() {
        assertEquals(busCount, busRepo.count());
        assertFalse(busRepo.existsById(nonExistingBus2.getId()));
        busRepo.deleteById(nonExistingBus2.getId());
        assertEquals(busCount, busRepo.count());
        assertFalse(busRepo.existsById(nonExistingBus2.getId()));
    }

    @Test
    void existsById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.existsById(null));
    }

    @Test
    void existsById_existingId_returnsTrue() {
        assertTrue(busRepo.existsById(existingBus1.getId()));
        assertTrue(busRepo.existsById(existingBus2.getId()));
        assertTrue(busRepo.existsById(existingBus3.getId()));
    }

    @Test
    void existsById_nonExistingId_returnsFalse() {
        assertFalse(busRepo.existsById(nonExistingBus1.getId()));
        assertFalse(busRepo.existsById(nonExistingBus2.getId()));
        assertFalse(busRepo.existsById(nonExistingBus3.getId()));
    }

    @Test
    void findAll_returnsExpectedValue() {
        Collection<Bus> allBusses = busRepo.findAll();
        assertEquals(busCount, allBusses.size());
        assertTrue(allBusses.containsAll(existingBusses));
    }

    @Test
    void findAllById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.findAllById(null));
        List<Long> idsWithNull = Arrays.asList(1L, null, 3L, 4L);
        assertThrows(NullPointerException.class, () -> busRepo.findAllById(idsWithNull));
    }

    @Test
    void findAllById_existingBusses_findsAll() {
        Collection<Bus> foundBusses = busRepo.findAllById(existingBussesIds);
        assertIterableEquals(existingBusses, foundBusses);
    }

    @Test
    void findAllById_nonExistingBusses_findsNone() {
        Collection<Bus> foundBusses = busRepo.findAllById(nonExistingBussesIds);
        assertTrue(foundBusses.isEmpty());
    }

    @Test
    void findAllById_existingAndNonExistingBusses_findsOnlyExistingBusses() {
        Collection<Bus> foundBusses = busRepo.findAllById(existingAndNonExistingBussesIds);
        assertIterableEquals(existingBusses, foundBusses);
    }

    @Test
    void findById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.findById(null));
    }

    @Test
    void findById_existingId_returnsBus() {
        assertEquals(existingBus1, busRepo.findById(existingBus1.getId()).get());
        assertEquals(existingBus2, busRepo.findById(existingBus2.getId()).get());
        assertEquals(existingBus3, busRepo.findById(existingBus3.getId()).get());
    }

    @Test
    void findById_nonExistingId_returnsNone() {
        assertFalse(busRepo.findById(nonExistingBus1.getId()).isPresent());
        assertFalse(busRepo.findById(nonExistingBus2.getId()).isPresent());
        assertFalse(busRepo.findById(nonExistingBus3.getId()).isPresent());
    }

    @Test
    void save_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.save(null));
    }

    @Test
    void save_existingBus_updatesExistingBus() {
        Bus updatedBus = new Bus(existingBus3.getId(), new Route(2L, 271), "CC000PPP", 110, true);
        assertEquals(busCount, busRepo.count());
        assertNotEquals(existingBus3, updatedBus);
        Bus savedBus = busRepo.save(updatedBus);
        assertEquals(updatedBus, savedBus);
        assertEquals(updatedBus, busRepo.findById(updatedBus.getId()).get());
        assertEquals(busCount, busRepo.count());
    }

    @Test
    void save_nonExistingBus_savesNewBus() {
        Bus newBus = new Bus(null, new Route(1L, 125), "XX111ZZ", 40, false);
        assertEquals(busCount, busRepo.count());
        Bus savedBus = busRepo.save(newBus);
        assertEquals(busCount + 1, busRepo.count());
        assertEquals(busCount + 1, savedBus.getId());
        assertTrue(savedBus.equalsWithoutId(newBus));
    }

    @Test
    void save_busWithNonExistingId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> busRepo.save(nonExistingBus1));
        assertThrows(IllegalArgumentException.class, () -> busRepo.save(nonExistingBus3));
    }

    @Test
    void saveAll_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> busRepo.saveAll(null));
        List<Bus> busesWithNull = Arrays.asList(existingBus1, null, existingBus3);
        assertThrows(NullPointerException.class, () -> busRepo.saveAll(busesWithNull));
    }

    @Test
    void saveAll_existingBusses_updatesExisting() {
        Bus updatedBus1 = new Bus(existingBus1.getId(), new Route(5L, 513), "KK000PPP", 60, false);
        Bus updatedBus2 = new Bus(existingBus2.getId(), new Route(3L, 189), "CC111PPP", 110, true);
        List<Bus> updatedBusses = Arrays.asList(updatedBus1, updatedBus2);
        List<Long> updatedBussesIds = updatedBusses.stream().map(Bus::getId)
                                                            .collect(Collectors.toList());

        assertEquals(busCount, busRepo.count());
        Collection<Bus> savedBusses = busRepo.saveAll(updatedBusses);
        assertEquals(busCount, busRepo.count());
        assertIterableEquals(updatedBusses, savedBusses);
        assertIterableEquals(updatedBusses, busRepo.findAllById(updatedBussesIds));
    }

    @Test
    void saveAll_nonExistingBusses_savesBusses() {
        Bus newBus1 = new Bus(null, new Route(4L, null), "WW000TT", 60, false);
        Bus newBus2 = new Bus(null, null, "CC111YYY", 110, true);
        List<Bus> newBusses = Arrays.asList(newBus1, newBus2);

        assertEquals(busCount, busRepo.count());
        List<Bus> savedBusses = new ArrayList<>(busRepo.saveAll(newBusses));
        assertEquals(busCount + 2, busRepo.count());

        assertTrue(newBusses.get(0).equalsWithoutId(savedBusses.get(0)));
        assertTrue(newBusses.get(1).equalsWithoutId(savedBusses.get(1)));
        assertTrue(newBusses.get(0).equalsWithoutId(busRepo.findById(savedBusses.get(0).getId()).get()));
        assertTrue(newBusses.get(1).equalsWithoutId(busRepo.findById(savedBusses.get(1).getId()).get()));
    }

    @Test
    void saveAll_existingAndNonExistingBusses_savesAndUpdatesBusses() {
        Bus updatedBus1 = new Bus(existingBus1.getId(), new Route(5L, 513), "KK000PPP", 60, false);
        Bus updatedBus2 = new Bus(existingBus2.getId(), new Route(3L, 189), "CC111PPP", 110, true);
        Bus newBus1 = new Bus(null, new Route(4L, null), "WW000TT", 60, false);
        Bus newBus2 = new Bus(null, null, "CC111YYY", 110, true);
        List<Bus> newAndUpdatedBusses = Arrays.asList(updatedBus1, updatedBus2, newBus1, newBus2);

        assertEquals(busCount, busRepo.count());
        List<Bus> savedBusses = new ArrayList<>(busRepo.saveAll(newAndUpdatedBusses));
        assertEquals(busCount + 2, busRepo.count());

        assertTrue(newAndUpdatedBusses.get(0).equalsWithoutId(savedBusses.get(0)));
        assertTrue(newAndUpdatedBusses.get(1).equalsWithoutId(savedBusses.get(1)));
        assertTrue(newAndUpdatedBusses.get(2).equalsWithoutId(savedBusses.get(2)));
        assertTrue(newAndUpdatedBusses.get(3).equalsWithoutId(savedBusses.get(3)));
        assertTrue(newAndUpdatedBusses.get(0).equalsWithoutId(busRepo.findById(savedBusses.get(0).getId()).get()));
        assertTrue(newAndUpdatedBusses.get(1).equalsWithoutId(busRepo.findById(savedBusses.get(1).getId()).get()));
        assertTrue(newAndUpdatedBusses.get(2).equalsWithoutId(busRepo.findById(savedBusses.get(2).getId()).get()));
        assertTrue(newAndUpdatedBusses.get(3).equalsWithoutId(busRepo.findById(savedBusses.get(3).getId()).get()));
    }

    @Test
    void saveAll_bussesWithNonExistingId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> busRepo.saveAll(nonExistingBusses));
        assertThrows(IllegalArgumentException.class, () -> busRepo.saveAll(existingAndNonExistingBusses));
    }
}