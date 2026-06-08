package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Manufacturer;
import rs.agroshop.service.ManufacturerService;
import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@CrossOrigin(origins = "*")
public class ManufacturerController {
    
    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping
    public List<Manufacturer> getAll(){
        return manufacturerService.findAll();
    }
}
