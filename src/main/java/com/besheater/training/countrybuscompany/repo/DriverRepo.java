package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Driver;
import com.besheater.training.countrybuscompany.entity.Route;

import java.util.Collection;

public interface DriverRepo extends CrudRepo<Driver> {

    Collection<Driver> getDriversOnRoute(Route route);
}