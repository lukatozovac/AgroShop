package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Category;
import rs.agroshop.model.CategoryType;
import rs.agroshop.service.CategoryService;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("/type/{type}")
    public List<Category> getByType(@PathVariable CategoryType type) {
    return categoryService.findByType(type);}
}
