package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Specification;
import rs.agroshop.service.SpecificationService;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/specifications")
@CrossOrigin(origins = "*")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

// --------------------------------------------------------------------------------------- //    
// --------------------------- Get methods for read operations --------------------------- //

    @GetMapping
    public ResponseEntity<List<Specification>> getAll() {
        try {
            List<Specification> specifications = specificationService.findAll();
            return ResponseEntity.ok(specifications);
        }
        
        catch (Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specification> getById(@PathVariable Integer id) {
        try {
            Specification specification = specificationService.findById(id);
            return ResponseEntity.ok(specification);
        }
        
        catch (RuntimeException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Specification>> getByMachineId(@PathVariable Integer machineId) {
        try {
            List<Specification> specifications = specificationService.findByMachineId(machineId);
            return ResponseEntity.ok(specifications);
        }
        
        catch (Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

// --------------------------------------------------------------------------------------- //    
// --------------------------- Post methods for create operations ------------------------ //

    @PostMapping
    public ResponseEntity<?> createSpecification(@RequestBody Specification specification) {
        try {
            Specification createdSpecification = specificationService.createSpecification(specification);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecification);
        }
        
        catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during creating specification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// --------------------------- Put methods for update operations ------------------------- //

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSpecification(@PathVariable Integer id, @RequestBody Specification specificationDetails) {
        try {
            Specification updatedSpecification = specificationService.updateSpecification(id, specificationDetails);
            return ResponseEntity.ok(updatedSpecification);
        }
        
        catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during updating specification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// ------------------------------------ Delete operations -------------------------------- //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpecification(@PathVariable Integer id) {
        try {
            specificationService.deleteSpecification(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Specification with ID " + id + " has deleted successfully.");
            return ResponseEntity.ok(response);
        }
        
        catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during deleting specification: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //
}
