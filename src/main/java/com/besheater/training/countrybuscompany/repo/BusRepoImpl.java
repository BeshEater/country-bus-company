package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Bus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
@Slf4j
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