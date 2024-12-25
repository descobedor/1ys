package iys.customer.employee.interactions.repository;

import iys.customer.employee.interactions.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    void deleteByCode(String code);

}
