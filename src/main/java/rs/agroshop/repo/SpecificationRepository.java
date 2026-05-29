package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Specification;
import java.util.List;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Integer>{
    List<Specification> findByMachine_MachineId(Integer machineId);
}
