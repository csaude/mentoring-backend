package mz.org.fgh.mentoring.repository.programaticarea;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import mz.org.fgh.mentoring.entity.program.Program;
import mz.org.fgh.mentoring.entity.programaticarea.ProgrammaticArea;
import mz.org.fgh.mentoring.util.LifeCycleStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramaticAreaRepository extends JpaRepository<ProgrammaticArea, Long> {

    Page<ProgrammaticArea> findByNameIlike(String name, Pageable pageable);

    @Query("select p from ProgrammaticArea p where p.code like concat(concat('%', :code) ,'%')  and p.name like concat(concat('%', :name),'%') and p.lifeCycleStatus =:lifeCycleStatus")
    List<ProgrammaticArea> findBySelectedFilter(final String code, final String name, final LifeCycleStatus lifeCycleStatus);

//    @Query("select p from ProgramaticArea p inner join fetch p.tutorProgrammaticAreas tpa inner join fetch tpa.tutor t where t.uuid =:tutorUuid")
//    List<ProgrammaticArea> findProgrammaticAreaByTutorProgrammaticAreaUuid(final String tutorUuid);

    @Query(value = "select * from programmatic_areas limit :lim offset :of ", nativeQuery = true)
    List<ProgrammaticArea> findProgrammaticAreaWithLimit(long lim, long of);

    @Query("select pa from ProgrammaticArea pa inner join fetch pa.program p where p.id =:programId")
    List<ProgrammaticArea> findProgrammaticAreasByProgramId(final Long programId);

    @Query(value = "select DISTINCT(p) from ProgrammaticArea p " +
            "inner join fetch p.program where p.lifeCycleStatus = :lifeCycleStatus",
            countQuery = "select count(DISTINCT p) from ProgrammaticArea p " +
                    "where p.lifeCycleStatus = :lifeCycleStatus")
    Page<ProgrammaticArea> fetchAll(LifeCycleStatus lifeCycleStatus, Pageable pageable);


    @Query("SELECT p FROM ProgrammaticArea p JOIN FETCH p.program WHERE p.id = :id")
    ProgrammaticArea getById(Long id);

    long countByProgram(Program program);

    Optional<ProgrammaticArea> findByUuid(String uuid);
}
