package rs.agroshop.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import rs.agroshop.entity.Manufacturer;
import rs.agroshop.repo.ManufacturerRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerService {
    
    private final ManufacturerRepository manufacturerRepository;

// ------------------------------- Read operations ------------------------------- //

    public List<Manufacturer> findAll(){return manufacturerRepository.findAll();}
    public Manufacturer findById(Integer id){return manufacturerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Manufacturer with ID " + id + " doesn't exist."));}

// -------------------------------------------------------------------------------- //                
// ---------------------------- Create operations --------------------------------- //

        public Manufacturer createManufacturer(Manufacturer manufacturer) {
    
        if(manufacturer.getName() == null || manufacturer.getName().isBlank()){throw new RuntimeException("Manufacturer is required.");}
        if(manufacturer.getMadeIn() == null || manufacturer.getMadeIn().isBlank()){throw new RuntimeException("Country is required.");}
        if(manufacturer.getLogo() == null || manufacturer.getLogo().isBlank()){throw new RuntimeException("Logo is required.");}
        return manufacturerRepository.save(manufacturer);
    }

// ------------------------------------------------------------------------------ //
// ------------------------------ Update operations ----------------------------- //

        public Manufacturer updateManufacturer(Integer id, Manufacturer manufacturerDetails){Manufacturer manufacturer = findById(id);
        
        if(manufacturerDetails.getName() != null && !manufacturerDetails.getName().isBlank()){manufacturer.setName(manufacturerDetails.getName());}
        if(manufacturerDetails.getMadeIn() != null && !manufacturerDetails.getMadeIn().isBlank()) {manufacturer.setMadeIn(manufacturerDetails.getMadeIn());}
        if(manufacturerDetails.getLogo() != null && !manufacturerDetails.getLogo().isBlank()){manufacturer.setLogo(manufacturerDetails.getLogo());}
        return manufacturerRepository.save(manufacturer);
    }

// ------------------------------------------------------------------------------ // 
// --------------------------- Delete operations -------------------------------- //

    public void deleteManufacturer(Integer id) {
        Manufacturer manufacturer = findById(id);
        manufacturerRepository.delete(manufacturer);
    }

// ------------------------------------------------------------------------------ //
}
