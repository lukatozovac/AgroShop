package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Picture;
import rs.agroshop.service.PictureService;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pictures")
@CrossOrigin(origins = "*")
public class PictureController {
    
    @Autowired
    private PictureService pictureService;

// --------------------------------------------------------------------------------------- //    
// --------------------------- Get methods for read operations --------------------------- //

    @GetMapping
    public ResponseEntity<List<Picture>> getAll() {
        try{
            List<Picture> pictures = pictureService.findAll();
            return ResponseEntity.ok(pictures);
        }
        
        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

    @GetMapping("/{id}")
    public ResponseEntity<Picture> getById(@PathVariable Integer id) {
        try {
            Picture picture = pictureService.findById(id);
            return ResponseEntity.ok(picture);
        }
        
        catch(RuntimeException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

// --------------------------------------------------------------------------------------- //    
// -------------------------- Post methods for create operations ------------------------- //

    @PostMapping
    public ResponseEntity<?> createPicture(@RequestBody Picture picture) {
        try {
            Picture createdPicture = pictureService.createPicture(picture);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPicture);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during adding a new picture: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// -------------------------- Put methods for update operations -------------------------- //

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePicture(@PathVariable Integer id, @RequestBody Picture pictureDetails) {
        try{
            Picture updatedPicture = pictureService.updatePicture(id, pictureDetails);
            return ResponseEntity.ok(updatedPicture);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during updating picture: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// ---------------------------------- Delete operations ---------------------------------- //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePicture(@PathVariable Integer id) {
        try{
            pictureService.deletePicture(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Picture with ID " + id + " has deleted successfully.");
            return ResponseEntity.ok(response);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during deleting picture: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //
}
