package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
}
