package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Garage;
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
public class GarageRepoImpl implements GarageRepo{

    private JdbcTemplate jdbcTemplate;
    private TownRepo townRepo;

    public GarageRepoImpl(DataSource dataSource, TownRepo townRepo) {
        if (dataSource == null) {
            throw new NullPointerException("DataSource cannot be null");
        }
        if (townRepo == null) {
            throw new NullPointerException("TownRepo cannot be null");
        }
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.townRepo = townRepo;
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM main.garage", Long.class);
    }

    @Override
    public void delete(Garage garage) {
        deleteById(garage.getId());
    }

    @Override
    public void deleteAll(Collection<? extends Garage> garages) {
        List<Object[]> args = garages.stream()
                                     .map( r -> new Object[] {r.getId()} )
                                     .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("DELETE FROM main.garage WHERE id = ?", args);

    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        jdbcTemplate.update("DELETE FROM main.garage WHERE id = ?", id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String sql = "SELECT count(*) FROM main.garage WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public Collection<Garage> findAll() {
        String query = "SELECT * FROM main.garage";
        return jdbcTemplate.query(query, new GarageRowMapper());
    }

    @Override
    public Collection<Garage> findAllById(Collection<Long> ids) {
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
    public Optional<Garage> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("id cannot be null");
        }
        String query = "SELECT * FROM main.garage WHERE id = ?";
        return jdbcTemplate.query(query, new GarageRowMapper(), id).stream().findFirst();
    }

    @Override
    public Garage save(Garage garage) {
        if (garage.getId() == null) {
            return saveNewGarage(garage);
        } else {
            return updateExistingGarage(garage);
        }
    }

    private Garage saveNewGarage(Garage garage) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", garage.getId());
        parameters.put("town_id", garage.getTown().getId());
        parameters.put("name", garage.getName());
        parameters.put("address", garage.getAddress());
        parameters.put("capacity", garage.getCapacity());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("main")
                .withTableName("garage")
                .usingGeneratedKeyColumns("id");

        Long savedGarageId = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return findById(savedGarageId).get();
    }

    private Garage updateExistingGarage(Garage garage) {
        Long id = garage.getId();
        Long townId = garage.getTown().getId();
        String name = garage.getName();
        String address = garage.getAddress();
        Integer capacity = garage.getCapacity();

        String query = "UPDATE main.garage SET town_id = ?, name = ?, " +
                "address = ?, capacity = ? WHERE id = ?";
        jdbcTemplate.update(query, townId, name, address, capacity, id);

        Optional<Garage> updatedGarage = findById(id);
        if (updatedGarage.isPresent()) {
            return updatedGarage.get();
        } else {
            String msg = String.format("Garage with id = %d is not exist", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Collection<Garage> saveAll(Collection<? extends Garage> garages) {
        return garages.stream()
                      .map(this::save)
                      .collect(Collectors.toList());
    }

    private class GarageRowMapper implements RowMapper<Garage> {

        @Override
        public Garage mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            Town town = townRepo.findById(rs.getLong("town_id")).get();
            String name = rs.getString("name");
            String address = rs.getString("address");
            Integer capacity = rs.getInt("capacity");

            return new Garage(id, town, name, address, capacity);
        }
    }
}