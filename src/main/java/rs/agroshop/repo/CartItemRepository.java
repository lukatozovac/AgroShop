package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    
}
