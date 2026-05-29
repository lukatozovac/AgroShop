package rs.agroshop.repo;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.agroshop.entity.Category;
import rs.agroshop.model.CategoryType;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
    List<Category> findByCategoryType(CategoryType categoryType);
}
