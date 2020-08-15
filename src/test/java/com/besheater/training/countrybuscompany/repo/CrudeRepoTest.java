package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.TestDatabaseFactory;
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

public abstract class CrudeRepoTest<T> {

    protected EmbeddedDatabase database;
    protected CrudeRepo<T> entityRepo;

    protected Long entityCount;

    protected T existingEntity1;
    protected T existingEntity2;
    protected T existingEntity3;

    protected T updatedExistingEntity1;
    protected T updatedExistingEntity2;
    protected T updatedExistingEntity3;

    protected T nonExistingEntity1;
    protected T nonExistingEntity2;
    protected T nonExistingEntity3;

    protected T newEntity1;
    protected T newEntity2;
    protected T newEntity3;

    private List<T> existingEntities;
    private List<T> updatedExistingEntities;
    private List<T> nonExistingEntities;
    private List<T> newEntities;
    private List<T> existingAndNonExistingEntities;
    private List<T> newAndUpdatedEntities;

    private List<Long> existingEntitiesIds;
    private List<Long> updatedExistingEntitiesIds;
    private List<Long> nonExistingEntitiesIds;
    private List<Long> existingAndNonExistingEntitiesIds;

    public CrudeRepoTest() { }

    @BeforeEach
    public void initDataSource() {
        database = TestDatabaseFactory.get();
        init();
        initHelperCollections();
    }

    @AfterEach
    public void closeDataSource() {
        database.shutdown();
    }

    public abstract void init();

    public abstract Long getEntityId(T entity);

    public abstract boolean entitiesEqualsWithoutId(T entity1, T entity2);

    private void initHelperCollections() {
        existingEntities =
                Arrays.asList(existingEntity1, existingEntity2, existingEntity3);
        updatedExistingEntities =
                Arrays.asList(updatedExistingEntity1, updatedExistingEntity2, updatedExistingEntity3);
        nonExistingEntities =
                Arrays.asList(nonExistingEntity1, nonExistingEntity2, nonExistingEntity3);
        newEntities =
                Arrays.asList(newEntity1, newEntity2, newEntity3);

        existingAndNonExistingEntities = new ArrayList<>();
        existingAndNonExistingEntities.addAll(existingEntities);
        existingAndNonExistingEntities.addAll(nonExistingEntities);

        newAndUpdatedEntities = new ArrayList<>();
        newAndUpdatedEntities.addAll(newEntities);
        newAndUpdatedEntities.addAll(updatedExistingEntities);

        existingEntitiesIds = extractIds(existingEntities);
        updatedExistingEntitiesIds = extractIds(updatedExistingEntities);
        nonExistingEntitiesIds = extractIds(nonExistingEntities);
        existingAndNonExistingEntitiesIds = extractIds(existingAndNonExistingEntities);
    }

    private List<Long> extractIds(Collection<T> collection) {
        return collection.stream()
                         .map(this::getEntityId)
                         .collect(Collectors.toList());
    }

    @Test
    void count_returnsExpectedValue() {
        assertEquals(entityCount, entityRepo.count());
        entityRepo.deleteById(getEntityId(existingEntity1));
        entityRepo.deleteById(getEntityId(existingEntity2));
        assertEquals(entityCount - 2, entityRepo.count());
    }

    @Test
    void delete_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.delete(null));
    }

    @Test
    void delete_existingEntity_deletesEntity() {
        assertEquals(entityCount, entityRepo.count());
        assertTrue(entityRepo.existsById(getEntityId(existingEntity2)));
        entityRepo.delete((T) existingEntity2);
        assertEquals(entityCount - 1, entityRepo.count());
        assertFalse(entityRepo.existsById(getEntityId(existingEntity2)));
    }

    @Test
    void delete_nonExistingEntity_deletesNone() {
        assertEquals(entityCount, entityRepo.count());
        assertFalse(entityRepo.findById(getEntityId(nonExistingEntity3)).isPresent());
        entityRepo.delete(nonExistingEntity3);
        assertEquals(entityCount, entityRepo.count());
        assertFalse(entityRepo.findById(getEntityId(nonExistingEntity3)).isPresent());
    }

    @Test
    void deleteAll_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.deleteAll(null));
        List<T> entitiesWithNull = Arrays.asList(existingEntity1, null, existingEntity3);
        assertThrows(NullPointerException.class, () -> entityRepo.deleteAll(entitiesWithNull));
    }

    @Test
    void deleteAll_existingEntities_deletesEntities() {
        assertEquals(entityCount, entityRepo.count());
        assertIterableEquals(existingEntities, entityRepo.findAllById(existingEntitiesIds));
        entityRepo.deleteAll(existingEntities);
        assertEquals(entityCount - 3, entityRepo.count());
        assertTrue(entityRepo.findAllById(existingEntitiesIds).isEmpty());
    }

    @Test
    void deleteAll_nonExistingEntities_deletesNone() {
        assertEquals(entityCount, entityRepo.count());
        assertTrue(entityRepo.findAllById(nonExistingEntitiesIds).isEmpty());
        entityRepo.deleteAll(nonExistingEntities);
        assertEquals(entityCount, entityRepo.count());
        assertTrue(entityRepo.findAllById(nonExistingEntitiesIds).isEmpty());
    }

    @Test
    void deleteAll_existingAndNonExistingEntities_deletesOnlyExistingEntities() {
        assertEquals(entityCount, entityRepo.count());
        assertFalse(entityRepo.findAllById(existingAndNonExistingEntitiesIds).isEmpty());
        entityRepo.deleteAll(existingAndNonExistingEntities);
        assertEquals(entityCount - 3, entityRepo.count());
        assertTrue(entityRepo.findAllById(existingAndNonExistingEntitiesIds).isEmpty());
    }

    @Test
    void deleteById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.deleteById(null));
    }

    @Test
    void deleteById_existingId_deletesEntity() {
        assertEquals(entityCount, entityRepo.count());
        assertTrue(entityRepo.existsById(getEntityId(existingEntity3)));
        entityRepo.deleteById(getEntityId(existingEntity3));
        assertEquals(entityCount - 1, entityRepo.count());
        assertFalse(entityRepo.existsById(getEntityId(existingEntity3)));
    }

    @Test
    void deleteById_nonExistingId_deletesNone() {
        assertEquals(entityCount, entityRepo.count());
        assertFalse(entityRepo.existsById(getEntityId(nonExistingEntity2)));
        entityRepo.deleteById(getEntityId(nonExistingEntity2));
        assertEquals(entityCount, entityRepo.count());
        assertFalse(entityRepo.existsById(getEntityId(nonExistingEntity2)));
    }

    @Test
    void existsById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.existsById(null));
    }

    @Test
    void existsById_existingId_returnsTrue() {
        assertTrue(entityRepo.existsById(getEntityId(existingEntity1)));
        assertTrue(entityRepo.existsById(getEntityId(existingEntity2)));
        assertTrue(entityRepo.existsById(getEntityId(existingEntity3)));
    }

    @Test
    void existsById_nonExistingId_returnsFalse() {
        assertFalse(entityRepo.existsById(getEntityId(nonExistingEntity1)));
        assertFalse(entityRepo.existsById(getEntityId(nonExistingEntity2)));
        assertFalse(entityRepo.existsById(getEntityId(nonExistingEntity3)));
    }

    @Test
    void findAll_returnsExpectedValue() {
        Collection<T> allEntities = entityRepo.findAll();
        assertEquals(entityCount, allEntities.size());
        assertTrue(allEntities.containsAll(existingEntities));
    }

    @Test
    void findAllById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.findAllById(null));
        List<Long> idsWithNull = Arrays.asList(1L, null, 3L, 4L);
        assertThrows(NullPointerException.class, () -> entityRepo.findAllById(idsWithNull));
    }

    @Test
    void findAllById_existingEntities_findsEntities() {
        Collection<T> foundEntities = entityRepo.findAllById(existingEntitiesIds);
        assertIterableEquals(existingEntities, foundEntities);
    }

    @Test
    void findAllById_nonExistingEntities_findsNone() {
        Collection<T> foundEntities = entityRepo.findAllById(nonExistingEntitiesIds);
        assertTrue(foundEntities.isEmpty());
    }

    @Test
    void findAllById_existingAndNonExistingEntities_findsOnlyExistingEntities() {
        Collection<T> foundEntities = entityRepo.findAllById(existingAndNonExistingEntitiesIds);
        assertIterableEquals(existingEntities, foundEntities);
    }

    @Test
    void findById_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.findById(null));
    }

    @Test
    void findById_existingId_returnsEntity() {
        assertEquals(existingEntity1, entityRepo.findById(getEntityId(existingEntity1)).get());
        assertEquals(existingEntity2, entityRepo.findById(getEntityId(existingEntity2)).get());
        assertEquals(existingEntity3, entityRepo.findById(getEntityId(existingEntity3)).get());
    }

    @Test
    void findById_nonExistingId_returnsNone() {
        assertFalse(entityRepo.findById(getEntityId(nonExistingEntity1)).isPresent());
        assertFalse(entityRepo.findById(getEntityId(nonExistingEntity2)).isPresent());
        assertFalse(entityRepo.findById(getEntityId(nonExistingEntity3)).isPresent());
    }

    @Test
    void save_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.save(null));
    }

    @Test
    void save_existingEntity_updatesExistingEntity() {
        assertEquals(entityCount, entityRepo.count());
        assertNotEquals(existingEntity3, updatedExistingEntity3);
        T savedEntity = entityRepo.save(updatedExistingEntity3);
        assertEquals(updatedExistingEntity3, savedEntity);
        assertEquals(updatedExistingEntity3, entityRepo.findById(getEntityId(updatedExistingEntity3)).get());
        assertEquals(entityCount, entityRepo.count());
    }

    @Test
    void save_nonExistingEntity_savesNewEntity() {
        assertEquals(entityCount, entityRepo.count());
        T savedEntity = entityRepo.save(newEntity2);
        assertEquals(entityCount + 1, entityRepo.count());
        assertEquals(entityCount + 1, getEntityId(savedEntity));
        assertTrue(entitiesEqualsWithoutId(savedEntity, newEntity2));
    }

    @Test
    void save_entityWithNonExistingId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> entityRepo.save(nonExistingEntity1));
        assertThrows(IllegalArgumentException.class, () -> entityRepo.save(nonExistingEntity2));
        assertThrows(IllegalArgumentException.class, () -> entityRepo.save(nonExistingEntity3));
    }

    @Test
    void saveAll_nullArgument_throwsException() {
        assertThrows(NullPointerException.class, () -> entityRepo.saveAll(null));
        List<T> entitiesWithNull = Arrays.asList(existingEntity1, null, existingEntity2);
        assertThrows(NullPointerException.class, () -> entityRepo.saveAll(entitiesWithNull));
    }

    @Test
    void saveAll_existingEntities_updatesExistingEntities() {
        assertEquals(entityCount, entityRepo.count());
        Collection<T> savedEntities = entityRepo.saveAll(updatedExistingEntities);
        assertEquals(entityCount, entityRepo.count());
        assertIterableEquals(updatedExistingEntities, savedEntities);
        assertIterableEquals(updatedExistingEntities, entityRepo.findAllById(updatedExistingEntitiesIds));
    }

    @Test
    void saveAll_nonExistingEntities_savesEntities() {
        assertEquals(entityCount, entityRepo.count());
        List<T> savedEntities = new ArrayList<>(entityRepo.saveAll(newEntities));
        assertEquals(entityCount + 3, entityRepo.count());
        for (int i = 0; i < newEntities.size(); i++) {
            T newEntity = newEntities.get(i);
            T savedEntity = savedEntities.get(i);
            T entityFromRepo = entityRepo.findById(getEntityId(savedEntity)).get();
            assertTrue(entitiesEqualsWithoutId(newEntity, savedEntity));
            assertTrue(entitiesEqualsWithoutId(newEntity, entityFromRepo));
        }
    }

    @Test
    void saveAll_existingAndNonExistingEntities_savesAndUpdatesEntities() {
        assertEquals(entityCount, entityRepo.count());
        List<T> savedEntities = new ArrayList<>(entityRepo.saveAll(newAndUpdatedEntities));
        assertEquals(entityCount + 3, entityRepo.count());
        for (int i = 0; i < newAndUpdatedEntities.size(); i++) {
            T newOrUpdatedEntity = newAndUpdatedEntities.get(i);
            T savedEntity = savedEntities.get(i);
            T entityFromRepo = entityRepo.findById(getEntityId(savedEntity)).get();
            assertTrue(entitiesEqualsWithoutId(newOrUpdatedEntity, savedEntity));
            assertTrue(entitiesEqualsWithoutId(newOrUpdatedEntity, entityFromRepo));
        }
    }

    @Test
    void saveAll_entitiesWithNonExistingId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> entityRepo.saveAll(nonExistingEntities));
        assertThrows(IllegalArgumentException.class, () -> entityRepo.saveAll(existingAndNonExistingEntities));
    }
}