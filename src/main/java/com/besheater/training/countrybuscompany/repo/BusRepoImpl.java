package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Bus;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
@Log4j2
public class BusRepoImpl implements BusRepo {

    private JdbcTemplate jdbcTemplate;

    public BusRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public void delete(Bus entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Bus> entities) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Iterable<Bus> findAll() {
        return null;
    }

    @Override
    public Iterable<Bus> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public Optional<Bus> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public <S extends Bus> S save(S entity) {
        return null;
    }
}