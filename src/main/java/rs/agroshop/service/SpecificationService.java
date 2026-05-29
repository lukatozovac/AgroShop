package rs.agroshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.agroshop.entity.Specification;
import rs.agroshop.repo.SpecificationRepository;
import java.util.List;

@Service
public class SpecificationService {

 @Autowired
    private SpecificationRepository specificationRepository;

    public List<Specification> findByMachineId(Integer machineId) {
        return specificationRepository.findByMachine_MachineId(machineId);
    }
}
