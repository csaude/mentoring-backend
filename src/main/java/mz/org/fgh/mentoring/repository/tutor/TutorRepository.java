package mz.org.fgh.mentoring.repository.tutor;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.repository.CrudRepository;
import mz.org.fgh.mentoring.entity.employee.Employee;
import mz.org.fgh.mentoring.entity.tutor.Tutor;
import mz.org.fgh.mentoring.entity.user.User;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;



public interface TutorRepository extends CrudRepository<Tutor, Long> {

    @Override
    List<Tutor> findAll();


    @Override
    Optional<Tutor> findById(@NotNull Long id);

    Optional<Tutor> findByUuid(String uuid);
/*
    Tutor findByUser(User user);*/

    @Query(value = "select * from tutors limit :lim offset :of ", nativeQuery = true)
    List<Tutor> findTutorWithLimit(long lim, long of);
    
    List<Tutor> search(@Nullable String name,@Nullable String nuit, User user,@Nullable String phoneNumber);

    Tutor findByEmployee(Employee employee);

}
