package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Town;
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
public class TownRepoImpl implements TownRepo {

    private JdbcTemplate jdbcTemplate;

    public TownRepoImpl(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("DataSource cannot be null");
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.town", Long.class);
    }

    @Override
    public void delete(Town town) {
        deleteById(town.getId());
    }

    @Override
    public void deleteAll(Collection<? extends Town> towns) {
        List<Object[]> args = towns.stream()
                                   .map( t -> new Object[] {t.getId()} )
                                   .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.town WHERE id = ?", args);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.town WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.town WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<Town> findAll() {
        String query = "SELECT * FROM main.town";
        return jdbcTemplate.query(query, new TownRowMapper());
    }

    @Override
    public Collection<Town> findAllById(Collection<Long> ids) {
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
    public Optional<Town> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.town WHERE id = ?";
        return jdbcTemplate.query(query, new TownRowMapper(), id).stream().findFirst();
    }

    @Override
    public Town save(Town town) {
        if (town.getId() == null) {
            return saveNewTown(town);
        } else {
            return updateExistingTown(town);
        }
    }

    private Town saveNewTown(Town town) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", town.getId());
        parameters.put("name", town.getName());
        parameters.put("country_code", town.getCountryCode());
        parameters.put("region", town.getRegion());
        parameters.put("latitude", town.getLatitude());
        parameters.put("longitude", town.getLongitude());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("town")
                .usingGeneratedKeyColumns("id");

        Long savedTownId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedTownId).get();
    }

    private Town updateExistingTown(Town town) {
        Long id = town.getId();
        String name = town.getName();
        String countryCode = town.getCountryCode();
        String region = town.getRegion();
        Double latitude = town.getLatitude();
        Double longitude = town.getLongitude();

        String query = "UPDATE main.town SET name = ?, country_code = ?, " +
                "region = ?, latitude = ?, longitude = ? WHERE id = ?";
        jdbcTemplate.update(query, name, countryCode, region, latitude, longitude, id);

        Optional<Town> updatedTown = findById(id);
        if (updatedTown.isPresent()) {
            return updatedTown.get();
        } else {
            String msg = String.format("Town with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<Town> saveAll(Collection<? extends Town> towns) {
        return towns.stream()
                    .map(this::save)
                    .collect(Collectors.toList());
    }

    private class TownRowMapper implements RowMapper<Town> {

        @Override
        public Town mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            String countryCode = rs.getString("country_code");
            String region = rs.getString("region");
            Double latitude = rs.getDouble("latitude");
            Double longitude = rs.getDouble("longitude");

            return new Town(id, name, countryCode, region, latitude, longitude);
        }
    }
}