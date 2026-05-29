package rs.agroshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.agroshop.entity.Category;
import rs.agroshop.model.CategoryType;
import rs.agroshop.repo.CategoryRepository;
import java.util.List;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findByType(CategoryType type) {
    return categoryRepository.findByCategoryType(type);}
}
