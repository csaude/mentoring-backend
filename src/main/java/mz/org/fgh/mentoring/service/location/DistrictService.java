package mz.org.fgh.mentoring.service.location;

import jakarta.inject.Singleton;
import mz.org.fgh.mentoring.entity.location.District;
import mz.org.fgh.mentoring.error.MentoringBusinessException;
import mz.org.fgh.mentoring.repository.district.DistrictRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton
public class DistrictService {

    private final DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository){
        this.districtRepository = districtRepository;
    }

    public District createDistrict(District district){
        return districtRepository.save(district);
    }

    public District findDistrictById(@NotNull Long id){
        Optional<District> optDistrict = districtRepository.findById(id);
        if(optDistrict.isEmpty()){
            throw new MentoringBusinessException("District with ID: "+id+" was not found.");
        }
        return optDistrict.get();
    }

    public List<District> findAllDistricts() {

        return districtRepository.findAll();
    }

    public District findDistrictByDesignation(final String designation){
        List<District>  districts = this.districtRepository.findByDescription(designation);
        if(districts.isEmpty()){
            throw new MentoringBusinessException("District : "+designation+" was not found.");
        }
        return districts.get(0);
    }
}
