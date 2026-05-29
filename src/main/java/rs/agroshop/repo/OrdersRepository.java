package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>{
    
}
