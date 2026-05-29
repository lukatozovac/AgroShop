package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer>{
    
}
