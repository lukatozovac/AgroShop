package rs.agroshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.agroshop.entity.Machine;
import rs.agroshop.entity.Specification;
import rs.agroshop.repo.MachineRepository;
import rs.agroshop.repo.SpecificationRepository;
import java.util.List;

@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository specificationRepository;

    @Autowired
    private MachineRepository machineRepository;

// ------------------------------------------------------------------------------------- //
// --------------------------- Read operations ----------------------------------------- //
      
    public List<Specification> findAll() {
        return specificationRepository.findAll();}
    
    public Specification findById(Integer id) {
        return specificationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Specification with ID " + id + " doesn't exist."));}

    public List<Specification> findByMachineId(Integer machineId) {
        return specificationRepository.findByMachine_MachineId(machineId);}

// -------------------------------------------------------------------------------------- //
// ------------------------------ Create operations ------------------------------------- //
    
        public Specification createSpecification(Specification specification) {
        
        if (specification.getName() == null || specification.getName().isBlank()) {
            throw new RuntimeException("Specification name is required.");}

        if (specification.getValue() == null || specification.getValue().isBlank()) {
            throw new RuntimeException("Value is required.");
        }
        if (specification.getMachine() == null || specification.getMachine().getMachineId() == null) {
            throw new RuntimeException("Machine is required.");}
        
        Machine machine = machineRepository.findById(specification.getMachine().getMachineId())
            .orElseThrow(() -> new RuntimeException("Machine with ID " + specification.getMachine().getMachineId() + " doesn't exist."));
        
        specification.setMachine(machine);
        return specificationRepository.save(specification);
    }

// -------------------------------------------------------------------------------------- //
// --------------------------- Update operations ---------------------------------------- //

    public Specification updateSpecification(Integer id, Specification specificationDetails) {
        Specification specification = findById(id);
        
        if (specificationDetails.getName() != null && !specificationDetails.getName().isBlank()) {
            specification.setName(specificationDetails.getName());}

        if (specificationDetails.getValue() != null && !specificationDetails.getValue().isBlank()) {
            specification.setValue(specificationDetails.getValue());}
        
        if (specificationDetails.getMachine() != null && specificationDetails.getMachine().getMachineId() != null) {
            Machine machine = machineRepository.findById(specificationDetails.getMachine().getMachineId())
                    .orElseThrow(() -> new RuntimeException("Machine with ID " + specificationDetails.getMachine().getMachineId() + " doesn't exist."));
            specification.setMachine(machine);
        }
        
        return specificationRepository.save(specification);
    }

// -------------------------------------------------------------------------------------- //
// ------------------------------- Delete operations ------------------------------------ //

        
    public void deleteSpecification(Integer id) {
        Specification specification = findById(id);
        specificationRepository.delete(specification);
    }

// -------------------------------------------------------------------------------------- //
}
