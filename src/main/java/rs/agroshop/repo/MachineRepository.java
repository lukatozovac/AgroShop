package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rs.agroshop.entity.Machine;
import java.util.List;
import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer>{
    
    List<Machine> findByCategory_CategoryId(Integer categoryId);
    List<Machine> findByCategory_CategoryName(String categoryName);

    @Query("SELECT m FROM Machine m LEFT JOIN FETCH m.specifications LEFT JOIN FETCH m.pictures WHERE m.name = :name")
    Optional<Machine> findByName(@Param("name") String name);
}
