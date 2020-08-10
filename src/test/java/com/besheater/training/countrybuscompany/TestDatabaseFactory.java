package com.besheater.training.countrybuscompany;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.concurrent.atomic.AtomicInteger;

public class TestDatabaseFactory {
    private static final AtomicInteger databaseNum = new AtomicInteger();
    private static final EmbeddedDatabaseBuilder databaseBuilder = makeDatabaseBuilder();

    public static EmbeddedDatabase get() {
        String dbName = String.format("testdb%d;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
                databaseNum.incrementAndGet());
        return databaseBuilder.setName(dbName).build();
    }

    private static EmbeddedDatabaseBuilder makeDatabaseBuilder() {
        return new EmbeddedDatabaseBuilder()
                     .setType(EmbeddedDatabaseType.H2)
                     .setScriptEncoding("UTF-8")
                     .addScripts("schema.sql", "test-data.sql");
    }
}