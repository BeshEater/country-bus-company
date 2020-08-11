package com.besheater.training.countrybuscompany.repo;

import java.util.Collection;
import java.util.Optional;

public interface CrudeRepo<T> {

    Long count();
    void delete(T entity);
    void deleteAll(Collection<? extends T> entities);
    void deleteById(Long id);
    boolean existsById(Long id);
    Collection<T> findAll();
    Collection<T> findAllById(Collection<Long> ids);
    Optional<T> findById(Long id);
    T save(T entity);
    Collection<T> saveAll(Collection<? extends T> entities);
}