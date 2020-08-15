package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Bus;
import com.besheater.training.countrybuscompany.entity.Route;

import java.util.Collection;

public interface BusRepo extends CrudRepo<Bus> {

    Collection<Bus> getBussesOnRoute(Route route);
}