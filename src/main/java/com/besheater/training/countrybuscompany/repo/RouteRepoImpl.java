package com.besheater.training.countrybuscompany.repo;

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
public class RouteRepoImpl implements RouteRepo {

    private JdbcTemplate jdbcTemplate;

    public RouteRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.route", Long.class);
    }

    @Override
    public void delete(Route route) {
        deleteById(route.getId());
    }

    @Override
    public void deleteAll(Collection<? extends Route> routes) {
        List<Object[]> args = routes.stream()
                                    .map( r -> new Object[] {r.getId()} )
                                    .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.route WHERE id = ?", args);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.route WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.route WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<Route> findAll() {
        String query = "SELECT * FROM main.route";
        return jdbcTemplate.query(query, new RouteRowMapper());
    }

    @Override
    public Collection<Route> findAllById(Collection<Long> ids) {
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
    public Optional<Route> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.route WHERE id = ?";
        return jdbcTemplate.query(query, new RouteRowMapper(), id).stream().findFirst();
    }

    @Override
    public Route save(Route route) {
        if (route.getId() == null) {
            return saveNewRoute(route);
        } else {
            return updateExistingRoute(route);
        }
    }

    private Route saveNewRoute(Route route) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", route.getId());
        parameters.put("avg_passengers_per_day", route.getAveragePassengersPerDay());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("route")
                .usingGeneratedKeyColumns("id");

        Long savedRouteId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedRouteId).get();
    }

    private Route updateExistingRoute(Route route) {
        Long id = route.getId();
        Integer avgPassengersPerDay = route.getAveragePassengersPerDay();

        String query = "UPDATE main.route SET avg_passengers_per_day = ? WHERE id = ?";
        jdbcTemplate.update(query, avgPassengersPerDay, id);

        Optional<Route> updatedRoute = findById(id);
        if (updatedRoute.isPresent()) {
            return updatedRoute.get();
        } else {
            String msg = String.format("Route with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<Route> saveAll(Collection<? extends Route> routes) {
        return routes.stream()
                     .map(this::save)
                     .collect(Collectors.toList());
    }

    private class RouteRowMapper implements RowMapper<Route> {

        @Override
        public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            Integer avgPassengersPerDay = rs.getInt("avg_passengers_per_day");
            if (rs.wasNull()) {
                avgPassengersPerDay = null;
            }

            return new Route(id, avgPassengersPerDay);
        }
    }
}
