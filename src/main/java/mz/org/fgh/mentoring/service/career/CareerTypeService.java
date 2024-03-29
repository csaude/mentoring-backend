package mz.org.fgh.mentoring.service.career;

import jakarta.inject.Singleton;
import mz.org.fgh.mentoring.dto.career.CareerTypeDTO;
import mz.org.fgh.mentoring.entity.career.CareerType;
import mz.org.fgh.mentoring.repository.career.CareerTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CareerTypeService {


    private final CareerTypeRepository careerTypeRepository;

    public CareerTypeService(CareerTypeRepository careerTypeRepository) {
        this.careerTypeRepository = careerTypeRepository;
    }

    public List<CareerTypeDTO> findAllCareerTypes(long limit, long offset){

        List<CareerType> careerTypes = new ArrayList<>();
        if(limit > 0){
           careerTypes = this.careerTypeRepository.findCareerTypeWithLimit(limit, offset);
        }else{
            careerTypes = this.careerTypeRepository.findAll();
        }

        List<CareerTypeDTO> careerTypeDTOS = new ArrayList<>(0);
        for (CareerType careerType: careerTypes) {
            CareerTypeDTO careerTypeDTO = new CareerTypeDTO(careerType);
            careerTypeDTOS.add(careerTypeDTO);
        }
        return careerTypeDTOS;
    }
}
