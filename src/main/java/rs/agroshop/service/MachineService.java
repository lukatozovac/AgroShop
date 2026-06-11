package rs.agroshop.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.agroshop.entity.Category;
import rs.agroshop.entity.Machine;
import rs.agroshop.entity.Manufacturer;
import rs.agroshop.repo.CategoryRepository;
import rs.agroshop.repo.MachineRepository;
import rs.agroshop.repo.ManufacturerRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService {
    
    private final MachineRepository machineRepository;

    private final CategoryRepository categoryRepository;

    private final ManufacturerRepository manufacturerRepository;
    
// ----------------------------------------------------------------------------------- //
// ----------------------------- Read operations ------------------------------------- //

    public List<Machine> findAll(){List<Machine> machines = machineRepository.findAll();

    machines.forEach(m -> {
        if (m.getCategory() != null) m.getCategory().getCategoryName();
        if (m.getManufacturer() != null) m.getManufacturer().getName();
    });
    return machines;}

    public List<Machine> findByCategoryId(Integer categoryId){return machineRepository.findByCategory_CategoryId(categoryId);}
    public Machine findById(Integer id){return machineRepository.findById(id).orElse(null);}
    public List<Machine> findByCategoryName(String categoryName){return machineRepository.findByCategory_CategoryName(categoryName);}
    public Machine findMachineByName(String name){return machineRepository.findByName(name).orElseThrow(() -> new RuntimeException("Machine with this name doesn't exist."));}
    public List<Machine> findByManufacturerName(String manufacturerName){return machineRepository.findByManufacturer_Name(manufacturerName);}
    public List<Machine> findByNameContainingIgnoreCase(String name){return machineRepository.findByNameContainingIgnoreCase(name);}

// --------------------------------------------------------------------------------------- //
// ------------------------------- Create operations ------------------------------------- //

    public Machine createMachine(Machine machine){

        if(machine.getName() == null || machine.getName().isBlank()){throw new RuntimeException("Machine name is required.");}
        if(machine.getPrice() == null){throw new RuntimeException("Price is required.");} 
        if(machine.getCategory() == null){throw new RuntimeException("Category is required.");}    
        if(machine.getManufacturer() == null){throw new RuntimeException("Manufacturer is required.");}

        Category category = categoryRepository.findById(machine.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category with ID " + machine.getCategory().getCategoryId() + " doesn't exist."));

        Manufacturer manufacturer = manufacturerRepository.findById(machine.getManufacturer().getManufacturerId())
                .orElseThrow(() -> new RuntimeException("Manufacturer with ID" + machine.getManufacturer().getManufacturerId() + " doesn't exist."));
        
        machine.setCategory(category);
        machine.setManufacturer(manufacturer);

        return machineRepository.save(machine);
    }
// -----------------------------------------------------------------------------------------------//
// --------------------------------------- Update operations -------------------------------------//

    public Machine updateMachine(Integer id, Machine machineDetails){

        Machine machine = findById(id);

        if(machineDetails.getName() != null && !machineDetails.getName().isBlank()){machine.setName(machineDetails.getName());}
        if(machineDetails.getDescription() != null && !machineDetails.getDescription().isBlank()){machine.setDescription(machineDetails.getDescription());}
        if(machineDetails.getPrice() != null){machine.setPrice(machineDetails.getPrice());}
        if(machineDetails.getReleaseYear() != null){machine.setReleaseYear(machineDetails.getReleaseYear());}
        if(machineDetails.getIconPath() != null && !machineDetails.getIconPath().isBlank()){machine.setIconPath(machineDetails.getIconPath());}
        
        // If we changes category
        if(machineDetails.getCategory() != null && machineDetails.getCategory().getCategoryId() != null) {
            Category category = categoryRepository.findById(machineDetails.getCategory().getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category with ID " + machineDetails.getCategory().getCategoryId() + " doesn't exist."));
            machine.setCategory(category);
        }
        
        // If we change manufacturer
        if (machineDetails.getManufacturer() != null && machineDetails.getManufacturer().getManufacturerId() != null) {
            Manufacturer manufacturer = manufacturerRepository.findById(machineDetails.getManufacturer().getManufacturerId())
                    .orElseThrow(() -> new RuntimeException("Manufacturer with ID " + machineDetails.getManufacturer().getManufacturerId() + " doesn't exist."));
            machine.setManufacturer(manufacturer);
        }

        return machineRepository.save(machine);
    }

// ----------------------------------------------------------------------------------------------- //
// ---------------------------------- Delete operations ------------------------------------------ //

    public void deleteMachine(Integer id) {
        Machine machine = findById(id);
        machineRepository.delete(machine);
    }

// ----------------------------------------------------------------------------------------------- //
}
