package com.besheater.training.countrybuscompany.repo;

import java.util.Optional;

public interface CrudeRepo<T> {

    Long count();
    void delete(T entity);
    void deleteAll(Iterable<? extends T> entities);
    void deleteById(Long id);
    boolean existsById(Long id);
    Iterable<T> findAll();
    Iterable<T> findAllById(Iterable<Long> ids);
    Optional<T> findById(Long id);
    <S extends T> S save(S entity);
}