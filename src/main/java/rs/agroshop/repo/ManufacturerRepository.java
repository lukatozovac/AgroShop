package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Manufacturer;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer>{
    
}
