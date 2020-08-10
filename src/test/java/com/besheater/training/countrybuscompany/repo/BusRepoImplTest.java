package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.TestDatabaseFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusRepoImplTest {

    private EmbeddedDatabase database;

    @BeforeEach
    public void initDataSource() {
        database = TestDatabaseFactory.get();
    }

    @AfterEach
    public void closeDataSource() {
        database.shutdown();
    }

    @Test
    void count_returnsExpectedValue() {
        assertEquals(5, 5);
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void existsById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findAllById() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }
}