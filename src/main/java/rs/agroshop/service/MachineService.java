package rs.agroshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.agroshop.entity.Machine;
import rs.agroshop.repo.MachineRepository;
import java.util.List;

@Service
public class MachineService {
    
    @Autowired
    private MachineRepository machineRepository;

    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    public List<Machine> findByCategoryId(Integer categoryId) {
        return machineRepository.findByCategory_CategoryId(categoryId);
    }
    
    public Machine findById(Integer id) {
        return machineRepository.findById(id).orElse(null);    
    }

    public List<Machine> findByCategoryName(String categoryName){
        return machineRepository.findByCategory_CategoryName(categoryName);
    }

    public Machine findMachineByName(String name) {
    return machineRepository.findByName(name)
            .orElseThrow(() -> new RuntimeException("Mašina sa tim imenom nije pronađena"));
    }
}
