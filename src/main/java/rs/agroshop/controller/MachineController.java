package rs.agroshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import rs.agroshop.entity.Machine;
import rs.agroshop.service.MachineService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/machines")
@CrossOrigin(origins = "*")
public class MachineController {

    private final MachineService machineService;

// --------------------------------------------------------------------------------------- //    
// ------------------------------ Get methods for read operations ------------------------ //

    @GetMapping
    public ResponseEntity<List<Machine>> getMachines(
    @RequestParam(required = false) String categoryName,
    @RequestParam(required = false) String manufacturer)
    {
        try{
            List<Machine> machines;
            if (categoryName != null) {machines = machineService.findByCategoryName(categoryName);}
            else if (manufacturer != null) {machines = machineService.findByManufacturerName(manufacturer);}
            else {machines = machineService.findAll();}
            return ResponseEntity.ok(machines);
        }

        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }


    @GetMapping("/{id}")
    public ResponseEntity<Machine> getOne(@PathVariable Integer id) {
        try {
            Machine machine = machineService.findById(id);
            return ResponseEntity.ok(machine);
        }

        catch(RuntimeException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);}
        
        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        try {
            Machine machine = machineService.findMachineByName(name);
            return ResponseEntity.ok(machine);
        }

        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Machine>> search(@RequestParam String name) {
        try {
            List<Machine> machines = machineService.findByNameContainingIgnoreCase(name);
            return ResponseEntity.ok(machines);
        }

        catch(Exception e){return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}
    }

// --------------------------------------------------------------------------------------- //    
// ------------------------------ Post methods for create operations --------------------- //

    @PostMapping
    public ResponseEntity<?> createMachine(@RequestBody Machine machine) {
        try{
            Machine createdMachine = machineService.createMachine(machine);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMachine);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during creating a new machine: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //    
// ------------------------------ Put methods for update operations ---------------------- //

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMachine(@PathVariable Integer id, @RequestBody Machine machineDetails) {
        try{
            Machine updatedMachine = machineService.updateMachine(id, machineDetails);
            return ResponseEntity.ok(updatedMachine);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during updating machine: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //
// -------------------------------- Delete operations ------------------------------------ //

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMachine(@PathVariable Integer id) {
        try{
            machineService.deleteMachine(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Machine with ID " + id + " has deleted successfully.");
            return ResponseEntity.ok(response);
        }
        
        catch(RuntimeException e){
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        
        catch(Exception e){
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error during deleting machine: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

// --------------------------------------------------------------------------------------- //
}
