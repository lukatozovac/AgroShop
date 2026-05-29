package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
    
}
