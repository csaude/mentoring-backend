package mz.org.fgh.mentoring.service.tutor;

import jakarta.inject.Singleton;
import mz.org.fgh.mentoring.entity.career.CareerType;
import mz.org.fgh.mentoring.entity.tutor.Tutor;
import mz.org.fgh.mentoring.repository.tutor.TutorRepository;
import mz.org.fgh.mentoring.util.LifeCycleStatus;

import java.util.List;
import java.util.Optional;

@Singleton
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public Tutor create(Tutor tutor) {
        return this.tutorRepository.save(tutor);
    }

    public Tutor update(Tutor tutor){
        return this.tutorRepository.update(tutor);
    }

    public List<Tutor> findAll() {
        return this.tutorRepository.findAll();
    }
    public Optional<Tutor> findById(final Long id){
      return this.tutorRepository.findById(id);
    }

    public Tutor fetchTutorByUuid(final String partnerUuid){
       return this.tutorRepository.fetchByUuid(partnerUuid, LifeCycleStatus.ACTIVE);
    }

    public List<Tutor> findBySelectedFilter(final String code, final String name, final String surname, final String phoneNumber,
                                            final CareerType careerType){
        return this.tutorRepository.findBySelectedFilter(code, name, surname, careerType, phoneNumber, LifeCycleStatus.ACTIVE);
    }

    public List<Tutor> findBySelectedFilter(final String code, final String name, final String surname, final String phoneNumber,
                                            final CareerType careerType, final String partnerUuid){
      return this.tutorRepository.findBySelectedFilter(code, name, surname,careerType, phoneNumber,partnerUuid, LifeCycleStatus.ACTIVE);
    }
}
