package rs.agroshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.agroshop.entity.Picture;
import rs.agroshop.entity.Machine;
import rs.agroshop.repo.PictureRepository;
import rs.agroshop.repo.MachineRepository;
import java.util.List;

@Service
public class PictureService {
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private MachineRepository machineRepository;

// -------------------------------------------------------------------------- //    
// ------------------------- Read operations -------------------------------- //    

    public List<Picture> findAll(){return pictureRepository.findAll();}
    public Picture findById(Integer id){return pictureRepository.findById(id).orElseThrow(() -> new RuntimeException("Picture with ID " + id + " doesn't exist."));}

// -------------------------------------------------------------------------- //
// ------------------------ Create operations ------------------------------- //
    
    public Picture createPicture(Picture picture) {

        if(picture.getPath() == null || picture.getPath().isBlank()){throw new RuntimeException("Picture path is required.");}
        if(picture.getMachine() == null || picture.getMachine().getMachineId() == null){throw new RuntimeException("Machine is required.");}
        Machine machine = machineRepository.findById(picture.getMachine().getMachineId())
                .orElseThrow(() -> new RuntimeException("Machine with ID " + picture.getMachine().getMachineId() + " doesn't exist."));

        picture.setMachine(machine);
        return pictureRepository.save(picture);
    }

// -------------------------------------------------------------------------- //
// ------------------------ Update operations ------------------------------- //

    public Picture updatePicture(Integer id, Picture pictureDetails) {
        Picture picture = findById(id);
        
        if(pictureDetails.getPath() != null && !pictureDetails.getPath().isBlank()){picture.setPath(pictureDetails.getPath());}
        
        if(pictureDetails.getMachine() != null && pictureDetails.getMachine().getMachineId() != null) {
            Machine machine = machineRepository.findById(pictureDetails.getMachine().getMachineId())
                    .orElseThrow(() -> new RuntimeException("Machine with ID " + pictureDetails.getMachine().getMachineId() + " doesn't exist."));
                    
            picture.setMachine(machine);
        }
        
        return pictureRepository.save(picture);
    }

// -------------------------------------------------------------------------- //
// ------------------------ Delete operations ------------------------------- //

        public void deletePicture(Integer id) {
        Picture picture = findById(id);
        pictureRepository.delete(picture);
    }

// -------------------------------------------------------------------------- //
}
