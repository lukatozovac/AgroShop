package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Machine;
import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer>{
    
    List<Machine> findByCategory_CategoryId(Integer categoryId);
    List<Machine> findByCategory_CategoryName(String categoryName);

    Machine findByName(String name);
}
