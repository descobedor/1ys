package iys.customer.employee.interactions.repository;

import iys.customer.employee.interactions.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {

    Page<Space> findByIsActiveIsTrue(Pageable pageable);
}
