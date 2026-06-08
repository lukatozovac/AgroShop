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

// ------------------------------------------------------------------------------- //    
// ---------------------------- Read operations ---------------------------------- //

    public List<Category> findAll() {
        return categoryRepository.findAll();}

        
    public Category findById(Integer id) {
        return categoryRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Category with ID " + id + " doesn't exist."));}

    public List<Category> findByType(CategoryType type) {
        return categoryRepository.findByCategoryType(type);}

// ------------------------------------------------------------------------------ //
// --------------------------- Create operations -------------------------------- //

        public Category createCategory(Category category) {
        // Validacija
        if (category.getCategoryName() == null || category.getCategoryName().isBlank()) {
            throw new RuntimeException("Category name is required."); }

        if (category.getDescription() == null || category.getDescription().isBlank()) {
            throw new RuntimeException("Description is required."); }

        if (category.getPicture() == null || category.getPicture().isBlank()) {
            throw new RuntimeException("Picture is required."); }

        if (category.getCategoryType() == null) {
            throw new RuntimeException("Category type is required.");}

         return categoryRepository.save(category);    
    }

// ------------------------------------------------------------------------------ //
// ----------------------------- Update operations ------------------------------ //
        public Category updateCategory(Integer id, Category categoryDetails) {
        Category category = findById(id);
        
        if (categoryDetails.getCategoryName() != null && !categoryDetails.getCategoryName().isBlank()) {
            category.setCategoryName(categoryDetails.getCategoryName()); }

        if (categoryDetails.getDescription() != null && !categoryDetails.getDescription().isBlank()) {
            category.setDescription(categoryDetails.getDescription());}

        if (categoryDetails.getPicture() != null && !categoryDetails.getPicture().isBlank()) {
            category.setPicture(categoryDetails.getPicture());}

        if (categoryDetails.getCategoryType() != null) {
            category.setCategoryType(categoryDetails.getCategoryType());}
        
        return categoryRepository.save(category);
    }


// ------------------------------------------------------------------------------ //
// ---------------------------- Delete operations ------------------------------- //
        
    public void deleteCategory(Integer id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }

//------------------------------------------------------------------------------- //
}
