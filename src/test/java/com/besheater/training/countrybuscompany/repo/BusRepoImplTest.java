package com.besheater.training.countrybuscompany.repo;

import com.besheater.training.countrybuscompany.entity.Bus;
import com.besheater.training.countrybuscompany.entity.Route;
import org.junit.jupiter.api.BeforeEach;

class BusRepoImplTest extends CrudeRepoTest<Bus>{

    public BusRepoImplTest() {

        entityCount = 11L;

        existingEntity1 = new Bus(1L, new Route(1L, 125), "KZ726GK01", 65, false);
        existingEntity2 = new Bus(2L, new Route(2L, 271), "KZ146GK01", 120, true);
        existingEntity3 = new Bus(5L, null, "KZ560AK10", 75, false);

        updatedExistingEntity1 = new Bus(1L, new Route(5L, 513), "KK000PPP", 60, false);
        updatedExistingEntity2 = new Bus(2L, null, "CC111PPP", 120, true);
        updatedExistingEntity3 = new Bus(5L, new Route(3L, 189), "KZ560AK10", 90, true);

        nonExistingEntity1 = new Bus(12L, new Route(1L, 125), "TK4481", 50, false);
        nonExistingEntity2 = new Bus(13L, null, "TT111PPP", 65, false);
        nonExistingEntity3 = new Bus(17L, new Route(3L, 189), "KZ146GK01", 120, true);

        newEntity1 = new Bus(null, new Route(4L, null), "WW000TT", 60, false);
        newEntity2 = new Bus(null, null, "CC111YYY", 110, true);
        newEntity3 = new Bus(null, new Route(3L, 189), "TT148KPU", 65, false);

        init();
    }

    @BeforeEach
    public void initEntityRepo() {
        entityRepo = new BusRepoImpl(database, new RouteRepoImpl(database));
    }
}