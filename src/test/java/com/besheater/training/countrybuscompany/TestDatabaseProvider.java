package com.besheater.training.countrybuscompany;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TestDatabaseProvider {
    private static final AtomicInteger databaseNum = new AtomicInteger();
    private static final String databaseSchema = readFile("/schema.sql");
    private static final String databaseData = readFile("/test-data.sql");

    public static DataSource get() {
        JdbcDataSource dataSource = new JdbcDataSource();
        String jdbcUrl = String.format("jdbc:h2:mem:test%d;" +
                        "MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1;",
                        databaseNum.incrementAndGet());
        dataSource.setUrl(jdbcUrl);
        dataSource.setUser("duke");
        dataSource.setPassword("123456");

        initSchema(dataSource);
        return dataSource;
    }

    public static void close(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            connection.createStatement().executeUpdate("SHUTDOWN");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void initSchema(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            RunScript.execute(connection, new StringReader(databaseSchema));
            RunScript.execute(connection, new StringReader(databaseData));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static String readFile(String resourcePath) {
        // The old Stupid Scanner trick
        // See https://stackoverflow.com/questions/6068197/utils-to-read-resource-text-file-to-string-java
        InputStream inputStream = TestDatabaseProvider.class.getResourceAsStream(resourcePath);
        return new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
    }
}