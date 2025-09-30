package mz.org.fgh.mentoring.repository.form;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.CrudRepository;
import mz.org.fgh.mentoring.entity.form.Form;
import mz.org.fgh.mentoring.entity.program.Program;
import mz.org.fgh.mentoring.entity.programaticarea.ProgrammaticArea;
import mz.org.fgh.mentoring.util.LifeCycleStatus;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface FormRepository extends CrudRepository<Form, Long> {

    @Override
    List<Form> findAll();

    Page<Form> findAll(Pageable pageable);

    Optional<Form> findById(@NotNull Long id);

    @Query(value = "SELECT DISTINCT f FROM Form f LEFT JOIN FETCH f.formSections fs",
           countQuery = "SELECT COUNT(f) FROM Form f")
    Page<Form> findAllWithFormSections(Pageable pageable);

    Optional<Form> findByUuid(@NotNull String uuid);

    Form findByCode(String code);

    @Query("select f FROM Answer a INNER JOIN a.form f INNER JOIN a.question q INNER JOIN FETCH f.programmaticArea WHERE q.uuid IN (:questionUuids) AND f.lifeCycleStatus = :lifeCycleStatus")
    List<Form> findSampleIndicatorForms(List<String> questionUuids ,LifeCycleStatus lifeCycleStatus);

    @Query("select f from Form f " +
            "INNER JOIN FETCH f.programmaticArea pa " +
            "INNER JOIN FETCH pa.program p " +
            "INNER JOIN FETCH f.partner pt " +
            "where f.code like concat(concat('%',:code) ,'%') and f.name like concat(concat('%',:name),'%') " +
            "and pa.code like concat(concat('%',:programmaticAreaCode) ,'%') and p.uuid like concat(concat('%',:program) ,'%') ")
    List<Form> findBySelectedFilter(final String code, final String name, final String programmaticAreaCode, String program);

    @Query("select f FROM Form f INNER JOIN FETCH f.programmaticArea pa where pa.uuid = :programmaticAreaUuid")
    List<Form> findFormByProgrammaticAreaUuid(final String programmaticAreaUuid);

    @Query("select f FROM Form f INNER JOIN FETCH f.programmaticArea pa  where pa.id = :programmaticAreaId")
    List<Form> findFormByProgrammaticAreaId(final Long programmaticAreaId);

    @Query(value = "select * from forms limit :lim offset :of ", nativeQuery = true)
    List<Form> findFormWithLimit(long lim, long of);

    @Query(value = "SELECT DISTINCT f FROM Form f " +
            "INNER JOIN f.programmaticArea pa " +
            "INNER JOIN pa.program p " +
            "LEFT JOIN f.formSections fs " +
            "WHERE (:code IS NULL OR f.code LIKE CONCAT('%', :code, '%')) " +
            "AND (:name IS NULL OR f.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:program IS NULL OR p.name LIKE CONCAT('%', :program, '%')) " +
            "AND (:programmaticArea IS NULL OR pa.name LIKE CONCAT('%', :programmaticArea, '%')) " +
            "ORDER BY fs.sequence ASC",
            countQuery = "SELECT COUNT(DISTINCT f) FROM Form f " +
                    "INNER JOIN f.programmaticArea pa " +
                    "INNER JOIN pa.program p " +
                    "LEFT JOIN f.formSections fs " +
                    "WHERE (:code IS NULL OR f.code LIKE CONCAT('%', :code, '%')) " +
                    "AND (:name IS NULL OR f.name LIKE CONCAT('%', :name, '%')) " +
                    "AND (:program IS NULL OR p.name LIKE CONCAT('%', :program, '%')) " +
                    "AND (:programmaticArea IS NULL OR pa.name LIKE CONCAT('%', :programmaticArea, '%'))")
    Page<Form> search(
            @Nullable String code,
            @Nullable String name,
            @Nullable String program,
            @Nullable String programmaticArea,
            Pageable pageable);




    @Query("select f from Form f " +
            "INNER JOIN FETCH f.programmaticArea pa " +
            "INNER JOIN FETCH pa.program p " +
            "INNER JOIN FETCH f.partner pt " +
            "where pt.id = :partnerId ")
    List<Form> fetchByPartnerId(final Long partnerId);

    @Query("SELECT f FROM Form f " +
            "WHERE f.programmaticArea.program = :program " +
            "ORDER BY f.createdAt DESC")
    Optional<Form> findTopByProgramOrderByCreatedAtDesc(Program program);

    @Query("SELECT f FROM Form f " +
            "INNER JOIN FETCH f.programmaticArea pa " +
            "INNER JOIN pa.tutorProgrammaticAreas tpa " +
            "INNER JOIN tpa.tutor t " +
            "WHERE t.uuid IN (:tutorUuids)")
    List<Form> findFormsByTutorUuids(List<String> tutorUuids);

    @Query("SELECT f FROM Form f " +
            "INNER JOIN FETCH f.programmaticArea pa " +
            "INNER JOIN pa.tutorProgrammaticAreas tpa " +
            "INNER JOIN tpa.tutor t " +
            "WHERE t.id = :tutorId")
    List<Form> findFormsByTutorId(Long tutorId);


    long countByProgrammaticArea(ProgrammaticArea area);
}
