package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Bus;
import com.besheater.training.countrybuscompany.entity.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class BusRepoImpl implements BusRepo {

    private JdbcTemplate jdbcTemplate;
    private RouteRepo routeRepo;

    public BusRepoImpl(DataSource dataSource, RouteRepo routeRepo) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.routeRepo = routeRepo;
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.bus", Long.class);
    }

    @Override
    public void delete(Bus bus) {
        jdbcTemplate.update("DELETE FROM main.bus WHERE id = ?", bus.getId());
    }

    @Override
    public void deleteAll(Collection<? extends Bus> buses) {
        List<Object[]> args = buses.stream()
                                   .map( b -> new Object[] {b.getId()} )
                                   .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.bus WHERE id = ?", args);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.bus WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.bus WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<Bus> findAll() {
        String query = "SELECT * FROM main.bus";
        return jdbcTemplate.query(query, new BusRowMapper());
    }

    @Override
    public Collection<Bus> findAllById(Collection<Long> ids) {
        // TODO Rewrite using batch method
        // See https://stackoverflow.com/questions/3107044/preparedstatement-with-list-of-parameters-in-a-in-clause
        // And https://javaranch.com/journal/200510/Journal200510.jsp#a2
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
    public Optional<Bus> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.bus WHERE id = ?";
        return jdbcTemplate.query(query, new BusRowMapper(), id).stream().findFirst();
    }

    @Override
    public Bus save(Bus bus) {
        if (bus.getId() == null) {
            return saveNewBus(bus);
        } else {
            return updateExistingBus(bus);
        }
    }

    private Bus saveNewBus(Bus bus) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", bus.getId());
        parameters.put("route_id", bus.getRoute() != null ? bus.getRoute().getId() : null);
        parameters.put("registration_number", bus.getRegistrationNumber());
        parameters.put("capacity", bus.getCapacity());
        parameters.put("is_double_decker", bus.isDoubleDecker());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("bus")
                .usingGeneratedKeyColumns("id");

        Long savedBusId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedBusId).get();
    }

    private Bus updateExistingBus(Bus bus) {
        Long id = bus.getId();
        Long routeId = bus.getRoute() != null ? bus.getRoute().getId() : null;
        String registrationNumber = bus.getRegistrationNumber();
        Integer capacity = bus.getCapacity();
        boolean isDoubleDecker = bus.isDoubleDecker();

        String query = "UPDATE main.bus SET route_id = ?, registration_number = ?, " +
                "capacity = ?, is_double_decker = ? WHERE id = ?";
        jdbcTemplate.update(query, routeId, registrationNumber, capacity, isDoubleDecker, id);

        Optional<Bus> updatedBus = findById(id);
        if (updatedBus.isPresent()) {
            return updatedBus.get();
        } else {
            String msg = String.format("Bus with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<Bus> saveAll(Collection<? extends Bus> buses) {
        // TODO Rewrite using batch method
        return buses.stream()
                    .map(this::save)
                    .collect(Collectors.toList());
    }

    private class BusRowMapper implements RowMapper<Bus> {

        @Override
        public Bus mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            Route route = null;
            Long routeId = rs.getLong("route_id");
            if (!rs.wasNull()) {
                route = routeRepo.findById(routeId).orElse(null);
            }
            String registrationNum = rs.getString("registration_number");
            Integer capacity = rs.getInt("capacity");
            boolean isDoubleDecker = rs.getBoolean("is_double_decker");

            return new Bus(id, route, registrationNum, capacity, isDoubleDecker);
        }
    }
}