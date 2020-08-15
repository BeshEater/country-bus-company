package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.RoutePart;
import com.besheater.training.countrybuscompany.entity.Town;
import com.besheater.training.countrybuscompany.entity.Transit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class TransitRepoImpl implements TransitRepo{

    private JdbcTemplate jdbcTemplate;
    private RoutePartRepo routePartRepo;
    private TownRepo townRepo;

    public TransitRepoImpl(DataSource dataSource, RoutePartRepo routePartRepo, TownRepo townRepo) {
        if (dataSource == null) {
            throw new NullPointerException("DataSource cannot be null");
        }
        if (routePartRepo == null) {
            throw new NullPointerException("RoutePartRepo cannot be null");
        }
        if (townRepo == null) {
            throw new NullPointerException("TownRepo cannot be null");
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.routePartRepo = routePartRepo;
        this.townRepo = townRepo;
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.transit", Long.class);
    }

    @Override
    public void delete(Transit transit) {
        deleteById(transit.getId());
    }

    @Override
    public void deleteAll(Collection<? extends Transit> transits) {
        List<Object[]> args = transits.stream()
                                      .map( r -> new Object[] {r.getId()} )
                                      .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.transit WHERE id = ?", args);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.transit WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.transit WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<Transit> findAll() {
        String query = "SELECT * FROM main.transit";
        return jdbcTemplate.query(query, new TransitRowMapper());
    }

    @Override
    public Collection<Transit> findAllById(Collection<Long> ids) {
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
    public Optional<Transit> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.transit WHERE id = ?";
        return jdbcTemplate.query(query, new TransitRowMapper(), id).stream().findFirst();
    }

    @Override
    public Transit save(Transit transit) {
        if (transit.getId() == null) {
            return saveNewTransit(transit);
        } else {
            return updateExistingTransit(transit);
        }
    }

    private Transit saveNewTransit(Transit transit) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", transit.getId());
        parameters.put("route_part_id", transit.getRoutePart().getId());
        parameters.put("from_town_id", transit.getFromTown().getId());
        parameters.put("to_town_id", transit.getToTown().getId());
        parameters.put("position", transit.getPosition());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("transit")
                .usingGeneratedKeyColumns("id");

        Long savedTransitId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedTransitId).get();
    }

    private Transit updateExistingTransit(Transit transit) {
        Long id = transit.getId();
        Long routePartId = transit.getRoutePart().getId();
        Long fromTownId = transit.getFromTown().getId();
        Long toTownId = transit.getToTown().getId();
        Integer position = transit.getPosition();

        String query = "UPDATE main.transit SET route_part_id = ?, from_town_id = ?, " +
                "to_town_id = ?, position = ? WHERE id = ?";
        jdbcTemplate.update(query, routePartId, fromTownId, toTownId, position, id);

        Optional<Transit> updatedTransit = findById(id);
        if (updatedTransit.isPresent()) {
            return updatedTransit.get();
        } else {
            String msg = String.format("Transit with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<Transit> saveAll(Collection<? extends Transit> transits) {
        return transits.stream()
                       .map(this::save)
                       .collect(Collectors.toList());
    }

    private class TransitRowMapper implements RowMapper<Transit> {

        @Override
        public Transit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            RoutePart routePart = routePartRepo.findById(rs.getLong("route_part_id")).get();
            Town fromTown = townRepo.findById(rs.getLong("from_town_id")).get();
            Town toTown = townRepo.findById(rs.getLong("to_town_id")).get();
            Integer position = rs.getInt("position");

            return Transit.builder()
                    .id(id)
                    .routePart(routePart)
                    .fromTown(fromTown)
                    .toTown(toTown)
                    .position(position)
                    .build();
        }
    }
}