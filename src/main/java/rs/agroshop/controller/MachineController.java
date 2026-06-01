package rs.agroshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.agroshop.entity.Machine;
import rs.agroshop.service.MachineService;
import java.util.List;

@RestController
@RequestMapping("api/machines")
@CrossOrigin(origins = "*")
public class MachineController {

    @Autowired
    private MachineService machineService; // Preimenuj ovo iz categoryService u machineService zbog čitljivosti

    // 1. Vraća sve ili filtrirane mašine
    @GetMapping
    public List<Machine> getMachines(@RequestParam(required = false) String categoryName) {
        if (categoryName != null) {
            return machineService.findByCategoryName(categoryName);
        }
        return machineService.findAll();
    }

    // 2. Vraća detalje jedne mašine
    @GetMapping("/{id}")
    public Machine getOne(@PathVariable Integer id) {
        return machineService.findById(id);

    }

    @GetMapping(value = "/name/{name}")
    public Machine getByName(@PathVariable String name) {
    return machineService.findMachineByName(name);
    }
}
