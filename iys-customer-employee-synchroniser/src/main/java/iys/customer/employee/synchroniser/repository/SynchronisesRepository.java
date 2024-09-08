package iys.customer.employee.synchroniser.repository;

import iys.customer.employee.synchroniser.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SynchronisesRepository extends JpaRepository<String, Space> {

}
