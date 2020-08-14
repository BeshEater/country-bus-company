package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Driver;
import com.besheater.training.countrybuscompany.entity.RoutePart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DriverRepoImpl implements DriverRepo {

    private JdbcTemplate jdbcTemplate;
    private RoutePartRepo routePartRepo;

    public DriverRepoImpl(DataSource dataSource, RoutePartRepo routePartRepo) {
        if (dataSource == null) {
            throw new NullPointerException("DataSource cannot be null");
        }
        if (routePartRepo == null) {
            throw new NullPointerException("RoutePartRepo cannot be null");
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.routePartRepo = routePartRepo;
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.driver", Long.class);
    }

    @Override
    public void delete(Driver driver) {
        deleteById(driver.getId());
    }

    @Override
    public void deleteAll(Collection<? extends Driver> drivers) {
        List<Object[]> args = drivers.stream()
                                     .map( d -> new Object[] {d.getId()} )
                                     .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.driver WHERE id = ?", args);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.driver WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.driver WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<Driver> findAll() {
        String query = "SELECT * FROM main.driver";
        return jdbcTemplate.query(query, new DriverRowMapper());
    }

    @Override
    public Collection<Driver> findAllById(Collection<Long> ids) {
        if (ids == null) {
            throw new NullPointerException("collection cannot be null");
        }
        if (ids.contains(null)) {
            throw new NullPointerException("id in collection cannot be null");
        }
        return ids.stream()
                  .map(this::findById)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toList());
    }

    @Override
    public Optional<Driver> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.driver WHERE id = ?";
        return jdbcTemplate.query(query, new DriverRowMapper(), id).stream().findFirst();
    }

    @Override
    public Driver save(Driver driver) {
        if (driver.getId() == null) {
            return saveNewDriver(driver);
        } else {
            return updateExistingDriver(driver);
        }
    }

    private Driver saveNewDriver(Driver driver) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", driver.getId());
        parameters.put("route_part_id",
                driver.getRoutePart() != null ? driver.getRoutePart().getId() : null);
        parameters.put("first_name", driver.getFirstName());
        parameters.put("last_name", driver.getLastName());
        parameters.put("date_of_birth", driver.getDateOfBirth());
        parameters.put("address", driver.getAddress());
        parameters.put("driver_licence_number", driver.getDriverLicenseNumber());
        parameters.put("phone_number", driver.getPhoneNumber());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("driver")
                .usingGeneratedKeyColumns("id");

        Long savedDriverId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedDriverId).get();
    }

    private Driver updateExistingDriver(Driver driver) {
        Long id = driver.getId();
        Long routePartId = driver.getRoutePart() != null ? driver.getRoutePart().getId() : null;
        String firstName = driver.getFirstName();
        String lastName = driver.getLastName();
        LocalDate dateOfBirth = driver.getDateOfBirth();
        String address = driver.getAddress();
        String driverLicenceNumber = driver.getDriverLicenseNumber();
        String phoneNumber = driver.getPhoneNumber();

        String query = "UPDATE main.driver SET route_part_id = ?, first_name = ?, " +
                "last_name = ?, date_of_birth = ?, address = ?, " +
                "driver_licence_number = ?, phone_number = ? WHERE id = ?";
        jdbcTemplate.update(query, routePartId, firstName, lastName, dateOfBirth,
                address, driverLicenceNumber, phoneNumber, id);

        Optional<Driver> updatedDriver = findById(id);
        if (updatedDriver.isPresent()) {
            return updatedDriver.get();
        } else {
            String msg = String.format("Driver with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<Driver> saveAll(Collection<? extends Driver> drivers) {
        return drivers.stream()
                      .map(this::save)
                      .collect(Collectors.toList());
    }

    private class DriverRowMapper implements RowMapper<Driver> {

        @Override
        public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            RoutePart routePart = null;
            Long routePartId = rs.getLong("route_part_id");
            if (!rs.wasNull()) {
                routePart = routePartRepo.findById(routePartId).orElse(null);
            }
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
            String address = rs.getString("address");
            String driverLicenceNumber = rs.getString("driver_licence_number");
            String phoneNumber = rs.getString("phone_number");

            return Driver.builder()
                    .id(id)
                    .routePart(routePart)
                    .firstName(firstName)
                    .lastName(lastName)
                    .dateOfBirth(dateOfBirth)
                    .address(address)
                    .driverLicenseNumber(driverLicenceNumber)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }
}