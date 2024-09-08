package iys.customer.employee.synchroniser.service;

import iys.customer.employee.synchroniser.entity.Space;
import iys.customer.employee.synchroniser.model.CustomerSpace;
import iys.customer.employee.synchroniser.model.SpaceCreated;
import iys.customer.employee.synchroniser.repository.SynchronisesRepository;

public class SynchronisesService {

    private final SynchronisesRepository repository;

    public SynchronisesService(SynchronisesRepository repository) {
        this.repository = repository;
    }

    public SpaceCreated createNewCustomerSpace() {
        return repository.save(new Space());
    }
}
