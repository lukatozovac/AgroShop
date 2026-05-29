package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Picture;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer>{
    
}
