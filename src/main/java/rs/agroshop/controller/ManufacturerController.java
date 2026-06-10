package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Manufacturer;
import rs.agroshop.service.ManufacturerService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manufacturers")
@CrossOrigin(origins = "*")
public class ManufacturerController {
    
    @Autowired
    private ManufacturerService manufacturerService;

// --------------------------------------------------------------------------------------- //    
// ------------------------------ Get methods for read operations ------------------------ //
    
    @GetMapping
    public ResponseEntity<List<Manufacturer>> getAll() {
        try {
            List<Manufacturer> manufacturers = manufacturerService.findAll();
            return ResponseEntity.ok(manufacturers);
        }
        
        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getById(@PathVariable Integer id) {
        try{
            Manufacturer manufacturer = manufacturerService.findById(id);
            return ResponseEntity.ok(manufacturer);
        }

        catch(RuntimeException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

// --------------------------------------------------------------------------------------- //    
// --------------------------- Post methods for create operations ------------------------ //

    @PostMapping
    public ResponseEntity<?> createManufacturer(@RequestBody Manufacturer manufacturer) {
        try {
            Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdManufacturer);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during creating a new manufacturer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// ---------------------------- Put methods for update operations ------------------------ //

    @PutMapping("/{id}")
    public ResponseEntity<?> updateManufacturer(@PathVariable Integer id, @RequestBody Manufacturer manufacturerDetails) {
        try{
            Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(id, manufacturerDetails);
            return ResponseEntity.ok(updatedManufacturer);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during updating manufacturer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// --------------------------------- Delete operations ----------------------------------- //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable Integer id) {
        try{
            manufacturerService.deleteManufacturer(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Manufacturer with ID " + id + " has deleted successfully.");
            return ResponseEntity.ok(response);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during deleting manufacturer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //
}
