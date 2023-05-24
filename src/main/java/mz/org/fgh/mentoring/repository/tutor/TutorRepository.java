package mz.org.fgh.mentoring.repository.tutor;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import mz.org.fgh.mentoring.entity.career.CareerType;
import mz.org.fgh.mentoring.entity.tutor.Tutor;
import mz.org.fgh.mentoring.util.LifeCycleStatus;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


@Repository
public interface TutorRepository extends CrudRepository<Tutor, Long> {

    @Override
    List<Tutor> findAll();

    @Query("From Tutor t inner join fetch t.career c inner join fetch t.partner p " +
            "where t.uuid = :partnerUuid and t.lifeCycleStatus = :lifeCycleStatus")
    Tutor fetchByUuid(final String partnerUuid, final LifeCycleStatus lifeCycleStatus);

    @Query("From Tutor t inner join fetch t.career c inner join fetch t.partner p inner join fetch c.careerType ct " +
            "where t.code like concat(concat('%',:code),'%') and t.name like concat(concat('%',:name),'%') " +
            "and t.surname like concat(concat('%',:surname),'%') and ct = :careerType " +
            "and t.phoneNumber = :phoneNumber and t.lifeCycleStatus = :lifeCycleStatus")
    List<Tutor> findBySelectedFilter(final String code, final String name, final String surname,
                                     final CareerType careerType, final String phoneNumber, final LifeCycleStatus lifeCycleStatus);
    @Query("From Tutor t inner join fetch t.career c inner join fetch t.partner p inner join fetch c.careerType ct " +
            "where t.code like concat(concat('%',:code),'%') and t.name like concat(concat('%',:name),'%')" +
            "and t.surname like concat(concat('%',:surname),'%') and ct = :careerType " +
            "and t.phoneNumber = :phoneNumber and p.uuid = :partnerUuid and t.lifeCycleStatus = :lifeCycleStatus")
    List<Tutor> findBySelectedFilter(final String code, final String name, final String surname,
                                     final CareerType careerType, final String phoneNumber, final String partnerUuid, final LifeCycleStatus lifeCycleStatus);

}
