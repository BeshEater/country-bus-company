package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Route;
import com.besheater.training.countrybuscompany.entity.RoutePart;
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
public class RoutePartRepoImpl implements RoutePartRepo {

    private JdbcTemplate jdbcTemplate;
    private RouteRepo routeRepo;

    public RoutePartRepoImpl(DataSource dataSource, RouteRepo routeRepo) {
        if (dataSource == null) {
            throw new NullPointerException("DataSource cannot be null");
        }
        if (routeRepo == null) {
            throw new NullPointerException("RouteRepo cannot be null");
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.routeRepo = routeRepo;
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.route_part", Long.class);
    }

    @Override
    public void delete(RoutePart routePart) {
        deleteById(routePart.getId());
    }

    @Override
    public void deleteAll(Collection<? extends RoutePart> routeParts) {
        List<Object[]> args = routeParts.stream()
                                        .map( r -> new Object[] {r.getId()} )
                                        .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.route_part WHERE id = ?", args);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.route_part WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.route_part WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<RoutePart> findAll() {
        String query = "SELECT * FROM main.route_part";
        return jdbcTemplate.query(query, new RoutePartRowMapper());
    }

    @Override
    public Collection<RoutePart> findAllById(Collection<Long> ids) {
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
    public Optional<RoutePart> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.route_part WHERE id = ?";
        return jdbcTemplate.query(query, new RoutePartRowMapper(), id).stream().findFirst();
    }

    @Override
    public RoutePart save(RoutePart routePart) {
        if (routePart.getId() == null) {
            return saveNewRoutePart(routePart);
        } else {
            return updateExistingRoutePart(routePart);
        }
    }

    private RoutePart saveNewRoutePart(RoutePart routePart) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", routePart.getId());
        parameters.put("route_id", routePart.getRoute().getId());
        parameters.put("position", routePart.getPosition());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("route_part")
                .usingGeneratedKeyColumns("id");

        Long savedRoutePartId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedRoutePartId).get();
    }

    private RoutePart updateExistingRoutePart(RoutePart routePart) {
        Long id = routePart.getId();
        Long routeId = routePart.getRoute().getId();
        Integer position = routePart.getPosition();

        String query = "UPDATE main.route_part SET route_id = ?, position = ? WHERE id = ?";
        jdbcTemplate.update(query, routeId, position, id);

        Optional<RoutePart> updatedRoutePart = findById(id);
        if (updatedRoutePart.isPresent()) {
            return updatedRoutePart.get();
        } else {
            String msg = String.format("RoutePart with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<RoutePart> saveAll(Collection<? extends RoutePart> routeParts) {
        return routeParts.stream()
                         .map(this::save)
                         .collect(Collectors.toList());
    }

    private class RoutePartRowMapper implements RowMapper<RoutePart> {

        @Override
        public RoutePart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            Route route = routeRepo.findById(rs.getLong("route_id")).get();
            Integer position = rs.getInt("position");

            return new RoutePart(id, route, position);
        }
    }
}