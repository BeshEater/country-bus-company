package com.besheater.training.countrybuscompany;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RunnerTest {

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
    public void myVeryThoroughTest() {
        assertTrue(true);
    }

    @Test
    public void test1() {
        String query = "SELECT * FROM main.bus";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("registration_number"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String query = "SELECT * FROM main.driver";

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("first_name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}