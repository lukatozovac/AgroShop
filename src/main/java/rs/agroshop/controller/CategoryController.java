package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Category;
import rs.agroshop.model.CategoryType;
import rs.agroshop.service.CategoryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

// --------------------------------------------------------------------------------------- //    
// ------------------------------ Get methods for read operations ------------------------ //
    
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        try{
            List<Category> categories = categoryService.findAll();
            return ResponseEntity.ok(categories);
        }

        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Category>> getByType(@PathVariable CategoryType type) {
        try{
            List<Category> categories = categoryService.findByType(type);
            return ResponseEntity.ok(categories);
        }

        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Integer id) {
        try{
            Category category = categoryService.findById(id);
            return ResponseEntity.ok(category);}
            catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

// -------------------------------------------------------------------------------------- //
// ------------------------- Post methods for create operations ------------------------- //

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try{
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        }

        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
         
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during creating categories: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// -------------------------------------------------------------------------------------- //
// ------------------------- Put methods for update operations -------------------------- //

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody Category categoryDetails) {
        try{
            Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        }

        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
            
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during updating category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// -------------------------------------------------------------------------------------- //
// ------------------------------ Delete operations ------------------------------------- //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try{
            categoryService.deleteCategory(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category with ID " + id + " has deleted successfully.");
            return ResponseEntity.ok(response);
        }

        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during deleting category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// -------------------------------------------------------------------------------------- //
}
