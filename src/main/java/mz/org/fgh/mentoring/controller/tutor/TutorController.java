package mz.org.fgh.mentoring.controller.tutor;

import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import mz.org.fgh.mentoring.api.RestAPIResponse;
import mz.org.fgh.mentoring.base.BaseController;
import mz.org.fgh.mentoring.entity.career.CareerType;
import mz.org.fgh.mentoring.entity.tutor.Tutor;
import mz.org.fgh.mentoring.entity.tutor.TutorLocation;
import mz.org.fgh.mentoring.service.tutor.TutorLocationService;
import mz.org.fgh.mentoring.service.tutor.TutorService;
import mz.org.fgh.mentoring.api.RESTAPIMapping;
import mz.org.fgh.mentoring.util.LifeCycleStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static mz.org.fgh.mentoring.api.RESTAPIMapping.API_VERSION;

@Controller(RESTAPIMapping.TUTOR_CONTROLLER)
public class TutorController extends BaseController {

    @Inject
    private TutorService tutorService;
    @Inject
    private TutorLocationService tutorLocationService;

    public TutorController() {
    }

    public static final Logger LOG = LoggerFactory.getLogger(TutorController.class);

    @Operation(summary = "Return a list off all Tutor")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @Tag(name = "Tutor")
    @Version(API_VERSION)
    @Get
    public List<Tutor> getAll() {
        LOG.debug("Searching tutors version 2");
        return tutorService.findAll();
    }

    @Post(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    public Tutor create (@Body Tutor tutor) {

       Tutor tutorResult = this.tutorService.create(tutor);
        LOG.debug("Created tutor {}", tutorResult);

        return tutorResult;
    }

    @Put(
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON
    )
    public Tutor update(@Body Tutor tutor){

        Tutor tutorResult = this.tutorService.update(tutor);
        LOG.debug("Update tutor {}", tutorResult);

        return tutorResult;
    }

    @Get("{uuid}")
    public Tutor fetchTutorByUuid(@PathVariable("uuid") String partnerUuid){
        LOG.debug(" fetchTutorByUuid uuid ");
        return this.tutorService.fetchTutorByUuid(partnerUuid);
    }

    @Get()
    public List<Tutor> findBySelectedFilter(final String code, final String name, final String surname, final String phoneNumber,
                                            final CareerType careerType){
        return this.tutorService.findBySelectedFilter(code, name, surname, phoneNumber, careerType);
    }

    @Get("/tutor-partner")
    public List<Tutor> findBySelectedFilter(final String code, final String name, final String surname, final String phoneNumber,
                                            final CareerType careerType, String partnerUuid){

        return this.tutorService.findBySelectedFilter(code, name, surname, phoneNumber, careerType,partnerUuid );
    }

    @Post("v2/tutor-locations")
    public List<TutorLocation> allocateTutorLocations(final TutorBeanResource tutorBeanResource){
       return this.tutorLocationService.allocateTutorLocations(tutorBeanResource.getTutor() , tutorBeanResource.getLocations());
    }

}
