package mz.org.fgh.mentoring.repository.programaticarea;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.repository.CrudRepository;
import mz.org.fgh.mentoring.entity.programaticarea.ProgrammaticArea;
import mz.org.fgh.mentoring.entity.tutor.Tutor;
import mz.org.fgh.mentoring.entity.tutorprogramaticarea.TutorProgrammaticArea;
import mz.org.fgh.mentoring.entity.user.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface TutorProgrammaticAreaRepository extends CrudRepository<TutorProgrammaticArea, Long> {

    TutorProgrammaticArea createOrUpdate(TutorProgrammaticArea tutorProgrammaticArea, User user, Tutor tutor);

    @Override
    List<TutorProgrammaticArea> findAll();

    Optional<TutorProgrammaticArea> findByUuid(@NotNull String uuid);

    @Query("SELECT tpa FROM TutorProgrammaticArea tpa JOIN FETCH tpa.tutor WHERE tpa.uuid = :uuid")
    Optional<TutorProgrammaticArea> findByUuidWithTutor(String uuid);


    @Query("select t from TutorProgrammaticArea t join fetch t.tutor join fetch t.programmaticArea where t.tutor.id = :tutorId")
    public List<TutorProgrammaticArea> getAllByTutorId(final Long tutorId);

    @Query(value = "select t from TutorProgrammaticArea t join fetch t.programmaticArea pa where pa.id = :programmaticAreaId ")
    List<TutorProgrammaticArea>findByProgrammaticAreaId(Long programmaticAreaId);

    @Query("SELECT tpa FROM TutorProgrammaticArea tpa " +
            "INNER JOIN FETCH tpa.tutor t " +
            "WHERE t.uuid IN (:tutorUuids)")
    List<TutorProgrammaticArea> findByTutorUuidIn(List<String> tutorUuids);

    long countByProgrammaticArea(ProgrammaticArea area);
}
